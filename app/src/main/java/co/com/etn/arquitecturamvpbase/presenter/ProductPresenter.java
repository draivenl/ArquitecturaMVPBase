package co.com.etn.arquitecturamvpbase.presenter;

import android.util.Log;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.repository.ProductRepository;
import co.com.etn.arquitecturamvpbase.view.activity.IProductView;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/16/17.
 */

public class ProductPresenter extends BasePresenter<IProductView> {

    private static final String TAG = "ProductPresenter";
    private ProductRepository productRepository;

    public ProductPresenter() {
        this.productRepository = new ProductRepository();
    }

    public void validateInternetProduct() {
        if (getValidateInternet().isConnected()) {
            createThreadProduct();
        } else {
            getView().showNotConnected();
            // TODO: Implementacion alert
        }
    }

    private void createThreadProduct() {
        Log.d(TAG, "createThreadProduct");
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getProductList();
            }
        });
        thread.start();
    }

    private void getProductList() {
        try {
            Log.d(TAG, "getProductList");
            ArrayList<Product>  productArrayList = productRepository.getProductList();
            getView().showProductList(productArrayList);


        } catch (RetrofitError retrofitError){
            // TODO: Mostrar RetrofitError
        } finally {
            getView().hideProgress();
        }

    }
}
