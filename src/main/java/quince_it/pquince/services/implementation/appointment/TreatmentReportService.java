package quince_it.pquince.services.implementation.appointment;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.TreatmentReport;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.appointment.TreatmentReportRepository;
import quince_it.pquince.services.contracts.dto.appointment.TreatmentReportDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.ITreatmentReportService;

@Service
public class TreatmentReportService implements ITreatmentReportService {
	
	@Autowired
	TreatmentReportRepository treatmentReportRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public List<IdentifiableDTO<TreatmentReportDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentifiableDTO<TreatmentReportDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID create(TreatmentReportDTO entityDTO) {
		// TODO Auto-generated method stub
		try {
			Appointment appointment = appointmentRepository.findById(entityDTO.getAppointmentId()).get();
			TreatmentReport treatmentReport = new TreatmentReport(entityDTO.getAnamnesis(), entityDTO.getDiagnosis(), entityDTO.getTherapy(), appointment);
			treatmentReportRepository.save(treatmentReport);
			return treatmentReport.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}

	@Override
	public void update(TreatmentReportDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

}
