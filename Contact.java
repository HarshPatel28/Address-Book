
public class Contact {
	
	/*
	 * This is the main "node" class that will contain each individual
	 * contact's information and also link to other contacts/nodes.
	 */
	
	public String name;
	public long number;
	public String email;
	public String address;
	Contact left;
	Contact right;
	
	public Contact(String name, long number, String email, String address) {
		this.name = name;
		this.number = number;
		this.email = email;
		this.address = address;
		this.left = null;
		this.right = null;
	}
	
}