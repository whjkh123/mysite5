package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.CmtBoardDao;
import com.javaex.vo.CmtBoardVo;

@Service
public class CmtBoardService {

	@Autowired
	private CmtBoardDao cbDao;

	public List<CmtBoardVo> list() {

		System.out.println("[CmtBoard Service]: list() 연결");

		return cbDao.selectList();

	}

	public int write(CmtBoardVo cbVo) {

		// 비지니스 로직
		// #1 작성 된 게시글에 댓글작성 기능 포함
		// #2 먼저 작성 된 글이 아래로..
		// #3 로그인 된 사용자만 작성 가능..

		if (cbVo.getGroup_no() > 0) {

			System.out.println("[CmtBoard Service]: coment write(CmtBoardVo cbVo) 연결");

			cbVo.setOrder_no(cbVo.getOrder_no() + 1);

			cbVo.setDepth(cbVo.getDepth() + 1);

			cbDao.orderUpdate(cbVo);

			return cbDao.cmtInsert(cbVo);

		} else {

			System.out.println("[CmtBoard Service]: write(CmtBoardVo cbVo) 연결");

			return cbDao.insert(cbVo);

		}

	}

	public CmtBoardVo read(CmtBoardVo cbVo) {

		// 비지니스 로직
		// 게시글을 읽으면 조회수가 증가

		System.out.println("[CmtBoard Service]: read(int no) 연결");

		cbDao.hitUpdate(cbVo);

		return cbDao.selectOne(cbVo);

	}

	public CmtBoardVo cmtwForm(CmtBoardVo cbVo) {

		System.out.println("[CmtBoard Service]: cmtwForm(CmtBoardVo cbVo) 연결");

		return cbDao.selectBoard(cbVo);

	}

}
