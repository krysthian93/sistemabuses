import { call, put, takeLatest } from 'redux-saga/effects';
import { setError } from '../../common/actions';
import { GENERIC_MESSAGE_ERROR } from '../../../utils/Constants';
import api from '../../../utils/Api';
import { START_GET_DASHBOARD } from '../../common/types';
import { showDashboard } from '../slices/reportsSlice';

const fetchDashboard = () => api.get(process.env.REACT_APP_BASE_URL!.concat('/reports/dashboard'));

function* dashboardSaga() {
    try {
        const response = yield call(fetchDashboard);
        yield put(showDashboard(response.data));
    } catch (e) {
        yield put(setError(e?.message || GENERIC_MESSAGE_ERROR));
    }
}

export function* watchDashboard() {
    yield takeLatest(START_GET_DASHBOARD, dashboardSaga);
}
