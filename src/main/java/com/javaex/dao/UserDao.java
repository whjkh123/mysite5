package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	// fields
	@Autowired
	private SqlSession sql;

	// constructors

	// get&set method

	// general method
	// join(insert)
	public int join(UserVo uVo) {

		System.out.println("[Dao]: join(UserVo uVo) 실행");

		System.out.println("[Dao]: " + uVo.toString());

		return sql.insert("user.join", uVo);
	}

	// login(selectOne)
	public UserVo login(UserVo uVo) {

		System.out.println("[Dao]: login(UserVo uVo) 실행");

		System.out.println("[Dao]: " + uVo.toString());

		return sql.selectOne("user.login", uVo);

	}

	// modifyForm(selectOne)
	public UserVo selectOne(int no) {

		System.out.println("[Dao]: selectOne(int no) 실행");

		return sql.selectOne("user.selectOne", no);

	}

	// modify(update)
	public int modify(UserVo uVo) {

		System.out.println("[Dao]: modify(UserVo uVo) 실행");

		System.out.println("[Dao]: " + uVo.toString());

		return sql.update("user.modify", uVo);

	}

	// modify(update) → session
	public UserVo session(int no) {

		System.out.println("[Dao]: session(int no) 실행");

		return sql.selectOne("user.session", no);

	}

}
