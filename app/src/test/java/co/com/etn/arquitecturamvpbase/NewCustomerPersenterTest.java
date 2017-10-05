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
import co.com.etn.arquitecturamvpbase.model.Location;
import co.com.etn.arquitecturamvpbase.model.PhoneList;
import co.com.etn.arquitecturamvpbase.presenter.NewCustomerPresenter;
import co.com.etn.arquitecturamvpbase.repository.ICustomerRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;
import co.com.etn.arquitecturamvpbase.view.activity.INewCustomerView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by draiven on 10/3/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class NewCustomerPersenterTest {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    ICustomerRepository customerRepository;

    @Mock
    INewCustomerView newCustomerView;

    @Mock
    NewCustomerPresenter newCustomerPresenter;


    public Customer getCustomer(){
        Customer customer = new Customer();

        customer.setName("Test");
        customer.setSurName("Tester");

        ArrayList<PhoneList> phoneLists = new ArrayList<PhoneList>();

        PhoneList phoneList = new PhoneList();
        phoneList.setDescription("Desc PhoneList1");
        phoneList.setNumber("Number PhoneList");
        Location location = new Location();
        location.setType("Point");

        Double coordinate[] = {3.0, 2.9};
        location.setCoordinates(coordinate);

        phoneList.setLocation(location);

        PhoneList phoneList2 = new PhoneList();
        phoneList2.setDescription("Desc PhoneList2");
        phoneList2.setNumber("Number PhoneList2");
        Location location2 = new Location();
        location2.setType("Point");

        Double coordinate2[] = {-74.0, 32.9};
        location2.setCoordinates(coordinate2);

        phoneList2.setLocation(location2);

        phoneLists.add(phoneList);
        phoneLists.add(phoneList2);

        customer.setPhoneList(phoneLists);


        return customer;
    }


    @Before
    public void setUp() throws Exception {
        newCustomerPresenter = Mockito.spy(new NewCustomerPresenter(customerRepository));
        newCustomerPresenter.inject(newCustomerView, validateInternet);
    }

    @Test
    public void addCustomer_withConnection_createThreadAddProduct(){
        Customer customer = getCustomer();
        when(validateInternet.isConnected()).thenReturn(true);
        newCustomerPresenter.addCustomer(customer);
        verify(newCustomerPresenter).createThreadAddCustomer(customer);
        verify(newCustomerView, never()).showAlertDialog(R.string.validate_internet);

    }

    @Test
    public void addCustomer_withoutConnection_shouldShowAlertDialog(){
        Customer customer = getCustomer();
        when(validateInternet.isConnected()).thenReturn(false);
        newCustomerPresenter.addCustomer(customer);
        verify(newCustomerView).showAlertDialog(R.string.validate_internet);
        verify(newCustomerPresenter, never()).createThreadAddCustomer(customer);
    }


    @Test
    public void addCustomerRepository_callMethodaddCustomerInRepository_showToastExito() throws RepositoryError {
        Customer customer = getCustomer();
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);

        when(customerRepository.addCustomer(customer)).thenReturn(customer);
        newCustomerPresenter.addCustomerRepository(customer);

        verify(newCustomerView).showToast(R.string.success_created);
        verify(newCustomerView, never()).showToast(repositoryError.getMessage());


    }

    @Test
    public void addCustomerRepository_callMethodaddCustomerInRepository_throwError() throws RepositoryError {
        Customer customer = getCustomer();

        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        when(customerRepository.addCustomer(customer)).thenThrow(repositoryError);
        newCustomerPresenter.addCustomerRepository(customer);

        verify(newCustomerView).showToast(repositoryError.getMessage());
        verify(newCustomerView, never()).showToast(R.string.success_created);


    }




}
