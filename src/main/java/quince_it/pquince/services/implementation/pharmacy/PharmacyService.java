package quince_it.pquince.services.implementation.pharmacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.EReceipt;
import quince_it.pquince.entities.drugs.EReceiptItems;
import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.drugs.EReceiptItemsRepository;
import quince_it.pquince.repository.drugs.EReceiptRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.drugs.PharmacyERecipeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.EditPharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradePriceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;
import quince_it.pquince.services.contracts.interfaces.drugs.IEReceiptService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyService;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.LocationUtil;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;

@Service
public class PharmacyService implements IPharmacyService {


	@Autowired 
	private EReceiptItemsRepository eReceiptItemsRepository;
	
	@Autowired 
	private EReceiptRepository eReceiptRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private IPharmacyFeedbackService pharmacyFeedbackService;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILoyaltyProgramService loyalityProgramService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private  EmailService emailService;
	
	@Autowired
	private DrugStorageRepository drugStorageRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Override
	public List<IdentifiableDTO<PharmacyDTO>> findAll() {
		List<IdentifiableDTO<PharmacyDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDTO>>();
		pharmacyRepository.findAll().forEach((p) -> pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(p)));
	
		return pharmacies;
	}

	@Override
	public IdentifiableDTO<PharmacyDTO> findById(UUID id) {
		return PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(pharmacyRepository.getOne(id));
	}
	
	@Override
	public IdentifiableDTO<PharmacyGradeDTO> findByIdWithGrade(UUID id) {
		return MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(pharmacyRepository.getOne(id));
	}

	@Override
	public UUID create(PharmacyDTO entityDTO) {
		// TODO Auto-generated method stub
		Pharmacy pharmacy = new Pharmacy(entityDTO.getName(), entityDTO.getDescription(),
						entityDTO.getAddress(), entityDTO.getConsultationPrice());

		pharmacyRepository.save(pharmacy);
		
		return pharmacy.getId();
	}

	@Override
	public void update(PharmacyDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGrades() {
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyRepository.findAll().forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));

		return pharmacies;
	}
	
	public IdentifiableDTO<PharmacyGradeDTO> MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();
		
		double avgGrade = getAvgGradeForPharmacy(pharmacy.getId());

		return new IdentifiableDTO<PharmacyGradeDTO>(pharmacy.getId(), new PharmacyGradeDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(),avgGrade,pharmacy.getConsultationPrice()));
	}
	
	
	private double getAvgGradeForPharmacy(UUID pharmacyId) {
		double avgGrade;
		
		try {
			avgGrade = pharmacyFeedbackService.findAvgGradeForPharmacy(pharmacyId);
		} catch (AopInvocationException e) {
			avgGrade = 0.0;
		}
		
		return avgGrade;
	}
	
	public IdentifiableDTO<PharmacyGradePriceDTO> MapPharmacyPersistenceToPharmacyGradePriceIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();
		
		UUID patientId = getLoggedUserId();
		
		double avgGrade = getAvgGradeForPharmacy(pharmacy.getId());
		double getDiscountConsultationPrice = loyalityProgramService.getDiscountAppointmentPriceForPatient(pharmacy.getConsultationPrice(), AppointmentType.CONSULTATION, patientId);
		// TODO : add discount price based on actions and benefits
		
		return new IdentifiableDTO<PharmacyGradePriceDTO>(pharmacy.getId(), new PharmacyGradePriceDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(),avgGrade, pharmacy.getConsultationPrice(), getDiscountConsultationPrice));
	}
	

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		List<IdentifiableDTO<PharmacyGradeDTO>> tempPharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();

		if(pharmacyFiltrationDTO.getGradeFrom() == 0) {
			tempPharmacies = findByNameAndCity(pharmacyFiltrationDTO.getName(), pharmacyFiltrationDTO.getCity());
			for(IdentifiableDTO<PharmacyGradeDTO> pharmacy : tempPharmacies) {
				if(pharmacy.EntityDTO.getGrade() <= pharmacyFiltrationDTO.getGradeTo() || pharmacyFiltrationDTO.getGradeTo() == 0)
					pharmacies.add(pharmacy);
			}
		}
		else
			pharmacies = pharmacyFeedbackService.findByNameCityAndGrade(pharmacyFiltrationDTO);
		
		if(pharmacyFiltrationDTO.getLatitude() < -500 || pharmacyFiltrationDTO.getDistanceFrom() == pharmacyFiltrationDTO.getDistanceTo()) return pharmacies;
		
		return findByDistance(pharmacies, pharmacyFiltrationDTO);
	}
	
	private List<IdentifiableDTO<PharmacyGradeDTO>> findByDistance(List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies, PharmacyFiltrationDTO pharmacyFiltrationDTO){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> returnPharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		
		for(IdentifiableDTO<PharmacyGradeDTO> pharmacy : pharmacies) {
			double distance = LocationUtil.calculateDistance(pharmacy.EntityDTO.getAddress().getLatitude(), pharmacy.EntityDTO.getAddress().getLongitude(), pharmacyFiltrationDTO.getLatitude(), pharmacyFiltrationDTO.getLongitude());
			if(isDistanceInRange(distance, pharmacyFiltrationDTO.getDistanceFrom(), pharmacyFiltrationDTO.getDistanceTo()))
				returnPharmacies.add(pharmacy);
		}
		
		return returnPharmacies;
	}
	


	private boolean isDistanceInRange(double distance, double distanceFrom, double distanceTo) {		
		if(distanceTo == 0) distanceTo = Double.MAX_VALUE;
		
		return distance >= distanceFrom && distance <= distanceTo;
	}
	
	private List<IdentifiableDTO<PharmacyGradeDTO>> findByNameAndCity(String name, String city){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyRepository.findByNameAndCity(name.toLowerCase(), city.toLowerCase()).forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));

		return pharmacies;
	}


	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPrice(Date startDateTime) {
		
		long time = startDateTime.getTime();
		Date endDateTime= new Date(time + (Integer.parseInt(env.getProperty("consultation_time")) * 60000));
				
		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradePriceDTO>>();
		appointmentService.findAllDistinctPharmaciesForAppointmentTime(startDateTime, endDateTime).forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradePriceIdentifiableDTO(p)));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeAscending(Date startDateTime) {

		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getGrade(), s2.EntityDTO.getGrade()));

		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeDescending(Date startDateTime) {

		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getGrade(), s2.EntityDTO.getGrade()));
		Collections.reverse(pharmacies);
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceAscending(Date startDateTime) {
		
		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getPrice(), s2.EntityDTO.getPrice()));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceDescending(Date startDateTime) {
		
		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getPrice(), s2.EntityDTO.getPrice()));
		Collections.reverse(pharmacies);
		
		return pharmacies;
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {

		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getName().compareTo(p2.EntityDTO.getName())));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getName().compareTo(p2.EntityDTO.getName())));
		Collections.reverse(pharmacies);
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getAddress().getCity().compareTo(p2.EntityDTO.getAddress().getCity())));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getAddress().getCity().compareTo(p2.EntityDTO.getAddress().getCity())));
		Collections.reverse(pharmacies);
	
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getGrade(), p2.EntityDTO.getGrade()));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getGrade(), p2.EntityDTO.getGrade()));
		Collections.reverse(pharmacies);
	
		return pharmacies;
	}

	@Override
	public void updatePharmacy(EditPharmacyDTO editPharmacyDTO) {
		Pharmacy pharmacy = pharmacyRepository.getOne(editPharmacyDTO.getPharmacyId());		
		
		pharmacy.setAddress(editPharmacyDTO.getAddress());
		pharmacy.setName(editPharmacyDTO.getName());
		pharmacy.setDescription(editPharmacyDTO.getDescription());
		pharmacy.setConsultationPrice(editPharmacyDTO.getConsultationPrice());
		
		pharmacyRepository.save(pharmacy);
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllSubscribedPharmaciesWithGrades() {

		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		UUID patientId = getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();	
				
		patient.getPharmacies().forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));
	
		return pharmacies;
	}
	
	private UUID getLoggedUserId() {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email = currentUser.getName();
		User user = userRepository.findByEmail(email);	
		
		return user.getId();
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findWithQR(UUID id) {
	
		List<EReceiptItems> items = eReceiptItemsRepository.findAllByEReceiptId(id);
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDrugPriceDTO>>();
		int price;

		double avgGrade;
		for(Pharmacy p : allPharmacies) {
			if((price = allDrugsAreInPharmacy(items,p)) != -1) {
				avgGrade = getAvgGradeForPharmacy(p.getId());
				pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyDrugPriceIdentifiableDTO(p, avgGrade, price));
			}
		}
		
		return pharmacies;
	}
	
	
	private int allDrugsAreInPharmacy(List<EReceiptItems> items, Pharmacy p) {
		boolean var = false;
		int price = 0;
		Integer priceForDrug;
		List<DrugStorage> drugs = drugStorageRepository.findAllBPharmacyId(p.getId());
		
		for(EReceiptItems item: items) {
			var = false;
			for(DrugStorage drug: drugs) {
				if(drug.getDrugInstance().getId().equals(item.getDrugInstance().getId())) {
					if(drug.getCount() >= item.getAmount()) {
						var = true;
						priceForDrug = drugPriceForPharmacyRepository.findCurrentDrugPrice(drug.getDrugInstance().getId(), p.getId());
						price = price + priceForDrug;
						continue;
					}
				}
			}
			if(!var)
				return -1;
		}
		
		return price;
	}

	@Override
	public UUID buyWithQR(PharmacyERecipeDTO pharmacyERecipeDTO) {
		
		List<EReceiptItems> items = eReceiptItemsRepository.findAllByEReceiptId(pharmacyERecipeDTO.geteRecipeId());
		
		for(EReceiptItems item: items) {
			DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(item.getDrugInstance().getId(), pharmacyERecipeDTO.getPharmacyId());
			drugStorage.reduceAmount(item.getAmount());
			drugStorageRepository.save(drugStorage);
		}
		
		EReceipt eReceipt = eReceiptRepository.getOne(pharmacyERecipeDTO.geteRecipeId());
		eReceipt.setStatus(EReceiptStatus.PROCESSED);
		eReceipt.setPharmacy(pharmacyRepository.getOne(pharmacyERecipeDTO.getPharmacyId()));
		eReceipt.setPrice(pharmacyERecipeDTO.getPrice());
		eReceiptRepository.save(eReceipt);

		User patient = userRepository.getOne(userService.getLoggedUserId());
		
		try {
			emailService.sendQRBuyDrugsNotificaitionAsync(patient);
		} catch (MailException | InterruptedException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
}
