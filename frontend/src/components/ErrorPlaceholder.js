import React from 'react';
import PropTypes from "prop-types";

const ErrorPlaceholder = ({error}) => {
    return (
        <div className="container">
            <fieldset className="box">
                <legend>Error</legend>
                <div className="error-message">
                    {error}
                </div>
            </fieldset>
        </div>
    )
}

ErrorPlaceholder.propTypes = {
    error: PropTypes.object.isRequired
};


export default ErrorPlaceholder;