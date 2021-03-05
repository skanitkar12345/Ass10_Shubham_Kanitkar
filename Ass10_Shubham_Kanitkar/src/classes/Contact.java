package classes;

import java.io.Serializable;
import java.util.*;

public class Contact implements Comparable<Contact>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int contactId;
	private String contactName;
	private String contactEmailId;
	private List<String> contactNumbers;
	
	public int getContactId() {
		return contactId;
	}
	
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactEmail() {
		return contactEmailId;
	}
	
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}
	
	public List<String> getContactNumbers() {
		return contactNumbers;
	}
	
	public void setContactNumbers(List<String> contactNumbers) {
		this.contactNumbers = contactNumbers;
	}
	
	@Override
	public int compareTo(Contact c) {
		return (this.getContactName().compareTo(c.getContactName()));
	}

}
