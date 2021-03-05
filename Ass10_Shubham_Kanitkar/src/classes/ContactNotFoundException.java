package classes;

public class ContactNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public ContactNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	
}
