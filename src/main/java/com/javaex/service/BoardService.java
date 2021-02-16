package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<BoardVo> list2(String keyword) {

		System.out.println("[Board Service]: list2() 연결");

		System.out.println("[Board Service]: " + keyword + "(keyword)");

		List<BoardVo> bList = bDao.selectList2(keyword);

		return bList;

	}

	public Map<String, Object> list3(String keyword, int crtPage) {

		System.out.println("[Board Service]: list3() 연결");

		System.out.println("[Board Service]: " + keyword + "(keyword)");

		System.out.println("[Board Service]: " + crtPage + "(crtPage)");

		int listCnt = 10;

		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);

		/*
		 * if (crtPage > 0) {
		 * 		crtPage = crtPage;
		 * } else {
		 * 		crtPage = 1;
		 * }
		 */

		int startNum = (crtPage - 1) * listCnt + 1;

		int endNum = (startNum + listCnt) - 1;

		List<BoardVo> bList = bDao.selectList3(keyword, startNum, endNum);

		System.out.println("[Board Service]: " + startNum + "(startNum)");

		System.out.println("[Board Service]: " + endNum + "(endNum)");

		int pageBtnCnt = 5;

		int totalCnt = bDao.selectTotalCnt(keyword);

		int endPageBtnNo = (int) Math.ceil(crtPage / (double) pageBtnCnt) * pageBtnCnt;
		// 1/5.0 → 0.2 → 1.0 → 1*5 = 5
		// 5/5.0 → 1.0 → 1.0 → 1*5 = 5
		// 6/5.0 → 1.2 → 2.0 → 2*5 = 10
		// 10/5.0 → 2.0 → 2.0 → 2*5 = 10
		// 11/5.0 → 2.2 → 3.0 → 3*5 = 15
		// 15/5.0 → 3.0 → 3.0 → 3*5 = 15

		int startPageBtnNo = endPageBtnNo - (pageBtnCnt - 1);
		// 5 - (5-1) = 1
		// 10 - (5-1) = 6
		// 15 - (5-1) = 11

		boolean next;

		if (endPageBtnNo * listCnt < totalCnt) {
			next = true;
		} else {
			next = false;
			endPageBtnNo = (int) Math.ceil(totalCnt/(double)listCnt);
		}

		boolean prev;

		if (startPageBtnNo != 1) {
			prev = true;
		} else {
			prev = false;
		}

		Map<String, Object> pMap = new HashMap<String, Object>();

		pMap.put("bList", bList);
		pMap.put("prev", prev);
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("next", next);

		return pMap;

	}

	/*
	 * public int write(BoardVo bVo) {
	 * 
	 * 		System.out.println("[Board Service]: write(BoardVo bVo) 연결");
	 * 
	 * 		return bDao.insert(bVo);
	 * 
	 * }
	 */

	public int write(BoardVo bVo) {

		System.out.println("[Board Service]: write(BoardVo bVo) 연결");

		for (int i = 0; i <= 1234; i++) {
			bVo.setTitle(i + "번째 글 입니다.");
			bDao.insert(bVo);
		}

		return 1;

	}

	public int remove(BoardVo bVo) {

		System.out.println("[Board Service]: remove(BoardVo bVo) 연결");

		return bDao.delete(bVo);

	}

	public BoardVo read(BoardVo bVo) {

		System.out.println("[Board Service]: read(BoardVo bVo) 연결");

		bDao.hitUpdate(bVo);

		return bDao.selectOne(bVo);

	}

	public BoardVo selectModify(BoardVo bVo) {

		System.out.println("[Board Service]: mForm(BoardVo bVo) 연결");

		bDao.selectModify(bVo);

		return bDao.selectModify(bVo);

	}

	public int modify(BoardVo bVo) {

		System.out.println("[Board Service]: modify(BoardVo bVo) 연결");

		return bDao.update(bVo);

	}

}
