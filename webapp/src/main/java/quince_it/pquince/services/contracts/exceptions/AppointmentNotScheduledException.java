package quince_it.pquince.services.contracts.exceptions;

public class AppointmentNotScheduledException extends Exception{

	private static final long serialVersionUID = 1L;

	public AppointmentNotScheduledException(){
		super();
	}
	
	public AppointmentNotScheduledException(String s){  
		  super(s);  
	}
}
