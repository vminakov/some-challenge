import React from 'react';
import PropTypes from "prop-types";

const LoadingPlaceholder = ({title}) => {
    return (
        <div className="container">
            <fieldset className="box">
                <legend>{title}</legend>
                <div className="loading">Loading...</div>
            </fieldset>
        </div>
    )
}

LoadingPlaceholder.propTypes = {
    title: PropTypes.string.isRequired
};


export default LoadingPlaceholder;