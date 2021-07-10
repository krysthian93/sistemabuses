import React from 'react';
import { Route } from 'react-router-dom';
import PropTypes from 'prop-types';

const RouteWithLayout = (props) => {
    const { layout: Layout, component: Component } = props;

    return (
        <Route
            render={(matchProps) => (
                <Layout>
                    <Component {...matchProps} />
                </Layout>
            )}
        />
    );
};

RouteWithLayout.propTypes = {
    component: PropTypes.elementType.isRequired,
    layout: PropTypes.elementType.isRequired,
    path: PropTypes.string.isRequired,
};

export default RouteWithLayout;
