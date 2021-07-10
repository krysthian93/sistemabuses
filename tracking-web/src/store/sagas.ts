import { all, fork } from 'redux-saga/effects';
import loginSagas from '../apps/login/sagas';
import reportsSagas from '../apps/reports/sagas';

export default function* rootSaga() {
    yield all(
        [
            ...Object.values(loginSagas),
            ...Object.values(reportsSagas),
        ].map(fork),
    );
}
