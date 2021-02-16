package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileUploadService;

@Controller
@RequestMapping(value = "/fileup")
public class FileUploadController {

	@Autowired
	private FileUploadService fS;

	@RequestMapping(value = "/form", method = { RequestMethod.GET, RequestMethod.POST })
	public String form() {

		System.out.println("[File Ctrl]: form 진입");

		return "/fileUpload/form";

	}

	@RequestMapping(value = "/upload", method = { RequestMethod.GET, RequestMethod.POST })
	public String upload(@RequestParam("file") MultipartFile file, Model model) {

		System.out.println("[File Ctrl]: upload 진입");

		System.out.println("[File Ctrl]: " + file.getOriginalFilename());

		String saveName = fS.restore(file);

		model.addAttribute("saveName", saveName);

		return "/fileUpload/result";

	}

}
