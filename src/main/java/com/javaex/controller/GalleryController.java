package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;

@Controller
@RequestMapping(value = "/gallery")
public class GalleryController {

	@Autowired
	private GalleryService gS;

	// 전체리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {

		System.out.println("[Gallery Ctrl]: list 진입");

		List<GalleryVo> gList = gS.gList();

		model.addAttribute("GalleryList", gList);

		return "gallery/list";

	}

	// 등록 >> 작성자이름, 이미지파일
	@RequestMapping(value = "/upload", method = { RequestMethod.GET, RequestMethod.POST })
	public String upload(@ModelAttribute GalleryVo gVo, @RequestParam("file") MultipartFile file) {

		System.out.println("[Gallery Ctrl]: upload 진입");

		gS.gUpload(gVo, file);

		return "redirect:/gallery/list";

	}

	// 이미지 하나 가져오기(모달 창에 출력 → 모달 창에서 삭제 >> ajax(http 응답본문에 담기)
	@ResponseBody
	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public GalleryVo read(@ModelAttribute GalleryVo gVo) {

		System.out.println("[Gallery Ctrl]: read 진입");

		gVo = gS.getOne(gVo);

		return gVo;

	}

	// 삭제(ajax)
	@ResponseBody
	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public int remove(@RequestParam("no") int no) {

		System.out.println("[Gallery Ctrl]: remove 진입");

		int count = gS.remove(no);

		return count;

	}

}
