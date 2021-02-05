import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header";
import QrReader from 'react-qr-reader'
import Axios from "axios";
import { withRouter } from "react-router";
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
	
   constructor(props){
        super(props)
        this.state = {
            delay: 100,
            result: 'No result',
        }

        this.handleScan = this.handleScan.bind(this)
    }
    handleScan(data){
        this.setState({
            result: data,
            redirect:true,
			redirectUrl : "/qrpharmacies/"+data
        })
     
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
                
                <p>{this.state.result}</p>
            </div>
            </div>
		</React.Fragment>
    )
  }
}


export default withRouter(QRreader);