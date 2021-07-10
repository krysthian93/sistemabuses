package tracking.com.trackingandroid.apps.register.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.apps.record_tour.ui.DialogLoading;
import tracking.com.trackingandroid.apps.register.RegisterPresenter;
import tracking.com.trackingandroid.data.model.User;
import tracking.com.trackingandroid.main.DrawerActivity;
import tracking.com.trackingandroid.util.CommonUtils;

public class RegisterFragment extends Fragment implements RegisterView {

    @BindView(R.id.et_full_name) EditText fullName;
    @BindView(R.id.et_username) EditText username;
    @BindView(R.id.et_password) EditText password;
    @BindView(R.id.et_repassword) EditText rePassword;

    @Inject
    RegisterPresenter registerPresenter;
    @Inject
    Context context;


    private DialogLoading dialogLoading;
    private ViewPager viewPager;
    private Unbinder unbinder;
    private static final String TAG = RegisterFragment.class.getSimpleName();

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        viewPager = getActivity().findViewById(R.id.viewPager);
        dialogLoading = new DialogLoading(getActivity());
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        if (returnFormattedText(fullName).isEmpty()
                || returnFormattedText(username).isEmpty()
                || returnFormattedText(password).isEmpty()
                || returnFormattedText(rePassword).isEmpty()
        ) {
            CommonUtils.showSimpleToastMessages(context,
                    "Debe Ingresar todos los campos Requeridos");
        } else if (!password.getText().toString().trim().equals(rePassword.getText().toString().trim())) {
            CommonUtils.showSimpleToastMessages(context,
                    "Las contraseñas ingresadas no son iguales");
        } else {
            User user = new User(returnFormattedText(username),
                    returnFormattedText(fullName),
                    CommonUtils.convertPasswordBCrypt(returnFormattedText(password)), 1);
            registerPresenter.register(user);
        }
    }

    @Override
    public void showMessages(String message) {
        CommonUtils.showSimpleToastMessages(context, message);
    }

    @Override
    public void showProgress() {
        dialogLoading.startLoadingDialog();
    }

    @Override
    public void hideProgress() {
        dialogLoading.dismissLoadingDialog();
    }

    @Override
    public void clearFormAndNavigate() {
        fullName.setText("");
        username.setText("");
        password.setText("");
        rePassword.setText("");
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String returnFormattedText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
