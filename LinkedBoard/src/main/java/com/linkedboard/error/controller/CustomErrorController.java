package com.linkedboard.error.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;

@Controller
public class CustomErrorController implements ErrorController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String ERROR_PATH = "/error";

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
	
	@RequestMapping(value = ERROR_PATH)
	public String handleError(Model model, HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    model.addAttribute("status", status);
	    logger.error(status.toString());
	    return "error/error";
	}
}
