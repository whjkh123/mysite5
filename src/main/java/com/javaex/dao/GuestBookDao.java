package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sql;

	// 전체 리스트
	public List<GuestBookVo> addList() {

		System.out.println("[GUEST Dao]: addList() 실행");

		List<GuestBookVo> gList = sql.selectList("guestbook.selectList");

		System.out.println("[GUEST Dao]: " + gList.toString());

		return gList;

	}

	// 방문록 등록
	public int add(GuestBookVo gVo) {

		System.out.println("[GUEST Dao]: add(GuestBookVo gVo) 실행");

		System.out.println("[GUEST Dao]: " + gVo.toString());

		return sql.insert("guestbook.insert", gVo);

	}

	// 방문록 삭제
	public int delete(GuestBookVo gVo) {

		System.out.println("[GUEST Dao]: delete(GuestBookVo gVo) 실행");

		System.out.println("[GUEST Dao]: " + gVo.toString());

		return sql.delete("guestbook.delete", gVo);

	}

	// ajax 방문록 등록
	public void insertSelectKey(GuestBookVo gVo) {

		System.out.println("[GUEST Dao]: insertSelectKey(GuestBookVo gVo) 실행");

		System.out.println("[GUEST Dao]: xml 실행 전 " + gVo);

		sql.insert("guestbook.insertSelectKey", gVo);

		System.out.println("[GUEST Dao]: xml 실행 후 " + gVo);

	}

	// ajax 글 1개 조회
	public GuestBookVo selectOne(int no) {

		System.out.println("[GUEST Dao]: selectOne(GuestBookVo gVo) 실행");

		return sql.selectOne("guestbook.select", no);
	}

}
