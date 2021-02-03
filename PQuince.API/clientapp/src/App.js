import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UserProfilePage from "./pages/UserProfilePage";
import StaffProfilePage from "./pages/StaffProfilePage";
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
import ObservePatientsPage from "./pages/ObservePatientsPage";
import PatientProfilePage from "./pages/PatientProfilePage";
import ScheduleAppointmentPage from "./pages/dermatologist-appointment/ScheduleAppointmentPage";
import ConsultationTimeSelectPage from "./pages/pharmacist-appointment/ConsultationTimeSelectPage";
import AdminComplaints from "./pages/complaints/AdminComplaints";
import LoyaltyProgram from "./pages/loyalty-program/LoyaltyProgram";
import ObservePatientsCosultation from "./pages/pharmacist-appointment/ObservePatientsCosultation";
import ObservePatientsCosultationHistory from "./pages/pharmacist-appointment/ObservePatientsConsultationHistory";
import UnauthorizedPage from "./pages/UnauthorizedPage";
import AppointmentFromHomePage from "./pages/dermatologist-appointment/AppointmentFromHomePage";
import DermatologistsPageForPatient from "./pages/dermatologist/DermatologistPageForPatient";
import PharmacyForAdmin from "./pages/Pharmacy/PharmacyForAdmin";
import CreateAndScheduleAppointmentPage from "./pages/dermatologist-appointment/CreateAndScheduleAppointmentPage";
import PatientsRedirectComplaints from "./pages/complaints/PatientsRedirectComplaints";
import DermatologistCalendarPage from "./pages/DermatologistCalendarPage";

function App() {
	return (
		<Router basename="/clientapp">
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
				<Link exact to="/staff-profile" path="/staff-profile" component={StaffProfilePage} />
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
				<Link exact to="/patients" path="/patients" component={ObservePatientsPage} />
				<Link exact to="/schedule-consultation" path="/schedule-consultation" component={ConsultationTimeSelectPage} />
				<Link exact to="/loyalty-program" path="/loyalty-program" component={LoyaltyProgram} />
				<Link exact to="/observe-consultations" path="/observe-consultations" component={ObservePatientsCosultation} />
				<Link exact to="/observe-consultations-history" path="/observe-consultations-history" component={ObservePatientsCosultationHistory} />
				<Link exact to="/patient-complaint" path="/patient-complaint" component={PatientsRedirectComplaints} />

				<Link exact to="/unauthorized" path="/unauthorized" component={UnauthorizedPage} />
				<Link exact to="/home-dermatologist-reservation" path="/home-dermatologist-reservation" component={AppointmentFromHomePage} />

				<Route path="/pharmacy/:id" children={<PharmacyProfilePage />} />
				<Route path="/reserve-appointment/:id" children={<Appointments />} />
				<Link exact to="/pharmacy-for-admin" path="/pharmacy-for-admin" component={PharmacyForAdmin} />

				<Route path="/patient-profile/:id" children={<PatientProfilePage />} />
				<Route path="/schedule-appointment/:id" children={<ScheduleAppointmentPage />} />
				<Route path="/create-and-schedule-appointment/:id" children={<CreateAndScheduleAppointmentPage />} />

				<Route path="/pharmacy/:id" children={<PharmacyProfilePage />} />
				<Route path="/patient-profile/:id" children={<PatientProfilePage />} />
				<Route path="/schedule-appointment/:id" children={<ScheduleAppointmentPage />} />

				<Link exact to="/dermatologist-calendar" path="/dermatologist-calendar" component={DermatologistCalendarPage} />
			</Switch>
		</Router>
	);
}

export default App;
