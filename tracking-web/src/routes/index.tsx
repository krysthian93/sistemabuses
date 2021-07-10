import React from 'react';
import { Route, Switch } from 'react-router-dom';
import SignInSide from '../apps/login/components/SignInSide';
import Dashboard from '../apps/dashboard/Dashboard';
import ReportAdvanced from '../apps/reports/components/ReportAdvanced';
import RouteWithLayout from './RouteWithLayout';
import MainLayout from '../layouts/MainLayout';
import ReportDetail from '../apps/reports/components/ReportDetail';

export default function Routes() {
    return (
        <Switch>
            <Route exact path="/login" component={SignInSide} />
            <RouteWithLayout
                layout={MainLayout}
                path="/dashboard"
                component={Dashboard}
            />
            <RouteWithLayout
                layout={MainLayout}
                path="/reports/advanced"
                component={ReportAdvanced}
            />
            <RouteWithLayout
                layout={MainLayout}
                path="/reports/detail"
                component={ReportDetail}
            />
        </Switch>
    );
}
