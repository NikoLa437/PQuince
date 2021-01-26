import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UserProfilePage from "./pages/UserProfilePage";
import PharmaciesPage from "./pages/PharmaciesPage";
import DrugReservation from "./pages/drug-reservation/DrugReservation";
import PatientsDrugReservations from "./pages/drug-reservation/PatientsDrugReservations";
import PharmacyProfilePage from "./pages/PharmacyProfilePage";
import Appointments from "./pages/dermatologist-appointment/Appointments";
import PatientsAppointments from "./pages/dermatologist-appointment/PatientsAppointments";
import HistoryDermatologistAppointments from "./pages/appointment-history/HistoryDermatologistAppointments";
import PatientsDrugReservationHistory from "./pages/drug-reservation/PatientsDrugReservationHistory";

function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
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
			</Switch>
		</Router>
	);
}

export default App;
