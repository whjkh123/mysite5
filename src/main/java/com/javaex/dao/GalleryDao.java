package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sql;

	// 전체리스트
	public List<GalleryVo> selectList() {

		System.out.println("[Gallery Dao]: selectList() 실행");

		return sql.selectList("gallery.selectList");

	}

	// 등록
	public void insert(GalleryVo gVo) {

		System.out.println("[Gallery Dao]: insert(GalleryVo gVo) 실행");

		System.out.println(gVo);

		sql.insert("gallery.insert", gVo);

	}

	// 이미지 하나 가져오기
	public GalleryVo selectOne(GalleryVo gVo) {

		System.out.println("[Gallery Dao]: selectOne(GalleryVo gVo) 실행");

		return sql.selectOne("gallery.selectOne", gVo);
	}

	public int delete(int no) {

		System.out.println("[Gallery Dao]: delete(GalleryVo gVo) 실행");

		return sql.delete("gallery.delete", no);
	}

}
