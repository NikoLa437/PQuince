package quince_it.pquince.services.contracts.exceptions;

public class FeedbackNotAllowedException extends Exception{  
	
	private static final long serialVersionUID = 1L;

	public FeedbackNotAllowedException(){
		super();
	}
	
	public FeedbackNotAllowedException(String s){  
		  super(s);  
	}
}