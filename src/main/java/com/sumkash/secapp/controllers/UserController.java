package com.sumkash.secapp.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sumkash.secapp.models.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/welcome")
	public ModelAndView welcome(@AuthenticationPrincipal User user, Authentication authentication) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		// TODO
		
		return new ModelAndView("user/welcome", model);
	}
	
}
