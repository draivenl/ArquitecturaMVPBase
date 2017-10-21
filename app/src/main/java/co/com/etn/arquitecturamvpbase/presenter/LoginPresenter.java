package co.com.etn.arquitecturamvpbase.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.CustomSharedPreferences;
import co.com.etn.arquitecturamvpbase.model.User;
import co.com.etn.arquitecturamvpbase.repository.LoginRepository;
import co.com.etn.arquitecturamvpbase.view.activity.ILoginView;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by Lisandro Gómez on 10/14/17.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {
    private LoginRepository loginRepository;

    public LoginPresenter(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void login(final String userName, final String password, final Context context) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loginService(userName, password, context);
            }
        });
        thread.start();

    }


    public void loginService(String userName, String password, Context context) {
        try {

            User user = loginRepository.login(userName, password);
            saveUser(user, context);
            getView().loginSuccess(user);


        } catch (RetrofitError retrofitError){
//            String s = retrofitError.getMessage();
            retrofitError.printStackTrace();
            String s =  new String(((TypedByteArray)retrofitError.getResponse().getBody()).getBytes());
            getView().showToast(s);
        } finally {
//            getView().hideProgress();
        }


    }

    private void saveUser(User user, Context context) {
        CustomSharedPreferences preferences = new CustomSharedPreferences(context);
        preferences.saveObjectUser(User.class.getName(), user);
    }

    public void validateAutoLogin(Context context) {
        CustomSharedPreferences preferences = new CustomSharedPreferences(context);
        User user = preferences.getObjectUser(User.class.getName());
        if (user != null) {
            getView().showToast(user.getToken());
            getUserWithTokenThread(user.getToken());
        }
    }


    public void getUserWithTokenThread(final String token) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loginWhitTokenService(token);
            }
        });
        thread.start();
    }

    public void loginWhitTokenService(String token) {
        try {

            User user = loginRepository.authLogin(token);
            getView().loginSuccess(user);


        } catch (RetrofitError retrofitError){
//            String s = retrofitError.getMessage();
            retrofitError.printStackTrace();
            String s =  new String(((TypedByteArray)retrofitError.getResponse().getBody()).getBytes());
            getView().showToast(s);
        } finally {
//            getView().hideProgress();
        }


    }
}
