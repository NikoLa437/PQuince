package quince_it.pquince.services.contracts.dto.users;


public class AuthorityDTO  {

	String name;
	
	
	public AuthorityDTO(String name) {
		super();
		this.name = name;
	}
	
	public String getAuthority() {
	    return name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
	
	public String getName() {
	    return name;
	}
	
	
	
}
