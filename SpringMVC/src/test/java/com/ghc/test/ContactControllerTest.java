/**
 * ContactControllerTest.java
 *
 *	
 */
package com.ghc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

import com.ghc.domain.Contact;
import com.ghc.service.jpa.ContactService;
import com.ghc.web.controller.ContactController;

/**
 * 
 */
public class ContactControllerTest extends AbstractControllerTest {
	private final List<Contact> contacts = new ArrayList<Contact>();
	private ContactService contactService;

	@Before
	public void initContacts() {
		// Initialize list of contacts for mocked ContactService
		Contact contact = new Contact();
		contact.setId(1l);
		contact.setFirstName("Clarence");
		contact.setLastName("Ho");
		contacts.add(contact);
	}

	@Test
	public void testList() throws Exception {
		contactService = mock(ContactService.class);
		when(contactService.findAll()).thenReturn(contacts);
		ContactController contactController = new ContactController();
		ReflectionTestUtils.setField(contactController, "contactService",
				contactService);
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = contactController.list(uiModel);
		assertNotNull(result);
		assertEquals(result, "contacts/list");
		List<Contact> modelContacts = (List<Contact>) uiModel.get("contacts");
		assertEquals(1, modelContacts.size());
	}
}
