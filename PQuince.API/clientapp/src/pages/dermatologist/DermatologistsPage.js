import React, { Component } from "react";
import TopBar from "../../components/TopBar";
import Header from "../../components/Header"
import Axios from "axios";
import { BASE_URL } from "../../constants.js";
import dermatologistLogo from "../../static/dermatologistLogo.png";

class DermatologistsPage extends Component {
	state = {
        dermatologists: []
    };

    componentDidMount() {

		Axios.get(BASE_URL + "/api/users/dermatologist-for-pharmacy/cafeddee-56cb-11eb-ae93-0242ac130202")
			.then((res) => {
				this.setState({ dermatologists: res.data });
				console.log(res.data);
			})
			.catch((err) => {
				console.log(err);
			});
    }
    render() {
		return (
        <React.Fragment>
                    <TopBar />
                    <Header />

                    <div className="container" style={{ marginTop: "10%" }}>
                        <h5 className=" text-center mb-0 mt-2 text-uppercase">Our dermatologists</h5>

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
                                                <button style={{height:'30px'},{verticalAlign:'center'}} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Dodaj radno vreme</button>
                                                <br></br>
                                                <button style={{height:'30px'},{verticalAlign:'center'},{marginTop:'2%'}} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Remove dermatologist</button>
                                            </div>
                                               

                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </React.Fragment>
		);
	}
}

export default DermatologistsPage