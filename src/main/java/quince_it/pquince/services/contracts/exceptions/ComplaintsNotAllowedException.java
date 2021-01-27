package quince_it.pquince.services.contracts.exceptions;

public class ComplaintsNotAllowedException extends Exception{  
	
	private static final long serialVersionUID = 1L;

	public ComplaintsNotAllowedException(){
		super();
	}
	
	public ComplaintsNotAllowedException(String s){  
		  super(s);  
	}
}