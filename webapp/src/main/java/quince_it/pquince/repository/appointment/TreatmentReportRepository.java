package quince_it.pquince.repository.appointment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.appointment.TreatmentReport;
import quince_it.pquince.entities.pharmacy.Pharmacy;

public interface TreatmentReportRepository  extends JpaRepository<TreatmentReport, UUID>{

}
