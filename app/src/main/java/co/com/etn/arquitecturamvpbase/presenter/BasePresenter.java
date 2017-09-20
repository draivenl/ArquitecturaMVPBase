package co.com.etn.arquitecturamvpbase.presenter;

import co.com.etn.arquitecturamvpbase.helper.IValidateInternet;
import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 9/16/17.
 */

public class BasePresenter <T extends IBaseView> {

    private T view;
    private IValidateInternet validateInternet;

    public void inject(T view, IValidateInternet validateInternet) {
        this.view = view;
        this.validateInternet = validateInternet;
    }

    public T getView() {
        return view;
    }

    public IValidateInternet getValidateInternet() {
        return validateInternet;
    }
}
