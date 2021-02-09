package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.DrugFormatId;

public interface DrugFormatRepository  extends JpaRepository<DrugFormatId, UUID>{
	DrugFormatId findByType ( String type );
}
