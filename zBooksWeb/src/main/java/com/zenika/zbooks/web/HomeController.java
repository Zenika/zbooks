package com.zenika.zbooks.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView home () {
        //git test
		return new ModelAndView("/WEB-INF/views/home.jsp");
	}
}