package co.com.etn.arquitecturamvpbase.view.activity;

import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 9/19/17.
 */

public interface INewProductView extends IBaseView {


    void setButtonCreateEnabled();
    void setButtonCreateDisabled();

    void onSuccessNewProduct();
    void showValidateInternetWarningDialog();
}
