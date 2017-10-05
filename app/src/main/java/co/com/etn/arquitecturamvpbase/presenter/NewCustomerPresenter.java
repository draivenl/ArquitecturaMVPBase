package co.com.etn.arquitecturamvpbase.presenter;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.repository.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;
import co.com.etn.arquitecturamvpbase.view.activity.INewCustomerView;

/**
 * Created by draiven on 10/3/17.
 */

public class NewCustomerPresenter extends BasePresenter <INewCustomerView> {
    private ICustomerRepository customerRepository;


    public NewCustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(Customer customer) {
        if (getValidateInternet().isConnected()) {
            createThreadAddCustomer(customer);
        } else {
            getView().showAlertDialog(R.string.validate_internet);
        }

    }

    public void createThreadAddCustomer(final Customer customer) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                addCustomerRepository(customer);
            }
        });
        thread.start();

    }

    public void addCustomerRepository(Customer customer) {
        try {
            customerRepository.addCustomer(customer);

            getView().showToast(R.string.success_created);
            getView().closeActivity();



        } catch (RepositoryError repositoryError) {

            getView().showToast(repositoryError.getMessage());
        }
    }
}
