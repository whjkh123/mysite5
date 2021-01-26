package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardCtrl");

		request.setCharacterEncoding("UTF-8");

		String act = request.getParameter("action");

		if ("list".equals(act)) {
			System.out.println(act + " 게시판메인");

			BoardDao bDao = new BoardDao();
			List<BoardVo> bList = bDao.boardList();

			request.setAttribute("BoardList", bList);

			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("writeForm".equals(act)) {
			System.out.println(act + " 게시글 작성 창");

			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");
		} else if ("write".equals(act)) {
			System.out.println(act + " 게시글 작성");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			int user_no = authUser.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo bVo = new BoardVo(user_no, title, content);

			BoardDao bDao = new BoardDao();
			bDao.bIsrt(bVo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else if ("read".equals(act)) {
			System.out.println(act + " 게시글 보기");

			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao bDao = new BoardDao();

			BoardVo bVo = bDao.readBoard(no);

			bDao.bHit(no);

			request.setAttribute("BoardVo", bVo);

			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
		} else if ("modifyForm".equals(act)) {
			System.out.println(act + " 게시글 수정 창");

			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao bDao = new BoardDao();

			BoardVo bVo = bDao.readBoard(no);

			request.setAttribute("BoardVo", bVo);

			WebUtil.forword(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		} else if ("modify".equals(act)) {
			System.out.println(act + " 게시글 수정");

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));

			BoardVo bVo = new BoardVo(title, content, no);

			BoardDao bDao = new BoardDao();
			bDao.bUpd(bVo);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			// db table 상엔 content update 확인, 실제 'modifyForm'에선 변경 된 내용이 확인되질 않음..
			// modify(bUpd) 테스트 중 localhost 무한 응답 대기 걸림..
			// 응답 대기 중 '취소' 클릭 시 '게시판 메인'으로 탈출 가능..
			// 수정한 내용을 db로 보내는 과정에서 무슨 오류가 있는 모양..
			// db table 롤백 후 오류 해결, 'content' 수정내용이 적용되질 않아 db에서 직접 바꾼것이 문제였던 모양..
			// 여전히 'content' 수정 적용 X
			// 기존의 게시글 수정이 아니라 새로운 글 작성시 'content' 정상출력 확인, 'modifyForm.jsp'의 문제인 것으로 추정..
			// 제공 된 html 코드에 textarea 속성 값이 누락, 추가 후 정상구동..
			// db table 상에서도 수정 된 내용 적용 확인..
		} else if ("delete".equals(act)) {
			System.out.println(act + " 게시글 삭제");

			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao bDao = new BoardDao();
			bDao.bDle(no);

			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		} else if ("search".equals(act)) {
			System.out.println(act + " 게시글 검색");

			String str = request.getParameter("keyword");

			BoardDao bDao = new BoardDao();
			List<BoardVo> bList = bDao.boardSearch(str);

			request.setAttribute("BoardList", bList);

			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
