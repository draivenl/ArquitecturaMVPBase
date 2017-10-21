package co.com.etn.arquitecturamvpbase.view;

import co.com.etn.arquitecturamvpbase.model.User;

/**
 * Created by draiven on 9/16/17.
 */

public interface IBaseView {

    void showProgress(int message);
    void hideProgress();
    void closeActivity();



    void showToast(String message);
    void showToast(int resId);


}
