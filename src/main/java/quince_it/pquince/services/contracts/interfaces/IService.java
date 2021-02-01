package quince_it.pquince.services.contracts.interfaces;

import java.util.List;
import java.util.UUID;

import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface IService<T, IdentifiableT> {

	List<IdentifiableT> findAll();
	IdentifiableT findById(UUID id);
	UUID create(T entityDTO);
	void update(T entityDTO, UUID id);
    boolean delete(UUID id);
}
