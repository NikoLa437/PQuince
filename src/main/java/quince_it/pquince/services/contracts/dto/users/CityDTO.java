package quince_it.pquince.services.contracts.dto.users;

import java.util.UUID;

public class CityDTO {

	private String name;
	private UUID countryId;

	public CityDTO(String name, UUID countryID) {
		super();
		this.name = name;
		this.countryId = countryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}
}
