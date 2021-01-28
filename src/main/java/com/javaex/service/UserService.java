package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao uDao;

	// join
	public int join(UserVo uVo) {

		System.out.println("[Service]: join(UserVo uVo) 연결");

		return uDao.join(uVo);

	}

	// login
	public UserVo login(UserVo uVo) {

		System.out.println("[Service]: login(UserVo uVo) 연결");

		return uDao.login(uVo);

	}

	// modifyForm
	public UserVo modifyForm(int no) {

		System.out.println("[Service]: modifyForm(int no) 연결");

		return uDao.modifyForm(no);

	}

	// modifyForm
	public int modify(UserVo uVo) {

		System.out.println("[Service]: modify(UserVo uVo) 연결");

		return uDao.modify(uVo);

	}

	// session
	public UserVo session(int no) {

		System.out.println("[Service]: session(int no) 연결");

		return uDao.session(no);

	}

}
