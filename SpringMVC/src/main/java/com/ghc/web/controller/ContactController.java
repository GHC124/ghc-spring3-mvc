/**
 * ContactController.java
 *
 *	
 */
package com.ghc.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ghc.domain.Contact;
import com.ghc.service.jpa.ContactService;
import com.ghc.web.form.ContactGrid;
import com.ghc.web.form.Message;
import com.ghc.web.util.UrlUtil;

/**
 * 
 */
@RequestMapping("/contacts")
@Controller
public class ContactController extends AbsController {
	@Autowired
	private ContactService contactService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Contact> contacts = contactService.findAll();
		model.addAttribute("contacts", contacts);
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
			RedirectAttributes redirectAttributes, Locale locale,
			@RequestParam(value = "file", required = false) Part file) {
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

	@PreAuthorize("isAuthenticated()") 
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		Contact contact = contactService.findById(id);
		model.addAttribute("contact", contact);
		return "contacts/update";
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(@Valid Contact contact, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale,
			@RequestParam(value = "file", required = false) Part file) {
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
		// Process upload file
		if (file != null) {
			byte[] fileContent = null;
			try {
				InputStream inputStream = file.getInputStream();
				if (inputStream == null) {
					Log.error("File inputstream is null");
				}
				fileContent = IOUtils.toByteArray(inputStream);
				contact.setPhoto(fileContent);
			} catch (IOException ex) {
				Log.error("Error saving uploaded file");
			}			
		}

		contactService.save(contact);
		return "redirect:/contacts/"
				+ UrlUtil.encodeUrlPathSegment(contact.getId().toString(),
						httpServletRequest);
	}

	@PreAuthorize("isAuthenticated()") 
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Contact contact = new Contact();
		uiModel.addAttribute("contact", contact);
		return "contacts/create";
	}

	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ContactGrid listGrid(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		Sort sort = null;
		String orderBy = sortBy;

		if (orderBy != null && orderBy.equals("birthDateString")) {
			orderBy = "birthDate";
		}
		if (orderBy != null && order != null) {
			if (order.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, orderBy);
			} else {
				sort = new Sort(Sort.Direction.ASC, orderBy);
			}
		}
		PageRequest pageRequest = null;
		if (sort != null) {
			pageRequest = new PageRequest(page - 1, rows, sort);
		} else {
			pageRequest = new PageRequest(page - 1, rows);
		}
		Page<Contact> contactPage = contactService.findAllByPage(pageRequest);
		ContactGrid contactGrid = new ContactGrid();
		contactGrid.setCurrentPage(contactPage.getNumber() + 1);
		contactGrid.setTotalPages(contactPage.getTotalPages());
		contactGrid.setTotalRecords(contactPage.getTotalElements());
		contactGrid.setContactData(contactPage.getContent());

		return contactGrid;
	}

	@RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("id") Long id) {
		Contact contact = contactService.findById(id);
		if (contact.getPhoto() != null) {
			Log.info("Downloading photo for id: {} with size: {}",
					contact.getId(), contact.getPhoto().length);
		}
		return contact.getPhoto();
	}
}
