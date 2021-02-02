package quince_it.pquince.services.implementation.pharmacy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.pharmacy.ComplaintPharmacy;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.pharmacy.PharmacyComplaintRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.exceptions.ComplaintsNotAllowedException;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyComplaintService;

@Service
public class PharmacyComplaintService implements IPharmacyComplaintService {

	@Autowired
	private PharmacyComplaintRepository pharmacyComplaintRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;



	@Override
	public void create(ComplaintPharmacyDTO entityDTO) {
		//TODO : get logged patient
		
		try {
			Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
			
			if(!CanPatientGiveComplaint(patient.getId(), entityDTO.getPharmacyId())) throw new ComplaintsNotAllowedException();
			
			Pharmacy pharmacy = pharmacyRepository.findById(entityDTO.getPharmacyId()).get();
			ComplaintPharmacy pharmacyComplaint = new ComplaintPharmacy(pharmacy, patient,  entityDTO.getText());
			pharmacyComplaintRepository.save(pharmacyComplaint);
			
		} catch (Exception e) {
		}
	}

	private boolean CanPatientGiveComplaint(UUID patientId, UUID pharmacyId) {
		// TODO : eReciept check, consultation check!
		return appointmentRepository.findAllPreviousAppointmentsForPatientForPharmacy(patientId, pharmacyId, AppointmentType.CONSULTATION).size() > 0 ||
			   appointmentRepository.findAllPreviousAppointmentsForPatientForPharmacy(patientId, pharmacyId, AppointmentType.EXAMINATION).size() > 0  ||
			   drugReservationRepository.findProcessedDrugReservationsForPatient(patientId).size() > 0;
	}


}