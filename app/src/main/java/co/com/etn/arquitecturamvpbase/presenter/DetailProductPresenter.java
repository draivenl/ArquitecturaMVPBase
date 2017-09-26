package co.com.etn.arquitecturamvpbase.presenter;

import android.util.Log;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.repository.IProductsRepository;
import co.com.etn.arquitecturamvpbase.view.activity.IDetailProductView;
import retrofit.RetrofitError;

/**
 * Created by draiven on 9/23/17.
 */



public class DetailProductPresenter extends BasePresenter<IDetailProductView> {
    private static final String TAG = "DetailProductPresenter";
    private IProductsRepository productsRepository;

    public DetailProductPresenter(IProductsRepository productRepository) {
        this.productsRepository = productRepository;
    }

    public void deleteProduct(String id) {
        if (getValidateInternet().isConnected()){
//            Log.d(TAG, "Crear hilo...");
            createThreadDeleteProduct(id);
        } else {
//            Log.e(TAG, "Error, no está conectado");
            getView().showAlertDialog(R.string.validate_internet);
        }
    }

    public void createThreadDeleteProduct(final String id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                deleteProductRepository(id);
            }
        });
        thread.start();

    }

    public void deleteProductRepository(String id) {
        try {
            DeleteProductResponse deleteProductResponse = productsRepository.deleteProduct(id);

            if (deleteProductResponse.isStatus()) {
//                Log.d(TAG, "Eliminado el producto: " + id);
                getView().showToast(R.string.success_deleted);
                getView().closeActivity();
            } else {
                getView().showAlertDialogError(R.string.error_deleted);
            }
        } catch (RetrofitError retrofitError) {
            //TODO: manejar error
        }


    }
}
