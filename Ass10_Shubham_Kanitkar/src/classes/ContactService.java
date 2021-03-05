package classes;

import java.util.*;
import java.io.IOException;
import java.sql.*;
//import db.CreateConnection;


public class ContactService {
	
	private static final String dbConfigFile = "src/database.properties";
	
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
	
	public static void main(String[] args) throws IOException, SQLException {
		
		ContactService cs = new ContactService();
		List<Contact> contacts = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("------------------------------");
			System.out.println("Enter choice ");
			System.out.println("1. Add new contact");
			System.out.println("------------------------------");
			int n = sc.nextInt();
			
			switch(n) {
			case 1: cs.addContact(new Contact(), contacts);
			        break;
		
			}
		}
	}
}
