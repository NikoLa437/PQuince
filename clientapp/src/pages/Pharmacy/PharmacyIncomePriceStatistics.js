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
import DatePicker from "react-datepicker";
import { Button, Modal } from 'react-bootstrap';


class PharmacyIncomePriceStatistics extends Component {
	state = {
		statistics:[],
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        tableHide:true,
        sum:0,
    };

    handleStartDateChange = (date) => {
        this.setState({
            selectedStartDate:date,
        });

        if(date>this.state.selectedEndDate){
            this.setState({
                selectedEndDate:date,
            }); 
        }
    }

    handleSearch = () => {
        Axios
		.get(BASE_URL + "/api/pharmacy/find-income-statistics-for-pharmacy/"+new Date(
            this.state.selectedStartDate.getFullYear(),
            this.state.selectedStartDate.getMonth(),
            this.state.selectedStartDate.getDate(),
            0,
            0,
            0,
            0
        ).getTime()+"/"+new Date(
            this.state.selectedEndDate.getFullYear(),
            this.state.selectedEndDate.getMonth(),
            this.state.selectedEndDate.getDate(),
            0,
            0,
            0,
            0
        ).getTime(), {
				headers: { Authorization: getAuthHeader() },
			}).then((res) =>{
				this.setState({
					statistics : res.data.listOfIncomeStatistics, 
                    tableHide : false,
                    sum:res.data.priceSum
				});
				console.log(res.data);
			}).catch((err) => {console.log(err);});
            
        
        
        }


    handleEndDateChange = (date) => {
        this.setState({selectedEndDate:date});
    }
    
	componentDidMount() {


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
						<NavLink className="nav-link" exact to="/pharmacy-statistics">
							Examination statistics
						</NavLink>
						<NavLink className="nav-link" exact to="/pharmacy-drugs-statistics">
							Drugs statistics
						</NavLink>
						<NavLink className="nav-link active" exact to="/pharmacy-income-statistics">
							Pharmacy income
						</NavLink>
					</nav>
					<div style={{ marginTop: "3%" }}>
                        <table style={{width:'100%'},{marginLeft:'37%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "15em"}}  onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "15em"}}  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                             </tr>
                        </table>
                        <div  className="form-group text-center">
                            <Button className="mt-3"  onClick = {() => this.handleSearch()} >Search</Button>
                        </div>

                        <div hidden={this.state.tableHide}>

                            <h5 className=" text-center mb-0 mt-2 text-uppercase">Total income in this period is: {this.state.sum} </h5>

                            <table border='1' style={{width:'100%'}}>
                                    <tr>
                                        <th>Date</th>
                                        <th>Price</th>
                                    </tr>
                                    {this.state.statistics.map((stats) => (
                                    
                                    <tr>
                                        <td>{new Date(stats.date).toDateString()}</td>
                                        <td>{stats.price}</td>
                                    </tr>
                                ))}
                                </table>
                        </div>

					</div>

				</div>

			</React.Fragment>
		);
	}
}

export default PharmacyIncomePriceStatistics;
