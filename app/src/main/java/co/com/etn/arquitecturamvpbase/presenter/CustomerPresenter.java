package co.com.etn.arquitecturamvpbase.presenter;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.repository.CustomerRepository;
import co.com.etn.arquitecturamvpbase.repository.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;
import co.com.etn.arquitecturamvpbase.view.activity.ICustomerView;

/**
 * Created by draiven on 10/5/17.
 */

public class CustomerPresenter extends BasePresenter<ICustomerView> {
    private ICustomerRepository customerRepository;


    public CustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void getCustomers() {
        if (getValidateInternet().isConnected()) {
            createThreadGetCustomers();
        } else {
            getView().showAlertDialog(R.string.validate_internet);
        }

    }

    public void createThreadGetCustomers() {
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getCustomersRepository();
            }
        });
        thread.start();

    }


    public void getCustomersRepository() {
        try {
            ArrayList<Customer> customers = customerRepository.getCustomerList();
            getView().showCustomersList(customers);
        } catch (RepositoryError repositoryError) {
            getView().showToast(repositoryError.getMessage());
        } finally {
            getView().hideProgress();
        }
    }
}
