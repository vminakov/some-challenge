import React from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom';

import CredictCards from './components/CreditCards';
import Transactions from './components/Transactions';

import './App.scss';

class App extends React.Component {
    render() {
        return (
            <Router>
                <div>
                    <Switch>
                        <Route exact path={"/"} component={CredictCards} />
                        <Route exact path={"/transactions/:creditCardId"} 
                            render={(props) => <Transactions {...props} creditCardId ={props.match.params.creditCardId} /> }
                        />
                    </Switch>
                </div>
            </Router>
        )
    }
}

export default App;