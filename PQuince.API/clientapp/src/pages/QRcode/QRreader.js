import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import QrReader from 'react-qr-reader'
import Axios from "axios";
import getAuthHeader from "../../GetHeader";
import { withRouter } from "react-router";
import ModalDialog from "../../components/ModalDialog";
import { BASE_URL } from "../../constants.js";
import PharmacyLogo from "../../static/pharmacyLogo.png";
import '../../App.js'
import { Redirect } from "react-router-dom";
 
class QRreader extends Component {
	state = {
		pharmacies: [],
		formShowed: false,
		name: "",
		city: "",
		openModal: false,
		openModalRefused: false,
		gradeFrom: "",
		gradeTo: "",
		distanceFrom: "",
		distanceTo: "",
		showingSearched: false,
		showingSorted: false,
		currentLatitude: null,
		currentLongitude: null,
		sortIndicator: 0,
		redirect:false,
		redirectUrl:''
	};
	

	componentDidMount() {
	
		Axios.get(BASE_URL + "/api/users/patient", { headers: { Authorization: getAuthHeader() } })
			.then((res) => {
				if(res.data.EntityDTO.penalty > 2){
					this.setState({
						openModal: true ,
						redirectUrl : "/",
        			})
				}
			})
			.catch((err) => {
				console.log(err);
			});
	
	}
	
	handleModalClose = () => {
		this.setState({ 
			openModal: false,
			redirect:true, 
		});
	};
	
   constructor(props){
        super(props)
        this.state = {
            delay: 100,
            result: 'No result',
        }
        this.handleScan = this.handleScan.bind(this)
    }
    
    handleModalCloseRefused= () => {
		this.setState({ 
			openModalRefused: false,
		});
	};
    
    handleScan(data){
	    let RefuseReceiptDTO = {
	    	id: data
	    }
	    
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
    handleError(err){
        console.error(err)
    }
    openImageDialog() {
        this.refs.qrReader1.openImageDialog()
    }
	
  render() {
  if (this.state.redirect) return <Redirect push to={this.state.redirectUrl} />;
	
	  const previewStyle = {
            height: 240,
            width: 320,
        }
    return (
    <React.Fragment>
			<TopBar />
			<Header />
    <div className="container" style={{ marginTop: "10%" }}>
					<h5 className=" text-center mb-0 mt-2 text-uppercase">Enter QR code</h5>
       		<div className="container" style={{ marginTop: "0%" }}>
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
                
            </div>
            </div>
            <ModalDialog
				show={this.state.openModal}
				href="/"
				onCloseModal={this.handleModalClose}
				header="Error"
				text="You can't use this option because your penalty points are 3 and more."
			/>
			<ModalDialog
				show={this.state.openModalRefused}
				onCloseModal={this.handleModalCloseRefused}
				header="Error"
				text="This EReceipt is REFUSED, you CAN'T use it."
			/>
		</React.Fragment>
    )
  }
}


export default withRouter(QRreader);