package co.com.etn.arquitecturamvpbase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.User;
import co.com.etn.arquitecturamvpbase.presenter.LoginPresenter;
import co.com.etn.arquitecturamvpbase.repository.LoginRepository;
import co.com.etn.arquitecturamvpbase.view.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    private TextInputEditText userName;
    private TextInputEditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setPresenter(new LoginPresenter(new LoginRepository()));
        getPresenter().inject(this, getValidateInternet());

        validateAutoLogin(getApplicationContext());

        initViews();
    }

    private void validateAutoLogin(Context context) {
        getPresenter().validateAutoLogin(context);
    }

    private void initViews() {
        userName = (TextInputEditText) findViewById(R.id.login_edittext_user);
        password = (TextInputEditText) findViewById(R.id.login_edittext_password);
    }

    public void login(View view) {
        getPresenter().login(userName.getText().toString(), password.getText().toString(), getApplicationContext());


    }

    @Override
    public void loginSuccess(User user) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
    }


}
