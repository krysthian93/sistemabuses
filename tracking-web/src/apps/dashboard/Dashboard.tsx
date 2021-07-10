import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
    BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, AreaChart, Area,
} from 'recharts';
import { Grid } from '@material-ui/core';
import { Link } from 'react-router-dom';
import * as moment from 'moment';
import 'moment/locale/es';

import { startDashboard } from '../common/actions';
import { RootState } from '../../store/reducers';
import TypeReportEnum from '../../utils/TypeReportEnum';
import TypeReportColorEnum from '../../utils/TypeReportColorEnum';

const DetailButton = ({ color, typeReport }) => {
    return (
        <Grid item>
            <Link
                to={{
                    pathname: '/reports/detail',
                    state: { typeReport, color },
                }}
            >
                Detalles
            </Link>
        </Grid>
    );
};

export default function Dashboard() {
    const dispatch = useDispatch();
    const dashboard = useSelector((state: RootState) => state.reports.dashboard);
    const distanceBetweenReport = dashboard.distanceBetween
        .map(({ distanceBetween, month }) => ({ name: moment.months(month), distanceBetween }));
    const timeTraveledReport = dashboard.timeTraveled
        .map(({ timeTraveled, month }) => ({ name: moment.months(month), timeTraveled }));
    const tourPerMonthReport = dashboard.tourPerMonth
        .map(({ tourPerMonth, month }) => ({ name: moment.months(month), tourPerMonth }));

    useEffect(() => {
        dispatch(startDashboard());
    }, []);

    return (
        <Grid container direction="row">
            <Grid item xs={6}>
                <Grid container direction="column">
                    <Grid item xs={6}>
                        <Grid container direction="column" spacing={2}>
                            <DetailButton
                                typeReport={TypeReportEnum.DISTANCE_BETWEEN}
                                color={TypeReportColorEnum.DISTANCE_BETWEEN}
                            />
                            <Grid item>
                                <BarChart
                                    width={600}
                                    height={310}
                                    data={distanceBetweenReport || []}
                                    margin={{
                                        top: 5, right: 30, left: 20, bottom: 5,
                                    }}
                                >
                                    <CartesianGrid strokeDasharray="3 3" />
                                    <XAxis dataKey="name" />
                                    <YAxis />
                                    <Tooltip />
                                    <Legend />
                                    <Bar
                                        name="Distancia Recorrida"
                                        dataKey="distanceBetween"
                                        fill={TypeReportColorEnum.DISTANCE_BETWEEN}
                                        barSize={20}
                                    />
                                </BarChart>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={6}>
                        <Grid container direction="column" spacing={2}>
                            <DetailButton
                                typeReport={TypeReportEnum.TOUR_PER_MONTH}
                                color={TypeReportColorEnum.TOUR_PER_MONTH}
                            />
                            <Grid item>
                                <BarChart
                                    width={600}
                                    height={310}
                                    data={tourPerMonthReport || []}
                                    margin={{
                                        top: 5, right: 30, left: 20, bottom: 5,
                                    }}
                                >
                                    <CartesianGrid strokeDasharray="3 3" />
                                    <XAxis dataKey="name" />
                                    <YAxis />
                                    <Tooltip />
                                    <Legend />
                                    <Bar
                                        name="Viajes por mes"
                                        dataKey="tourPerMonth"
                                        fill={TypeReportColorEnum.TOUR_PER_MONTH}
                                        barSize={20}
                                    />
                                </BarChart>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
            <Grid container item xs={6} alignItems="center">
                <Grid container direction="column" spacing={2}>
                    <DetailButton
                        typeReport={TypeReportEnum.TIME_TRAVELED}
                        color={TypeReportColorEnum.TIME_TRAVELED}
                    />
                    <Grid item>
                        <AreaChart
                            width={500}
                            height={400}
                            data={timeTraveledReport}
                            margin={{
                                top: 10, right: 30, left: 0, bottom: 0,
                            }}
                        >
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="name" />
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            <Area
                                name="Tiempo viajado"
                                type="monotone"
                                dataKey="timeTraveled"
                                stroke={TypeReportColorEnum.TIME_TRAVELED}
                                fill={TypeReportColorEnum.TIME_TRAVELED}
                            />
                        </AreaChart>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
}
