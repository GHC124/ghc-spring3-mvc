/**
 * ContactRepository.java
 *
 *	
 */
package com.ghc.repository.jpa;

import org.springframework.data.repository.CrudRepository;

import com.ghc.domain.Contact;

/**
 * 
 */
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
}
