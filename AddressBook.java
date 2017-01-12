import java.util.Scanner;

public class AddressBook {
	
	
	/*
	 * Main driver class that asks the user for what actions to perform, 
	 * such as adding, deleting, searching, and printing all contacts.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		ContactAVL AB = new ContactAVL();
		
		String[] operations = {"add", "delete", "print", "search", "check", "size"};
		
		System.out.println("Hello! This is your personal address book! You can 'add' a contact, 'delete' a contact,"
				+ "'print' all contacts, \n'search' for a contact, 'check' if a contact exists, and check the 'size' of your "
				+ "address book!\n");
		
		System.out.println("Simply type 'add', 'delete', 'print', 'search', 'check', and 'size' depending on "
				+ "what operation you want to perform!\n"
				+ "To exit the program, simply write 'EXIT'.\n");
		
		String response = "";
		do {
			System.out.print("What operation do you want to perform? ");
			response = scanner.next().toLowerCase();
			if(response.equalsIgnoreCase("EXIT")) {
				System.out.println();
				System.out.println("Address Book closed.");
				System.exit(0);
			}
			while(true) {
				boolean acceptable = false;
				for(int i = 0; i < operations.length; i++) {
					if(operations[i].equals(response)) {
						acceptable = true;
					}
				}
				if(acceptable) {
					break;
				} else {
					System.out.print("\nPlease enter a valid operation. You can select from 'add', 'delete', 'print', "
							+ "'search', 'check', and 'size'. To exit, type 'EXIT'."
							+ "\nWhat operation do you want to perform? ");
					response = scanner.next().toLowerCase();
				}
				if(response.equalsIgnoreCase("EXIT")) {
					System.out.println();
					System.out.println("Address Book closed.");
					System.exit(0);
				}
			}
			System.out.println();
			if(response.equals("delete") || response.equals("print") || response.equals("search") ||
					response.equals("check") && AB.isEmpty()) {
				System.out.println("Sorry, this operation cannot be performed as your Address Book is empty! "
						+ "Please add a contact.\n");
				continue;
			}
			if(response.equalsIgnoreCase("add")) {
				System.out.println("Enter the full name of the contact: ");
				String name = scanner.nextLine();
				
				System.out.print("Enter the number of the contact: ");
				long number = scanner.nextLong();
				
				System.out.print("Enter the email of the contact: ");
				String email = scanner.next();
				
				System.out.print("Enter the address of the contact: ");
				String address = scanner.nextLine();
				
				AB.addContact(name, number, email, address);
				System.out.println();
				System.out.println("Contact succesfully added!");
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("delete")) {
				
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("print")) {
				
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("search")) {
				
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("check")) {
				
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("size")) {
				System.out.println("There are currently " + AB.sizeOfAB + " contacts in the Address Book.");
				System.out.println();
				continue;
			}
			
		} while (!response.equalsIgnoreCase("EXIT"));
		
	}

}
