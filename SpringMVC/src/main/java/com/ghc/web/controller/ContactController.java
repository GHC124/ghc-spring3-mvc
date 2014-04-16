/**
 * ContactController.java
 *
 *	
 */
package com.ghc.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ghc.domain.Contact;
import com.ghc.service.jpa.ContactService;
import com.ghc.util.Log;
import com.ghc.web.form.Message;
import com.ghc.web.util.UrlUtil;

/**
 * 
 */
@RequestMapping("/contacts")
@Controller
public class ContactController {
	@Autowired
	private ContactService contactService;
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, new DateTimeEditor());
	}

	public class DateTimeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			String datePattern = messageSource.getMessage("", new Object[] {}, locale);
			DateTime dateTime = org.joda.time.format.DateTimeFormat.forPattern(
					"yyyy-MM-dd").parseDateTime(text);
			setValue(dateTime);
		}

		@Override
		public String getAsText() throws IllegalArgumentException {
			String s = "";
			if (getValue() != null) {
				s = org.joda.time.format.DateTimeFormat
						.forPattern("yyyy-MM-dd").print((DateTime) getValue());
			}
			return s;
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		Log.debug("Listing Contacts");

		List<Contact> contacts = contactService.findAll();
		model.addAttribute("contacts", contacts);

		Log.debug("Get %s contact(s)", contacts.size());

		return "contacts/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model model) {
		Contact contact = contactService.findById(id);
		model.addAttribute("contact", contact);
		return "contacts/show";
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Contact contact, BindingResult bindingResult,
			Model model, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		if (bindingResult.hasErrors()) {
			model.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"contact_save_fail", new Object[] {}, locale)));
			model.addAttribute("contact", contact);
			return "contacts/update";
		}
		model.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"contact_save_success", new Object[] {}, locale)));
		contactService.save(contact);
		return "redirect:/contacts/"
				+ UrlUtil.encodeUrlPathSegment(contact.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		Contact contact = contactService.findById(id);
		model.addAttribute("contact", contact);
		return "contacts/update";
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(@Valid Contact contact, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"contact_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("contact", contact);
			return "contacts/create";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"contact_save_success", new Object[] {}, locale)));
		contactService.save(contact);
		return "redirect:/contacts/"
				+ UrlUtil.encodeUrlPathSegment(contact.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Contact contact = new Contact();
		uiModel.addAttribute("contact", contact);
		return "contacts/create";
	}
}
