package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

		UserVo userVo = (UserVo) session.getAttribute("authUser");

		cbVo.setUser_no(userVo.getNo());
		cbS.write(cbVo);

		System.out.println("[CmtBoard Ctrl]: " + cbVo.toString());

		return "redirect:/cmtboard/list";

	}

	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@RequestParam("no") int no, Model model) {

		System.out.println("[CmtBoard Ctrl]: read 진입");

		CmtBoardVo cbVo = cbS.read(no);

		System.out.println("[CmtBoard Ctrl]: " + cbVo.toString());

		model.addAttribute("CmtBoardVo", cbVo);

		return "/cmtboard/read";

	}

	@RequestMapping(value = "/cmtwForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String cmtwForm(@RequestParam("group_no") int group_no, Model model) {

		System.out.println("[CmtBoard Ctrl]: cmtwForm 진입");

		CmtBoardVo cbVo = cbS.cmtwForm(group_no);

		model.addAttribute("CmtBoardVo", cbVo);

		return "/cmtboard/writeForm";

	}

}
