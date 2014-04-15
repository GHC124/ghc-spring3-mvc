/**
 * ContactService.java
 *
 *	
 */
package com.ghc.service.jpa;

import java.util.List;

import com.ghc.domain.Contact;

/**
 * 
 */
public interface ContactService {
	List<Contact> findAll();
	Contact findById(Long id);
	Contact save(Contact contact);
}
