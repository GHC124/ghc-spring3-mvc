package com.ghc.web.form;

import java.util.List;

import com.ghc.domain.Contact;

public class ContactGrid {
	private int mTotalPages;
	private int mCurrentPages;
	private long mTotalRecords;
	private List<Contact> mContactData;
	
	public int getTotalPages() {
		return mTotalPages;
	}
	public void setTotalPages(int totalPages) {
		mTotalPages = totalPages;
	}
	public int getCurrentPages() {
		return mCurrentPages;
	}
	public void setCurrentPages(int currentPages) {
		mCurrentPages = currentPages;
	}
	public long getTotalRecords() {
		return mTotalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		mTotalRecords = totalRecords;
	}
	public List<Contact> getContactData() {
		return mContactData;
	}
	public void setContactData(List<Contact> contactData) {
		mContactData = contactData;
	}
	
		
}
