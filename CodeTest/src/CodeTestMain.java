import java.util.Scanner;

public class CodeTestMain {
	
	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		int opt1=0;
		String opt="",sterm,svalue;
	
		while(!opt.equalsIgnoreCase("quit")) {
			
			System.out.println("\nType 'quit' to exit at any time, Press 'Enter' to continue");
			System.out.print("Select search options: \n 1. Press 1 to search"
					+ "\n 2. Press 2 to view a list of searchable fields"
					+ "\n Type 'quit' to exit \n");
			
			opt = sc.nextLine();
			
			if(opt.equals("1")) {
				System.out.println("\nSelect 1) Users or 2) Tickets or 3) Organizations");
				try {
					opt1 = Integer.parseInt(sc.nextLine());
	
					if(opt1 == 1 || opt1 == 2 || opt1 == 3) {
						
						System.out.println("\nEnter search term");
						sterm=sc.nextLine();
						System.out.println("\nEnter search value");
						svalue=sc.nextLine();
						
						DataRetrieve obj1 = CodeTestFactory.getDatafrom(opt1,sterm,svalue);
						if (obj1 != null) {
							obj1.display();
				        } else {
				            System.out.println("\nData cannot be retrieve \n");
				        }
						
					}else {
						System.out.println("\nWrong selection! Try again.. \n");
					}
				}
				catch(NumberFormatException nfe) {
					System.out.println("\nWrong selection! Accepted numbers only \n");
					
				}
				
			}else if(opt.equals("2")) {
				
				DataRetrieve obj2 = CodeTestFactory.getDatafrom(4,"","");
				if (obj2 != null) {
					obj2.display();
		        } else {
		            System.out.println("\nData cannot be retrieve \n");
		        }

			}else if(opt.equalsIgnoreCase("quit")) {
				System.out.println("Thank You! Have a nice day..");

			}else {
				System.out.println("\nWrong Selction.. Try again! \n");

			}
		}
		
	}

}
