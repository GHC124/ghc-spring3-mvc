/**
 * ContactService.java
 *
 *	
 */
package com.ghc.service.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ghc.domain.Contact;

/**
 * 
 */
public interface ContactService {
	List<Contact> findAll();
	Contact findById(Long id);
	Contact save(Contact contact);
	Page<Contact> findAllByPage(Pageable pageable);
}
