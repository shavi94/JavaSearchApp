import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Organization implements DataRetrieve {
	
	private Object term = null;
	private String value = null;
	private int val=0;
	private int tickval=0;
	private boolean organizationfound = false;

	public Organization(Object term, String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
		this.term = term;
	}

	@Override
	public void display() {
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/organizations.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
//			System.out.println(users);
			
			users.forEach( org -> parseOrganizationObject( (JSONObject) org ) );
			
			if(organizationfound==false) {
				System.out.println("\nSearching organization for "+term+" with a value of "+value);
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
	
	private void parseOrganizationObject(JSONObject org) {
		try {
			String termval = org.get(term).toString();
	//		System.out.println(termval);
			Object assId=null;
			Object subId=null;
			Object orgId=null;
			val =0;
			tickval=0;
			if(termval!=null && termval.equals(value)) {
				
				org.keySet().parallelStream().forEachOrdered(key -> {
					organizationfound = true;
					System.out.printf("%-25s %-10s %n",key,org.get(key));
				});
				
				orgId = org.get("_id");
				
				getDatafromTickets(orgId);
				getDatafromUsers(orgId);
				System.out.println("");
				
			}else if(term.equals("tags")) {
				JSONArray tagsArr = (JSONArray) org.get(term);
				Iterator<String> iterator = tagsArr.iterator();
				while(iterator.hasNext()) {
					
					if(iterator.next().equals(value)) {
						
						org.keySet().parallelStream().forEachOrdered(key -> {
							organizationfound = true;
							System.out.printf("%-25s %-10s %n",key,org.get(key));
						});
						
						orgId = org.get("_id");
						
						getDatafromTickets(orgId);
						getDatafromUsers(orgId);
						System.out.println("");
					}
				}
			}else if(term.equals("domain_names")) {
				JSONArray domainArr = (JSONArray) org.get(term);
				Iterator<String> iterator = domainArr.iterator();
				while(iterator.hasNext()) {
					
					if(iterator.next().equals(value)) {
						
						org.keySet().parallelStream().forEachOrdered(key -> {
							organizationfound = true;
							System.out.printf("%-25s %-10s %n",key,org.get(key));
						});
						
						orgId = org.get("_id");
						
						getDatafromTickets(orgId);
						getDatafromUsers(orgId);
						System.out.println("");
					}
				}
			}		
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		
	}
	
	private void getDatafromUsers(Object orgId) {
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/users.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
			
			users.forEach( user -> parseUsersObject( (JSONObject) user, orgId) );
			
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
	
	private void parseUsersObject(JSONObject emp, Object orgId) {
		Long userid = (Long) emp.get("organization_id");
		if(userid!=null && userid.equals(orgId)) {
			emp.keySet().parallelStream().forEachOrdered(key -> {
				if(key.equals("name")) {
		
					System.out.printf("%-25s %-10s %n","User_name_"+val,emp.get(key));
					val++;
				}
				
			});
			
		}
		
	}
	
private void getDatafromTickets(Object orgid) {
		
		JSONParser parser = new JSONParser();
		
		try(FileReader reader = new FileReader("/tickets.json")){
			
			Object obj = parser.parse(reader);
			JSONArray users = (JSONArray) obj;
			int val=0;
			
			users.forEach( user -> parseTicketObject( (JSONObject) user, orgid ) );
			
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
	
	private void parseTicketObject(JSONObject emp, Object orgid) {
		Long tickorgid = (Long) emp.get("organization_id");
		if(tickorgid!=null && tickorgid.equals(orgid)) {
			emp.keySet().parallelStream().forEachOrdered(key -> {
				if(key.equals("subject")) {

					System.out.printf("%-25s %-10s %n","ticket_"+tickval,emp.get(key));
					tickval++;
				}
				
			});
			
		}
		
	}

}
