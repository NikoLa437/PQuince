package quince_it.pquince.security.exception;

// Custom izuzetak
public class ResourceConflictException extends RuntimeException {
	private static final long serialVersionUID = 1791564636123821405L;

	private String resourceId;

	public ResourceConflictException(String email, String message) {
		super(message);
		this.setResourceId(email);
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
