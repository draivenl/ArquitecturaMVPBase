package co.com.etn.arquitecturamvpbase.repository;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.ServicesFactory;
import co.com.etn.arquitecturamvpbase.model.ProductResponse;
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
    public ProductResponse deleteProduct(String id) throws RepositoryError {
//        Log.d(TAG, "deleteProduct");
        try {
            return services.deleteProductResponse(id);

        } catch (RetrofitError retrofitError) {
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }

    @Override
    public ProductResponse updateProduct(String id, Product product) throws RepositoryError {
        try {
            return services.updateProductResponse(id, product);
        } catch (RetrofitError retrofitError) {
            throw MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }


}
