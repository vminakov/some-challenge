import React from 'react';

import LoadingPlaceholder from "./LoadingPlaceholder";
import ErrorPlaceholder from './ErrorPlaceholder';
import Table from "./Transactions/Table";
import Form from "./Transactions/Form";
import { fetchTransactions } from '../api';

class Transactions extends React.Component {
    constructor(props) {
        super(props); 

        this.state = {
            loading: true,
            transactions: [],
            error: ""
        }
    }


    init() {
        fetchTransactions(this.props.creditCardId).then(res => {
            this.setState({
                loading: false,
                transactions: res.data
            })
        }).catch(err => {
            this.setState({
                loading: false,
                error: err.response ? err.response.data.message : err.message
            })
        });
    }

    componentDidMount() {
        this.init();
    }

    refresh() {
        this.init();
    }


    render() {
        if (this.state.loading) {
            return <LoadingPlaceholder title="Transactions" />
        }

        if (this.state.error) {
            return <ErrorPlaceholder error={this.state.error} />
        }



        return (
            <div className="container">
                <fieldset className="box">
                    <legend>Transactions</legend>
                        <Form onSuccess={() => this.refresh()} creditCardId={this.props.creditCardId} />
                        <div className="">
                            <Table transactions={this.state.transactions} />
                    </div>
                </fieldset>
            </div>
          
        )
    }

  
}

export default Transactions;