package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Service
public class GuestService {

	@Autowired
	private GuestBookDao gDao;

	public List<GuestBookVo> addList() {

		System.out.println("[GUEST Service]: addList() 연결");

		return gDao.addList();

	}

	public int add(GuestBookVo gVo) {

		System.out.println("[GUEST Service]: add(GuestBookV gVo) 연결");

		return gDao.add(gVo);

	}

	public int delete(GuestBookVo gVo) {

		System.out.println("[GUEST Service]: add(GuestBookV gVo) 연결");

		return gDao.delete(gVo);

	}

	// ajax 방문록 등록
	public GuestBookVo writeResultVo(GuestBookVo gVo) {

		System.out.println("[GUEST Service]: writeResultVo(GuestBookVo gVo) 연결");
		System.out.println("[GUEST Service]: gDao.insertSelectKey(gVo) 실행 전 " + gVo);

		// int no = gDao.insertSelectKey(gVo);
		// 방문록 저장 → 저장 된 글 조회
		gDao.insertSelectKey(gVo);
		System.out.println("[GUEST Service]: gDao.insertSelectKey(gVo) 실행 후 " + gVo);
		int no = gVo.getNo();

		// 글 1개 조회
		return gDao.selectOne(no);

	}

}
