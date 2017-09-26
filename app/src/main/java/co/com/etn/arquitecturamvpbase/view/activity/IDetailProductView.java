package co.com.etn.arquitecturamvpbase.view.activity;

import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 9/23/17.
 */

public interface IDetailProductView extends IBaseView {
    void showAlertDialog(int validate_internet);

    void showToast(int success_deleted);

    void showAlertDialogError(int error_deleted);
}
