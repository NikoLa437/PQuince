import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import OrderLogo from "../../static/order.png";
import getAuthHeader from "../../GetHeader";
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css
import HeadingSuccessAlert from "../../components/HeadingSuccessAlert";
import HeadingAlert from "../../components/HeadingAlert";
import { confirmAlert } from 'react-confirm-alert'; // Import

class OrdersPage extends Component {
	state = {
        orders: [],
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
        .get(BASE_URL + "/api/order/find-orders-for-pharmacy?pharmacyId="+ pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({orders : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    removeOrderClick = id => {
        confirmAlert({
            message: 'Are you sure to remove order?',
            buttons: [
              {
                label: 'Yes',
                onClick: () => {
                    let EntityIdDTO = {
                        id: id 
                    };

                    Axios
                    .put(BASE_URL + "/api/order/remove-order-from-pharmacy", EntityIdDTO, {
                        validateStatus: () => true,
                        headers: { Authorization: getAuthHeader() },
                    }).then((res) =>{
                        if (res.status === 200) {
                            this.setState({
                                hiddenSuccessAlert: false,
                                hiddenFailAlert:true,
                                successHeader: "Success",
                                successMessage: "You successfully delete order.",
                            })
                            this.updateOrders();
                        }else if(res.status === 400)
                        {
                            this.setState({
                                hiddenSuccessAlert: true,
                                hiddenFailAlert:false,
                                failHeader: "Unsuccess", 
                                failMessage: "Not possible to remove order because have offers"
                            })
                        }else if(res.status === 500)
                        {
                            this.setState({
                                hiddenSuccessAlert: true,
                                hiddenFailAlert:false,
                                failHeader: "Unsuccess", 
                                failMessage: "We have some problem. Try later."
                            })
                        }
                        
                    }).catch(() => {

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

    updateOrders = () =>{
        Axios
        .get(BASE_URL + "/api/order/find-orders-for-pharmacy?pharmacyId="+ this.state.pharmacyId, {
			headers: { Authorization: getAuthHeader() },
		}).then((res) =>{
            this.setState({orders : res.data});
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    
    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    render() {
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
					
                        <table className="table mt-5" style={{width:"100%"}}>
                            <tbody>
                                {this.state.orders.map(order => 
                                    <tr hidden={false} id={order.Id} key={order.Id} >
                                        <td width="130em">
                                            <img className="img-fluid" src={OrderLogo} width="100em"/>
                                        </td>
                                        <td>
                                            <div><b>Deadline:</b> {new Date(order.EntityDTO.endDate).toDateString()}</div>
                                            <div><b>Created by:</b> {order.EntityDTO.creator}</div>
                                            <div><b>Number of offers:</b> {order.EntityDTO.numberOfOffers}</div>
                                        </td>
                                        <td >
                                            <div  style={{marginLeft:'25%'}}>
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-outline-secondary" onClick={() => this.onEditStorageClick(order.Id)} type="button"><i className="icofont-subscribe mr-1"></i>View offers</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary" onClick={() => this.onEditPriceClick(order.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Edit order</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-outline-secondary mt-1" onClick={() => this.removeOrderClick(order.Id)} type="button"><i className="icofont-subscribe mr-1"></i>Delete order</button>
                                            </div>
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

export default OrdersPage