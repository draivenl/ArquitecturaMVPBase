package co.com.etn.arquitecturamvpbase.service;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.Product;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by draiven on 9/16/17.
 */

public interface IServices {

    @GET("/products")
    ArrayList<Product> getProductList();

    @POST("/products")
    Product addProduct(@Body Product product);



}
