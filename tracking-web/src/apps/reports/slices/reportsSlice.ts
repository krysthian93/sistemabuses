import { createSlice } from '@reduxjs/toolkit';
import { DistanceBetween } from '../../../types/DistanceBetween';
import { TourPerMonth } from '../../../types/TourPerMonth';
import { TimeTraveled } from '../../../types/TimeTraveled';

interface Dashboard {
    distanceBetween: Array<DistanceBetween>,
    tourPerMonth: Array<TourPerMonth>,
    timeTraveled: Array<TimeTraveled>
}

interface AdvancedReportState {
    advanced: Array<object>,
    dashboard: Dashboard,
}

const initialState: AdvancedReportState = {
    advanced: [],
    dashboard: {
        distanceBetween: [],
        tourPerMonth: [],
        timeTraveled: [],
    },
};

const reportsSlice = createSlice({
    name: 'reports',
    initialState,
    reducers: {
        showAdvancedReport(state, action) {
            state.advanced = action.payload;
        },
        showDashboard(state, action) {
            state.dashboard = action.payload;
        },
    },
});

export const { showAdvancedReport, showDashboard } = reportsSlice.actions;
export default reportsSlice.reducer;
