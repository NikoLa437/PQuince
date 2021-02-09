package quince_it.pquince.services.contracts.exceptions;

public class AppointmentTimeOutofWorkTimeRange extends Exception {
	private static final long serialVersionUID = 1L;

	public AppointmentTimeOutofWorkTimeRange(){
		super();
	}
	
	public AppointmentTimeOutofWorkTimeRange(String s){  
		  super(s);  
	}
}
