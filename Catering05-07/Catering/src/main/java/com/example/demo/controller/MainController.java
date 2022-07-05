package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/index")
	public String index(Model model) {
		return "index.html";
	}
	
	@GetMapping("/admin/home")
	public String homeAdmin(Model model) {
		return "/admin/home.html";
	}
	
	@GetMapping("/home")
	public String homeUser(Model model) {
		return "home.html";
	}
	
}
