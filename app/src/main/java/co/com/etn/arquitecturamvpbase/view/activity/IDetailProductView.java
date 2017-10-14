package co.com.etn.arquitecturamvpbase.view.activity;

import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 9/23/17.
 */

public interface IDetailProductView extends IBaseView {

    void showAlertDialogError(int resId);

    void showAlertDialog(int resId);

    void showValidateInternetWarningDialog(int process);
}
