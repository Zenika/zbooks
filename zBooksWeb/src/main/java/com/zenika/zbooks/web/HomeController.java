package com.zenika.zbooks.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;

@Controller
public class HomeController {

	@RequestMapping(value="/", produces = MediaType.TEXT_HTML_VALUE)
	public String index() {
        return "index.html";
	}

}