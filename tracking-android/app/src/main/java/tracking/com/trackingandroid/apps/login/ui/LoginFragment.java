package tracking.com.trackingandroid.apps.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.apps.login.LoginPresenter;
import tracking.com.trackingandroid.apps.record_tour.ui.DialogLoading;
import tracking.com.trackingandroid.main.DrawerActivity;

public class LoginFragment extends Fragment implements LoginView {

    @Inject
    LoginPresenter loginPresenter;
    @Inject
    Context context;

    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;

    private DialogLoading dialogLoading;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        dialogLoading = new DialogLoading(getActivity());
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @OnClick(R.id.btn_login)
    public void login() {
        loginPresenter.login(username.getText().toString().trim(), password.getText().toString().trim());
    }

    @Override
    public void showMainMenu() {
        Intent myIntent = new Intent(getActivity(), DrawerActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(myIntent);
        getActivity().finish();
    }

    @Override
    public void showMessages(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        dialogLoading.startLoadingDialog();
    }

    @Override
    public void hideProgress() {
        dialogLoading.dismissLoadingDialog();
    }
}
