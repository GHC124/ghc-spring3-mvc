package com.ghc.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ghc.web.form.Message;

@RequestMapping("/security")
@Controller
public class SecurityController extends AbstractController {
	
	@RequestMapping("/loginfail")
	public String loginFail(Model model, Locale locale){
		model.addAttribute(
				"message",
				new Message("error", messageSource.getMessage(
						"message_login_fail", new Object[]{}, locale)));
		return "contacts/list";
	}
}
