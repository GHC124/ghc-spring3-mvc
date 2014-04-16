/**
 * ContactRepository.java
 *
 *	
 */
package com.ghc.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ghc.domain.Contact;

/**
 * 
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
	
}
