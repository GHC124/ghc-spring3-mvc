package com.ghc.web.form;

import java.util.List;

import com.ghc.domain.Contact;

public class ContactGrid {
	private int mTotalPages;
	private int mCurrentPage;
	private long mTotalRecords;
	private List<Contact> mContactData;
	
	public int getTotalPages() {
		return mTotalPages;
	}
	public void setTotalPages(int totalPages) {
		mTotalPages = totalPages;
	}
	public int getCurrentPage() {
		return mCurrentPage;
	}
	public void setCurrentPage(int currentPage) {
		mCurrentPage = currentPage;
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
