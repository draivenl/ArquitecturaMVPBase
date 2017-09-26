package co.com.etn.arquitecturamvpbase.repository;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.model.Product;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/23/17.
 */

public interface IProductsRepository {

    public ArrayList<Product> getProductList()  throws RetrofitError;
    public Product addProduct(Product product)  throws RetrofitError;

    DeleteProductResponse deleteProduct(String id);
}
