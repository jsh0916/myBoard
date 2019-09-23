<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		
		<script src="/resources/myhomepage/vendor/jquery/jquery-3.4.1.min.js"></script>
	</head>
	<script>
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880; // 5MB
		
		function checkExtension(fileName, fileSize) {
			if (fileSize >= maxSize) {
				alert("첨부할 수 있는 파일용량을 초과하였습니다.");
				
				return false;
			}
			
			if (regex.test(fileName)) {
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				
				return false;
			}
			
			return true;
		}
		
		function showImage(fileCallPath) {
			$(".bigPictureWrapper").css("display", "flex").show();
			$(".bigPicture")
			.html("<img src='/showThumbnail.do?fileName=" + encodeURI(fileCallPath) + "'>")
			.animate({width: '100%', height: '100%'}, 1000);
		}
		
		$(document).ready(function() {
			var cloneObj = $(".uploadDiv").clone();
			
			$("#uploadBtn").on("click", function() {
				var formData = new FormData(); // form 태그와 같은 역할
				var inputFile = $("input[name='uploadFile']");
				var files = inputFile[0].files;
				
				console.log(files);
				
				// Add FileData to formData
				for (var i = 0; i < files.length; i++) {
					
					if (!checkExtension(files[i].name, files[i].size)) {
						return false;
					}
					
					formData.append("uploadFile", files[i]);
				}
				
				$.ajax({
					url 			:	"uploadAjaxAction.do",
					processData 	:	false,
					contentType		:	false,
					data			:	formData,
					type			:	"post",
					dataType		:	"json",
					success			:	function (result) {
						console.log(result);

						showUploadedFile(result);
						
						// input type 영역을 초기화 하기 위한 소스. input type='file' 이 readonly라 안쪽의 내용을 수정할 수 없으므로.
						$(".uploadDiv").html(cloneObj.html());
					},
					erro			:	function () {
						alert("AJAX ERROR");
					}
				});
			});
			
			var uploadResult = $(".uploadResult ul");
			
			function showUploadedFile(uploadResultArr) {
				var str = "";
				
				$(uploadResultArr).each(function(i, obj) {
					if (!obj.image) {
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
						
						str += "<li><div><a href='downloadFile.do?fileName="
							+ fileCallPath + "'><img src='/resources/img/attach.png'>"
							+ obj.fileName + "</a>" + "<span data-file=\'" + fileCallPath + "\' data-type='file'> x </span>" + "</div></li>";
					} else {
						//str += "<li>" + obj.fileName + "</li>";						
						
						// 브라우저에서 GET 방식으로 첨부파일의 이름을 사용할 때에는 파일 이름에 포함된 공백, 한글 이름을 주의
						// 이를 위해 encodeURIComponent()를 이용해 URI 호출에 적합한 문자열로 인코딩 처리
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
						var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
						originPath = originPath.replace(new RegExp(/\\/g),"/");
						
						str += "<li><a href=\"javascript:showImage(\'" + originPath + "\')\">" + 
							   "<img src='/showThumbnail.do?fileName=" + fileCallPath + "'></a>" +
							   "<span data-file=\'" + fileCallPath + "\' data-type='image'> x </span></li>"
					}
				});
				
				uploadResult.append(str);
			}
			
			$(".bigPictureWrapper").on("click", function() {
				$(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
				setTimeout(function() {
					$(".bigPictureWrapper").hide();
				}, 1000);
			});
			
			$(".uploadResult").on("click", "span", function() {
				var targetFile = $(this).data("file");
				var type = $(this).data("type");
				console.log(targetFile);
				
				$.ajax({
					url			:	"/deleteFile.do",
					data		:	{fileName : targetFile, type: type},
					dataType	:	"text",
					type		:	"post",
					success		:	function(result) {
						alert(result);
					}
				});
			});
		});
		
	</script>
	<style>
		.uploadResult {
			width: 100%;
			background-color: gray;
		}
		
		.uploadResult ul {
			display: flex;
			flex-flow: row;
			justify-content: center;
			align-items: center;
		}
		
		.uploadResult ul li {
			list-style: none;
			padding: 10px;
			aling-content: center;
			text-align: center;
		}
		
		.uploadResult ul li img {
			width: 100px;
		}
		
		.uploadResult ul li span {
			color: white;
		}
		
		.bigPictureWrapper {
			position: absolute;
			display: none;
			justify-content: center;
			align-items: center;
			top: 0%;
			width: 100%;
			height: 100%;
			background-color: gray;
			z-index: 100;
			background:rgba(255, 255, 255, 0.5);
		}
		
		.bigPicture {
			position: relative;
			display: flex;
			justify-content: cneter;
			align-items: center;
		}
		
		.bigPicture img {
			width: 600px;
		}
	</style>
	<body>
		<h1>Upload with Ajax</h1>
		
		<div class="uploadDiv">
			<input type="file" name="uploadFile" multiple>
		</div>
		
		<div class="uploadResult">
			<ul>
			</ul>
		</div>
		
		<button id="uploadBtn">Upload</button>
		
		<div class="bigPictureWrapper">
			<div class="bigPicture">
			</div>
		</div>
	</body>
</html>