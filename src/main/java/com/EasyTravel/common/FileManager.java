package com.EasyTravel.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileManager {
	
	// 파일이 업로드 될 기본 디렉토리 경로를 정의하는 상수
	//public static final String FILE_UPLOAD_PATH = "/home/ec2-user/upload/rmTheater";
	public static final String FILE_UPLOAD_PATH = "C:\\web\\spring project\\upload\\easyTravel";
	
	// 이 클래스의 로그 메시지를 기록하는 로거 인스턴스
	public static Logger logger = LoggerFactory.getLogger(FileManager.class);
	
	// 업로드된 파일을 고유한 디렉토리에 저장하고 파일의 접근 가능한 URL 경로를 반환
	public static String saveFile(String title, MultipartFile file) {
		
		if (file == null || file.isEmpty()) {
			// 파일이 없으면 null 반환
			return null;
		}
		
		// 같은 이름의 파일을 처리하기 위해
		// 폴더를 만들어서 파일 저장
		// 영화의 movieId를 폴더이름
		// 현재시간 정보를 폴더 이름에 포함
		
		// directoryName movieId와 현재 시간을 밀리초로 사용하여 고유한 디렉토리 이름을 생성
		String directoryName = "/" + title + "_" + System.currentTimeMillis();
		
		// 폴더 생성
		// directoryPath는 새로운 디렉토리의 전체 경로
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(directoryPath);
		
		// 디렉토리를 생성
		if (!directory.mkdir()) {
			// 디렉토리 생성 실패 > 오류를 기록, null 반환
			logger.error("saveFile :: 디렉토리 생성 실패 - " + directoryPath);
			return null;
		}
		
		// 파일이 저장될 전체 경로
		String filePath = directoryPath + "/" + file.getOriginalFilename();
		
		try {
			// file 내용을 바이트 배열로 가져옴
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath);
			// 바이트 배열을 지정된 경로에 씀
			Files.write(path, bytes);
		} catch (IOException e) {
			logger.error("saveFile :: 파일 저장 실패 - " + filePath);
			e.printStackTrace();
			// 파일 저장 실패
			return null;
		}
		
		// url 경로 반환
		// 클라이언트가 파일에 접근할 수 있는 url 경로를 생성하여 반환
		return "/images" + directoryName + "/" + file.getOriginalFilename();
	}
	
	
	public static String saveFile(String title, MultipartFile file, String existingImagePath) {
		
		if (file == null || file.isEmpty()) {
			// 새 파일이 업로드 되지 않은 경우 기존 이미지 경로를 반환
			return existingImagePath;
		}
		
		// 같은 이름의 파일을 처리하기 위해
		// 폴더를 만들어서 파일 저장
		// 영화의 movieId를 폴더이름
		// 현재시간 정보를 폴더 이름에 포함
		
		// directoryName movieId와 현재 시간을 밀리초로 사용하여 고유한 디렉토리 이름을 생성
		String directoryName = "/" + title + "_" + System.currentTimeMillis();
		
		// 폴더 생성
		// directoryPath는 새로운 디렉토리의 전체 경로
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		File directory = new File(directoryPath);
		
		// 디렉토리를 생성
		if (!directory.mkdir()) {
			// 디렉토리 생성 실패 > 오류를 기록, null 반환
			logger.error("saveFile :: 디렉토리 생성 실패 - " + directoryPath);
			return null;
		}
		
		// 파일이 저장될 전체 경로
		String filePath = directoryPath + "/" + file.getOriginalFilename();
		
		
		try {
			// file 내용을 바이트 배열로 가져옴
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath);
			// 바이트 배열을 지정된 경로에 씀
			Files.write(path, bytes);
		} catch (IOException e) {
			logger.error("saveFile :: 파일 저장 실패 - " + filePath);
			e.printStackTrace();
			// 파일 저장 실패
			return null;
		}
		
		// url 경로 반환
		// 클라이언트가 파일에 접근할 수 있는 url 경로를 생성하여 반환
		return "/images" + directoryName + "/" + file.getOriginalFilename();
	}
	
	
	
	// 파일을 삭제하는 경우
	public static boolean removeFile(String filePath) {
		
		if (filePath == null) {
			return false;
		}
		
		// 삭제 대상 파일 경로
		String fullFilePath = FILE_UPLOAD_PATH + filePath.replace("/images", "");
		
		Path path = Paths.get(fullFilePath);
		
		// 파일 존재 여부 확인
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		// 디렉토리 삭제
		Path dirPath = path.getParent();
		
		if (Files.exists(dirPath)) {
			try {
				Files.delete(dirPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;

	}
	
}