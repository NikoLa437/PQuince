package quince_it.pquince.services.implementation.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.AbsenceStatus;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.users.AbsenceRepository;
import quince_it.pquince.repository.users.PharmacistRepository;
import quince_it.pquince.repository.users.StaffRepository;
import quince_it.pquince.services.contracts.dto.EntityIdDTO;
import quince_it.pquince.services.contracts.dto.users.AbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.AbsenceResponseDTO;
import quince_it.pquince.services.contracts.dto.users.RejectAbsenceDTO;
import quince_it.pquince.services.contracts.dto.users.RequestAbsenceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.users.IAbsenceService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.users.AbsenceMapper;

@Service
public class AbsenceService implements IAbsenceService{

	@Autowired
	private AbsenceRepository absenceRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private UserService userService;
	@Autowired 
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO) {
		UUID staffId = userService.getLoggedUserId();
		Staff staff = staffRepository.getOne(staffId);	
		Pharmacy pharmacy = null;
		if(staff.getStaffType() == StaffType.PHARMACIST)
			pharmacy = pharmacistRepository.getOne(staffId).getPharmacy();
		else if (staff.getStaffType() == StaffType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		Absence absence = new Absence(staff,pharmacy,getDateWithoutTime(requestAbsenceDTO.getStartDate()),getDateWithoutTime(requestAbsenceDTO.getEndDate()));
		absenceRepository.save(absence);
		return absence.getId();
	}
	
	private Date getDateWithoutTime(Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDate(),0,0,0);
	}

	@Override
	public List<IdentifiableDTO<AbsenceDTO>> getAbsencesForStaff(UUID staffId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdentifiableDTO<AbsenceResponseDTO>> getAbsencesForPharmacy(UUID pharmacyId) {
		// TODO Auto-generated method stub
		List<Absence> listOfAbsences = absenceRepository.findNotProcessAbsenceForPharmacy(pharmacyId);
		List<IdentifiableDTO<AbsenceResponseDTO>> retVal = new ArrayList<IdentifiableDTO<AbsenceResponseDTO>>();
		
		listOfAbsences.forEach(a -> retVal.add(MapAbsencePersistanceToAbsenceResponseDTO(a)));
		
		return retVal;
	}

	private IdentifiableDTO<AbsenceResponseDTO> MapAbsencePersistanceToAbsenceResponseDTO(Absence a) {
		return new IdentifiableDTO<AbsenceResponseDTO>(a.getId(),new AbsenceResponseDTO("Dr " + a.getForStaff().getName()+" "+ a.getForStaff().getSurname(),a.getStartDate(),a.getEndDate()));
	}
	
	
	@Override
	public boolean approveAbsence(EntityIdDTO absenceIdDTO) throws MessagingException {
		Absence absence =absenceRepository.getOne(absenceIdDTO.getId());
		
		if(!hasStaffAppointmentAtAbsenceDataRande(absence)) {
			absence.setAbsenceStatus(AbsenceStatus.ACCEPTED);
			absenceRepository.save(absence);
			emailService.sendMailForApprovedAbsence(absence);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void rejectAbsence(RejectAbsenceDTO rejectAbsenceDTO) throws MessagingException {
		Absence absence =absenceRepository.getOne(rejectAbsenceDTO.getId());
		
		absence.setAbsenceStatus(AbsenceStatus.REJECTED);
		absence.setRejectReason(rejectAbsenceDTO.getReason());
		absenceRepository.save(absence);
		
		emailService.sendMailForRejectAbsence(absence);
	}

	private boolean hasStaffAppointmentAtAbsenceDataRande(Absence absence) {
		
		List<Appointment> appointments = appointmentRepository.findAllAppointmentByPharmacyAndDoctorForDateRange(absence.getStartDate(), absence.getEndDate(), absence.getPharmacy().getId(), absence.getForStaff().getId());
		
		if(appointments.size()>0)
			return true;
		
		return false;
	}
	
	@Override
	public List<IdentifiableDTO<AbsenceDTO>> getAbsencesAsStaff(){
		UUID staffId = userService.getLoggedUserId();
		List<Absence> absences = absenceRepository.findAllAbsencesByStaff(staffId);
		return AbsenceMapper.MapAbsencePersistenceListToAbsenceIdentifiableDTOList(absences);
	}
	
}
