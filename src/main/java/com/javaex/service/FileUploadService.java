package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public String restore(MultipartFile file) {

		System.out.println("[File Service]: restore(MultipartFile file) 연결");

		System.out.println("[File Service]: " + file.getOriginalFilename());

		// db에 저장할 정보 수집

		// 원본파일이름
		String orgName = file.getOriginalFilename();
		System.out.println("[File Service]: orgName = " + orgName);

		// 확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("[File Service]: exName = " + exName);

		// 서버에 저장할 파일이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println("[File Service]: saveName = " + saveName);

		// 서버파일패스(저장경로)
		String saveDir = "C:\\javaStudy\\upload";

		String filePath = saveDir + "\\" + saveName;
		System.out.println("[File Service]: filePath = " + filePath);

		// 파일크기
		long fileSize = file.getSize();
		System.out.println("[File Service]: fileSize = " + fileSize);

		// 서버에 저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);

			bos.write(fileData);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saveName;

	}

}
