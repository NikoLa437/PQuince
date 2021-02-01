package quince_it.pquince.services.contracts.exceptions;

public class AlreadyBeenScheduledConsultationException extends Exception {

	private static final long serialVersionUID = 1L;

	public AlreadyBeenScheduledConsultationException(){
		super();
	}
	
	public AlreadyBeenScheduledConsultationException(String s){  
		  super(s);  
	}
}
