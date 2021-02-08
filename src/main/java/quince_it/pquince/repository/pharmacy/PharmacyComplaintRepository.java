package quince_it.pquince.repository.pharmacy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.pharmacy.ComplaintPharmacy;
import quince_it.pquince.entities.pharmacy.ComplaintPharmacyId;

public interface PharmacyComplaintRepository  extends JpaRepository<ComplaintPharmacy, UUID>{
}
