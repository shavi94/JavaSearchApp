import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class User implements DataRetrieve {
	
	private String term = null;
	private String value = null;
	private int val=0;
	private boolean userfound = false;

	public User(String term, String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
		this.term = term;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
//		System.out.println("User "+term+": "+value+"\n");
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/users.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
//			System.out.println(users);
			
			users.forEach( user -> parseUserObject( (JSONObject) user ) );
			
			if(userfound==false) {
				System.out.println("\nSearching users for "+term+" with a value of "+value);
				System.out.println("No results found");
			}
			
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void parseUserObject(JSONObject emp) {
		try{
			String termval = emp.get(term).toString();
	//		System.out.println(termval);
			Object extid=null;
			Object orgId=null;
			if(termval!=null && termval.equals(value)) {
				
				emp.keySet().parallelStream().forEachOrdered(key -> {
					userfound = true;
					System.out.printf("%-25s %-10s %n",key,emp.get(key));
				});
				
				orgId = emp.get("organization_id");
				extid = emp.get("_id");
				
				getDatafromOrganizations(orgId);
				getDatafromTickets(extid);
				System.out.println("");
			}
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		
	}

	private void getDatafromOrganizations(Object orgId) {
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/organizations.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
			int val=0;
			
			users.forEach( user -> parseOrganizationsObject( (JSONObject) user, orgId ) );
			
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void parseOrganizationsObject(JSONObject emp, Object id) {
		Long orgid = (Long) emp.get("_id");
		if(orgid!=null && orgid.equals(id)) {
			emp.keySet().parallelStream().forEachOrdered(key -> {
				if(key.equals("name")) {

					System.out.printf("%-25s %-10s %n","organization_name",emp.get(key));
				}
				
			});
			
		}
		
	}

	private void getDatafromTickets(Object extid) {
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/tickets.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
			int val=0;
			
			users.forEach( user -> parseTicketObject( (JSONObject) user, extid ) );
			
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseTicketObject(JSONObject emp, Object id) {
		Long submitterid = (Long) emp.get("submitter_id");
		if(submitterid!=null && submitterid.equals(id)) {
			emp.keySet().parallelStream().forEachOrdered(key -> {
				if(key.equals("subject")) {
//					System.out.println("ticket_"+val+"\t \t "+ emp.get(key));
					System.out.printf("%-25s %-10s %n","ticket_"+val,emp.get(key));
					val++;
				}
				
			});
			
		}
		
	}
	
	
	
	

}
