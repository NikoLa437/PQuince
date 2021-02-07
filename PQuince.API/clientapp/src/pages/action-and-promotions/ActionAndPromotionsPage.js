import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import PromotionIcon from "../../static/promotionIcon.png";
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";
import AddActionAndPromotionsModal from "../../components/AddActionAndPromotionsModal";
import { BASE_URL } from "../../constants.js";
import getAuthHeader from "../../GetHeader";

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

        Axios
        .get(BASE_URL + "/api/pharmacy/find-action-and-promotion-by-pharmacy", {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({actions : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    updateActionAndPromotions = () =>{
        
        Axios
        .get(BASE_URL + "/api/pharmacy/find-action-and-promotion-by-pharmacy", {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({actions : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    handleAddActionModalClose = () => {
        this.updateActionAndPromotions();

        this.setState({ showAddActionModal: false });
    }

    handleAddActionClick=()=>{
        this.setState({showAddActionModal: true});

    }

    handleModalClose = () => {
        this.setState({showAddActionModal: false});
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
            marginLeft:'210%'
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
                                    <a href="#"  className="appointment-btn scrollto" style={myStyle} onClick={this.handleAddActionClick}>
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
                                            <img className="img-fluid" src={PromotionIcon} width="70em"/>
                                        </td>
                                        <td>
										<div hidden={action.EntityDTO.actionAndPromotionType!=="DRUGDISCOUNT"}>
											<b>Action for drug buying</b>
										</div>
                                        <div hidden={action.EntityDTO.actionAndPromotionType!=="EXAMINATIONDISCOUNT"}>
											<b>Action for examination</b>
										</div>
                                        <div hidden={action.EntityDTO.actionAndPromotionType!=="CONSULTATIONDISCOUNT"}>
											<b>Action for colsutation</b>
										</div>
										<div>
											<b>Date from: </b> {new Date(action.EntityDTO.dateFrom).toDateString()}
										</div>
										<div>
											<b>Date to: </b> {new Date(action.EntityDTO.dateTo).toDateString()}
										</div>
                                        <div>
											<b>Percent of discount: </b> {action.EntityDTO.percentOfDiscount}
										</div>
									</td>
                                    </tr>
                                    )}
                            </tbody>
                        </table>
                    </div>
                    <div>
                        <AddActionAndPromotionsModal 
                            show={this.state.showAddActionModal}
                            updateAction = {this.updateActionAndPromotions}
                            onCloseModal={this.handleModalClose} 
                            forPharmacy={this.state.forPharmacy} 
                            header="Add action and promotions" />

                    </div>
                </React.Fragment>
		);
	}
}

export default ActionAndPromotionsPage