package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryDao gDao;

	@Autowired
	private HttpSession session;

	// 전체리스트
	public List<GalleryVo> gList() {

		System.out.println("[Gallery Service]: gList() 연결");

		List<GalleryVo> gList = gDao.selectList();

		return gList;

	}

	// 등록
	public void gUpload(GalleryVo gVo, MultipartFile file) {

		System.out.println("[Gallery Service]: gUpload(GalleryVo gVo, MultipartFile file) 연결");

		// 원본파일이름
		String orgName = file.getOriginalFilename();
		System.out.println("[File Service]: orgName = " + orgName);
		gVo.setOrgName(orgName);

		// 확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("[File Service]: exName = " + exName);

		// 서버에 저장할 파일이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println("[File Service]: saveName = " + saveName);
		gVo.setSaveName(saveName);

		// 서버파일패스(저장경로)
		String saveDir = "C:\\javaStudy\\upload";

		String filePath = saveDir + "\\" + saveName;
		System.out.println("[File Service]: filePath = " + filePath);
		gVo.setFilePath(filePath);

		// 파일크기
		long fileSize = file.getSize();
		System.out.println("[File Service]: fileSize = " + fileSize);
		gVo.setFileSize(fileSize);

		// 작성자
		UserVo uVo = (UserVo) session.getAttribute("authUser");
		gVo.setUser_no(uVo.getNo());

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

		gDao.insert(gVo);

	}

	// 이미지 하나 가져오기
	public GalleryVo getOne(GalleryVo gVo) {

		System.out.println("[Gallery Service]: getOne(GalleryVo gVo) 연결");

		return gDao.selectOne(gVo);
	}

	public int remove(int no) {

		System.out.println("[Gallery Service]: remove(GalleryVo gVo) 연결");

		return gDao.delete(no);
	}

}
