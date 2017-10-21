package co.com.etn.arquitecturamvpbase.presenter;

import android.util.Log;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Database;
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
            createThreadProduct(true);
        } else {
//            getView().showNotConnected();
            getView().showAlertDialogInternet(R.string.error, R.string.validate_internet);
        }
    }

    public void createThreadProduct(final boolean onLine) {
        Log.d(TAG, "createThreadProduct");
//        getView().showProgress(R.string.loading_message);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (onLine) {
                    getProductList();
                } else {
                    getProductListLocal();
                }

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
//            getView().hideProgress();
        }

    }

    private void getProductListLocal() {
        try {
            ArrayList<Product> productArrayList = Database.productDao.fetchAllProducts();
            getView().showProductList(productArrayList);
        }catch (Exception ex) {
            Log.w("ErrorGetProductList", ex.getMessage());
            getView().showAlertError(R.string.error, R.string.product_get_local_error);
        }finally {
//            getView().hideProgress();
        }
    }
}
