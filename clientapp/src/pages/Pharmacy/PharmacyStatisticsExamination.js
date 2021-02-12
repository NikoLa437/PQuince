import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import { YMaps, Map, GeoObject, Placemark } from "react-yandex-maps";
import getAuthHeader from "../../GetHeader";
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";
import { Chart } from "react-google-charts";
import { NavLink, Redirect } from "react-router-dom";

class PharmacyStatisticsExamination extends Component {
	state = {
		montlyExaminationStatistics:'',
		quartalExaminationStatistics:'',
		yearsExaminationStatistics:'',

    };
    
	componentDidMount() {

		Axios
		.get(BASE_URL + "/api/pharmacy/find-statistics-for-examinations-and-consultations", {
				headers: { Authorization: getAuthHeader() },
			}).then((res) =>{
				this.setState({
					montlyExaminationStatistics : res.data.montlyStatistics, 
					quartalExaminationStatistics:res.data.quartalStatistics,
					yearsExaminationStatistics:res.data.yearsStatistics
				});
				console.log(res.data);
			}).catch((err) => {console.log(err);});
    }
	render() {
		const { pharmacy, pharmacyName, pharmacyDescription, pharmacyAdress, pharmacyCity, x, y } = this.state;
		const mapState = { center: [x, y], zoom: 17 };
		const myStyle = {
			color: "white",
			textAlign: "center",
		};

		return (
			<React.Fragment>
				<TopBar />
				<Header />

				
				<div className="container" style={{ marginTop: "8%" }}>

					<div className="row" style={{ verticalAlign: "center" }}></div>
					<nav className="nav nav-pills nav-justified justify-content-center mt-5">
						<NavLink className="nav-link" exact to="/pharmacy-for-admin">
							Pharmacy
						</NavLink>
						<NavLink className="nav-link active" exact to="/pharamcy-statistics">
							Examination statistics
						</NavLink>
						<NavLink className="nav-link " exact to="/pharmacy-drugs-statistics">
							Drugs statistics
						</NavLink>
						<NavLink className="nav-link " exact to="/pharmacy-income-statistics">
						Pharmacy income
						</NavLink>
					</nav>
					<div className="row" style={{ marginTop: "3%" }}>
             
                    <Chart
						width={'550px'}
						height={'400px'}
						chartType="PieChart"
						loader={<div>Loading Chart</div>}
						data={[
							['Task', 'Hours per Day'],
							['Jan',this.state.montlyExaminationStatistics[0]],
							['Feb', this.state.montlyExaminationStatistics[1]],
							['Mar', this.state.montlyExaminationStatistics[2]],
							['Apr', this.state.montlyExaminationStatistics[3]],
							['May', this.state.montlyExaminationStatistics[4]],
							['Jun', this.state.montlyExaminationStatistics[5]],
							['Jul', this.state.montlyExaminationStatistics[6]],
							['Avg', this.state.montlyExaminationStatistics[7]],
							['Sep', this.state.montlyExaminationStatistics[8]],
							['Okt', this.state.montlyExaminationStatistics[9]],
							['Nov', this.state.montlyExaminationStatistics[10]],
							['Dec', this.state.montlyExaminationStatistics[11]],

						]}
						options={{
							title: 'Montly examinations',
						}}
						rootProps={{ 'data-testid': '1' }}
						/>

                        <Chart
						width={'550px'}
						height={'400px'}
						chartType="PieChart"
						loader={<div>Loading Chart</div>}
						data={[
							['Task', 'Hours per Day'],
							['First',this.state.quartalExaminationStatistics[0]],
							['Second', this.state.quartalExaminationStatistics[1]],
							['Third', this.state.quartalExaminationStatistics[2]],
							['Fourth', this.state.quartalExaminationStatistics[3]],

						]}
						options={{
							title: 'Quartals examinations',
						}}
						rootProps={{ 'data-testid': '1' }}
						/>

<Chart
						width={'550px'}
						height={'400px'}
						chartType="PieChart"
						loader={<div>Loading Chart</div>}
						data={[
							['Task', 'Hours per Day'],
							['This year',this.state.yearsExaminationStatistics[0]],
							['Last year', this.state.yearsExaminationStatistics[1]],
							['Year before last', this.state.yearsExaminationStatistics[2]],

						]}
						options={{
							title: 'years examinations',
						}}
						rootProps={{ 'data-testid': '1' }}
						/>
					</div>

				</div>

			</React.Fragment>
		);
	}
}

export default PharmacyStatisticsExamination;
