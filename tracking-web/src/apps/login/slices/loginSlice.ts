import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export type Credentials = {
    username: string,
    password: string,
};

interface LoginData {
    token: string
}

interface LoginState {
    loading: boolean,
    data: object,
    errorMessage: string,
    isSuccess: boolean
}

const initialState: LoginState = {
    loading: false,
    data: {},
    errorMessage: '',
    isSuccess: false,
};

const loginSlice = createSlice({
    name: 'login',
    initialState,
    reducers: {
        startLogin(state, action: PayloadAction<Credentials>) {
            state.loading = true;
        },
        successLogin(state, action: PayloadAction<LoginData>) {
            state.loading = false;
            state.data = action.payload;
            state.isSuccess = true;
        },
        failedLogin(state, action) {
            state.loading = false;
            state.errorMessage = action.payload;
            state.isSuccess = false;
        },
    },
});

export const { startLogin, successLogin, failedLogin } = loginSlice.actions;
export default loginSlice.reducer;
