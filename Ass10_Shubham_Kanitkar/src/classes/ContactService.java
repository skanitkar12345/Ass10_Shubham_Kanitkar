package classes;

import java.util.*;
import java.io.IOException;
import java.sql.*;
//import db.CreateConnection;


public class ContactService {
	
	//private static final String dbConfigFile = "src/database.properties";
	
	public void addContact(Contact contact, List<Contact> contacts) {
		
		Scanner scan = new Scanner(System.in);
        int n;
        List<String> c = new ArrayList<>();
        
        System.out.println("Enter contact id : ");
        contact.setContactId(scan.nextInt());
        
        System.out.println("Enter contact name : ");
        scan.nextLine();
        
        contact.setContactName(scan.nextLine());
        System.out.println("Enter contact email : ");
        contact.setContactEmailId(scan.nextLine());
        
        System.out.println("How many contact numbers does contact have :");
        n = scan.nextInt();
        System.out.println("Enter the contact numbers ");
        scan.nextLine();
        for(int i=0; i<n; i++)
            c.add(scan.nextLine());
        contact.setContactNumbers(c);
        
        contacts.add(contact);
	}
	
	public void removeContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException{
		
		for (Contact c : contacts) {
			if(c.getContactId()==contact.getContactId()) {
				contacts.remove(c);
				return;
			}
		}
		
		throw new ContactNotFoundException("Cannot Delete.Contact not found!");
	}
	
	public Contact searchContacyByName(String name, List<Contact> contacts) throws ContactNotFoundException{
		
		for (Contact c : contacts) {
			if(c.getContactName().equals(name)) {
				return c;
			}
		}
		
		throw new ContactNotFoundException("Contact not found!");
	}
	
	public List<String> searchContactByNumber(String contact, List<Contact> contacts) throws ContactNotFoundException{
		
		List<String> resultList = new ArrayList<>();
		
		for (Contact c : contacts) {
			List<String> numbers = c.getContactNumbers();
			
			for (String number : numbers) {
				if(number.contains(contact))
					resultList.add(number);
			}
					
		}
		
		if(resultList.isEmpty())
			throw new ContactNotFoundException("No contact has any number which matches with input!");
		
		return resultList;
	}
	
	public void addContactNumber(int id, String number, List<Contact> contacts) throws ContactNotFoundException{
		
		for (Contact contact : contacts) {
			if (contact.getContactId()==id) {
				List<String> numbers = contact.getContactNumbers();
				numbers.add(number);
				contact.setContactNumbers(numbers);
				System.out.println("Success");
				return;
			} 
		}
		throw new ContactNotFoundException("Unsuccessful");
		
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		ContactService cs = new ContactService();
		List<Contact> contacts = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		
		
		while (true) {
			System.out.println("------------------------------");
			System.out.println("Enter choice ");
			System.out.println("1. Add new contact");
			System.out.println("2. Remove a contact");
			System.out.println("3. Search by name");
			System.out.println("4. Search by number");
			System.out.println("5. Add contact number to existing numbers");
			System.out.println("------------------------------");
			int n = sc.nextInt();
			
			switch(n) {
			case 1: cs.addContact(new Contact(), contacts);
			        break;
			
			case 2: System.out.println("Enter contact id to be removed");
			        int id = sc.nextInt();
			        Contact c = new Contact();
					c.setContactId(id);
			        try {
						cs.removeContact(c, contacts);
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
	                break;
	                
			case 3: System.out.println("Enter contact name to be searched");
			        sc.nextLine();
			        String name = sc.nextLine();
			        try {
						Contact c1 = cs.searchContacyByName(name, contacts);
						System.out.println("Following contact found");
						System.out.println("------------------------------");
						System.out.println(c1.getContactName());
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
			        
			        break;
			        
			case 4: System.out.println("Enter contact number to be searched");
	                sc.nextLine();
	                String number = sc.nextLine();
	                try {
	                	List<String> numbers = cs.searchContactByNumber(number, contacts);
	                	System.out.println("Following numbers found");
	                	System.out.println("------------------------------");
	                	for (String string : numbers) {
							System.out.println(string);
						}
	                } catch (Exception e) {
	                	System.out.println(e.getMessage());
	                }
	                
	                break;
			case 5: System.out.println("Enter contact id and new number");
			        sc.nextLine();
			        String newNumber = sc.nextLine();
			        sc.nextLine();
			        int id1 = sc.nextInt();
			        try {
			        	cs.addContactNumber(id1, newNumber, contacts);
			        	
			        } catch (Exception e) {
			        	System.out.println(e.getMessage());
			        }
			        break;
			   
			default: System.exit(0);
		
			}
		}
	}
}
