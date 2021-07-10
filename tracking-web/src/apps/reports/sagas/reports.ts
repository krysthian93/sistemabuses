import { call, put, takeLatest } from 'redux-saga/effects';
import api from '../../../utils/Api';
import { showAdvancedReport } from '../slices/reportsSlice';
import { START_ADVANCED_REPORTS, StartAdvancedReportAction } from '../../common/types';
import { setError } from '../../common/actions';
import { GENERIC_MESSAGE_ERROR } from '../../../utils/Constants';

const fetchAdvancedReports = (fromDate, toDate) => api.get(process.env.REACT_APP_BASE_URL!
    .concat('/reports/tours/range-date'), { params: { fromDate, toDate } });

function* advancedReportsSaga(startDateAction: StartAdvancedReportAction) {
    try {
        const response = yield call(fetchAdvancedReports,
            startDateAction.payload.fromDate,
            startDateAction.payload.toDate);
        yield put(showAdvancedReport(response.data));
    } catch (e) {
        yield put(setError(e?.message || GENERIC_MESSAGE_ERROR));
    }
}

export function* watchAdvancedReports() {
    yield takeLatest(START_ADVANCED_REPORTS, advancedReportsSaga);
}
