package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping(value = "/gbc")
public class GuestBookController {

	// fields
	@Autowired
	private GuestService gS;

	// constructors

	// get/set method

	// general method
	// addList
	@RequestMapping(value = "/addList", method = { RequestMethod.GET, RequestMethod.POST })
	public String addList(Model model) {

		System.out.println("[Guest Ctrl]: addList 진입");

		List<GuestBookVo> gList = gS.addList();

		System.out.println("[Board Ctrl]: " + gList.toString());

		model.addAttribute("GuestList", gList);

		return "guestbook/addlist";

	}

	// http://localhost:8088/mysite/gbc/add?name=[]&password=[]&content=[]
	// add
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public String add(@ModelAttribute GuestBookVo gVo) {

		System.out.println("[Guest Ctrl]: add 진입");

		System.out.println("[Guest Ctrl]: " + gVo.toString());

		gS.add(gVo);

		return "redirect:/gbc/addList";

	}

	// dForm
	@RequestMapping(value = "/dForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String dForm() {

		System.out.println("[Guest Ctrl]: dForm 진입");

		return "guestbook/deleteForm";

	}

	// http://localhost:8088/mysite/gbc/delete?no=[]
	// delete
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestBookVo gVo) {

		System.out.println("[Guest Ctrl]: delete 진입");

		int count = gS.delete(gVo);

		if (count == 1) {
			return "redirect:/gbc/addList";
		} else {
			System.out.println("[Guest Ctrl]: password 오류");
			return "guestbook/pswError";
		}

	}

	// ajaxList
	@RequestMapping(value = "/ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
	public String ajaxList() {

		System.out.println("[Guest Ctrl]: ajaxList 진입");

		return "guestbook/ajaxList";

	}

}
