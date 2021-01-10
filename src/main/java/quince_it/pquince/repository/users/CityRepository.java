package quince_it.pquince.repository.users;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import quince_it.pquince.entities.users.City;

public interface CityRepository extends JpaRepository<City, UUID>{

	@Query(value = "SELECT c FROM City c WHERE c.country.id = ?1")
	List<City> findByCountryId(UUID countryId);
}
