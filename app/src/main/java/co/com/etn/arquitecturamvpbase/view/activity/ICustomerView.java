package co.com.etn.arquitecturamvpbase.view.activity;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.view.IBaseView;

/**
 * Created by draiven on 10/5/17.
 */

public interface ICustomerView extends IBaseView {


    void showCustomersList(ArrayList<Customer> customers);

    void showAlertDialog(int resId);
}
