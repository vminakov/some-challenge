import React from 'react';
import PropTypes from "prop-types";

import { createCard } from '../../api';

class Form extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            number: "",
            initialBalance: "",
            isValid: true,
            validationMessage: ""
        }
    }

    handleOnSubmit(e) {
        e.preventDefault();

        if (!this.isValid()) {
            return;
        }

        createCard(this.state.number, this.state.initialBalance).then(() => {
            this.setState({
                number: "",
                initialBalance: ""
            });
            this.props.onSuccess();
        }).catch(error => {
            const { message, errors } = error.response.data;
            const validationMessage = (errors) ?  errors.map(e => e.defaultMessage).join("<br />") : message;

            this.setState({
                isValid: false,
                validationMessage: validationMessage
            });
        });
    }

    handleOnChange(e) {
        this.setState({
            [e.target.name]: e.target.value,
            isValid: true,
            validationMessage: ""
        });
    }

    isValid() {
        if (this.state.number.length <= 0) {
            this.setState({
                isValid: false,
                validationMessage: "Please enter credit card number"
            })
            return false;
        }
        
        if (isNaN(this.state.initialBalance) || this.state.initialBalance <= 0) {
            this.setState({
                isValid: false,
                validationMessage: "Please enter valid amount greater than 0"
            })
            return false;
        }

        return true;
    }

    render() {
        return (
            <form className="widget-form" onSubmit={(e) => this.handleOnSubmit(e)}>
                <table>
                    <thead>
                        <tr>
                            <th>Card number</th>
                            <th>Initial balance</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        {!this.state.isValid &&
                            <tr>
                                <td colSpan="3">
                                    <div className="error-message">{this.state.validationMessage}</div>
                                </td>
                            </tr>
                        }
                        <tr>
                            <td>
                                <input type="text" name="number" value={this.state.number} onChange={e => this.handleOnChange(e)} placeholder="Enter credit card number" />
                            </td>
                            <td>
                                <input type="number" name="initialBalance" value={this.state.initialBalance} onChange={e => this.handleOnChange(e)} placeholder="Enter initial balance" />
                            </td>
                            <td>
                                <input type="submit" value="Add card" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        )
    }
}

Form.propTypes = {
    onSuccess: PropTypes.func.isRequired
};

export default Form;