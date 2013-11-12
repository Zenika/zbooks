package com.zenika.zbooks.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public String home () {
		return "/views/home.html";
	}
	
	@RequestMapping(value="/#/user/{name}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody String sayHello (@PathVariable String name) {
		return "Hello " + name;
	}
}