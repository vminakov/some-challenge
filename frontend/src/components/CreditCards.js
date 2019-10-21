import React from 'react';

import { fetchCards } from '../api';
import LoadingPlaceholder from './LoadingPlaceholder';
import Table from './CreditCards/Table';
import Form from './CreditCards/Form';
import ErrorPlaceholder from './ErrorPlaceholder';

class CreditCards extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            cards: [],
            error: ""
        }
    }

    init() {
        fetchCards().then(res => {
            this.setState({
                loading: false,
                cards: res.data
            })
        }).catch(err => {
            this.setState({
                loading: false,
                error: err.message || JSON.stringify(err)
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
            return <LoadingPlaceholder title="Cards" />
        }

        if (this.state.error) {
            return <ErrorPlaceholder error={this.state.error} />
        }


        return (
            <div className="container">
                <fieldset className="box">
                    <legend>Cards</legend>
                    <Form onSuccess={() => this.refresh()} />
                    <div>
                        <Table cards={this.state.cards} />
                    </div>
                </fieldset>
            </div>
          
        )
    }
}

export default CreditCards;