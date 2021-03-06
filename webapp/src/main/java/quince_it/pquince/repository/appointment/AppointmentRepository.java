package quince_it.pquince.repository.appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.services.contracts.dto.appointment.DermatologistAppointmentDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, UUID>{

	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND a.appointmentStatus = 'CREATED' AND a.appointmentType = ?2")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = ?2")
	List<Appointment> findAllFutureAppointmentsForPatient(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
				+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2")
	List<Appointment> findAllPreviousAppointmentsForPatient(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.staff.id = ?2")
	List<Appointment> findAllPreviousAppointmentsForPatientForStaff(UUID patientId, UUID staffId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.pharmacy.id = ?2 AND a.appointmentType = ?3")
	List<Appointment> findAllPreviousAppointmentsForPatientForPharmacy(UUID patientId, UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2 ORDER BY a.startDateTime ASC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByDateAscending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.startDateTime DESC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByDateDescending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.priceToPay ASC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByPriceAscending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.priceToPay DESC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByPriceDescending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.endDateTime - a.startDateTime ASC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByTimeAscending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.endDateTime - a.startDateTime DESC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByTimeDescending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND a.appointmentStatus = 'CREATED'  AND a.appointmentType = ?2 ORDER BY a.price ASC")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'CREATED' AND a.appointmentType = ?2 ORDER BY a.price DESC")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);

	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1 AND a.pharmacy.id = ?2 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'CREATED' AND a.appointmentType = 'EXAMINATION'  ORDER BY a.startDateTime DESC")
	List<Appointment> getCreatedAppointmentsByDermatologist(UUID dermatologistId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1)"
				+ " AND a.appointmentStatus = 'SCHEDULED'")
	List<Appointment> findAllConsultationsByAppointmentTime(Date dateTimeFrom, Date dateTimeTo);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1)"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.pharmacy.id = ?3")
	List<Appointment> findAllConsultationsByAppointmentTimeAndPharmacy(Date dateTimeFrom, Date dateTimeTo, UUID pharmacyId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1 AND (CAST(a.startDateTime as date) = CAST(?2 as date))"
			+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'SCHEDULED') AND a.pharmacy.id = ?3")
	List<Appointment> getCreatedAppoitntmentsByDermatologistByDate(UUID dermatologistId,Date date, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1)"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.staff.id = ?3")
	List<Appointment> findAllConsultationsByAppointmentTimeAndPharmacist(Date dateTimeFrom, Date dateTimeTo, UUID pharmacistId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = 'EXAMINATION'  AND a.pharmacy.id=?2")
	List<Appointment> findAllAppointmentForDermatologistInFutureInPharmacy(UUID dermatologistId, UUID pharmacyId);
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND a.startDateTime = ?1"
			+ " AND a.staff.id = ?2 AND a.patient.id = ?3")
	Appointment findConsultationsByAppointmentTimePharmacistAndPatient(Date dateTimeFrom, UUID pharmacistId, UUID patientId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1)"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.patient.id = ?3")
	List<Appointment> findAllAppointmentsByAppointmentTimeAndPatient(Date dateTimeFrom, Date dateTimeTo, UUID patientId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.staff.id = ?2 AND a.pharmacy.id = ?3 AND a.appointmentType = 'CONSULTATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getScheduledPharmacistAppointmentsByPatient(UUID patientId, UUID staffId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = 'CONSULTATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getFinishedPharmacistAppointmentsByPatient(UUID patientId, UUID staffId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.staff.id = ?2 AND a.pharmacy.id = ?3 AND a.appointmentType = 'EXAMINATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getScheduledDermatologistAppointmentsByPatient(UUID patientId, UUID staffId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = 'EXAMINATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getFinishedDermatologistAppointmentsByPatient(UUID patientId, UUID staffId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1"
			+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'SCHEDULED') AND a.appointmentType = 'EXAMINATION' AND a.pharmacy.id=?2")
	List<Appointment> getCalendarDermatologistAppointmentsForPharamacy(UUID dermatologistId, UUID pharmacyId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = 'CONSULTATION' AND a.pharmacy.id=?2")
	List<Appointment> getCalendarAppointmentsByPharmacist(UUID loggedUserId, UUID pharmacyId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = 'CONSULTATION'  AND a.pharmacy.id=?2")
	List<Appointment> findAllAppointmentForPharmacistInFutureInPharmacy(UUID pharmacistId, UUID pharmacyId);

	@Query(value = "SELECT a FROM Appointment a WHERE NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1)"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.staff.id = ?3")
	List<IdentifiableDTO<DermatologistAppointmentDTO>> findAllAppointmentsByAppointmentTimeAndStaff(Date startDateTime, Date endDateTime, UUID id);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.endDateTime < CURRENT_TIMESTAMP AND a.appointmentStatus = 'SCHEDULED'")
	List<Appointment> findExpiredAppointments();
	
	@Query(value = "SELECT a FROM Appointment a WHERE NOT (DATE(a.startDateTime) >= ?2 OR DATE(a.endDateTime) <= ?1)"
			+ " AND a.pharmacy.id = ?3 AND a.staff.id = ?4")
	List<Appointment> findAllAppointmentByPharmacyAndDoctorForDateRange(Date dateTimeFrom, Date dateTimeTo,UUID pharmacyId, UUID staffId);

	@Query(value = "SELECT a FROM Appointment a WHERE DATE(a.startDateTime) >= ?2 AND DATE(a.startDateTime) <= ?3"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.pharmacy.id = ?1")
	List<Appointment> findAllAppointmentForPharmacyInDateRange(UUID pharmacyId, Date dateFrom, Date dateTo);

	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?2 AND a.staff.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED'")
	List<Appointment> getFinishedAppointmentsForStaffForPatient(UUID staffId, UUID patientId);
}
