import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import dermatologistLogo from "../../static/dermatologistLogo.png";
import WorkTimesModal from "../../components/WorkTimesModal";
import CreateAppointmentForDermatologistModal from "../../components/CreateAppointmentForDermatologistModal";
import AddDermatologistToPharmacy from "../../components/AddDermatologistToPharmacy";
import PharmaciesForDermatologistModal from "../../components/PharmaciesForDermatologistModal";
import getAuthHeader from "../../GetHeader";
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css

class DermatologistsPage extends Component {
	state = {
        dermatologists: [],
        staffIdForWorkTimes:'',
        showWorkTimesModal: false,
        showCreateAppointmentModal: false,
        showAddDermatologistModal: false,
        workTimes:[],
        forPharmacy:'cafeddee-56cb-11eb-ae93-0242ac130202',
        forStaff:'',
        formShowed:false,
        name:'',
        surname:'',
        gradeFrom:'',
        gradeTo:'',
        showingSearched: false,
        pharmaciesForDermatologist:[],
        showPharmaciesModal:false,
    };



    componentDidMount() {

		Axios.get(BASE_URL + "/api/users/dermatologist-for-pharmacy/cafeddee-56cb-11eb-ae93-0242ac130202" , {
			headers: { Authorization: getAuthHeader() },
		})
			.then((res) => {
				this.setState({ dermatologists: res.data });
                console.log(res.data);
            
			})
			.catch((err) => {
				console.log(err);
			});
    }

    handleCreateAppoitmentClose = () => {
		this.setState({ showCreateAppointmentModal: false });
    };

    handleAddDermatologistModalClose = () => {
        this.setState({ showAddDermatologistModal: false });
    }
    
    handleAddAppointmentClick = () => {
		this.setState({
			showCreateAppointmentModal: true,
		});
    };
    
    handleAddDermatologistClick = () => {
        this.setState({
			showAddDermatologistModal: true,
		});
    }

    handleModalClose = () => {
        this.setState({showWorkTimesModal: false});
    }

    handlePharmaciesModalClose = () =>{
        this.setState({showPharmaciesModal: false});
    }

    onWorkTimeClick = (id) =>{
        
		Axios.get(BASE_URL + "/api/worktime/worktime-for-staff/" + id, {
			headers: { Authorization: getAuthHeader() },
		})
        .then((res) => {
            this.setState({ workTimes: res.data , forStaff:id});
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        });
        this.setState({
            showWorkTimesModal: true
        });
    }

    showPharmacies = (id) => {
        Axios.get(BASE_URL + "/api/pharmacy")
			.then((res) => {
				this.setState({ pharmaciesForDermatologist: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
        this.setState({
            showPharmaciesModal: true
        });
    }

    removeDermatologistClick = (id) =>{

        confirmAlert({
            message: 'Are you sure to remove dermatologist.',
            buttons: [
              {
                label: 'Yes',
                onClick: () => {
                    let removeDermatologistDTO = {
                        pharmacyId : 'cafeddee-56cb-11eb-ae93-0242ac130202',
                        dermatologistId: id,
                    };
            
                    Axios
                    .put(BASE_URL + "/api/users/remove-dermatologist-from-pharmacy", removeDermatologistDTO, {
                        headers: { Authorization: getAuthHeader() },
                    }).then((res) =>{
                        console.log(res.data);
                        
                        Axios.get(BASE_URL + "/api/users/dermatologist-for-pharmacy/cafeddee-56cb-11eb-ae93-0242ac130202", {
                            headers: { Authorization: getAuthHeader() },
                        }).then((res) => {
                            this.setState({ dermatologists: res.data });
                            console.log(res.data);
                        
                        })
                        .catch((err) => {
                            console.log(err);
                        });
                        
                    }).catch((err) => {
                        alert("Nije moguce obrisati datog dermatologa! ")
                    });
                }
              },
              {
                label: 'No',
                onClick: () => {
                    
                }
              }
            ]
        });
       
    }

    hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
    };
    
    handleNameChange = (event) => {
		this.setState({ name: event.target.value });
    };
    
    handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
    };
    
    handleGradeFromChange = (event) => {
		this.setState({ gradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ gradeTo: event.target.value });
    };
    
    handleSearchClick = () =>{
        alert("IMPLEMENT")
    }

    render() {
        const myStyle = {
			color: "white",
			textAlign: "center",
		};
		return (
        <React.Fragment>
            <div>

            </div>

                    <TopBar />
                    <Header />

                

                    
                    <div className="container" style={{ marginTop: "10%" }} >

                        <nav className="nav-menu d-none d-lg-block">
                            <ul style={{ marginLeft: "27%" }}>
                                <li>
                                    <a href="#"  className="appointment-btn scrollto" style={myStyle} onClick={this.handleAddDermatologistClick}>
                                        Add dermatologist
                                    </a>
                                </li>
                                <li>
                                    <a href="#" className="appointment-btn scrollto" style={myStyle} onClick={this.handleAddAppointmentClick}>
                                        Add appointments
                                    </a>
                                </li>
                            </ul>
                        </nav>

                        <button
                            className="btn btn-outline-primary btn-xl"
                            type="button"
                            onClick={this.hangleFormToogle}
                        >
                            <i className="icofont-rounded-down mr-1"></i>
                            Search dermatologists
                        </button>
                        <form
                            className={
                                this.state.formShowed ? "form-inline mt-3" : "form-inline mt-3 collapse"
                            }
                            width="100%"
                            id="formCollapse"
                            >
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
                                    placeholder="LastName"
                                    className="form-control mr-3"
                                    style={{ width: "9em" }}
                                    type="text"
                                    onChange={this.handleSurnameChange}
                                    value={this.state.surname}
                                />

                                <input
                                    placeholder="Grade from"
                                    className="form-control mr-3"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeFromChange}
                                    value={this.state.gradeFrom}
                                />
                                <input
                                    placeholder="Grade to"
                                    className="form-control"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeToChange}
                                    value={this.state.gradeTo}
                                />
                                
                            </div>
                            <div style={{marginLeft:'1%'}}>

                                <button
                                    style={{ background: "#1977cc" },{marginLeft:'15%'},{marginBottom:'8%'}}
                                    onClick={this.handleSearchClick}
                                    className="btn btn-primary btn-x2"
                                    type="button"
                                        >
                                    <i className="icofont-search mr-1"></i>
                                    Search
                                </button>
                            </div>

                        </form>

                        <div
						className={
							this.state.showingSearched
								? "form-group mt-2"
								: "form-group mt-2 collapse"
						}
					>
						<button
							type="button"
							className="btn btn-outline-secondary"
							onClick={this.handleResetSearch}
						>
							<i className="icofont-close-line mr-1"></i>Reset criteria
						</button>
					</div>

                        <table className="table" style={{ width: "100%", marginTop: "3rem" }}>
                            <tbody>
                                {this.state.dermatologists.map((dermatologist) => (
                                    <tr id={dermatologist.Id} key={dermatologist.Id}>
                                        <td width="130em">
                                            <img
                                                className="img-fluid"
                                                src={dermatologistLogo}
                                                width="70em"
                                            />
                                        </td>

                                        <td>
                                            <div>
                                                <b>Name: </b> {dermatologist.EntityDTO.name}
                                            </div>
                                            <div>
                                                <b>Surname: </b> {dermatologist.EntityDTO.surname}
                                            </div>
                                            <div>
                                                <b>Email: </b> {dermatologist.EntityDTO.email}
                                            </div>
                                            <div>
                                                <b>Phone number: </b> {dermatologist.EntityDTO.phoneNumber}
                                            </div>
                                            <div>
                                                <b>Grade: </b> {dermatologist.EntityDTO.grade}
                                                <i
                                                    className="icofont-star"
                                                    style={{ color: "#1977cc" }}
                                                ></i>
                                            </div>
                                        </td>
                                        <td >
                                            <div style={{marginLeft:'55%'}}>
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-secondary" onClick={() => this.onWorkTimeClick(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>WorkTimes</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary mt-1" onClick={() => this.showPharmacies(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Pharmacies</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary mt-1" onClick={() => this.removeDermatologistClick(dermatologist.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Remove dermatologist</button>
                                            </div>
                                               
                                        </td>
                                    </tr>

                                ))}
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>

                                </tr>
                            </tbody>
                        </table>

                    </div>
                    <div>
                        <WorkTimesModal show={this.state.showWorkTimesModal} onCloseModal={this.handleModalClose} workTimesForStaff={this.state.workTimes} forPharmacy={this.state.forPharmacy} forStaff={this.state.forStaff} header="WorkTimes" />
                        <CreateAppointmentForDermatologistModal
					        show={this.state.showCreateAppointmentModal}
					        onCloseModal={this.handleCreateAppoitmentClose}
					        pharmacyId={this.state.pharmacyId}
					        header="Create appointment"
					        dermatologists={this.state.dermatologists}
				        />
                        <AddDermatologistToPharmacy
					        show={this.state.showAddDermatologistModal}
					        onCloseModal={this.handleAddDermatologistModalClose}
                            pharmacyId={this.state.pharmacyId}
                            dermatologists={this.state.dermatologists}
					        header="Add dermatologist"
				        />
                        <PharmaciesForDermatologistModal
					        show={this.state.showPharmaciesModal}
					        onCloseModal={this.handlePharmaciesModalClose}
					        pharmacies={this.state.pharmaciesForDermatologist}
					        header="Work in pharmacies"
				        />
                    </div>
                </React.Fragment>
		);
	}
}

export default DermatologistsPage