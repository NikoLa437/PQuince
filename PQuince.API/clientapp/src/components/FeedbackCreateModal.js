import React, { Component } from "react";
import { Button, Modal } from "react-bootstrap";
import Star from "../static/star.png";
import StarOutline from "../static/star-outline.png";

class FeedbackCreateModal extends Component {
	render() {
		return (
			<Modal
				show={this.props.show}
				dialogClassName="modal-80w-100h"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				onHide={this.props.onCloseModal}
			>
				<Modal.Header closeButton>
					<Modal.Title id="contained-modal-title-vcenter">
						{this.props.header}
					</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<h5>{"Rate " + this.props.name + " " + this.props.forWho}</h5>

					<div className="form-group">
						<div className="form-row">
							<img
								hidden={this.props.grade >= 1}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={StarOutline}
								width="50em"
								onClick={() => this.props.handleClickIcon(1)}
							/>
							<img
								hidden={this.props.grade < 1}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={Star}
								width="50em"
								onClick={() => this.props.handleClickIcon(1)}
							/>
							<img
								hidden={this.props.grade >= 2}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={StarOutline}
								width="50em"
								onClick={() => this.props.handleClickIcon(2)}
							/>
							<img
								hidden={this.props.grade < 2}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={Star}
								width="50em"
								onClick={() => this.props.handleClickIcon(2)}
							/>
							<img
								hidden={this.props.grade >= 3}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={StarOutline}
								width="50em"
								onClick={() => this.props.handleClickIcon(3)}
							/>
							<img
								hidden={this.props.grade < 3}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={Star}
								width="50em"
								onClick={() => this.props.handleClickIcon(3)}
							/>
							<img
								hidden={this.props.grade >= 4}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={StarOutline}
								width="50em"
								onClick={() => this.props.handleClickIcon(4)}
							/>
							<img
								hidden={this.props.grade < 4}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={Star}
								width="50em"
								onClick={() => this.props.handleClickIcon(4)}
							/>
							<img
								hidden={this.props.grade >= 5}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={StarOutline}
								width="50em"
								onClick={() => this.props.handleClickIcon(5)}
							/>
							<img
								hidden={this.props.grade < 5}
								className="img-fluid ml-1"
								style={{ cursor: "pointer" }}
								src={Star}
								width="50em"
								onClick={() => this.props.handleClickIcon(5)}
							/>
						</div>
					</div>

					<Button className="mt-3" onClick={this.props.giveFeedback}>
						{this.props.buttonName}
					</Button>
				</Modal.Body>
				<Modal.Footer>
					<Button onClick={this.props.onCloseModal}>Close</Button>
				</Modal.Footer>
			</Modal>
		);
	}
}

export default FeedbackCreateModal;
