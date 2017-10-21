package co.com.etn.arquitecturamvpbase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import co.com.etn.arquitecturamvpbase.helper.IValidateInternet;
import co.com.etn.arquitecturamvpbase.helper.ShowAlertDialog;
import co.com.etn.arquitecturamvpbase.helper.ValidateInternet;
import co.com.etn.arquitecturamvpbase.presenter.BasePresenter;

/**
 * Created by Lisandro Gómez on 10/14/17.
 */

public class BaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {

    private BaseActivity baseActivity;
    private IValidateInternet validateInternet;
    private T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        validateInternet = baseActivity.getValidateInternet();

    }


    @Override
    public void showProgress(int message) {
        baseActivity.showProgress(message);

    }

    @Override
    public void hideProgress() {
        baseActivity.hideProgress();

    }

    @Override
    public void closeActivity() {
        baseActivity.closeActivity();

    }

    @Override
    public void showToast(String message) {
        baseActivity.showToast(message);
    }

    @Override
    public void showToast(int resId) {
        baseActivity.showToast(resId);

    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public IValidateInternet getValidateInternet() {
        return validateInternet;
    }

    public void setValidateInternet(IValidateInternet validateInternet) {
        this.validateInternet = validateInternet;
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }
}
