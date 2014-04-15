/**
 * ContactController.java
 *
 *	
 */
package com.ghc.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ghc.domain.Contact;
import com.ghc.service.jpa.ContactService;
import com.ghc.util.Log;

/**
 * 
 */
@RequestMapping("/contact")
@Controller
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model) {
		Log.debug("Listing Contacts");
		
		List<Contact> contacts = contactService.findAll();
		model.addAttribute("contacts", contacts);
		
		Log.debug("Get %s contact(s)", contacts.size());
		
		return "contacts/list";
	}
}
