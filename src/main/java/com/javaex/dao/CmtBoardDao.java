package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.CmtBoardVo;

@Repository
public class CmtBoardDao {

	@Autowired
	private SqlSession sql;

	public List<CmtBoardVo> selectList() {

		System.out.println("[CmtBoard Dao]: selectList() 실행");

		List<CmtBoardVo> cbList = sql.selectList("cmtboard.list");

		System.out.println("[CmtBoard Dao]: " + cbList.toString());

		return cbList;

	}

	public int orderUpdate(CmtBoardVo cbVo) {

		System.out.println("[CmtBoard Dao]: orderUpdate(CmtBoardVo cbVo) 실행");

		return sql.update("cmtboard.orderUpdate", cbVo);

	}

	public int cmtInsert(CmtBoardVo cbVo) {

		System.out.println("[CmtBoard Dao]: cmtInsert(CmtBoardVo cbVo) 실행");

		return sql.insert("cmtboard.cmtInsert", cbVo);

	}

	public int insert(CmtBoardVo cbVo) {

		System.out.println("[CmtBoard Dao]: insert(CmtBoardVo cbVo) 실행");

		System.out.println("[CmtBoard Dao]: " + cbVo.toString());

		return sql.insert("cmtboard.write", cbVo);

	}

	public int hitUpdate(int no) {

		System.out.println("[CmtBoard Dao]: hitUpdate(CmtBoardVo cbVo) 실행");

		return sql.update("cmtboard.hit", no);

	}

	public CmtBoardVo selectOne(int no) {

		System.out.println("[CmtBoard Dao]: selectOne(CmtBoardVo cbVo) 실행");

		return sql.selectOne("cmtboard.read", no);

	}

}
