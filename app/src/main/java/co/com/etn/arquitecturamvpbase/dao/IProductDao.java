package co.com.etn.arquitecturamvpbase.dao;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.Product;

/**
 * Created by Lisandro Gómez on 10/7/17.
 */

public interface IProductDao {

    public ArrayList<Product> fetchAllProducts();
    public ArrayList<Product> fetchNoSyncProducts();
    public Boolean createProduct(Product product);
    public Boolean deleteProduct(String id);
    public Boolean updateProduct(String id , Product product);

}