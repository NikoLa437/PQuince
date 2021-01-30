package quince_it.pquince.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name="AUTHORITY")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
	private UUID id;

    @Column(name="name", unique=true)
    String name;


	public Authority() {}
	
	public Authority(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
    
    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
