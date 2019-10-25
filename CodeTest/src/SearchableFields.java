import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SearchableFields implements DataRetrieve {
	
	private ArrayList<String> ticketlist = new ArrayList<>();
	private ArrayList<String> userlist = new ArrayList<>();
	private ArrayList<String> orglist = new ArrayList<>();

	@Override
	public void display() {
		
		JSONParser parser = new JSONParser();
		
		try{
			FileReader reader = new FileReader("/tickets.json");
			Object obj = parser.parse(reader);
			JSONArray tickets = (JSONArray) obj;
			
			tickets.forEach( ticket -> parseTicketObject( (JSONObject) ticket ) );
			
			List<String> newTicketList = ticketlist.stream().distinct().collect(Collectors.toList());
			
			System.out.println("\n--------------------------------------------------");
			System.out.println("Search Tickets with");
			newTicketList.forEach(field -> System.out.println(field));
			

			FileReader readeruser = new FileReader("/users.json");
			Object objuser = parser.parse(readeruser);
			JSONArray users = (JSONArray) objuser;
			
			users.forEach( user -> parseUserObject( (JSONObject) user ) );
			
			List<String> newUserList = userlist.stream().distinct().collect(Collectors.toList());
			
			System.out.println("\n--------------------------------------------------");
			System.out.println("Search Users with");
			newUserList.forEach(field -> System.out.println(field));
			
			
			FileReader readerorg = new FileReader("/organizations.json");
			Object objorg = parser.parse(readerorg);
			JSONArray organizations = (JSONArray) objorg;
			
			organizations.forEach( org -> parseOrgObject( (JSONObject) org ) );
			
			List<String> newOrgList = orglist.stream().distinct().collect(Collectors.toList());
			
			System.out.println("\n--------------------------------------------------");
			System.out.println("Search Organizations with");
			newOrgList.forEach(field -> System.out.println(field));
			
			
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
		
		ticket.keySet().parallelStream().forEachOrdered(key -> {
			
			ticketlist.add(key.toString());
		});
		
	}
	
	private void parseUserObject(JSONObject user) {
		
		user.keySet().parallelStream().forEachOrdered(key -> {
			
			userlist.add(key.toString());
		});
		
	}
	
	private void parseOrgObject(JSONObject org) {
		
		org.keySet().parallelStream().forEachOrdered(key -> {
			
			orglist.add(key.toString());
		});
		
	}

}
