import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import PharmacyAdminProfilPage from "./pages/PharmacyAdminProfilPage";
import UserProfilePage from "./pages/UserProfilePage";
import StaffProfilePage from "./pages/examination/StaffProfilePage";
import PharmaciesPage from "./pages/PharmaciesPage";
import DrugReservation from "./pages/drug-reservation/DrugReservation";
import PatientsDrugReservations from "./pages/drug-reservation/PatientsDrugReservations";
import PharmacyProfilePage from "./pages/PharmacyProfilePage";
import Appointments from "./pages/dermatologist-appointment/Appointments";
import AdminRegisterStaff from "./pages/admin-registration/RegisterStaff";
import AdminRegisterDrug from "./pages/admin-registration/RegisterDrug";
import AdminRegisterPharmacies from "./pages/admin-registration/RegisterPharmacies";
import PatientsAppointments from "./pages/dermatologist-appointment/PatientsAppointments";
import HistoryDermatologistAppointments from "./pages/appointment-history/HistoryDermatologistAppointments";
import PatientsDrugReservationHistory from "./pages/drug-reservation/PatientsDrugReservationHistory";
import DermatologistsPage from "./pages/dermatologist/DermatologistsPage";
import PatientsPage from "./pages/examination/PatientsPage";
import PatientProfilePage from "./pages/examination/PatientProfilePage";
import ScheduleAppointmentPage from "./pages/examination/dermatologist/ScheduleAppointmentPage";
import TreatmentReportPage from "./pages/examination/TreatmentReportPage";
import ConsultationTimeSelectPage from "./pages/pharmacist-appointment/ConsultationTimeSelectPage";
import AdminComplaints from "./pages/complaints/AdminComplaints";
import LoyaltyProgram from "./pages/loyalty-program/LoyaltyProgram";
import ObservePatientsCosultation from "./pages/pharmacist-appointment/ObservePatientsCosultation";
import ObservePatientsCosultationHistory from "./pages/pharmacist-appointment/ObservePatientsConsultationHistory";
import UnauthorizedPage from "./pages/UnauthorizedPage";
import AppointmentFromHomePage from "./pages/dermatologist-appointment/AppointmentFromHomePage";
import DermatologistsPageForPatient from "./pages/dermatologist/DermatologistPageForPatient";
import PharmacyForAdmin from "./pages/Pharmacy/PharmacyForAdmin";
import CreateAndScheduleAppointmentPage from "./pages/examination/dermatologist/CreateAndScheduleAppointmentPage";
import PatientsRedirectComplaints from "./pages/complaints/PatientsRedirectComplaints";
import CalendarPage from "./pages/examination/CalendarPage";
import PharmacistPage from "./pages/pharmacist/PharmacistsPage";
import PharmacistPageForPatient from "./pages/pharmacist/PharmacistPageForPatient";
import DrugPageForPharmacyAdmin from "./pages/drug/DrugPageForPharmacyAdmin";
import PatientsSubscribedPharmacies from "./pages/subscribed-pharmacies/PatientsSubscribedPharmacies";
import EReceiptsForPatient from "./pages/eReceipt-patient/EReceiptsForPatient";
import DrugsFromEReceiptForPatient from "./pages/eReceipt-patient/DrugsFromEReceiptForPatient";
import Subscription from "./pages/subscription/Subscription";
import QRreader from "./pages/QRcode/QRreader";
import QRpharmacies from "./pages/QRcode/QRpharmacies";
import AbsencePage from "./pages/examination/AbsencePage";
import NewAppointmentPage from "./pages/examination/pharmacist/NewAppointmentPage";
import ReservedDrugPage from "./pages/examination/pharmacist/ReservedDrugPage";
import Offers from "./pages/offers/Offers";
import ActionAndPromotionsPage from "./pages/action-and-promotions/ActionAndPromotionsPage";
import ComplaintsPharmacy from "./pages/complaints/ComplaintsPharmacy";
import TimeSelectingPage from "./pages/pharmacist-from-pharmacy/TimeSelectingPage";
import Orders from "./pages/offers/Orders";

function App() {
	return (
		<Router basename="/clientapp">
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
				<Link exact to="/staff-profile" path="/staff-profile" component={StaffProfilePage} />
				<Link exact to="/pharmacy-admin-profile" path="/pharmacy-admin-profile" component={PharmacyAdminProfilPage} />
				<Link exact to="/pharmacies" path="/pharmacies" component={PharmaciesPage} />
				<Link exact to="/admin-register-staff" path="/admin-register-staff" component={AdminRegisterStaff} />
				<Link exact to="/admin-register-drug" path="/admin-register-drug" component={AdminRegisterDrug} />
				<Link exact to="/admin-register-pharmacies" path="/admin-register-pharmacies" component={AdminRegisterPharmacies} />
				<Link exact to="/drugs-reservation" path="/drugs-reservation" component={PatientsDrugReservations} />
				<Link exact to="/drugs-reservation-history" path="/drugs-reservation-history" component={PatientsDrugReservationHistory} />
				<Link exact to="/drugs-reservation" path="/drugs-reservation" component={PatientsDrugReservations} />
				<Link exact to="/drugs-reservation-history" path="/drugs-reservation-history" component={PatientsDrugReservationHistory} />
				<Link exact to="/drugs" path="/drugs" component={DrugReservation} />
				<Link exact to="/admin-complaints" path="/admin-complaints" component={AdminComplaints} />
				<Link exact to="/patients-appointments" path="/patients-appointments" component={PatientsAppointments} />
				<Link exact to="/dermatologist-history" path="/dermatologist-history" component={HistoryDermatologistAppointments} />
				<Link exact to="/dermatologists" path="/dermatologists" component={DermatologistsPage} />
				<Link exact to="/dermatologists-for-patient" path="/dermatologists-for-patient" component={DermatologistsPageForPatient} />
				<Link exact to="/patients" path="/patients" component={PatientsPage} />
				<Link exact to="/offers" path="/offers" component={Offers} />
				<Link exact to="/orders" path="/orders" component={Orders} />
				<Link exact to="/admin-complaints-pharmacy" path="/admin-complaints-pharmacy" component={ComplaintsPharmacy} />
				
				<Link exact to="/schedule-consultation" path="/schedule-consultation" component={ConsultationTimeSelectPage} />
				<Link exact to="/loyalty-program" path="/loyalty-program" component={LoyaltyProgram} />
				<Link exact to="/observe-consultations" path="/observe-consultations" component={ObservePatientsCosultation} />
				<Link exact to="/observe-consultations-history" path="/observe-consultations-history" component={ObservePatientsCosultationHistory} />
				<Link exact to="/patient-complaint" path="/patient-complaint" component={PatientsRedirectComplaints} />
				<Link exact to="/patient-ereceipts" path="/patient-ereceipts" component={EReceiptsForPatient} />
				<Link exact to="/patient-ereceipt-drugs" path="/patient-ereceipt-drugs" component={DrugsFromEReceiptForPatient} />
				<Link exact to="/subscription" path="/subscription" component={Subscription} />
				<Link exact to="/qr" path="/qr" component={QRreader} />
				<Link exact to="/qrpharmacies" path="/qrpharmacies" component={QRpharmacies} />

				<Link exact to="/patient-pharmacies-subscription" path="/patient-pharmacies-subscription" component={PatientsSubscribedPharmacies} />

				<Link exact to="/unauthorized" path="/unauthorized" component={UnauthorizedPage} />
				<Link exact to="/home-dermatologist-reservation" path="/home-dermatologist-reservation" component={AppointmentFromHomePage} />

				<Route path="/pharmacy/:id" children={<PharmacyProfilePage />} />
				<Route path="/qrpharmacies/:id" children={<QRpharmacies />} />
				<Route path="/reserve-appointment/:id" children={<Appointments />} />
				<Link exact to="/pharmacy-for-admin" path="/pharmacy-for-admin" component={PharmacyForAdmin} />

				<Route path="/patient-profile/:id" children={<PatientProfilePage />} />
				<Route path="/schedule-appointment/:id" children={<ScheduleAppointmentPage />} />
				<Route path="/create-and-schedule-appointment/:id" children={<CreateAndScheduleAppointmentPage />} />

				<Route path="/pharmacy/:id" children={<PharmacyProfilePage />} />
				<Route path="/patient-profile/:id" children={<PatientProfilePage />} />

				<Link exact to="/calendar" path="/calendar" component={CalendarPage} />
				<Route path="/pharmacy-consultation/:id" children={<TimeSelectingPage />} />

				<Link exact to="/pharmacist" path="/pharmacist" component={PharmacistPage} />
				<Link exact to="/pharmacist-for-patient" path="/pharmacist-for-patient" component={PharmacistPageForPatient} />
				<Link exact to="/drugs-in-pharmacy" path="/drugs-in-pharmacy" component={DrugPageForPharmacyAdmin} />
				<Route path="/treatment-report/:id" children={<TreatmentReportPage />} />
				<Link exact to="/absence" path="/absence" component={AbsencePage} />
				<Route path="/new-appointment/:id" children={<NewAppointmentPage />} />
				<Link exact to="/reserved-drug" path="/reserved-drug" component={ReservedDrugPage} />
				<Link exact to="/actions-and-promotions" path="/actions-and-promotions" component={ActionAndPromotionsPage} />
			</Switch>
		</Router>
	);
}

export default App;
