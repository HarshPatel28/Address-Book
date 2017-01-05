import java.util.NoSuchElementException;

public class ContactAVL {
	
	/*
	 * This is the class that will handle all the important methods,
	 * that'll add, delete, search, sort, and print the contacts of the
	 * AddressBook. It is modeled after the AVL tree data structure, 
	 * and the Contacts are thus sorted in AVL tree format to ensure
	 * O(log(n)) complexity for add, search, and delete. 
	 * 
	 */
	
	Contact root;
	int sizeOfAB;
	
	public ContactAVL() {
		root = null;
		sizeOfAB = 0;
	}
	
	public void addContact(String name, long number, String email, String address)
	throws IllegalArgumentException {	// throw argument if same name AND number
		Contact ptr = root, prev = null;
		Contact newContact = new Contact(name, number, email, address);
		if(ptr == null) {	// AddressBook is empty
			root = newContact;
			sizeOfAB++;
			updateHeight();
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
					updateHeight();
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
		updateHeight();
		sizeOfAB++;
	}
	
	public void sortContacts() {
		
	}
	
	public void deleteContact(String name, long number)
	throws NoSuchElementException {
		
	}
	
	public void updateHeight() {
		if(root == null) {
			return;
		}
		recursiveUpdateHeight(root);
	}
	
	private int recursiveUpdateHeight(Contact root) {
		if(root == null) {
			return -1;
		}
		root.height = 1 + recursiveUpdateHeight(root.left);
		root.height = Math.max(root.height, (1 + recursiveUpdateHeight(root.right)));
		return root.height;
	}
	
	public void printContacts() {
		if(root == null) {
			System.out.println("Address book is empty.");
			return;
		}
		recursivePrintContacts(root);
	}
	
	private void recursivePrintContacts(Contact rootNode) {
		if(rootNode == null) {
			return;
		}
		recursivePrintContacts(rootNode.left);
		System.out.println(rootNode.name + "\t" + rootNode.number + "\t" + 
				rootNode.email + "\t" + rootNode.address);
		recursivePrintContacts(rootNode.right);
		
	}
	
	public boolean contains(String name) {
		Contact checker = root;
		int equals = 0;
		while(checker != null) {
			equals = name.compareToIgnoreCase(checker.name);
			if(equals == 0) {
				return true;
			}
			checker = equals > 0 ? checker.right : checker.left;
		}
		return false;
	}	
	
}
