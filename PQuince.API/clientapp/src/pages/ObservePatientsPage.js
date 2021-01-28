import React, { Component } from 'react';
import Header from '../components/Header';
import TopBar from '../components/TopBar';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import { Button } from 'react-bootstrap';
import PatientLogo from "../static/patientLogo.png";

class ObservePatientsPage extends Component {
    state = {
        name:"",
        surname:"",
        patients: []
    }
    handleNameChange = (event) => {
        this.setState({name: event.target.value});
      }
    handleSurnameChange = (event) => {
        this.setState({surname: event.target.value});
    }

    handleSearch = () => {
        console.log(this.state);
        const SEARCH_URL =
            BASE_URL +
            "/api/users/search?name=" +
            this.state.name +
            "&surname=" +
            this.state.surname;

        Axios.get(SEARCH_URL)
            .then((res) => {
                this.setState({
                    patients: res.data
                });
                console.log(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    };
    
    handlePatientClick = (patientId) => {
		Axios.get(BASE_URL + "/api/users/patient/" + patientId)
			.then((res) => {
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
	};

    render() { 
        return ( 
        <React.Fragment>
                <TopBar/>
                <Header/>

                <div className="container" style={{marginTop:"10%"}}>
                    <h5 className=" text-left mb-0 text-uppercase" style={{marginTop: "2rem"}}>Patients</h5>
 
                    <div className="row section-design">
                        <div className="col-lg-8 mx-auto">
                            <br/>

                            <form className="form-inline mt-3" width="100%">
                                <div className="form-group mb-2" width="100%">
                                    <input
                                        placeholder="Name"
                                        className="form-control mr-3"
                                        style={{ width: "9em" }}
                                        type="text"
                                        onChange={this.handleNameChange}
                                        value={this.state.name}
                                    />
                                    <input
                                        placeholder="Surname"
                                        className="form-control mr-3"
                                        style={{ width: "9em" }}
                                        type="text"
                                        onChange={this.handleSurnameChange}
                                        value={this.state.surname}
                                    />
                                </div>
                                <div>
                                    <button
                                        style={{ background: "#1977cc", marginBottom: "8px" }}
                                        onClick={this.handleSearch}
                                        className="btn btn-primary btn-xl"
                                        type="button"
                                    >
                                        <i className="icofont-search mr-1"></i>
                                        Search
                                    </button>
                                </div>
					        </form>
                        </div>
                    </div>

                    <table className="table table-hover" style={{ width: "100%", marginTop: "3rem" }}>
						<tbody>
							{this.state.patients.map((patient) => (
								<tr id={patient.Id} key={patient.Id} onClick={() => this.handlePatientClick(patient.Id)} className="rounded" style={{ cursor: "pointer" }}>
                                    <td width="130em">
										<img
											className="img-fluid"
											src={PatientLogo}
											width="70em"
										/>
									</td>
									<td>
										<div>
											<b>Name: </b> {patient.EntityDTO.name + " " + patient.EntityDTO.surname}
										</div>
                                        <div>
											<b>Phone number: </b> {patient.EntityDTO.phoneNumber}
										</div>
                                        <div>
											<b>Email: </b> {patient.EntityDTO.email}
										</div>
									</td>
								</tr>
							))}
						</tbody>
					</table>
                </div>
        </React.Fragment> );
    }
}
 
export default ObservePatientsPage;