import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Grid, Typography } from '@material-ui/core';
import MaterialTable from 'material-table';
import { KeyboardDatePicker } from '@material-ui/pickers';
import { MaterialUiPickersDate } from '@material-ui/pickers/typings/date';
import moment from 'moment';

import { startAdvancedReports } from '../../common/actions';
import { RootState } from '../../../store/reducers';
import { DATE_FORMAT } from '../../../utils/Constants';
import { handleSaveToPC } from '../../../utils/CommonUtils';

export default function ReportAdvanced() {
    const dateOneMonthAgo = new Date();
    dateOneMonthAgo.setMonth(dateOneMonthAgo.getMonth() - 1);
    const [fromDate, setDateFrom] = React.useState<MaterialUiPickersDate>(dateOneMonthAgo);
    const [toDate, setDateTo] = React.useState<MaterialUiPickersDate>(new Date());

    const reportsData = useSelector((state: RootState) => state.reports.advanced);
    const tableData = JSON.parse(JSON.stringify(reportsData));

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(startAdvancedReports({
            fromDate: moment(fromDate).format(DATE_FORMAT),
            toDate: moment(toDate).format(DATE_FORMAT),
        }));
    }, []);

    useEffect(() => {
        dispatch(startAdvancedReports({
            fromDate: moment(fromDate).format(DATE_FORMAT),
            toDate: moment(toDate).format(DATE_FORMAT),
        }));
    }, [fromDate, toDate]);

    return (
        <Grid container spacing={4} direction="column">
            <Grid item xs={12}>
                <Typography variant="h5">Reporte Personalizado</Typography>
            </Grid>
            <Grid item xs={12}>
                <Grid container spacing={3}>
                    <Grid item xs={12} lg={4}>
                        <KeyboardDatePicker
                            autoOk
                            variant="inline"
                            inputVariant="outlined"
                            label="Desde"
                            format="dd/MM/yyyy"
                            value={fromDate}
                            onChange={(date) => setDateFrom(date)}
                        />
                    </Grid>
                    <Grid item xs={12} lg={4}>
                        <KeyboardDatePicker
                            autoOk
                            variant="inline"
                            inputVariant="outlined"
                            label="Hasta"
                            format="dd/MM/yyyy"
                            value={toDate}
                            onChange={(date) => setDateTo(date)}
                        />
                    </Grid>
                </Grid>
            </Grid>
            <Grid item xs={12}>
                <div style={{ flex: 1, maxWidth: '100%' }}>
                    <MaterialTable
                        title="Tours"
                        columns={[
                            { title: 'Id', field: 'tourId', type: 'numeric' },
                            { title: 'Inicio', field: 'timeStart' },
                            { title: 'Fin', field: 'timeFinish' },
                            { title: 'Tiempo Recorrido', field: 'timeTravel' },
                            { title: 'Distancia Recorrida', field: 'distanceBetween' },
                        ]}
                        data={tableData}
                        options={{
                            search: false,
                            draggable: false,
                            exportButton: true,
                            exportCsv: (columns, data) => {
                                handleSaveToPC(data);
                            },
                            rowStyle: (x) => {
                                if (x.tableData.id % 2) {
                                    return { backgroundColor: '#c4d9f2', color: '#518CBF' };
                                }
                                return { color: '#518CBF' };
                            },
                        }}
                    />
                </div>
            </Grid>
        </Grid>
    );
}
