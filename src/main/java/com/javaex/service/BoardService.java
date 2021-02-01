package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao bDao;

	public List<BoardVo> list() {

		System.out.println("[Board Service]: list() 연결");

		return bDao.selectList();

	}

	public int write(BoardVo bVo) {

		System.out.println("[Board Service]: write(BoardVo bVo) 연결");

		return bDao.insert(bVo);

	}

	public int remove(BoardVo bVo) {

		System.out.println("[Board Service]: remove(BoardVo bVo) 연결");

		return bDao.delete(bVo);

	}

	public BoardVo read(BoardVo bVo) {

		System.out.println("[Board Service]: read(BoardVo bVo) 연결");

		return bDao.selectOne(bVo);

	}

	public int modify(BoardVo bVo) {

		System.out.println("[Board Service]: modify(BoardVo bVo) 연결");

		return bDao.update(bVo);

	}

}
