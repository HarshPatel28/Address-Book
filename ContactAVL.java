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
	throws IllegalArgumentException {	// throw argument if same name AND number!!!!!
		Contact ptr = root, prev = null;
		Contact newContact = new Contact(name, number, email, address);
		if(ptr == null) {	// AddressBook is empty
			root = newContact;
			sizeOfAB++;
			updateHeight();
			updateParent();
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
					updateParent();
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
		updateParent();
		sizeOfAB++;
	}
	
	public void sortContacts() {
		
	}
	
	public void deleteContact(String name, long number)// what if duplicate name but different number?????
	throws NoSuchElementException {
		
	}
	
	public void updateHeight() {
		if(root == null) {
			return;
		}
	
		if(recursiveUpdateHeight(root) == -1) {
			updateHeight();
		}
	}
	
	private int recursiveUpdateHeight(Contact rootNode) {
		if(rootNode == null) {
			return -1;
		}
		rootNode.height = 1 + recursiveUpdateHeight(rootNode.left);
		rootNode.height = Math.max(rootNode.height, (1 + recursiveUpdateHeight(rootNode.right)));
		
		
		
		if(isBalanced(rootNode)) {
			return -1;
		}
		return rootNode.height;
	}
	
	public boolean isBalanced(Contact rootNode) {
		int a = 0;
		int b = 0;
		if(rootNode.left == null) {
			a = 0;
		} else {
			a = rootNode.height;
		}
		if(rootNode.right == null) {
			b = 0;
		} else {
			b = rootNode.height;
		}
		if((a-b) == -2) {	// right is unbalanced
			if(rootNode.right.right == null) {
				
			} else {
				
			}
			return true;
		} else if ((a-b) == 2) {	// left is unbalanced
			if(rootNode.left.left == null) {
				
			} else {
				
			}
			return true;
		} else {
			return false;
		}
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
	
	public void updateParent() { 	
		recursiveUpdateParent(root);
	}
	
	private Contact recursiveUpdateParent(Contact rootNode) {
		if(rootNode == null) {
			return null;
		}
		if(rootNode.left != null) {
			rootNode.left.parent = rootNode;
		}
		if(rootNode.right != null) {
			rootNode.right.parent = rootNode;
		}
		recursiveUpdateParent(rootNode.left);
		recursiveUpdateParent(rootNode.right);
		// should this return this?????
		return null;
	}
	
	public boolean contains(String name) {	// what if user wants to search using number????
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
	}	// this method might be called by update method
	
}
