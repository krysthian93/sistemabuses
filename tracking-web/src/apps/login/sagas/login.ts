import { put, takeLatest } from 'redux-saga/effects';

import { startLogin, failedLogin, successLogin } from '../slices/loginSlice';
import api from '../../../utils/Api';

function* startLoginSaga({ payload }) {
    try {
        const response = yield api.post(process.env.REACT_APP_BASE_URL!.concat('/login'), payload);
        yield put(successLogin({ token: response?.headers['authorization'] }));
    } catch (e) {
        yield put(failedLogin(e?.message));
    }
}

export function* watchStartLogin() {
    yield takeLatest(startLogin, startLoginSaga);
}
