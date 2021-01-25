package quince_it.pquince.repository.appointment;

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
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
				+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'CANCELED') AND a.appointmentType = ?2 ORDER BY a.price ASC")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceAscending(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'CANCELED') AND a.appointmentType = ?2 ORDER BY a.price DESC")
	List<Appointment> findAllFreeAppointmentsByPharmacyAndAppointmentTypeSortByPriceDescending(UUID pharmacyId, AppointmentType appointmentType);
}
