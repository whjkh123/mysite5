package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserCtrl {

	// fields
	@Autowired
	private UserDao uDao;

	// constructors

	// get&set method

	// general method
	// joinForm
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {

		System.out.println("[joinForm Ctrl]: joinForm 진입");

		return "user/joinForm";

	}

	// mysite/user/join?id=[]&password=[]&name=[]&gender=[]
	// join
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo uVo) {

		System.out.println("[join Ctrl]: join 진입");

		System.out.println("[join Ctrl]: " + uVo.toString());

		int count = uDao.join(uVo);

		return "user/joinOk";

	}

	// loginForm
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {

		System.out.println("[loginForm Ctrl]: loginForm 진입");

		return "user/loginForm";

	}

	// mysite/user/login?id=[]&password=[]
	// login
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo uVo, HttpSession session) {

		System.out.println("[login Ctrl]: login 진입");

		System.out.println("[login Ctrl]: " + uVo.toString());

		UserVo authUser = uDao.login(uVo);

		if (authUser == null) {

			System.out.println("[login Ctrl]: login 실패");

			return "redirect:/user/loginForm?result=fail";

		} else {

			System.out.println("[login Ctrl]: login 성공");

			System.out.println("[login Ctrl]: " + authUser.toString());

			session.setAttribute("authUser", authUser);

			return "redirect:/main";

		}

	}

	// logout
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) {

		System.out.println("[logout Ctrl]: logout 진입");

		session.removeAttribute("authUser");
		session.invalidate();

		return "redirect:/main";

	}

	// modifyForm
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(HttpSession session, Model model) {

		System.out.println("[modifyForm Ctrl]: modifyForm 진입");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		authUser = uDao.selectOne(authUser.getNo());

		System.out.println("[modifyForm Ctrl]: " + authUser.toString());

		model.addAttribute("authVo", authUser);

		return "user/modifyForm";

	}

	// modify
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String moidfy(@ModelAttribute UserVo uVo, HttpSession session) {

		System.out.println("[modify Ctrl]: modify 진입");

		uDao.modify(uVo);

		System.out.println("[modify Ctrl]: " + uVo.toString());

		// id = test
		// password = 1234 → 1111
		// name = 홍길동 → 김태희
		// gender = male → female
		// 여기까지의 과정 후 db table상엔 수정내용이 적용 되었지만, 실제 사이트엔 적용 안 됨
		// ※ 수정한 회원정보를 session에 저장 할 필요가 있어보임

		UserVo authUser = uDao.session(uVo.getNo());

		session.setAttribute("authUser", authUser);

		return "redirect:/main";

	}

}
