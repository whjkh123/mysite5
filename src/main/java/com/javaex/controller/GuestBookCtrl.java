package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gbc")
public class GuestBookCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestBookCtrl");

		request.setCharacterEncoding("UTF-8");

		String act = request.getParameter("action");

		if ("addlist".equals(act)) {
			System.out.println(act + " 방문록메인");

			// list load
			GuestBookDao gDao = new GuestBookDao();
			List<GuestBookVo> gList = gDao.dbList();

			// list data attribute to jsp
			request.setAttribute("GuestList", gList);

			// addlist forword
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addlist.jsp");
		} else if ("add".equals(act)) {
			System.out.println(act + " 게시글 등록");

			// GuestBookVo data load
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			// GuestBookVo groupping
			GuestBookVo gVo = new GuestBookVo(name, password, content);

			// GuestBookDao.insert() execute
			GuestBookDao gDao = new GuestBookDao();
			gDao.dbIsrt(gVo);

			System.out.println(gVo.toString());

			WebUtil.redirect(request, response, "/mysite2/gbc?action=addlist");
		} else if ("deleteForm".equals(act)) {
			System.out.println(act + " 게시글 삭제 창");

			// deleteForm forword
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		} else if ("delete".equals(act)) {
			System.out.println(act + " 삭제결과");

			// GuestBookVo data load
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			// GuestBookVo groupping
			GuestBookVo gVo = new GuestBookVo(no, password);

			// GuestBookDao.delete() execute
			GuestBookDao gDao = new GuestBookDao();

			int cnt = gDao.dbDle(gVo);

			System.out.println(gVo.toString());

			if (cnt == 1) {
				WebUtil.redirect(request, response, "/mysite2/gbc?action=addlist");
			} else {
				// pswError forword
				WebUtil.forword(request, response, "/WEB-INF/views/guestbook/pswError.jsp");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
