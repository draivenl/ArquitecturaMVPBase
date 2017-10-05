package co.com.etn.arquitecturamvpbase.presenter;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.model.ProductResponse;
import co.com.etn.arquitecturamvpbase.repository.IProductsRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;
import co.com.etn.arquitecturamvpbase.view.activity.IDetailProductView;

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
            ProductResponse productResponse = productsRepository.deleteProduct(id);

            if (productResponse.isStatus()) {
//                Log.d(TAG, "Eliminado el producto: " + id);
                getView().showToast(R.string.success_deleted);
                getView().closeActivity();
            } else {
                getView().showAlertDialogError(R.string.error_deleted);
            }
        } catch (RepositoryError repositoryError) {
            getView().showToast(repositoryError.getMessage());
        }


    }

    public void updateProduct(String id, Product product) {
        if (getValidateInternet().isConnected()){
            createThreadUpdateProduct(id, product);
        } else {
            getView().showAlertDialog(R.string.validate_internet);
        }
    }

    public void createThreadUpdateProduct(final String id, final Product product) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateProductRepository(id, product);
            }
        });
        thread.start();
    }

    public void updateProductRepository(String id, Product product) {
        try {
            ProductResponse productResponse = productsRepository.updateProduct(id, product);

            if (productResponse.isStatus()) {
                getView().showToast(R.string.success_updated);
                getView().closeActivity();
            } else {
                getView().showAlertDialogError(R.string.error_updated);
            }
        } catch (RepositoryError repositoryError) {
            getView().showToast(repositoryError.getMessage());
            repositoryError.printStackTrace();
        }
    }
}
