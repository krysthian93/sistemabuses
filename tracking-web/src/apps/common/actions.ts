import {
    CommonActionTypes,
    DatesPayload,
    SET_ERROR,
    START_ADVANCED_REPORTS,
    START_GET_DASHBOARD,
} from './types';

export function startAdvancedReports(dates: DatesPayload): CommonActionTypes {
    return {
        type: START_ADVANCED_REPORTS,
        payload: dates,
    };
}

export function startDashboard(): CommonActionTypes {
    return {
        type: START_GET_DASHBOARD,
    };
}

export function setError(error: string): CommonActionTypes {
    return {
        type: SET_ERROR,
        payload: error,
    };
}
