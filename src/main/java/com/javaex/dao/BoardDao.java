package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sql;

	public List<BoardVo> selectList() {

		System.out.println("[Board Dao]: selectList() 실행");

		List<BoardVo> bList = sql.selectList("board.list");

		System.out.println("[Board Dao]: " + bList.toString());

		return bList;

	}

	public int insert(BoardVo bVo) {

		System.out.println("[Board Dao]: insert(BoardVo bVo) 실행");

		System.out.println("[Board Dao]: " + bVo.toString());

		return sql.insert("board.write", bVo);

	}

	public int delete(BoardVo bVo) {

		System.out.println("[Board Dao]: delete(BoardVo bVo) 실행");

		System.out.println("[Board Dao]: " + bVo.toString());

		return sql.delete("board.remove", bVo);

	}

	public BoardVo selectOne(BoardVo bVo) {

		System.out.println("[Dao]: selectOne(BoardVo bVo) 실행");

		System.out.println("[Board Dao]: " + bVo.toString());

		return sql.selectOne("board.read", bVo);

	}

}