
public abstract class CodeTestFactory {
	
	public static DataRetrieve getDatafrom(int getDatafrom, String term, String value) {
		DataRetrieve data = null;
        switch (getDatafrom) {
            case 1:
            	data = new User(term,value);
                break;
            case 2:
            	data = new Ticket(term,value);
                break;
            case 3:
            	data = new Organization(term,value);
                break;
            case 4:
            	data = new SearchableFields();
                break;
        }
        return data;
    }

}
