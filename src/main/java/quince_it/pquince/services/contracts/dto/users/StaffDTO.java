package quince_it.pquince.services.contracts.dto.users;

import java.util.List;


import quince_it.pquince.entities.users.Authority;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;

public class StaffDTO extends UserDTO{
	
	private List<AbsenceDTO> absences;
	
    private List<UserFeedbackDTO> feedbacks;

	public StaffDTO(String email, String name, String surname, String address, IdentifiableDTO<CityDTO> city,
			String phoneNumber, boolean active, List<Authority> authorities, List<AbsenceDTO> absencesDTO, List<UserFeedbackDTO> userFeedbacksDTO) {
		super(email, name, surname, address, city, phoneNumber, active, authorities);
		// TODO Auto-generated constructor stub
		this.absences= absencesDTO;
		this.feedbacks=userFeedbacksDTO;
	}

	public List<AbsenceDTO> getAbsences() {
		return absences;
	}

	public void setAbsences(List<AbsenceDTO> absences) {
		this.absences = absences;
	}

	public List<UserFeedbackDTO> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<UserFeedbackDTO> feedbacks) {
		this.feedbacks = feedbacks;
	}
}
