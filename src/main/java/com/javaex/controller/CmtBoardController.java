package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.CmtBoardService;
import com.javaex.vo.CmtBoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/cmtboard")
public class CmtBoardController {

	@Autowired
	private CmtBoardService cbS;

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {

		System.out.println("[CmtBoard Ctrl]: list 진입");

		List<CmtBoardVo> cbList = cbS.list();

		System.out.println("[CmtBoard Ctrl]: " + cbList.toString());

		model.addAttribute("CmtBoardList", cbList);

		return "cmtboard/list";

	}

	@RequestMapping(value = "/wForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String wForm() {

		System.out.println("[CmtBoard Ctrl]: wForm 진입");

		return "cmtboard/writeForm";

	}

	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(HttpSession session, @ModelAttribute CmtBoardVo cbVo) {

		System.out.println("[CmtBoard Ctrl]: write 진입");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		int user_no = authUser.getNo();

		cbVo.setUser_no(user_no);

		cbS.write(cbVo);

		System.out.println("[CmtBoard Ctrl]: " + cbVo.toString());

		return "redirect:/cmtboard/list";

	}

	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@ModelAttribute CmtBoardVo cbVo, Model model) {

		System.out.println("[CmtBoard Ctrl]: read 진입");

		cbVo = cbS.read(cbVo);

		System.out.println("[CmtBoard Ctrl]: " + cbVo.toString());

		model.addAttribute("CmtBoardVo", cbVo);

		return "/cmtboard/read";

	}

	@RequestMapping(value = "/cmtwForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String cmtwForm(@ModelAttribute CmtBoardVo cbVo, Model model) {

		System.out.println("[CmtBoard Ctrl]: cmtwForm 진입");

		cbVo = cbS.cmtwForm(cbVo);

		model.addAttribute("CmtBoardVo", cbVo);

		return "/cmtboard/wForm";

	}

}
