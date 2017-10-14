package co.com.etn.arquitecturamvpbase.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.IValidateInternet;
import co.com.etn.arquitecturamvpbase.helper.ShowAlertDialog;
import co.com.etn.arquitecturamvpbase.helper.ValidateInternet;
import co.com.etn.arquitecturamvpbase.presenter.BasePresenter;

/**
 * Created by draiven on 9/16/17.
 */

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    private ProgressDialog progressDialog;
    private IValidateInternet validateInternet;

    private T presenter;

    private ShowAlertDialog  showAlertDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateInternet = new ValidateInternet(BaseActivity.this);
        this.showAlertDialog = new ShowAlertDialog(this);
    }

    public ShowAlertDialog getShowAlertDialog() {
        return showAlertDialog;
    }

    @Override
    public void showProgress(final int message) {



        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(getResources().getString(message));
                progressDialog.show();

            }
        });

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void showToast(int resId) {
        showToast(getResources().getString(resId));
    }



    public IValidateInternet getValidateInternet() {
        return validateInternet;
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public void createProgressDialog(){
        this.progressDialog = new ProgressDialog(this);
    }

}
