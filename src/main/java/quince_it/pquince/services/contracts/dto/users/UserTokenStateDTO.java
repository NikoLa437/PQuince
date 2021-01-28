package quince_it.pquince.services.contracts.dto.users;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
public class UserTokenStateDTO {
	
    private String accessToken;
    private String role;
    private Long expiresIn;

    public UserTokenStateDTO() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenStateDTO(String accessToken, long expiresIn, String role) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}