package co.com.etn.arquitecturamvpbase.view;

/**
 * Created by draiven on 9/16/17.
 */

public interface IBaseView {

    void showProgress(int message);
    void hideProgress();
    void closeActivity();

}
