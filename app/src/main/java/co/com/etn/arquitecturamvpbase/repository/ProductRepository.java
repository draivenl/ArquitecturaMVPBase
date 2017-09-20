package co.com.etn.arquitecturamvpbase.repository;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.service.IServices;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/16/17.
 */

public class ProductRepository {

    private IServices services;

    public ProductRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    public ArrayList<Product> getProductList()  throws RetrofitError{
            ArrayList<Product> products = services.getProductList();
            return products;
    }

    public Product addProduct(Product product)  throws RetrofitError{
            Product products = services.addProduct(product);
            return products;
    }




}
