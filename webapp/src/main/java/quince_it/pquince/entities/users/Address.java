package quince_it.pquince.entities.users;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
    @Column(name = "latitude")
	private double latitude;
	
    @Column(name = "longitude")
	private double longitude;
	
    @Column(name = "city")
	private String city;
    
    @Column(name = "street")
   	private String street;
	
    @Column(name = "country")
	private String country;
    
    public Address() {}

	public Address(double latitude, double longitude, String city, String country,String street) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.country = country;
		this.street = street;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
    
	
}
