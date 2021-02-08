package quince_it.pquince.services.contracts.exceptions;

public class DrugStorageCountException extends Exception {
	private static final long serialVersionUID = 1L;

	public DrugStorageCountException(){
		super();
	}
	
	public DrugStorageCountException(String s){  
		  super(s);  
	}
}
