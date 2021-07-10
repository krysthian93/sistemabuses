import { HIDE_ERROR } from './types';

const initialState = {
    error: null,
};

export default function errorReducer(state = initialState, action) {
    const { error } = action;

    if (error) {
        return {
            error,
        };
    } else if (action.type === HIDE_ERROR) {
        return {
            error: null,
        };
    }

    return state;
}
