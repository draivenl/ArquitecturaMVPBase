package co.com.etn.arquitecturamvpbase.repository;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.Customer;

/**
 * Created by draiven on 10/3/17.
 */

public interface ICustomerRepository {
    public Customer addCustomer(Customer customer) throws RepositoryError;


    ArrayList<Customer> getCustomerList() throws RepositoryError;
}
