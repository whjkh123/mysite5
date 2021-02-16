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

		if (cbVo.getGroup_no() != 0) {// 그룹번호 = 게시글 식별번호 >> 게시글이 존재하면..

			System.out.println("[CmtBoard Service]: coment write(CmtBoardVo cbVo) 연결");

			cbVo.setOrder_no(cbVo.getOrder_no() + 1);// 오더넘버(그룹내 식별번호)에 +1

			cbVo.setDepth(cbVo.getDepth() + 1);// 댓글을 달면 뎁스에 +1(대댓글일 경우 댓글뎁스에 +1)

			cbDao.orderUpdate(cbVo);// 조회수(hit)와 같은 구조

			return cbDao.cmtInsert(cbVo);

		} else {

			System.out.println("[CmtBoard Service]: write(CmtBoardVo cbVo) 연결");

			return cbDao.insert(cbVo);

		}

	}

	public CmtBoardVo read(int no) {

		// 비지니스 로직
		// 게시글을 읽으면 조회수가 증가

		System.out.println("[CmtBoard Service]: read(int no) 연결");

		cbDao.hitUpdate(no);

		return cbDao.selectOne(no);

	}

	public CmtBoardVo cmtwForm(int no) {

		System.out.println("[CmtBoard Service]: cmtwForm(CmtBoardVo cbVo) 연결");

		return cbDao.selectOne(no);

	}

}
