import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UserProfilePage from "./pages/UserProfilePage";
import StaffProfilePage from "./pages/StaffProfilePage";
import PharmaciesPage from "./pages/PharmaciesPage";
import DrugReservation from "./pages/drug-reservation/DrugReservation";
import PatientsDrugReservations from "./pages/drug-reservation/PatientsDrugReservations";
import PharmacyProfilePage from "./pages/PharmacyProfilePage";
import Appointments from "./pages/dermatologist-appointment/Appointments";
import PatientsAppointments from "./pages/dermatologist-appointment/PatientsAppointments";
import HistoryDermatologistAppointments from "./pages/appointment-history/HistoryDermatologistAppointments";
import PatientsDrugReservationHistory from "./pages/drug-reservation/PatientsDrugReservationHistory";
import DermatologistsPage from "./pages/dermatologist/DermatologistsPage";
import ObservePatientsPage from "./pages/ObservePatientsPage";
import PatientProfilePage from "./pages/PatientProfilePage";
import ScheduleAppointmentPage from "./pages/dermatologist-appointment/ScheduleAppointmentPage";
import DermatologistComplaints from "./pages/complaints/DermatologistComplaint";
import PharmacyComplaints from "./pages/complaints/ComplaintsForPharmacy";

function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
				<Link exact to="/patient-profile" path="/patient-profile" component={PatientProfilePage} />
				<Link
					exact
					to="/staff-profile"
					path="/staff-profile"
					component={StaffProfilePage}
				/>
				<Link exact to="/pharmacies" path="/pharmacies" component={PharmaciesPage} />
				<Link
					exact
					to="/drugs-reservation"
					path="/drugs-reservation"
					component={PatientsDrugReservations}
				/>
				<Link
					exact
					to="/drugs-reservation-history"
					path="/drugs-reservation-history"
					component={PatientsDrugReservationHistory}
				/>
				<Link exact to="/drugs" path="/drugs" component={DrugReservation} />
				<Link exact to="/dermatologist-complaints" path="/dermatologist-complaints" component={DermatologistComplaints} />
				<Link exact to="/pharmacy-complaints" path="/pharmacy-complaints" component={PharmacyComplaints} />
				<Link exact to="/pharmacy" path="/pharmacy" component={PharmacyProfilePage} />
				<Link
					exact
					to="/reserve-appointment"
					path="/reserve-appointment"
					component={Appointments}
				/>
				<Link
					exact
					to="/patients-appointments"
					path="/patients-appointments"
					component={PatientsAppointments}
				/>
				<Link
					exact
					to="/dermatologist-history"
					path="/dermatologist-history"
					component={HistoryDermatologistAppointments}
				/>
				<Link
					exact
					to="/dermatologists"
					path="/dermatologists"
					component={DermatologistsPage}
				/>
				<Link exact to="/patients" path="/patients" component={ObservePatientsPage} />
				<Link
					exact
					to="/schedule-appointment"
					path="/schedule-appointment"
					component={ScheduleAppointmentPage}
				/>
				
			</Switch>
		</Router>
	);
}

export default App;
