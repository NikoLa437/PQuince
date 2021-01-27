package quince_it.pquince.repository.appointment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentType;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, UUID>{

	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'CANCELED') AND a.appointmentType = ?2")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentType(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = ?2")
	List<Appointment> findAllFutureAppointmentsForPatient(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
				+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2")
	List<Appointment> findAllPreviousAppointmentsForPatient(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.staff.id = ?2")
	List<Appointment> findAllPreviousAppointmentsForPatientForStaff(UUID patientId, UUID staffId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.pharmacy.id = ?2 AND a.appointmentType = ?3")
	List<Appointment> findAllPreviousAppointmentsForPatientForPharmacy(UUID patientId, UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2 ORDER BY a.startDateTime ASC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByDateAscending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.startDateTime DESC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByDateDescending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.price ASC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByPriceAscending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.price DESC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByPriceDescending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.endDateTime - a.startDateTime ASC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByTimeAscending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.endDateTime < CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = ?2  ORDER BY a.endDateTime - a.startDateTime DESC")
	List<Appointment> findAllPreviousAppointmentsForPatientSortByTimeDescending(UUID patientId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'CANCELED') AND a.appointmentType = ?2 ORDER BY a.price ASC")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'CANCELED') AND a.appointmentType = ?2 ORDER BY a.price DESC")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' OR a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = 'EXAMINATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getDermatologistAppointmentsByPatient(UUID patientId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.staff.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND (a.appointmentStatus = 'CREATED') AND a.appointmentType = 'EXAMINATION'  ORDER BY a.endDateTime - a.startDateTime ASC")
	List<Appointment> getCreatedAppointmentsByDermatologist(UUID dermatologistId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND NOT (a.startDateTime > ?2 OR a.endDateTime < ?1)"
				+ " AND a.appointmentStatus = 'SCHEDULED'")
	List<Appointment> findAllConsultationsByAppointmentTime(Date dateTimeFrom, Date dateTimeTo);

}
