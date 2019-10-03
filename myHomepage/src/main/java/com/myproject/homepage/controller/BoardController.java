package com.myproject.homepage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.vo.AttachFileVO;
import com.myproject.homepage.board.vo.BoardVO;
import com.myproject.homepage.board.vo.PageVO;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
/*
 * Client가 상세화면을 요청하면 getBoard() 메소드는 검색 결과인 BoardVO 객체를 board 라는 이름으로 Model에 저장함
 * @SessionAttributes('board') 설정으로 Model 에 'board' 라는 이름으로 저장되는 데이터가 있다면
 * 그 데이터를 세션(HttpSession)에도 자동으로 저장하라는 설정.
 * --> updateBoard 가 호출될 때, 스프링 컨테이너는 @ModelAttribute 설정을 해석하여
 * 	   세션에 board라는 이름으로 저장된 데이터가 있는지 확인.
 * 	   있으면 해당 객체를 vo 변수에 할당.
 * */
@SessionAttributes("board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	// 글 등록
	@RequestMapping(value="insertBoard.do", method=RequestMethod.GET)
	public String insertBoardView(HttpServletRequest request, Model model) {
		logger.info("=============== insertBoardView START ===============");
		
		String userName = request.getParameter("userName");
		
		logger.info("userName : " + userName);
		
		model.addAttribute("userName", userName);
		
		logger.info("=============== insertBoardView END ===============");
		return "insertBoard";
	}
	
	@RequestMapping(value="insertBoard.do", method=RequestMethod.POST)
	public String insertBoard(BoardVO vo, PageVO pd, HttpServletRequest request, RedirectAttributes rttr, Model model) throws IOException {		// 커맨드객체 사용
		logger.info("=============== insertBoard.do START ===============");
		
		// 파일 업로드 처리
		/*
		MultipartFile uploadFile = vo.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/upload/tmp/" + fileName));
			
			logger.info("FileName : " + fileName);
			logger.info("FileSize : " + uploadFile.getSize());
		}
		*/
		
		String uploadFolder = "C:\\upload";

		for (AttachFileVO list : vo.getAttachList()) {
			logger.info("=============== File Upload START ===============");
			
			logger.info("Upload File Name : " + list.getFileName());
			logger.info("Upload File UploadPath : " + list.getUploadPath());
			
			File saveFile = new File(uploadFolder, list.getFileName());
			
			try {
//				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
//		boardService.insertBoard(vo);
		pd = setPage(pd, request);
		getBoardListData(vo, pd, model);
		
		rttr.addAttribute("pageNum", pd.getPageNum());
		rttr.addAttribute("amount", pd.getAmount());
		
		if (vo.getAttachList() != null) {
			vo.getAttachList().forEach(attach -> logger.info("attach : " + attach));
		}
		
		logger.info("=============== insertBoard.do END ===============");
		return "redirect:index.do";
	}
	
	// 글 수정
	@RequestMapping(value="/updateBoard.do", method=RequestMethod.GET)
	public String updateBoardView(@ModelAttribute("board") BoardVO vo, PageVO pd, HttpServletRequest request, Model model) {
		logger.info("=============== updateBoardView START ===============");

		pd = setPage(pd, request);
		
		model.addAttribute("pageMaker", pd);
		model.addAttribute("board", vo);
		
		logger.info("=============== updateBoardView END ===============");
		return "updateBoard";
	}
	
	// 글 수정
	@RequestMapping(value="/updateBoard.do", method=RequestMethod.POST)
	public String updateBoard(@ModelAttribute("board") BoardVO vo, PageVO pd, HttpServletRequest request, RedirectAttributes rttr, Model model) {
		logger.info("=============== updateBoard.do START ===============");
		
		boardService.updateBoard(vo);
		
		pd = setPage(pd, request);
		getBoardListData(vo, pd, model);
		
		rttr.addAttribute("pageNum", pd.getPageNum());
		rttr.addAttribute("amount", pd.getAmount());
		rttr.addAttribute("type", pd.getType());
		rttr.addAttribute("keyword", pd.getKeyword());

		logger.info("=============== updateBoard.do END ===============");
		return "redirect:index.do";
	}
	
	// 글 삭제
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardVO vo, PageVO pd, HttpServletRequest request, RedirectAttributes rttr, Model model) {
		logger.info("=============== deleteBoard.do START ===============");
		
		vo.setSeq(Integer.parseInt(request.getParameter("seq")));
		boardService.deleteBoard(vo);
		
		pd = setPage(pd, request);
		getBoardListData(vo, pd, model);
		
		rttr.addAttribute("pageNum", pd.getPageNum());
		rttr.addAttribute("amount", pd.getAmount());
		rttr.addAttribute("type", pd.getType());
		rttr.addAttribute("keyword", pd.getKeyword());
		
		logger.info("=============== deleteBoard.do END ===============");
		return "redirect:index.do";
	}
	
	// 글 상세 조회
	@RequestMapping(value="/getBoard.do")
	public String getBoard(@RequestParam Map<String, String> param, BoardVO vo, PageVO pd, HttpServletRequest request, Model model) {
		logger.info("=============== getBoard.do START ===============");
		
		pd = setPage(pd, request);
		
		model.addAttribute("replyList", boardService.getReplyListData(param));
		model.addAttribute("pageMaker", pd);
		model.addAttribute("board", boardService.getBoard(vo));

		logger.info("=============== getBoard.do END ===============");
		return "getBoard";
	}
	
	// 검색 조건 목록 설정
	/*
	 * @ModelAttribute
	 * 1. 매개변수로 선언된 Command 객체의 이름을 변경할 때 사용가능
	 * 2. @ModelAttribute가 설정된 메소드는 @RequestMapping 이 적용된 메소드보다 먼저 호출됨
	 * 	  그리고 @ModelAttribute 메소드 실행 결과로 리턴된 객체는 자동으로 Model에 저장됨. 따라서 View 에서 사용 가능.
	 * 
	 * 
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		
		return conditionMap;
	}*/
	// 글 목록 검색
	/*
	@RequestMapping(value="/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		
		//  DispatcherServlet은 Controller가 리턴한 mav 객체에서 Model 정보를 추출한 다음
		//  HttpServletRequest 객체에 검색 결과에 해당하는 Model 정보를 저장하여 JSP로 포워딩.
		 
		getBoardListData(vo, model);
		
		return "index";
	}
	*/
	
	public void getBoardListData(BoardVO vo, PageVO pd, Model model) {
		logger.info("pageNum : " + pd.getPageNum() + " | Amount : " + pd.getAmount());
		
		pd.setEndPage((int)Math.ceil(pd.getPageNum() / 10.0) * 10);
		pd.setStartPage(pd.getEndPage() - 9);
		
		logger.info("EndPage : " + pd.getEndPage() + " | StartPage : " + pd.getStartPage());
		
		int total = boardService.getTotalCount(); // 임시설정
		int realEnd = (int)(Math.ceil((total * 1.0) / pd.getAmount()));
		
		if (realEnd < pd.getEndPage()) {
			pd.setEndPage(realEnd);
		}
		
		pd.setPrev(pd.getStartPage() > 1);
		pd.setNext(pd.getEndPage() < realEnd);
		
		model.addAttribute("boardList", boardService.getListWithPaging(pd));
		model.addAttribute("pageMaker", pd);
	}
	
	public PageVO setPage(PageVO pd, HttpServletRequest request) {
		pd.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
		pd.setAmount(Integer.parseInt(request.getParameter("amount")));
	
		return pd;
	}
	
	
	
	
	@RequestMapping(value="/uploadAjax.do")
	public String uploadAjax() {
		logger.info("upload ajax");
		
		return "uploadAjax_test";
	}
	
	/*
	 * 파일 업로드에서 고려해야 할 사항들
	 * 1. 동일한 이름으로 파일이 업로드 되었을 때 기존 파일이 사라지는 문제 --> UUID를 이용해 중복이 발생할 가능성이 거의 없는 문자열 생성하여 처리.
	 * 2. 이미지 파일의 경우 원본 파일의 용량이 큰 경우 섬네일 이미지를 생성해야 하는 문제 --> Thumbnailator 라이브러리 사용
	 * 3. 이미지 파일과 일반 파일을 구분해서 다운로드 혹은 페이지에서 조회하도록 처리하는 문제 --> Thumbnailator 라이브러리 사용
	 * 			--> 업로드된 파일이 이미지 종류의 파일인지 확인
	 * 			--> 이미지 파일의 경우 섬네일 이미지 생성 및 저장
	 * 4. 첨부파일 공격에 대비하기 위한 업로드 파일의 확장자 제한 --> js 에서 처리
	 * 5. 한 폴더 내에 너무 많은 파일이 생성 되었을 때 속도 저하, 개수의 제한 문제 --> 년/월/일 단위의 폴더 생성 하여 저장
	 * 
	 * */
	@RequestMapping(value="/uploadAjaxAction.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileVO>> uploadAjaxAction(BoardVO vo) {
		logger.info("uploadAjaxAction START");
		
		List<AttachFileVO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		String uploadFolderPath = getFolder();
		
		// Make Folder
		File uploadPath = new File(uploadFolder, getFolder());
		
		logger.info("Upload Path : " + uploadPath);
		
		if (uploadPath.exists() == false) {
			// Make yyyy/MM/dd Folder
			uploadPath.mkdirs();
		}
		
		MultipartFile[] uploadFile = vo.getUploadFile();
		
		for (MultipartFile multipartFile : uploadFile) {
			logger.info("Upload File Name : " + multipartFile.getOriginalFilename());
			logger.info("Upload File Size : " + multipartFile.getSize());
			
			AttachFileVO attachVO = new AttachFileVO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			attachVO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				
				attachVO.setUuid(uuid.toString());
				attachVO.setUploadPath(uploadFolderPath);
				
				// Check Image type File
				if (checkImageType(saveFile)) {
					attachVO.setFileType(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				
				multipartFile.transferTo(saveFile);
			// Add to List
				logger.info("Attach Info : " + attachVO.getFileName());
				
				list.add(attachVO);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	/*
	 * Spring 에서는 HttpEntity란 클래스를 제공하는데 이 클래스의 역할은 Http 프로토콜을 이용하는
	 * 통신의 header와 body 관련 정보를 저장할 수 있게끔 해준다. 이를 상속받은 클래스로 RequestEntity 와 ResponseEntity 가 있음
	 * 즉, 통신 메시지 관련 header와 body의 값들을 하나의 객체로 저장하는 것이 HttpEntity 클래스 객체이고
	 * Request 부분일 경우 HttpEntity 를 상속받은 RequestEntity가, Response일 경우 HttpEntity를 상속받은 ResponseEntity가 하게 됨
	 * @ResponseBody나 ResponseEntity를 return 하는거나 결과적으로 같은 기능이지만 구현 방법이 다름.
	 * 예를 들어 header 값을 변경시켜야 할 경우 @ResponseBody의 경우 파라미터로 Response 객체를 받아서 이 객체에서
	 * header 값을 변경시켜야 하고, ResponseEntity에서는 이 클래스 객체를 생성한 뒤 객체에서 header 값을 변경시키면 됨.
	 * 
	 * */
	
	@RequestMapping(value="/showThumbnail.do")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		logger.info("showThumbnail START");
		logger.info("fileName : " + fileName);
		
		File file = new File("C:\\upload\\" + fileName);
		
		logger.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			// 브라우저에서 보내주는 MIME 타입이 파일의 종류에 따라 달라짐
			// 이 부분을 해결하기 위해서 probeContentType()을 ㅣㅇ용해서 적절한 MIME 타입 데이터를 Http의 헤더 메시지에 포함할 수 있도록 처리.
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	
	// 파일 경로 생성을 위한 Method
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	// 특정한 파일이 이미지 타입인지 검사 위한 Method
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@RequestMapping(value="/downloadFile.do", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
		logger.info("Download File : " + fileName);
		
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		
		if (resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		// fileName에서 UUID 제거
		String resourceOriginName = resourceName.substring(resourceName.indexOf("_" + 1));
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			
			if (userAgent.contains("Trident")) {
				logger.info("Browser : IE Browser");
				
				downloadName = URLEncoder.encode(resourceOriginName, "UTF-8").replaceAll("\\+", " ");
			} else if (userAgent.contains("Edge")) {
				logger.info("Browser : Edge Browser");
				
				downloadName = URLEncoder.encode(resourceOriginName, "UTF-8");
			} else {
				logger.info("Browser : Chrome");
				
				downloadName = new String(resourceOriginName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			logger.info("DownloadName : " + downloadName);
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteFile.do")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		logger.info("deleteFile : " + fileName);
		
		File file;
		
		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			
			if (type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				logger.info("largeFileName : " + largeFileName);
				
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
