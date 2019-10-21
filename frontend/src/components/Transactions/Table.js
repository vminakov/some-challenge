import React from 'react';
import PropTypes from "prop-types";
import Moment from 'react-moment';

const Table = ({transactions}) => {

    return (
        <table className="widget-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Balance</th>
                </tr>
            </thead>
            <tbody>
                {transactions.map(transaction => {
                    return (
                        <tr key={transaction.id}>
                            <td><Moment format="DD.MM.YYYY HH:mm:ss">{transaction.createDateTime}</Moment></td>
                            <td>{transaction.type}</td>
                            <td>{transaction.amount}</td>
                            <td>{transaction.balance}</td>
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}

Table.propTypes = {
    transactions: PropTypes.array
};

export default Table;

