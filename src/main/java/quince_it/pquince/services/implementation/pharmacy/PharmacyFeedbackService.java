package quince_it.pquince.services.implementation.pharmacy;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.repository.pharmacy.PharmacyFeedbackRepository;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;

@Service
public class PharmacyFeedbackService implements IPharmacyFeedbackService {

	
	@Autowired
	private PharmacyFeedbackRepository pharmacyFeedbackRepository;

	@Override
	public double findAvgGradeForPharmacy(UUID pharmacyId) {
		return pharmacyFeedbackRepository.findAvgGradeForPharmacy(pharmacyId);
	}
}
