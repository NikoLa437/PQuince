import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import CapsuleLogo from "../../static/capsuleLogo.png";
import getAuthHeader from "../../GetHeader";
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";
import AddDrugToPharmacy from "../../components/AddDrugToPharmacy";
import EditPriceForDrugInPharmacy from "../../components/EditPriceForDrugInPharmacy";
import EditStorageAmountForDrug from "../../components/EditStorageAmountForDrug";

class ActionAndPromotionsPage extends Component {
	state = {
        actions: [],
        showAddActionModal: false,
        pharmacyId:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
    };

    componentDidMount() {
		let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})

        /*Axios
        .get(BASE_URL + "/api/drug/find-drugs-by-pharmacy-for-admin?pharmacyId="+ pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({drugs : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});*/
    }

    updateActionAndPromotions = () =>{
        /*
        Axios
        .get(BASE_URL + "/api/drug/find-drugs-by-pharmacy-for-admin?pharmacyId="+ this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({drugs : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});*/
    }

    handleAddActionModalClose = () => {
        this.updateActionAndPromotions();

        this.setState({ showAddActionModal: false });
    }


    handleAddDrugClick = () => {
        /*
        Axios.get(BASE_URL + "/api/drug/drugs-for-add-in-pharmacy?pharmacyId=" + this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) => {
                this.setState({ drugsToAdd: res.data });
				console.log(res.data);
		})
			.catch((err) => {
				console.log(err);
		});

        this.setState({
			showAddDrugModal: true,
		});     */
    }
    


    handleModalClose = () => {
        this.setState({showWorkTimesModal: false});
    }


 
    hangleFormToogle = () => {
		this.setState({ formShowed: !this.state.formShowed });
    };
    
    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    render() {
        const myStyle = {
			color: "white",
            textAlign: "center",
            marginLeft:'185%'
		};
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

                        <nav className="nav-menu d-none d-lg-block">
                            <ul>
                                <li>
                                    <a href="#"  className="appointment-btn scrollto" style={myStyle} onClick={this.handleAddDrugClick}>
                                        Add action and promotions
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    
                        <table className="table mt-5" style={{width:"100%"}}>
                            <tbody>
                                {this.state.actions.map(action => 
                                    <tr hidden={false} id={action.Id} key={action.Id} >
                                        <td width="130em">
                                            <img className="img-fluid" src={CapsuleLogo} width="70em"/>
                                        </td>

                                    </tr>
                                    )}
                            </tbody>
                        </table>
                    </div>
                    <div>
                    </div>
                </React.Fragment>
		);
	}
}

export default ActionAndPromotionsPage