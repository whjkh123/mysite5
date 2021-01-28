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

		System.out.println("[Service]: addList() 연결");

		return gDao.addList();

	}

	public int add(GuestBookVo gVo) {

		System.out.println("[Service]: add(GuestBookV gVo) 연결");

		return gDao.add(gVo);

	}

	public int delete(GuestBookVo gVo) {

		System.out.println("[Service]: add(GuestBookV gVo) 연결");

		return gDao.delete(gVo);

	}

}
