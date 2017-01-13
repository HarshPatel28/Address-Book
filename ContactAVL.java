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
	throws IllegalArgumentException {	
		Contact ptr = root, prev = null;
		Contact newContact = new Contact(name, number, email, address);
		if(ptr == null) {	// AddressBook is empty
			root = newContact;
			sizeOfAB++;
			root.height = 0;
			root.parent = null;
			return;
		}
		int equals = 0;
		while(ptr != null) {
			equals = name.compareToIgnoreCase(ptr.name);
			if(equals == 0) { 
				if(number == ptr.number && email.equalsIgnoreCase(ptr.email) && address.equalsIgnoreCase(ptr.address)) {
					throw new IllegalArgumentException("Contact already exists.");
				} else {
					Contact temp = ptr.right;
					ptr.right = newContact;
					newContact.right = temp;
					sizeOfAB++;
					updateHeight();
					rebalance(root);
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
		rebalance(root);
		updateParent();
		sizeOfAB++;
	}
	
	public void sortContacts(Contact rootNode, String unbalanced) {
		if(unbalanced.equals("right")) {
			if(rootNode.right.right == null) {
				rotateRL(rootNode);
			} else if (rootNode.right.left == null) {
				rotateRR(rootNode);
			} else if (rootNode.right.left.height > rootNode.right.right.height) {
				rotateRL(rootNode);
			} else if (rootNode.right.right.height > rootNode.right.left.height) {
				rotateRR(rootNode);
			}
		} else if (unbalanced.equals("left")) {
			if(rootNode.left.left == null) {
				rotateLR(rootNode);
			} else if (rootNode.left.right == null) {
				rotateLL(rootNode);
			} else if (rootNode.left.right.height > rootNode.left.left.height) {
				rotateLR(rootNode);
			} else if (rootNode.left.left.height > rootNode.left.right.height) {
				rotateLL(rootNode);
			}
		}
	}
	
	private void rotateRR(Contact rootNode) {
		boolean rootIsMainRoot;
		Contact tempParent = null;
		boolean isRight = false;
		if(rootNode == root) {
			rootIsMainRoot = true;
		} else {
			tempParent = rootNode.parent;
			if(tempParent.right == rootNode) {
				isRight = true;
			} else {
				isRight = false;
			}
			rootIsMainRoot = false;
		}
		
		Contact child = rootNode.right;
		Contact temp = child.left;
		
		rootNode.right = temp;
		if(temp != null) {
			temp.parent = rootNode;
		}
		child.left = rootNode;
		rootNode.parent = child;
		
		if(rootIsMainRoot) {
			root = child;
			child.parent = null;
		} else {
			child.parent = tempParent;
			if(isRight) {
				tempParent.right = child;
			} else {
				tempParent.left = child;
			}
		}
		
		updateHeight();
	}
	
	private void rotateRL(Contact rootNode) {
		Contact child = rootNode.right;
		Contact gChild = child.left;
		Contact temp = gChild.right;
		
		gChild.right = child;
		child.parent = gChild;
		child.left = temp;
		if(temp != null) {
			temp.parent = child;
		}
		rootNode.right = gChild;
		gChild.parent = rootNode;
		
		// Afterwards, it'll be a RR case.
		rotateRR(rootNode);
	}
	
	private void rotateLL(Contact rootNode) {
		boolean rootIsMainRoot;
		Contact tempParent = null;
		boolean isLeft = false;
		if(rootNode == root) {
			rootIsMainRoot = true;
		} else {
			tempParent = rootNode.parent;
			if(tempParent.left == rootNode) {
				isLeft = true;
			} else {
				isLeft = false;
			}
			rootIsMainRoot = false;
		}
		
		Contact child = rootNode.left;
		Contact temp = child.right;
		
		rootNode.left = temp;
		if(temp != null) {
			temp.parent = rootNode;
		}
		child.right = rootNode;
		rootNode.parent = child;
		
		if(rootIsMainRoot) {
			root = child;
			child.parent = null;
		} else {
			child.parent = tempParent;
			if(isLeft) {
				tempParent.left = child;
			} else {
				tempParent.right = child;
			}
		}
		
		updateHeight();
	}
	
	private void rotateLR(Contact rootNode) {
		Contact child = rootNode.left;
		Contact gChild = child.right;
		Contact temp = gChild.left;
		
		gChild.left = child;
		child.parent = gChild;
		child.right = temp;
		if(temp != null) {
			temp.parent = child;
		}
		rootNode.left = gChild;
		gChild.parent = rootNode;
		
		// Afterwards, it'll be a LL case.
		rotateLL(rootNode);
	}
	
	public void deleteContact(String name)
	throws NoSuchElementException {
		
		if(!contains(name)) {
			throw new NoSuchElementException("This contact does not exist.");
		}
		/*
			if(containsMultiple(name)) {
				// SEND TO A SPECIAL DELETION CASE.
			}
		*/
		
		Contact ptr = root, prev = null;
		int equals = 0;
		
		while(ptr != null) {
			equals = name.compareToIgnoreCase(ptr.name);
			if(equals == 0) {
				break;	// contact is found
			}
			prev = ptr;
			ptr = equals > 0 ? ptr.right : ptr.left;
		}
		
		if(ptr.right != null && ptr.left != null) {
			Contact replacement = ptr.left;
			prev = ptr;
			while(replacement.right != null) {
				prev = replacement;
				replacement = replacement.right;
			}
			ptr.name = replacement.name;
			ptr.number = replacement.number;
			ptr.email = replacement.email;
			ptr.address = replacement.address;
			ptr = replacement;
		} 
		
		if(ptr.left == null && ptr.right == null) {
			if(ptr == root) {
				root = null;
				sizeOfAB--;
				updateHeight(); 
				rebalance(root); 
				updateParent();
				return;
			}
			if(prev.left == ptr) {
				prev.left = null;
			} else {
				prev.right = null;
			}
			
			sizeOfAB--;
			updateHeight(); 
			rebalance(root); 
			updateParent();
			return;
		}
		
		if(ptr.left == null && ptr.right != null) {
			if(ptr == root) {
				root = ptr.right;
				sizeOfAB--;
				updateHeight(); 
				rebalance(root); 
				updateParent();
				return;
			}
			if(prev.left == ptr) {
				prev.left = ptr.right;
			} else {
				prev.right = ptr.right;
			}
			sizeOfAB--;
			updateHeight(); 
			rebalance(root); 
			updateParent();
			return;
		} 
		if(ptr.right == null && ptr.left != null) {
			if(ptr == root) {
				root = ptr.left;
				sizeOfAB--;
				updateHeight(); 
				rebalance(root); 
				updateParent();
				return;
			}
			if(prev.left == ptr) {
				prev.left = ptr.left;
			} else {
				prev.right = ptr.left;
			}
			sizeOfAB--;
			updateHeight(); 
			rebalance(root); 
			updateParent();
			return;
		}
	}
	
	public void specialDeletionCase(Contact rootNode, String name, long number) {
		if(rootNode == null) {
			return;
		}
		
		specialDeletionCase(rootNode.left, name, number);
		specialDeletionCase(rootNode.right, name, number);
		if(rootNode.name.equalsIgnoreCase(name) && rootNode.number == number) {
			
			if(rootNode.right != null && rootNode.left != null) {
				Contact replacement = rootNode.left;
				Contact prev = rootNode;
				while(replacement.right != null) {
					prev = replacement;
					replacement = replacement.right;
				}
				rootNode.name = replacement.name;
				rootNode.number = replacement.number;
				rootNode.email = replacement.email;
				rootNode.address = replacement.address;
				rootNode = replacement;
			}
			
			Contact prev = rootNode.parent;
			
			if(rootNode.left == null && rootNode.right == null) {
				if(rootNode == root) {
					root = null;
					sizeOfAB--;
					updateHeight(); 
					rebalance(root); 
					updateParent();
					return;
				}
				if(prev.left == rootNode) {
					prev.left = null;
				} else {
					prev.right = null;
				}
				
				sizeOfAB--;
				updateHeight(); 
				rebalance(root); 
				updateParent();
				return;
			}
			
			if(rootNode.left == null && rootNode.right != null) {
				if(rootNode == root) {
					root = rootNode.right;
					sizeOfAB--;
					updateHeight(); 
					rebalance(root); 
					updateParent();
					return;
				}
				if(prev.left == rootNode) {
					prev.left = rootNode.right;
				} else {
					prev.right = rootNode.right;
				}
				sizeOfAB--;
				updateHeight(); 
				rebalance(root); 
				updateParent();
				return;
			} 
			if(rootNode.right == null && rootNode.left != null) {
				if(rootNode == root) {
					root = rootNode.left;
					sizeOfAB--;
					updateHeight(); 
					rebalance(root); 
					updateParent();
					return;
				}
				if(prev.left == rootNode) {
					prev.left = rootNode.left;
				} else {
					prev.right = rootNode.left;
				}
				sizeOfAB--;
				updateHeight(); 
				rebalance(root); 
				updateParent();
				return;
			}
			
		}
	}
	
	public void updateHeight() {
		if(root == null) {
			return;
		}
		
		recursiveUpdateHeight(root);
	}
	
	private int recursiveUpdateHeight(Contact rootNode) {
		if(rootNode == null) {
			return -1;
		}
		rootNode.height = 1 + recursiveUpdateHeight(rootNode.left);
		rootNode.height = Math.max(rootNode.height, (1 + recursiveUpdateHeight(rootNode.right)));
		
		return rootNode.height;
	}
	
	public int rebalance(Contact rootNode) {
		if(rootNode == null) {
			return -1;
		}
		
		rebalance(rootNode.left);
		rebalance(rootNode.right);
		int a, b;
		if(rootNode.left == null && rootNode.height < 2) {
			return -1;
		} else if (rootNode.right == null && rootNode.height < 2) {
			return -1;
		} else {
			if(rootNode.left == null) {
				a = 0;
			} else {
				a = rootNode.left.height + 1;
			}
			if(rootNode.right == null) {
				b = 0;
			} else {
				b = rootNode.right.height + 1;
			}
			if((a-b) != -2 && (a-b) != 2) {
				return -1;
			} else if ((a-b) == -2) {
				// right is unbalanced
				sortContacts(rootNode, "right");
			} else if ((a-b) == 2) {
				// left is unbalanced
				sortContacts(rootNode, "left");
			}
		}
		
		return -1;
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
		
		System.out.printf("%-30s%-12d%-30s%s", rootNode.name, rootNode.number,
				rootNode.email, rootNode.address);
		System.out.println();
		//System.out.println(rootNode.name + "\t" + rootNode.number + "\t" + 
		//		rootNode.email + "\t" + rootNode.address);
		
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
		return null;
	}
	
	public void search(Contact rootNode, String name) {
		if(rootNode == null) {
			return;
		}
		name = name.toLowerCase();
		
		search(rootNode.left, name);
		if(rootNode.name.toLowerCase().contains(name)) {
			System.out.println(rootNode.name + "\t" + rootNode.number + "\t" + 
					rootNode.email + "\t" + rootNode.address);
		}
		search(rootNode.right, name);
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
	
	public boolean containsMultiple(String name) {
		if(recursiveContainsMultiple(root, name) > 1) {
			return true;
		}
		return false;
	}
	
	private int recursiveContainsMultiple(Contact rootNode, String name) {
		if(rootNode == null) {
			return 0;
		} 
			
		int left = recursiveContainsMultiple(rootNode.left, name);
		int right = recursiveContainsMultiple(rootNode.right, name);
		
		int counter = left + right;
		if(rootNode.name.equalsIgnoreCase(name)) {
			counter++;
		}
		return counter;
	}
	
	public boolean isEmpty() {
		if(root == null) {
			return true;
		} else {
			return false;
		}
	}
	
}