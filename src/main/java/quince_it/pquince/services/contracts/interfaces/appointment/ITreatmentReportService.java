package quince_it.pquince.services.contracts.interfaces.appointment;

import quince_it.pquince.entities.appointment.TreatmentReport;
import quince_it.pquince.services.contracts.dto.appointment.TreatmentReportDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.IService;

public interface ITreatmentReportService extends IService<TreatmentReportDTO, IdentifiableDTO<TreatmentReportDTO>> {
	
}
