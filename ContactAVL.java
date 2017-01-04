import java.util.NoSuchElementException;

public class ContactAVL {
	
	/*
	 * This is the class that will handle all the important methods,
	 * that'll add, delete, search, sort, and print the contacts of the
	 * AddressBook. It is modeled after the AVL tree data structure, 
	 * and the Contacts are thus sorted in AVL tree format to ensure
	 * O(log(n)) complexity for all the methods. 
	 * 
	 */
	
	Contact root;
	int sizeOfAB;
	
	public ContactAVL() {
		root = null;
		sizeOfAB = 0;
	}
	
	
	// should this call the sort() method or do it manually in driver??????
	public void addContact(String name, long number, String email, String address)
	throws IllegalArgumentException {	// throw argument if same name AND number!!!!!
		Contact ptr = root, prev = null;
		Contact newContact = new Contact(name, number, email, address);
		if(ptr == null) {	// AddressBook is empty
			root = newContact;
			sizeOfAB++;
			return;
		}
		int equals = 0;
		while(ptr != null) {
			equals = name.compareToIgnoreCase(ptr.name);
			if(equals == 0) { 
				if(number == ptr.number) {
					throw new IllegalArgumentException("Contact already exists.");
				} else {
					Contact temp = ptr.right;
					ptr.right = newContact;
					newContact.right = temp;
					sizeOfAB++;
					return;
				}
			} else {
				prev = ptr;
				ptr = equals > 0 ? ptr.right : ptr.left;
			}
		}
		// after while loop, prev is at the last node at which to add new Contact
		if(equals > 0) {
			prev.right = newContact;
		} else {
			prev.left = newContact;
		}
		sizeOfAB++;
	}
	
	public void sortContacts() {
		
	}
	
	public void deleteContact(String name, long number)
	throws NoSuchElementException {
		
	}
	
	public void updateHeight() {	
		
	}
	
	public void printContacts() {
		
	}
	
	public boolean contains(String name) {	
		
	}	
	
}
