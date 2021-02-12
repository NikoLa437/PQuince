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
import CreateOrdersModal from "../../components/CreateOrdersModal";
import ViewDrugRequestsModal from "../../components/ViewDrugRequestsModal";

class DrugPageForPharmacyAdmin extends Component {
	state = {
        drugs: [],
        showAddDrugModal: false,
        showEditDrugPriceModal:false,
        showEditStorageAmountModal:false,
        showCreateOrderModal:false,
        drugIdForEditAmount:'',
        drugIdForEditPrice:'',
        forStaff:'',
        formShowed:false,
        searchName:'',
        searchManufacturer:'',
        searchGradeFrom:'',
        searchGradeTo:'',
        showingSearched: false,
        drugsToAdd:[],
        pharmacyId:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
        drugRequests:[],
        showDrugRequests:false,
    };

    componentDidMount() {
		let pharmacyId=localStorage.getItem("keyPharmacyId")
		this.setState({
			pharmacyId: pharmacyId
		})

        Axios
        .get(BASE_URL + "/api/drug/find-drugs-by-pharmacy-for-admin?pharmacyId="+ pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({drugs : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    updateDrugs = () =>{
        Axios
        .get(BASE_URL + "/api/drug/find-drugs-by-pharmacy-for-admin?pharmacyId="+ this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({drugs : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    handleAddDrugModalClose = () => {
        this.updateDrugs();

        this.setState({ showAddDrugModal: false });
    }

    handleAddOrderSuccessClose = () => {
        this.setState({ showCreateOrderModal: false });
        this.setState({
            hiddenSuccessAlert: false,
            hiddenFailAlert:true,
            successHeader: "Success",
            successMessage: "You successfully edit drug price.",
        })
    }

    handleEditDrugPriceModalSuccessClose = () => {
        this.setState({ showEditDrugPriceModal: false });
        this.setState({
            hiddenSuccessAlert: false,
            hiddenFailAlert:true,
            successHeader: "Success",
            successMessage: "You successfully edit drug price.",
        })
        this.updateDrugs();

    }

    handleSuccesfullCloseCreateOrder = () => {
        this.setState({
            showCreateOrderModal:false,
            hiddenSuccessAlert: false,
            hiddenFailAlert:true,
            successHeader: "Success",
            successMessage: "You successfully create order for drugs.",
        })
        this.updateDrugs();

    }

    handleEditDrugPriceModalUnsuccessClose = () => {
        this.setState({ showEditDrugPriceModal: false });
        this.setState({
            hiddenSuccessAlert: true,
            hiddenFailAlert:false,
            failHeader: "Unsuccess", 
            failMessage: "It is not possible to edit price"});
    
        this.updateDrugs();
    }

    handleEditDrugStorageAmountModalUnsuccessClose = () => {
        this.setState({ showEditDrugStorageModal: false });
        this.setState({
            hiddenSuccessAlert: true,
            hiddenFailAlert:false,
            failHeader: "Unsuccess", 
            failMessage: "It is not possible to edit storage amount"});
    
        this.updateDrugs();

    }

    handleEditDrugPriceModalClose =() =>{
        this.updateDrugs();
        this.setState({ showEditDrugPriceModal: false });
    }

    handleEditDrugStorageAmountModalClose =() =>{
        this.updateDrugs();
        this.setState({ showEditDrugStorageModal: false });
    }


    handleEditDrugStorageModalClose = () => {
        
        this.updateDrugs();
        this.setState({ showEditDrugStorageModal: false });
    }

    handleEditDrugStorageModalSuccessClose = () => {
        
        this.updateDrugs();

        this.setState({ showEditDrugStorageModal: false });
    }
    
    handleAddDrugClick = () => {
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
		});     
    }
    
    //todo
    handleAddPharmacistClick = () => {
        Axios.get(BASE_URL + "/api/users/pharmacists-for-employment", {
			headers: { Authorization: getAuthHeader() },
		}).then((res) => {
                this.setState({ pharmacistsToEmploye: res.data });

				console.log(res.data);
		})
			.catch((err) => {
				console.log(err);
		});
        this.setState({
			showAddPharmacistModal: true,
		});
    }

    handleCreateOrderClick=() => {
        this.setState({
            showCreateOrderModal:true
        })
    }

    showCreateOrderModal = () =>{
        this.setState({
            showCreateOrderModal:true
        })
    }

    handleModalClose = () => {
        this.setState({showWorkTimesModal: false});
    }


    //todo
    removePharmacistClick = (id) =>{

        confirmAlert({
            message: 'Are you sure to remove drug from pharmacy.',
            buttons: [
              {
                label: 'Yes',
                onClick: () => {
                    let removeDrugDTO = {
                        pharmacyId : this.state.pharmacyId,
                        drugId: id,
                    };
            
                    Axios
                    .put(BASE_URL + "/api/drug/remove-drug-from-pharmacy", removeDrugDTO, {
                        headers: { Authorization: getAuthHeader() },
                    }).then((res) =>{
                        console.log(res.data);
                        this.setState({
                            hiddenSuccessAlert: false,
                            hiddenFailAlert:true,
                            successHeader: "Success",
                            successMessage: "You successfully remove drug.",
                        })
                        this.updateDrugs();
                        
                    }).catch((err) => {
                        this.setState({ 
                            hiddenSuccessAlert: true,
                            hiddenFailAlert: false, 
                            failHeader: "Unsuccess", 
                            failMessage: "It is not possible to remove the drug"});
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
		this.setState({ searchName: event.target.value });
    };
    
    handleManufacturerChange = (event) => {
		this.setState({ searchManufacturer: event.target.value });
    };
    
    handleGradeFromChange = (event) => {
		this.setState({ searchGradeFrom: event.target.value });
	};

	handleGradeToChange = (event) => {
		this.setState({ searchGradeTo: event.target.value });
    };

    onEditPriceClick = (id) => {
        this.setState({
			showEditDrugPriceModal: true,
            drugIdForEditPrice:id
		});
        
    };

    handleCreateOrderModalClose = () =>{
        this.setState({
			showCreateOrderModal: false,
		});
    }

    handleCreateOrderModalCloseSuccess = () =>{
        this.setState({
			showCreateOrderModal: false,
		});
    }

    onEditStorageClick = (id) =>{
        this.setState({
			showEditDrugStorageModal: true,
            drugIdForEditAmount:id
		});
    }

    handleDrugRequestsClick = ()=>{
        Axios.get(BASE_URL + "/api/drug/requests-drug-pharmacy", {
			headers: { Authorization: getAuthHeader() },
		}).then((res) => {
                this.setState({ drugRequests: res.data, showDrugRequests:true });

				console.log(res.data);
		})
			.catch((err) => {
				console.log(err);
		});
    }

    
    handleSearchClick = () =>{
			if (
				!(
					this.state.searchGradeFrom === "" &&
					this.state.searchGradeTo === "" &&
					this.state.searchName === "" &&
					this.state.searchSurname === ""
				)
			) {
				let gradeFrom = this.state.searchGradeFrom;
				let gradeTo = this.state.searchGradeTo;
				let name = this.state.searchName;
				let manufacturer = this.state.searchManufacturer;

				if (gradeFrom === "") gradeFrom = -1;
				if (gradeTo === "") gradeTo = -1;
				if (name === "") name = '';
				if (manufacturer === "") manufacturer = '';

				const SEARCH_URL =
					BASE_URL +
					"/api/drug/search-drugs-for-pharmacy-admin?name=" +
                    name +
                    "&manufacturer=" +
					manufacturer +
					"&gradeFrom=" +
					gradeFrom +
					"&gradeTo=" +
					gradeTo +
					"&pharmacyId=" +
					this.state.pharmacyId;

				Axios.get(SEARCH_URL, {
                    headers: { Authorization: getAuthHeader() },
                })
					.then((res) => {
						this.setState({
							drugs: res.data,
							formShowed: false,
							showingSearched: true,
						});
						console.log(res.data);
					})
					.catch((err) => {
						console.log(err);
					});
			}
     }

     handleCloseDrugRequestsModal = () =>{
         this.setState({
             showDrugRequests:false
         })
     }

     handleResetSearch = () => {
        this.updateDrugs();
        this.setState({
            formShowed: false,
            showingSearched: false,
            searchName: "",
            searchSurname: "",
            searchGradeFrom: "",
            searchGradeTo: "", 
        })
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
            marginLeft:'17%'
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
                            <ul style={myStyle}>
                                <li>
                                    <a href="#" style={{color:'white'}}  className="appointment-btn scrollto"  onClick={this.handleAddDrugClick}>
                                        Add drug
                                    </a>
                                </li>
                                <li>
                                    <a href="#" style={{color:'white'}} className="appointment-btn scrollto" onClick={this.handleCreateOrderClick}>
                                        Create order
                                    </a>
                                </li>
                                <li>
                                    <a href="#" style={{color:'white'}} className="appointment-btn scrollto"  onClick={this.handleDrugRequestsClick}>
                                        Drug requests
                                    </a>
                                </li>
                            </ul>
                        </nav>
                        <button
                            className="btn btn-outline-primary btn-xl "
                            type="button"
                            onClick={this.hangleFormToogle}
                        >
                            <i className="icofont-rounded-down mr-1"></i>
                            Search drugs
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
                                    value={this.state.searchName}
                                />

                                <input
                                    placeholder="Manufacturer"
                                    className="form-control mr-3"
                                    style={{ width: "9em" }}
                                    type="text"
                                    onChange={this.handleManufacturerChange}
                                    value={this.state.searchManufacturer}
                                />

                                <input
                                    placeholder="Grade from"
                                    className="form-control mr-3"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeFromChange}
                                    value={this.state.searchGradeFrom}
                                />
                                <input
                                    placeholder="Grade to"
                                    className="form-control"
                                    style={{ width: "9em" }}
                                    type="number"
                                    min="1"
                                    max="5"
                                    onChange={this.handleGradeToChange}
                                    value={this.state.searchGradeTo}
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

                        <table className="table mt-5" style={{width:"100%"}}>
                            <tbody>
                                {this.state.drugs.map(drug => 
                                    <tr hidden={false} id={drug.Id} key={drug.Id} >
                                        <td width="130em">
                                            <img className="img-fluid" src={CapsuleLogo} width="70em"/>
                                        </td>
                                        <td>
                                            <div><b>Drug:</b> {drug.EntityDTO.name}</div>
                                            <div><b>Name:</b> {drug.EntityDTO.drugInstanceName}</div>
                                            <div><b>Manufacturer:</b> {drug.EntityDTO.manufacturerName}</div>
                                            <div><b>Quantity:</b> {drug.EntityDTO.quantity} <b>mg</b></div>
                                        </td>
                                        <td>
                                            <div><b>Format:</b> {drug.EntityDTO.drugFormat}</div>
                                            <div><b>Actual price:</b> {drug.EntityDTO.price}</div>
                                            <div><b>Grade:</b> {drug.EntityDTO.avgGrade}</div>
                                        </td>
                                        <td >
                                            <div className="mt-1" style={{marginLeft:'25%'}}>
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-secondary" onClick={() => this.onEditStorageClick(drug.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Edit storage</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary" onClick={() => this.onEditPriceClick(drug.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Edit price</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary mt-1" onClick={() => this.removePharmacistClick(drug.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Remove drug</button>
                                            </div>
                                               
                                        </td>
                                    </tr>
                                    )}
                            </tbody>
                        </table>
                    </div>
                    <div>
                        <AddDrugToPharmacy
					        show={this.state.showAddDrugModal}
					        onCloseModal={this.handleAddDrugModalClose}
                            pharmacyId={this.state.pharmacyId}
                            drugs={this.state.drugsToAdd}
                            updateDrugs={this.updateDrugs}
					        header="Add drugs"
				        />
                        <CreateOrdersModal
					        show={this.state.showCreateOrderModal}
                            onCloseSuccess={this.handleCreateOrderModalCloseSuccess}
					        onCloseModal={this.handleCreateOrderModalClose}
                            pharmacyId={this.state.pharmacyId}
                            drugs={this.state.drugs}
					        header="Create order"
				        />
                        <EditPriceForDrugInPharmacy
                            show={this.state.showEditDrugPriceModal}
                            onCloseModal={this.handleEditDrugPriceModalClose}
                            pharmacyId={this.state.pharmacyId}
                            drug={this.state.drugIdForEditPrice}
                            updateDrugs={this.updateDrugs}
					        header="Edit drug price"
                         />
                          <EditStorageAmountForDrug
                            show={this.state.showEditDrugStorageModal}
                            onCloseModal={this.handleEditDrugStorageModalClose}
                            pharmacyId={this.state.pharmacyId}
                            drug={this.state.drugIdForEditAmount}
                            updateDrugs={this.updateDrugs}
					        header="Edit storage amount"
                         />
                        <ViewDrugRequestsModal
					        show={this.state.showDrugRequests}
					        onCloseModal={this.handleCloseDrugRequestsModal}
                            drugRequests={this.state.drugRequests}
					        header="Drug requests"
				        />
                    </div>
                </React.Fragment>
		);
	}
}

export default DrugPageForPharmacyAdmin