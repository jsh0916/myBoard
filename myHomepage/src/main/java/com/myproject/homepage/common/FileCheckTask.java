package com.myproject.homepage.common;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.vo.AttachFileVO;
import com.myproject.homepage.controller.BoardController;

@Component
public class FileCheckTask {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	/*
	 * 잘못 업로드된 파일을 삭제하기 위한 Class --> Ex) 게시글을 작성 중 첨부파일 업로드를 해놓고 글 등록을 안하고 브라우저를 닫음.
	 * 1. DB에서 어제 사용된 파일의 목록을 얻어오고,
	 * 2. 해당 폴더의 파일 목록에서 DB에 없는 파일을 찾는다.
	 * 3. 이후 DB에 없는 파일을 삭제한다.
	 * */
	@Scheduled(cron="* * 02 * * *")
	public void checkFiles() throws Exception {
		logger.warn("==================== File Check Task Run ====================");
		logger.warn("" + new Date());
		
		// DB에 있는 (오늘 - 1) 날짜의 첨부파일 List 를 가져옴
		List<AttachFileVO> fileList = boardService.getAllFiles();
		
		// 디렉토리에 DB에서 가져온 파일이 있는지 확인
		List<Path> fileListPaths = fileList.stream()
				.map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		// Image 파일은 섬네일파일도 함께 가지고 있으므로 섬네일파일 확인
		fileList.stream().filter(vo -> vo.isFileType() == true)
				.map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
				.forEach(p -> fileListPaths.add(p));
		
		logger.warn("=============================================================");
		
		fileListPaths.forEach(p -> logger.warn("fileListPaths : " + p));
		
		// 어제날짜의 폴더에 있는 파일들
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		logger.warn("=============================================================");
		
		for (File file : removeFiles) {
			logger.warn("File 절대경로 : " + file.getAbsolutePath());
			
			file.delete();
		}
	}
}
