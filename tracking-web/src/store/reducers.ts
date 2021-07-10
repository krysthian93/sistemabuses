/* eslint-disable no-param-reassign */
import { combineReducers } from 'redux';
import loginReducer from '../apps/login/slices/loginSlice';
import reportsReducer from '../apps/reports/slices/reportsSlice';

const appReducers = combineReducers({
    login: loginReducer,
    reports: reportsReducer,
});

/**
 * Reset all state of all reducers.
 * */
const rootReducer = (state, action) => {
    if (action.type === 'RESET_STATE') {
        state = undefined;
    }
    return appReducers(state, action);
};

export type RootState = ReturnType<typeof rootReducer>

export default rootReducer;
