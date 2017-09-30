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

    public void getListProduct() {
        if (getValidateInternet().isConnected()) {
            createThreadProduct();
        } else {
//            getView().showNotConnected();
            getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
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
            getView().showAlertError(R.string.error, R.string.error_retrofit);
        } finally {
            getView().hideProgress();
        }

    }
}
