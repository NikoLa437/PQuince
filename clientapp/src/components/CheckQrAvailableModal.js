import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import OrderLogo from "../static/order.png";
import TopBar from "../components/TopBar";
import Header from "../components/Header";
import QrReader from 'react-qr-reader'
import Axios from "axios";
import getAuthHeader from "../GetHeader";
import { withRouter } from "react-router";
import ModalDialog from "../components/ModalDialog";
import { BASE_URL } from "../constants.js";
import PharmacyLogo from "../static/pharmacyLogo.png";
import '../App.js'
import { Redirect } from "react-router-dom";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";
import HeadingAlert from "../components/HeadingAlert";

class CheckQrAvailableModal extends Component {
    state = {

		redirect:false,
		redirectUrl:'',
        ereceptId:'',
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",   
	};

    constructor(props){
        super(props)
        this.state = {
            delay: 100,
            result: 'No result',
            hiddenSuccessAlert: true,
            hiddenFailAlert: true,
            ereceptId:'',
        }
        this.handleScan = this.handleScan.bind(this)
    }

    componentDidMount() {
    }


    handleClickOnClose = () => {
        this.props.onCloseModal();
    }

    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};

    openImageDialog() {
        this.refs.qrReader1.openImageDialog()
    }
	


    handleScan(data){
	    let RefuseReceiptDTO = {
	    	id: data
	    }
        this.setState({
            ereceptId:data
        })
	    
    	Axios.post(BASE_URL + "/api/ereceipt/check-if-refused", RefuseReceiptDTO, { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				console.log(res.data)
				if(res.data.allergic == true){
					console.log("REJECT")
					this.setState({
						openModalRefused: true ,
        			})
				}else{
				  this.setState({
		            result: data,
		            redirect:true,
					redirectUrl : "/qrpharmacies/"+data
		       	 })
		        }
			})
			.catch((err) => {
				console.log(err);
			});
	
    }

    handleCheckAvailable = () => {
        if(this.state.ereceptId==''){
            this.setState({
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Please read a QR code"   
            })
        }else{
            Axios.get(BASE_URL + "/api/pharmacy/has-qrpharmacies?pharamcyId=" +this.props.pharmacyId +"&qrId="+ this.state.ereceptId, 
            {  
                validateStatus: () => true,
                headers: { Authorization: getAuthHeader() } })
                .then((res) => {
                    console.log(res.data)
                    if (res.status === 200) {
                        this.setState({
                            hiddenSuccessAlert: false,
                            hiddenFailAlert:true,
                            successHeader: "Success",
                            successMessage: "This pharmacy has drugs from QR code",
                            selectedStartDate:new Date(),
                        })
                        this.props.updateOrders();
                    }else if(res.status===400){
                        this.setState({ 
                            hiddenSuccessAlert: true,
                            hiddenFailAlert: false, 
                            failHeader: "Unsuccess", 
                            failMessage: "This pharmacy has not drugs from QR code"
                        });
                    }else if(res.status===401){
                        this.setState({ 
                            unauthorizedRedirect:true
                        });
                    }
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    }

    handleError(err){
        console.error(err)
    }

    render() { 
        const previewStyle = {
            height: 240,
            width: 320,
        }
        return ( 
            <Modal
                show = {this.props.show}
                size = "lg"
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'45%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
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
                <div>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Enter QR code</h5>
       		    <div className="container">
                    <QrReader ref="qrReader1"
                        delay={this.state.delay}
                        style={previewStyle}
                        onError={this.handleError}
                        onScan={this.handleScan}
                        legacyMode={true}
                    />
                    <button
                        type="button"
                        onClick={this.openImageDialog.bind(this)}
                        className="btn btn-outline-secondary mt-3"
                    >
                        Submit QR Code
                    </button>

                    <button
                        type="button"
                        onClick={this.handleCheckAvailable}
                        className="btn btn-outline-secondary mt-3"
                    >
                        Check available
                    </button>
                
                </div>
                </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default CheckQrAvailableModal;