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

		System.out.println("[USER Service]: join(UserVo uVo) 연결");

		System.out.println("[USER Service]: " + uVo.toString());

		return uDao.join(uVo);

	}

	// login
	public UserVo login(UserVo uVo) {

		System.out.println("[USER Service]: login(UserVo uVo) 연결");

		System.out.println("[USER Service]: " + uVo.toString());

		return uDao.login(uVo);

	}

	// modifyForm
	public UserVo modifyForm(int no) {

		System.out.println("[USER Service]: modifyForm(int no) 연결");

		return uDao.modifyForm(no);

	}

	// modify
	public int modify(UserVo uVo) {

		System.out.println("[USER Service]: modify(UserVo uVo) 연결");

		System.out.println("[USER Service]: " + uVo.toString());

		return uDao.modify(uVo);

	}

	// session
	public UserVo session(int no) {

		System.out.println("[USER Service]: session(int no) 연결");

		return uDao.session(no);

	}

	// idcheck
	public String idcheck(String id) {

		System.out.println("[USER Service]: idcheck(String id) 연결");

		UserVo uVo = uDao.selectOne(id);

		String result = "";

		if (uVo == null) {
			// 회원가입 가능
			result = "can";
		} else {
			// 회원가입 불가능
			result = "cant";
			System.out.println("[USER Service]: " + uVo.toString());
		}

		return result;

	}

}
