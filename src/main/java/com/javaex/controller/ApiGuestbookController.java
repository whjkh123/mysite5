package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping(value = "/api/guestbook")
public class ApiGuestbookController {

	@Autowired
	private GuestService gS;

	// 리스트 출력(ajax)
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public List<GuestBookVo> list() {

		System.out.println("[Api Ctrl]: list 진입");

		return gS.addList();
	}

	// 방문록 작성(ajax)
	@ResponseBody
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public GuestBookVo write(@ModelAttribute GuestBookVo gVo) {

		System.out.println("[Api Ctrl]: write 진입");
		System.out.println(gVo);

		// 입력 된 vo 송신, 저장 된 vo 수신
		return gS.writeResultVo(gVo);

	}

	// 방문록 작성(ajax - json)
	@ResponseBody
	@RequestMapping(value = "/write2", method = { RequestMethod.GET, RequestMethod.POST })
	public GuestBookVo write2(@RequestBody GuestBookVo gVo) {

		System.out.println("[Api Ctrl]: write 진입");
		System.out.println(gVo);

		// 입력 된 vo 송신, 저장 된 vo 수신
		return gS.writeResultVo(gVo);

	}

	// 방문록 삭제(ajax)
	@ResponseBody
	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public int remove(@ModelAttribute GuestBookVo gVo) {

		System.out.println("[Api Ctrl]: remove 진입");
		System.out.println(gVo);

		int count = gS.delete(gVo);
		System.out.println(count);

		return count;

	}
}
