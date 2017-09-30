package co.com.etn.arquitecturamvpbase.repository;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.ProductResponse;
import co.com.etn.arquitecturamvpbase.model.Product;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/23/17.
 */

public interface IProductsRepository {

    public ArrayList<Product> getProductList()  throws RetrofitError;
    public Product addProduct(Product product)  throws RetrofitError;

    ProductResponse deleteProduct(String id) throws RepositoryError;

    ProductResponse updateProduct(String id, Product product) throws RepositoryError;
}
