package co.com.etn.arquitecturamvpbase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.IValidateInternet;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.presenter.CustomerPresenter;
import co.com.etn.arquitecturamvpbase.repository.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;
import co.com.etn.arquitecturamvpbase.view.activity.ICustomerView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by draiven on 10/5/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CustomerPresenterTest {
    @Mock
    IValidateInternet validateInternet;

    @Mock
    ICustomerRepository customerRepository;

    @Mock
    ICustomerView customerView;

    @Mock
    CustomerPresenter customerPresenter;



    @Before
    public void setUp() throws Exception {
        customerPresenter = Mockito.spy(new CustomerPresenter(customerRepository));
        customerPresenter.inject(customerView, validateInternet);
    }

    @Test
    public void getCustomers_withConnection_createThreadGetCustomers(){
        when(validateInternet.isConnected()).thenReturn(true);
        customerPresenter.getCustomers();
        verify(customerPresenter).createThreadGetCustomers();
        verify(customerView, never()).showAlertDialog(R.string.validate_internet);
    }


    @Test
    public void getCustomers_withoutConnection_showAlertDialogError(){
        when(validateInternet.isConnected()).thenReturn(false);
        customerPresenter.getCustomers();
        verify(customerView).showAlertDialog(R.string.validate_internet);
        verify(customerPresenter, never()).createThreadGetCustomers();
    }

    @Test
    public void getCustomersRepository_callMethodgetCustomerInRepository_showCustomersList() throws RepositoryError {
        ArrayList<Customer> customers = new ArrayList<>();
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        when(customerRepository.getCustomerList()).thenReturn(customers);
        customerPresenter.getCustomersRepository();
        verify(customerView).showCustomersList(customers);
        verify(customerView, never()).showToast(repositoryError.getMessage());
    }

    @Test
    public void getCustomersRepository_callMethodgetCustomerInRepository_throwRepositoryError() throws RepositoryError {
        ArrayList<Customer> customers = new ArrayList<>();
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        when(customerRepository.getCustomerList()).thenThrow(repositoryError);
        customerPresenter.getCustomersRepository();
        verify(customerView).showToast(repositoryError.getMessage());
        verify(customerView, never()).showCustomersList(customers);

    }


}
