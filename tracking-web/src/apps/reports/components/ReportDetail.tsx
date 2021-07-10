/* eslint-disable max-len */
import React, { useEffect } from 'react';
import {
    FormControl,
    Grid,
    InputBase,
    InputLabel,
    MenuItem,
    Select,
} from '@material-ui/core';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { DatePicker } from '@material-ui/pickers';
import { AxiosError, AxiosResponse } from 'axios';
import PropTypes from 'prop-types';
import * as moment from 'moment';

import api from '../../../utils/Api';
import BarChartReport from './BarChartReport';
import { DistanceBetween } from '../../../types/DistanceBetween';
import { TourPerMonth } from '../../../types/TourPerMonth';
import { TimeTraveled } from '../../../types/TimeTraveled';
import TypeReportEnum from '../../../utils/TypeReportEnum';
import TypeReportColorEnum from '../../../utils/TypeReportColorEnum';

const useStyles = makeStyles((theme) => ({
    margin: {
        margin: theme.spacing(1),
    },
}));

const BootstrapInput = withStyles((theme) => ({
    root: {
        'label + &': {
            marginTop: theme.spacing(3),
        },
    },
    input: {
        borderRadius: 4,
        position: 'relative',
        backgroundColor: theme.palette.background.paper,
        border: '1px solid #ced4da',
        fontSize: 16,
        padding: '10px 26px 10px 12px',
        transition: theme.transitions.create(['border-color', 'box-shadow']),
        // Use the system font instead of the default Roboto font.
        fontFamily: [
            '-apple-system',
            'BlinkMacSystemFont',
            '"Segoe UI"',
            'Roboto',
            '"Helvetica Neue"',
            'Arial',
            'sans-serif',
            '"Apple Color Emoji"',
            '"Segoe UI Emoji"',
            '"Segoe UI Symbol"',
        ].join(','),
        '&:focus': {
            borderRadius: 4,
            borderColor: '#80bdff',
            boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)',
        },
    },
}))(InputBase);

const getChartDescription = (name) => ({
    [TypeReportEnum.DISTANCE_BETWEEN]: 'Distancia Recorrida',
    [TypeReportEnum.TOUR_PER_MONTH]: 'Viajes por mes',
    [TypeReportEnum.TIME_TRAVELED]: 'Tiempo viajado',
})[name];

interface DataReport {
    data: Array<DistanceBetween | TourPerMonth | TimeTraveled>,
    nameBar: string,
    dataKey: string,
    color: string,
}

export default function ReportDetail(props) {
    const [typeReport, setTypeReport] = React.useState(TypeReportEnum.DISTANCE_BETWEEN);
    const [year, setYear] = React.useState(new Date());
    const [dataReport, setDataReport] = React.useState<DataReport>({
        data: [],
        nameBar: '',
        dataKey: '',
        color: '',
    });
    const classes = useStyles();

    const getReport = (year: number, typeReport: TypeReportEnum, color: string | undefined = '#66ccff') => {
        api.get(process.env.REACT_APP_BASE_URL!.concat('/reports/', typeReport), { params: { year } })
            .then((response: AxiosResponse) => {
                setDataReport({
                    data: response.data
                        .map(({ [typeReport]: value, month }) => ({ name: moment.months(month), [typeReport]: value })),
                    nameBar: getChartDescription(typeReport),
                    dataKey: typeReport,
                    color,
                });
            }).catch((error: AxiosError) => {
                console.log('error: ', error);
            });
    };

    useEffect(() => {
        setTypeReport(props?.location?.state?.typeReport || TypeReportEnum.DISTANCE_BETWEEN);
        getReport(year.getFullYear(), props?.location?.state?.typeReport || TypeReportEnum.DISTANCE_BETWEEN,
            props?.location?.state?.color || TypeReportColorEnum.DISTANCE_BETWEEN);
    }, []);

    const handleChangeReport = (e) => {
        setTypeReport(e.target.value);
        getReport(year.getFullYear(), e.target.value);
    };

    const handleChangeYear = (e) => {
        setYear(new Date(e));
        getReport(new Date(e).getFullYear(), typeReport);
    };

    return (
        <Grid container direction="column">
            <Grid item container direction="row">
                <Grid item xs={12} lg={4}>
                    <FormControl className={classes.margin}>
                        <InputLabel id="demo-customized-select-label">Tipo de Reporte</InputLabel>
                        <Select
                            labelId="demo-customized-select-label"
                            id="demo-customized-select"
                            value={typeReport}
                            onChange={handleChangeReport}
                            input={<BootstrapInput />}
                        >
                            <MenuItem value={TypeReportEnum.DISTANCE_BETWEEN}>Distancia Recorrida</MenuItem>
                            <MenuItem value={TypeReportEnum.TOUR_PER_MONTH}>Viajes por mes</MenuItem>
                            <MenuItem value={TypeReportEnum.TIME_TRAVELED}>Tiempo viajado</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <Grid
                    item
                    xs={12}
                    lg={4}
                    container
                    justify="flex-start"
                    alignItems="center"
                >
                    <DatePicker
                        views={['year']}
                        label="Año"
                        value={year}
                        onChange={handleChangeYear}
                    />
                </Grid>
                <Grid item xs={12} lg={4}>
                    <p>1</p>
                </Grid>
            </Grid>
            <Grid item>
                <BarChartReport
                    nameBar={dataReport.nameBar}
                    dataKey={dataReport.dataKey}
                    data={dataReport.data}
                    color={dataReport.color}
                />
            </Grid>
        </Grid>
    );
}

ReportDetail.propTypes = {
    location: PropTypes.shape({
        state: PropTypes.shape({
            typeReport: PropTypes.string,
            color: PropTypes.string,
        }),
    }).isRequired,
};
