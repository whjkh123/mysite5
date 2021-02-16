package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserCtrl {

	// fields
	@Autowired
	private UserService uS;

	// constructors

	// get&set methods

	// general methods
	// joinForm
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {

		System.out.println("[USER Ctrl]: joinForm 진입");

		return "user/joinForm";

	}

	// mysite/user/join?id=[]&password=[]&name=[]&gender=[]
	// join
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo uVo) {

		System.out.println("[USER Ctrl]: join 진입");

		uS.join(uVo);

		System.out.println("[USER Ctrl]: " + uVo.toString());

		return "user/joinOk";

	}

	// loginForm
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {

		System.out.println("[USER Ctrl]: loginForm 진입");

		return "user/loginForm";

	}

	// mysite/user/login?id=[]&password=[]
	// login
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo uVo, HttpSession session) {

		System.out.println("[USER Ctrl]: login 진입");

		System.out.println("[USER Ctrl]: " + uVo.toString());

		UserVo authUser = uS.login(uVo);

		if (authUser == null) {

			System.out.println("[USER Ctrl]: login 실패");

			return "redirect:/user/loginForm?result=fail";

		} else {

			System.out.println("[USER Ctrl]: login 성공");

			System.out.println("[USER Ctrl]: " + authUser.toString());

			session.setAttribute("authUser", authUser);

			return "redirect:/main";

		}

	}

	// logout
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) {

		System.out.println("[USER Ctrl]: logout 진입");

		session.removeAttribute("authUser");
		session.invalidate();

		return "redirect:/main";

	}

	// modifyForm
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(HttpSession session, Model model) {

		System.out.println("[USER Ctrl]: modifyForm 진입");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		authUser = uS.modifyForm(authUser.getNo());

		/*
		 * // 선생님코드 int no = ((UserVo) session.getAttribute("authUser")).getNo();
		 * 
		 * UserVo authUser = uS.modifyForm(no);
		 */

		System.out.println("[USER Ctrl]: " + authUser.toString());

		model.addAttribute("authVo", authUser);

		return "user/modifyForm";

	}

	// mysite/user/modify?password=[]&name=[]&gender=[]
	// modify
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String moidfy(@ModelAttribute UserVo uVo, HttpSession session) {

		System.out.println("[USER Ctrl]: modify 진입");

		uS.modify(uVo);

		System.out.println("[USER Ctrl]: " + uVo.toString());

		// id = test
		// password = 1234 → 1111
		// name = 홍길동 → 김태희
		// gender = male → female
		// 여기까지의 과정 후 db table상엔 수정내용이 적용 되었지만, 실제 사이트엔 적용 안 됨
		// ※ 수정한 회원정보를 session에 저장 할 필요가 있어보임

		UserVo authUser = uS.session(uVo.getNo());

		session.setAttribute("authUser", authUser);

		/*
		 * // 선생님코드 UserVo authUser = (UserVo) session.getAttribute("authUser");
		 * 
		 * int no = authUser.getNo();
		 * 
		 * uVo.setNo(no);
		 * 
		 * int count = uS.modify(uVo);
		 * 
		 * authUser.setName(uVo.getName());
		 */

		return "redirect:/main";

	}

	// idcheck
	@ResponseBody
	@RequestMapping(value = "/idChk", method = { RequestMethod.GET, RequestMethod.POST })
	public String idcheck(@RequestParam("id") String id, @RequestParam("password") String password) {

		System.out.println("[USER Ctrl]: " + id + " idcheck 진입");
		System.out.println("[USER Ctrl]: " + password + " idcheck 진입");

		String result = uS.idcheck(id);

		System.out.println(result);

		// return "redirect:/user/joinForm?result=" + result;
		return result;// @ResponseBody >> 요청 한 body영역에 return데이터만 송신

	}

}
