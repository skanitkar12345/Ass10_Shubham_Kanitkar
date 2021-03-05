package classes;

import java.util.*;

import db.CreateConnection;

import java.io.*;
import java.sql.*;
//import db.CreateConnection;


public class ContactService {
	
	private static final String dbConfigFile = "src/database.properties";
	private static final String contactFile = "src/Contact.txt";
	private static final String outFile = "D:/contactfile.txt";
	
	public void addContact(Contact contact, List<Contact> contacts) {
		
		Scanner sc = new Scanner(System.in);
        int n;
        List<String> c = new ArrayList<>();
        
        System.out.println("Enter contact id : ");
        contact.setContactId(sc.nextInt());
        sc.nextLine();
        
        System.out.println("Enter contact name : ");
        contact.setContactName(sc.nextLine());
        
        System.out.println("Enter contact email : ");
        contact.setContactEmailId(sc.nextLine());
        
        System.out.println("How many contact numbers does contact have :");
        n = sc.nextInt();
        System.out.println("Enter the contact numbers ");
        sc.nextLine();
        for(int i=0; i<n; i++)
            c.add(sc.nextLine());
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
	
	public void sortContactsByName(List<Contact> contacts) {
		contacts.sort(null);
	}
	
	public void readContactsFromFile(List<Contact> contacts, String fName) throws IOException {
		
		FileInputStream fin = new FileInputStream(fName);
		Scanner sc = new Scanner(fin);
		String[] data;
		
		while (sc.hasNextLine()) {
			Contact contact = new Contact();
			List<String> numbers = new ArrayList<>();
			data = sc.nextLine().split(",");
			contact.setContactId(Integer.parseInt(data[0]));
			contact.setContactName(data[1]);
			contact.setContactEmailId(data[2]);
			String[] contactNumbers = data[3].split("-");
			for (String number : contactNumbers) {
				numbers.add(number);
			}
			contact.setContactNumbers(numbers);
			
			contacts.add(contact);
			
		}
		
		sc.close();
		fin.close();
	}
	
	public void serialize(List<Contact> contacts, String fName) throws IOException {
		File file = new File(fName);
		FileOutputStream fout = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fout);
		
		for (Contact contact : contacts) {
			out.writeObject(contact);
		}
		
		out.close();
		fout.close();
	}
	
	public List<Contact> deserialize(List<Contact> contacts,String fName) throws IOException, ClassNotFoundException {
		
		List<Contact> c = new ArrayList<>();
		
		File file = new File(fName);
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fin);
		
		for(int i = 0; i<contacts.size(); i++)
			c.add((Contact)in.readObject());
		
		in.close();
		fin.close();
		
		return c;
	}
	
	public Set<Contact> populateContactFromDb() throws SQLException, IOException{
		
		Set<Contact> resultSet = new HashSet<>();
		Connection conn = CreateConnection.databaseConnection(dbConfigFile);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("Select * From contact_tbl");
		
		
		while (rs.next()) {
			Contact contact = new Contact();
			contact.setContactId(rs.getInt(1));
			contact.setContactName(rs.getString(2));
			contact.setContactEmailId(rs.getString(3));
			String t = rs.getString(4);
			if (t != null) {
				contact.setContactNumbers(Arrays.asList(t.split(",")));
			}
			
			resultSet.add(contact);
		}
		
		statement.close();
		conn.close();
		return resultSet;
	}
	
	public boolean addContacts(List<Contact> existingContacts, Set<Contact> set) {
		return existingContacts.addAll(set);
	}
	
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		
		ContactService cs = new ContactService();
		List<Contact> contacts = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		Set<Contact> set = new HashSet<>();
		
		while (true) {
			System.out.println("------------------------------");
			System.out.println("Enter choice ");
			System.out.println("1. Add new contact");
			System.out.println("2. Remove a contact");
			System.out.println("3. Search by name");
			System.out.println("4. Search by number");
			System.out.println("5. Add contact number to existing numbers");
			System.out.println("6. Sort contacts according to name");
			System.out.println("7. Add contacts from file");
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
			        
			case 6: cs.sortContactsByName(contacts);
			        for (Contact contact : contacts) {
						System.out.println(contact.getContactName());
					}
			        
			        break;
			        
			case 7: cs.readContactsFromFile(contacts, contactFile);
			        break;
			        
			case 8: cs.serialize(contacts, outFile);
			        System.out.println("Done");
				    break;
				    
			case 9: List<Contact> contacts1 = cs.deserialize(contacts, outFile);
			        for (Contact contact : contacts1) {
						System.out.println(contact.getContactName());
					}
				    break;
				    
			case 10: set = cs.populateContactFromDb();
			         break;
			         
			case 11: cs.addContacts(contacts, set);
			         for (Contact contact : contacts) {
						System.out.println(contact.getContactName());
					}
				     break;
			   
			default: System.exit(0);
		
			}
		}
	}
}
