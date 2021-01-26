package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

	public static void forword(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		RequestDispatcher rqD = request.getRequestDispatcher(path);
		rqD.forward(request, response);
	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		response.sendRedirect(url);
	}

}
