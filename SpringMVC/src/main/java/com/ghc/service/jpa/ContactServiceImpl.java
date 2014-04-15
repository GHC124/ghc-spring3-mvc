/**
 * ContactServiceImpl.java
 *
 *	
 */
package com.ghc.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghc.domain.Contact;
import com.ghc.repository.jpa.ContactRepository;
import com.ghc.util.Lists;

/**
 * 
 */
@Service("contactService")
@Repository
@Transactional
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepository contactRepository;	
	
	/* (non-Javadoc)
	 * @see com.ghc.service.jpa.ContactService#findAll()
	 */
	
	@Override
	@Transactional(readOnly=true)
	public List<Contact> findAll() {
		return Lists.newArrayList(contactRepository.findAll());
	}

	/* (non-Javadoc)
	 * @see com.ghc.service.jpa.ContactService#findById(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Contact findById(Long id) {
		return contactRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.ghc.service.jpa.ContactService#save(com.ghc.domain.Contact)
	 */
	@Override
	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

}
