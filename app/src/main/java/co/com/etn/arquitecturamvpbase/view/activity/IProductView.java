package co.com.etn.arquitecturamvpbase.view.activity;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 9/16/17.
 */

public interface IProductView extends IBaseView {
    void showProductList(ArrayList<Product> productArrayList);

    void showNotConnected();

    void showAlertDialogInternet(int error, int validate_internet);

    void showAlertError(int error, int error_retrofit);

    void showAlertDialogLocalProducts();
}
