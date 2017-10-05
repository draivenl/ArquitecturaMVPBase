package co.com.etn.arquitecturamvpbase.view.activity;

import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 10/3/17.
 */

public interface INewCustomerView extends IBaseView {

    void showAlertDialog(int resId);

    void showAlertDialogError(int resId);
}
