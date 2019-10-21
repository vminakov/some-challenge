import React from 'react';
import PropTypes from "prop-types";

import { createTransaction } from '../../api';

class Form extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            type: "",
            amount: "",
            isValid: true,
            validationMessage: ""
        }
    }

    handleOnSubmit(e) {
        e.preventDefault();

        if (!this.isValid()) {
            return;
        }

        createTransaction(this.props.creditCardId, this.state.type, this.state.amount).then(() => {
            this.setState({
                type: "",
                amount: ""
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
        if (!["CHARGE", "CREDIT"].includes(this.state.type)) {
            this.setState({
                isValid: false,
                validationMessage: "Please select valid transaction type"
            });
            return false;
        }
        if (isNaN(this.state.amount) || this.state.amount <= 0) {
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
                            <th>Action</th>
                            <th>Amount</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        {!this.state.isValid &&
                            <tr>
                                <td colSpan="3">
                                    <div className="error-message">
                                        {this.state.validationMessage}
                                    </div>
                                </td>
                            </tr>
                        }

                        <tr>
                            <td>
                                <select name="type" value={this.state.type} onChange={e => this.handleOnChange(e)}>
                                    <option value=""></option>
                                    <option value="CHARGE">Charge</option>
                                    <option value="CREDIT">Credit</option>
                                </select>
                            </td>
                            <td>
                                <input type="number" name="amount" value={this.state.amount} onChange={e => this.handleOnChange(e)} placeholder="Enter amount" />
                            </td>
                            <td>
                                <input type="submit" value="Apply" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        )
    }
}

Form.propTypes = {
    creditCardId: PropTypes.string.isRequired,
    onSuccess: PropTypes.func.isRequired
};

export default Form;