import React from 'react';
import PropTypes from "prop-types";
import { NavLink as Link } from 'react-router-dom';

const Table = ({cards}) => {

    return (
        <table className="widget-table">
            <thead>
                <tr>
                    <th>Card number</th>
                    <th>Balance</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {cards.map(card => {
                    return (
                        <tr key={card.id}>
                            <td>{card.number}</td>
                            <td>{card.balance}</td>
                            <td>
                                <Link to={`/transactions/${card.id}`}>Transactions</Link>
                            </td>
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}

Table.propTypes = {
    cards: PropTypes.array
};

export default Table;

