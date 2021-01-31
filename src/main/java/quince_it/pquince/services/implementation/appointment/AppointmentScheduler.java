package quince_it.pquince.services.implementation.appointment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.users.DateRange;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentPeriodResponseDTO;

public class AppointmentScheduler {
	private DateRange workTime;
	private List<Appointment> scheduledAppointments;
	
	public AppointmentScheduler(DateRange workTime, List<Appointment> scheduledAppointments) {
		super();
		this.workTime = workTime;
		this.scheduledAppointments = scheduledAppointments;
		//this.scheduledAppointments = SortScheduledAppointment(scheduledAppointments);
	}

	private List<Appointment> SortScheduledAppointment(List<Appointment> scheduledAppointments2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AppointmentPeriodResponseDTO> GetFreeAppointment(){
		List<AppointmentPeriodResponseDTO> freeAppointmentsPeriods = new ArrayList<AppointmentPeriodResponseDTO>();
		
        LocalDateTime startTime = workTime.getStartDateTime();
        LocalDateTime endTime;
        //da li postoji
        if(scheduledAppointments!=null) {
        	for (Appointment appointment : scheduledAppointments)
            {
                 endTime = convertToLocalDateTimeViaInstant(appointment.getStartDateTime());
                 freeAppointmentsPeriods.addAll(FillFreeInterval(startTime, endTime));
                 startTime = convertToLocalDateTimeViaInstant(appointment.getEndDateTime());
            }
        }
        
        endTime = workTime.getEndDateTime();
        freeAppointmentsPeriods.addAll(FillFreeInterval(startTime, endTime));
        return freeAppointmentsPeriods;
	}

	private Collection<? extends AppointmentPeriodResponseDTO> FillFreeInterval(LocalDateTime startTime, LocalDateTime endTime) {
		List<AppointmentPeriodResponseDTO> freeAppointments = new ArrayList<AppointmentPeriodResponseDTO>();
		
		while (Duration.between(startTime, endTime).toMinutes() >= Duration.ofMinutes(30).toMinutes())
        {
            DateRange dateRange = new DateRange(startTime, startTime.plusMinutes(30));
            AppointmentPeriodResponseDTO freeAppointmentPeriod = new AppointmentPeriodResponseDTO(convertToDateViaInstant(dateRange.getStartDateTime()),convertToDateViaInstant(dateRange.getEndDateTime()));
            startTime = convertToLocalDateTimeViaInstant(freeAppointmentPeriod.getEndDate());
            freeAppointments.add(freeAppointmentPeriod);
        }
        return freeAppointments;
	}
	
	private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	    	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	    	      .toInstant());
	    }

	
	
	 
}
