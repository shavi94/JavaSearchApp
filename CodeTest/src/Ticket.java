import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ticket implements DataRetrieve {

	private Object term = null;
	private String value = null;
	private boolean ticketfound = false;
	
	public Ticket(Object term, String value) {
		this.value = value;
		this.term = term;
	}

	@Override
	public void display() {
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/tickets.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
//			System.out.println(users);
			
			users.forEach( ticket -> parseTicketObject( (JSONObject) ticket ) );
			
			if(ticketfound==false) {
				System.out.println("\nSearching tickets for "+term+" with a value of "+value);
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
	
	private void parseTicketObject(JSONObject ticket) {
		try {
			String termval = ticket.get(term).toString();
	//		System.out.println(termval);
			Object assId=null;
			Object subId=null;
			Object orgId=null;
			if(termval!=null && termval.equals(value)) {
				
				ticket.keySet().parallelStream().forEachOrdered(key -> {
					ticketfound = true;
					System.out.printf("%-25s %-10s %n",key,ticket.get(key));
				
				});
				
				assId = ticket.get("assignee_id");
				subId = ticket.get("submitter_id");
				orgId = ticket.get("organization_id");
				
				getDatafromOrganizations(orgId);
				getDatafromUsers(assId,subId);
				System.out.println("");
				
			}else if(term.equals("tags")) {
				JSONArray tagsArr = (JSONArray) ticket.get(term);
				if((value.equals("empty") || value.equals("") ) && tagsArr.isEmpty()) {
					
					ticket.keySet().parallelStream().forEachOrdered(key -> {
						ticketfound = true;
						System.out.printf("%-25s %-10s %n",key,ticket.get(key));
					});
					
					assId = ticket.get("assignee_id");
					subId = ticket.get("submitter_id");
					orgId = ticket.get("organization_id");
					
					getDatafromOrganizations(orgId);
					getDatafromUsers(assId,subId);
					System.out.println("");
					
				}
				Iterator<String> iterator = tagsArr.iterator();
				while(iterator.hasNext()) {
					if(iterator.next().equals(value)) {
						
						ticket.keySet().parallelStream().forEachOrdered(key -> {
							ticketfound = true;
							System.out.printf("%-25s %-10s %n",key,ticket.get(key));
						});
						
						assId = ticket.get("assignee_id");
						subId = ticket.get("submitter_id");
						orgId = ticket.get("organization_id");
						
						getDatafromOrganizations(orgId);
						getDatafromUsers(assId,subId);
						System.out.println("");
					}
				}
			}else if(value.equals("empty") && termval.equals("")) {
				ticket.keySet().parallelStream().forEachOrdered(key -> {
					ticketfound = true;
					System.out.printf("%-25s %-10s %n",key,ticket.get(key));
				});
				
				assId = ticket.get("assignee_id");
				subId = ticket.get("submitter_id");
				orgId = ticket.get("organization_id");
				
				getDatafromOrganizations(orgId);
				getDatafromUsers(assId,subId);
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
	
	private void getDatafromUsers(Object assId, Object subId) {
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/users.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
			
			users.forEach( user -> parseUsersObject( (JSONObject) user, assId,subId ) );
			
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
	
	private void parseUsersObject(JSONObject emp, Object assId, Object subId) {
		Long userid = (Long) emp.get("_id");
		if(userid!=null && userid.equals(assId)) {
			emp.keySet().parallelStream().forEachOrdered(key -> {
				if(key.equals("name")) {

					System.out.printf("%-25s %-10s %n","Assignee_name",emp.get(key));
				}
				
			});
			
		}else if(userid!=null && userid.equals(subId)) {
			emp.keySet().parallelStream().forEachOrdered(key -> {
				if(key.equals("name")) {

					System.out.printf("%-25s %-10s %n","Submitter_name",emp.get(key));
				}
				
			});
		}

		
	}

}
