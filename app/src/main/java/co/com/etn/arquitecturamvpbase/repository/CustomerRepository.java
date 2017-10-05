package co.com.etn.arquitecturamvpbase.repository;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.model.Customer;
import co.com.etn.arquitecturamvpbase.service.IServices;
import retrofit.RetrofitError;

/**
 * Created by draiven on 10/3/17.
 */

public class CustomerRepository implements ICustomerRepository {
    private IServices services;

    public CustomerRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public Customer addCustomer(Customer customer) throws RepositoryError {
        try {
            return services.addCustomer(customer);
        } catch (RetrofitError retrofitError) {
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }

    @Override
    public ArrayList<Customer> getCustomerList() throws RepositoryError {
        try {
            return services.getCustomersList();
        } catch (RetrofitError retrofitError) {
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }
}
