import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
public class AddressBook {
	
	/*
	 * Main driver class that asks the user for what actions to perform, 
	 * such as adding, deleting, searching, and printing all contacts.
	 * 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		ContactAVL AB = new ContactAVL();
		
		//System.out.printf("%-10s %-10s %-20s\n", "one", "two", "");
		String[] operations = {"add", "delete", "print", "search", "check", "size"};
		
		System.out.println("Hello! This is your personal address book! You can 'add' a contact, 'delete' a contact,"
				+ "'print' all contacts, \n'search' for a contact, 'check' if a contact exists, and check the 'size' of your "
				+ "address book!\n");
		
		System.out.println("Simply type 'add', 'delete', 'print', 'search', 'check', and 'size' depending on "
				+ "what operation you want to perform!\n"
				+ "To exit the program, simply write 'EXIT'.\n");
		
		while(true) {
			String response = "";
			System.out.print("What operation do you want to perform? ");
			response = input.readLine().toLowerCase();
			
			while(true) {	// checks if response is acceptable 
				if(response.equalsIgnoreCase("EXIT")) {
					System.out.println("\nAddress Book closed.");
					System.exit(0);
				}
				boolean acceptable = false;
				for(int i = 0; i < operations.length; i++) {
					if(operations[i].equals(response)) {
						System.out.println();
						acceptable = true;
					}
				}
				if(acceptable) {
					break;
				} else {
					System.out.print("\nPlease enter a valid operation. You can select from 'add', 'delete', 'print', "
							+ "'search', 'check', and 'size'. To exit, type 'EXIT'."
							+ "\nWhat operation do you want to perform? ");
					response = input.readLine().toLowerCase();
					
				}
			}
			
			if((response.equals("delete") || response.equals("print") || response.equals("search") ||
					response.equals("check")) && AB.isEmpty()) {
				System.out.println("Sorry, this operation cannot be performed as your Address Book is empty! "
						+ "Please add a contact.\n");
				continue;
			}
			
			if(response.equalsIgnoreCase("add")) {
				System.out.print("Enter the full name of the contact: ");
				String name = input.readLine();
				long number;
				try {
					System.out.print("Enter the number of the contact: ");
					String inputNum = input.readLine();
					BigInteger bigNum = new BigInteger(inputNum);
					number = bigNum.longValue();
				} catch (NumberFormatException e) {
					System.out.println("Please enter a valid number next time.\n");
					continue;
				}
				
				System.out.print("Enter the email of the contact: ");
				String email = input.readLine();
				if((!email.contains("@")) || (!email.contains("."))) {
					System.out.println("Please enter a valid email next time.\n");
					continue;
				}
					
				System.out.print("Enter the address of the contact: ");
				String address = input.readLine();
				try {
					AB.addContact(name, number, email, address);
					System.out.println("Contact succesfully added!");
					System.out.println();
				} catch (IllegalArgumentException e) {
					System.out.println("This contact already exists in your Address Book!\n");
				}
				continue;
			} else if (response.equalsIgnoreCase("delete")) {
				
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("print")) {
				if(AB.sizeOfAB == 1) {
					System.out.println("There is currently 1 contact in your Address Book.\n");
				} else {
					System.out.println("There are currently " + AB.sizeOfAB + " contacts in your Address Book.\n");
				}
				System.out.printf("%-30s%-12s%-30s%s\n\n", "Name", "Number", "Email", "Address");
				AB.printContacts();
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("search")) {
				System.out.print("Enter a name you would like to search for in your Address Book: ");
				String name = input.readLine();
				System.out.println("Here are the following contacts that have the name you provided: ");
				AB.search(AB.root, name);
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("check")) {
				System.out.println("Enter a contact name to see if it is already in your Address Book: ");
				String name = input.readLine();
				if(AB.contains(name)) {
					if(AB.containsMultiple(name)) {
						System.out.println("There are multiple people with this name that exist in your Address Book!");
					} else {
						System.out.println("This contact exists in your Address Book!");
					}
					System.out.println("To display their information, use the 'search' feature and input their name.");
				} else {
					System.out.println("This contact does not exist in your Address Book. Please 'add' the contact.");
				}
				System.out.println();
				continue;
			} else if (response.equalsIgnoreCase("size")) {
				if(AB.sizeOfAB == 1) {
					System.out.println("There is currently 1 contact in the Address Book.");
				} else {
					System.out.println("There are currently " + AB.sizeOfAB + " contacts in the Address Book.");
				}
				System.out.println();
				continue;
			}
			
		} // end of main while loop
	}

}
