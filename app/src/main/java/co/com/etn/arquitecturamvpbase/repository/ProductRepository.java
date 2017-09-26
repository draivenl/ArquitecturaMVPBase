package co.com.etn.arquitecturamvpbase.repository;

import android.util.Log;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.model.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.service.IServices;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/16/17.
 */

public class ProductRepository  implements IProductsRepository {

    private static final String TAG = "ProductRepository";
    private IServices services;

    public ProductRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public ArrayList<Product> getProductList()  throws RetrofitError{
            ArrayList<Product> products = services.getProductList();
            return products;
    }

    @Override
    public Product addProduct(Product product)  throws RetrofitError{
            Product products = services.addProduct(product);
            return products;
    }

    @Override
    public DeleteProductResponse deleteProduct(String id) throws RetrofitError {
//        Log.d(TAG, "deleteProduct");
        DeleteProductResponse deleteProductResponse = services.deleteProductResponse(id);
        return deleteProductResponse;
    }


}
