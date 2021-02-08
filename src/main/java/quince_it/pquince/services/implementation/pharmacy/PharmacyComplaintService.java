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
import quince_it.pquince.repository.drugs.EReceiptRepository;
import quince_it.pquince.repository.pharmacy.PharmacyComplaintRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.services.contracts.dto.users.ComplaintPharmacyDTO;
import quince_it.pquince.services.contracts.exceptions.ComplaintsNotAllowedException;
import quince_it.pquince.services.contracts.exceptions.FeedbackNotAllowedException;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyComplaintService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;

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

	@Autowired
	private EReceiptRepository eReceiptRepository;

	@Autowired
	private IUserService userService;
	
	@Override
	public void create(ComplaintPharmacyDTO entityDTO) throws  ComplaintsNotAllowedException{
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();		
		
		CanPatientGiveComplaint(patient.getId(), entityDTO.getPharmacyId());
		
		Pharmacy pharmacy = pharmacyRepository.findById(entityDTO.getPharmacyId()).get();
		ComplaintPharmacy pharmacyComplaint = new ComplaintPharmacy(pharmacy, patient,  entityDTO.getText(), pharmacy.getName());
		pharmacyComplaintRepository.save(pharmacyComplaint);
		
	}

	private void CanPatientGiveComplaint(UUID patientId, UUID pharmacyId) throws ComplaintsNotAllowedException {
		if(!(appointmentRepository.findAllPreviousAppointmentsForPatientForPharmacy(patientId, pharmacyId, AppointmentType.CONSULTATION).size() > 0 ||
				   appointmentRepository.findAllPreviousAppointmentsForPatientForPharmacy(patientId, pharmacyId, AppointmentType.EXAMINATION).size() > 0  ||
				   drugReservationRepository.findProcessedDrugReservationsForPatientForPharmacy(patientId, pharmacyId).size() > 0 || 
				   eReceiptRepository.findAllByPatienIdAndPharmacy(patientId, pharmacyId).size() > 0))
				throw new ComplaintsNotAllowedException();
	}

}