import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import WorkTimeIcon from "../../static/schedule.png";
import getAuthHeader from "../../GetHeader";
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";
import { confirmAlert } from 'react-confirm-alert'; // Import
import { Redirect } from "react-router-dom";
import AbsenceRejectReasonModal from "../../components/AbsenceRejectReasonModal";

class AbsencePageForPharmacyAdmin extends Component {
	state = {
        absences: [],
        pharmacyId:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
        unauthorizedRedirect: false,
        showReasonModal:false,
    };

    componentDidMount() {
		let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})

        Axios
        .get(BASE_URL + "/api/absence/get-absence-for-pharmacy?pharmacyId="+ pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({absences : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    onClickAcceptAbsence = id => {
        let EntityIdDTO = {
            id: id
        };
        
        Axios
            .put(BASE_URL + "/api/absence/accept-absence", EntityIdDTO, {
                validateStatus: () => true,
                headers: { Authorization: getAuthHeader() },
            }).then((res) =>{
                if (res.status === 204) {
                    this.setState({
                        hiddenSuccessAlert: false,
                        hiddenFailAlert:true,
                        successHeader: "Success",
                        successMessage: "You successfully approve absence"
                    })
                    this.updateAbsences();
                }else if(res.status===400){
                    this.setState({ 
                        hiddenSuccessAlert: true,
                        hiddenFailAlert: false, 
                        failHeader: "Unsuccess", 
                        failMessage: "Currently not possible to approve absence"
                    });
                }else if(res.status===401){
                    this.setState({ 
                        unauthorizedRedirect:true
                    });
                }
                console.log(res.data);
            }).catch((err) => {
            });
    }

    onClickRejectAbsence = (id) => {
        this.setState({
            showReasonModal:true,
            absenceToReject:id
        })
    }



    updateAbsences = () =>{
        Axios
        .get(BASE_URL + "/api/absence/get-absence-for-pharmacy?pharmacyId="+ this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({absences : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    
    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    handleCloseAbsenceModal = () =>{
        this.setState({
            showReasonModal:false
        })
    }

    handleRejectSuccessModalClose = () =>{
        this.setState({
            showReasonModal:false,
            hiddenSuccessAlert: false,
            hiddenFailAlert:true,
            successHeader: "Success",
            successMessage: "You successfully reject absence"
        })
        this.updateAbsences();
    }

    render() {
        if (this.state.unauthorizedRedirect) return <Redirect push to="/unauthorized" />;

		return (
        <React.Fragment>
            <div>

            </div>

                    <TopBar />
                    <Header />

        
                    <div className="container" style={{ marginTop: "10%" }} >
                        <HeadingSuccessAlert
                            hidden={this.state.hiddenSuccessAlert}
                            header={this.state.successHeader}
                            message={this.state.successMessage}
                            handleCloseAlert={this.handleCloseAlertSuccess}
                        />
                        <HeadingAlert
                                hidden={this.state.hiddenFailAlert}
                                header={this.state.failHeader}
                                message={this.state.failMessage}
                                handleCloseAlert={this.handleCloseAlertFail}
                        />
						<h5 className=" text-center mb-0 mt-2 text-uppercase">Absences </h5>

                        <table className="table mt-5" style={{width:"100%"}}>
                            <tbody>
                                {this.state.absences.map(absence => 
                                    <tr hidden={false} id={absence.Id} key={absence.Id} >
                                        <td width="130em">
                                            <img className="img-fluid" src={WorkTimeIcon} width="100em"/>
                                        </td>
                                        <td >
                                            <div><b>For employe:</b> {absence.EntityDTO.forStaff}</div>

                                            <div><b>Date from:</b> {new Date(absence.EntityDTO.startDate).toDateString()}</div>
                                            <div><b>Date to:</b> {new Date(absence.EntityDTO.endDate).toDateString()}</div>
                                        </td>
                                        <td >
                                            <div  style={{marginLeft:'25%'}} className="mt-2">
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-secondary" onClick={() => this.onClickAcceptAbsence(absence.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Accept</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary" onClick={() => this.onClickRejectAbsence(absence.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Reject</button>
                                                <br></br>                                       
                                             </div>
                                        </td>
                                    </tr>
                                    )}
                            </tbody>
                        </table>
                    </div>
                    <div>
                        <AbsenceRejectReasonModal
                            show={this.state.showReasonModal}
                            onCloseModal={this.handleCloseAbsenceModal}
                            onCloseWithRejectSuccess={this.handleRejectSuccessModalClose}
                            absenceId={this.state.absenceToReject}
                            updateDrugs={this.updateDrugs}
					        header="Reject absence"
                         />
                    </div>
                </React.Fragment>
		);
	}
}

export default AbsencePageForPharmacyAdmin