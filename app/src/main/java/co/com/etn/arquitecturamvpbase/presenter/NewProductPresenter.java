package co.com.etn.arquitecturamvpbase.presenter;

import android.util.Log;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.repository.ProductRepository;
import co.com.etn.arquitecturamvpbase.view.activity.INewProductView;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/19/17.
 */

public class NewProductPresenter extends BasePresenter<INewProductView> {

    private ProductRepository productRepository;
    private final String TAG = "NewProductPresenter";

    public NewProductPresenter() {
        this.productRepository = new ProductRepository();
    }



    public void createNewProduct(Product product) {
        Log.d(TAG, "createNewProduct");
        if (getValidateInternet().isConnected()) {

            createThreadNewProduct(product, true);
        } else {

            getView().showValidateInternetWarningDialog();
        }

    }


    public void createThreadNewProduct(final Product product, final boolean onLine) {
        Log.d(TAG, "createThreadNewProduct");
        getView().showProgress(R.string.loading_message);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (onLine) {
                    addProduct(productRepository.addProduct(product));
                } else  {
                    addProductLocal(product);
                }
            }
        });
        thread.start();
    }

    private void addProductLocal(Product product) {
        try{
            Database.productDao.createProduct(product);
            getView().onSuccessNewProduct();
        }catch (Exception ex){
            getView().showToast(R.string.new_product_error);
        }

    }

    private void addProduct(Product product) {
        try {

            Log.d(TAG, "addProduct");
            getView().onSuccessNewProduct();


        } catch (RetrofitError retrofitError){
            getView().showToast(retrofitError.getMessage());
        } finally {
            getView().hideProgress();
        }
    }
}
