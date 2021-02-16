package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	private BoardService bS;

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {

		System.out.println("[Board Ctrl]: list 진입");

		List<BoardVo> bList = bS.list();

		System.out.println("[Board Ctrl]: " + bList.toString());

		model.addAttribute("BoardList", bList);

		return "board/list";

	}

	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			Model model) {

		System.out.println("[Board Ctrl]: list2 진입");

		System.out.println("[Board Ctrl]: " + keyword + "(keyword)");

		List<BoardVo> bList = bS.list2(keyword);

		model.addAttribute("BoardList", bList);

		return "board/list2";

	}

	@RequestMapping(value = "/list3", method = { RequestMethod.GET, RequestMethod.POST })
	public String list3(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage, Model model) {

		System.out.println("[Board Ctrl]: list3 진입");

		System.out.println("[Board Ctrl]: " + keyword + "(keyword)");

		System.out.println("[Board Ctrl]: " + crtPage + "(crtPage)");

		Map<String, Object> pMap = bS.list3(keyword, crtPage);

		System.out.println("[Board Ctrl]: " + pMap);

		model.addAttribute("pMap", pMap);

		return "board/list3";

	}

	@RequestMapping(value = "/wForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String wForm() {

		System.out.println("[Board Ctrl]: wForm 진입");

		return "board/writeForm";

	}

	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(HttpSession session, @ModelAttribute BoardVo bVo) {

		System.out.println("[Board Ctrl]: write 진입");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		int user_no = authUser.getNo();

		bVo.setUser_no(user_no);

		bS.write(bVo);

		System.out.println("[Board Ctrl]: " + bVo.toString());

		return "redirect:/board/list";

	}

	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public String remove(@ModelAttribute BoardVo bVo) {

		System.out.println("[Board Ctrl]: remove 진입");

		bS.remove(bVo);

		System.out.println("[Board Ctrl]: " + bVo.toString());

		return "redirect:/board/list";

	}

	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@ModelAttribute BoardVo bVo, Model model) {

		System.out.println("[Board Ctrl]: read 진입");

		bVo = bS.read(bVo);

		System.out.println("[Board Ctrl]: " + bVo.toString());

		model.addAttribute("BoardVo", bVo);

		return "board/read";

	}

	@RequestMapping(value = "/mForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String mForm(@ModelAttribute BoardVo bVo, Model model) {

		System.out.println("[Board Ctrl]: mForm 진입");

		bVo = bS.selectModify(bVo);

		System.out.println("[Board Ctrl]: " + bVo.toString());

		model.addAttribute("BoardVo", bVo);

		return "board/modifyForm";

	}

	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute BoardVo bVo) {

		System.out.println("[Board Ctrl]: modify 진입");

		bS.modify(bVo);

		System.out.println("[Board Ctrl]: " + bVo.toString());

		return "redirect:/board/list";

	}

}
