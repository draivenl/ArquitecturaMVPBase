package co.com.etn.arquitecturamvpbase.synchronizer;

import android.util.Log;


import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.helper.Database;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.repository.ProductRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;

/**
 * Created by Lisandro Gómez on 10/12/17.
 */

public class Synchronizer {
    private static final String TAG = Synchronizer.class.getName();
    public static Synchronizer instance = null;

    public static Synchronizer getInstance(){
        if (instance == null){
            instance = new Synchronizer();
        }
        return instance;
    }

    public void executeSyncLocalToServer(boolean isConnected) {
        Log.i(TAG, "network changed: " + isConnected);

        if(isConnected){
            Log.i(TAG, "Sincronizar Productos: ");
            ArrayList<Product> productArrayList = Database.productDao.fetchNoSyncProducts();
            for (final Product product:productArrayList) {
                Log.i(TAG, "Producto: " + product.getName());
                Log.i(TAG, "Producto is sync: " + product.getIsSync());
                if (product.getIsSync() != null && product.getIsSync().equals("N")) {
                    Log.i(TAG, "Sincronizando... " + product.getName());
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ProductRepository productRepository = new ProductRepository();
                            Product productResult = productRepository.addProduct(product);
                            if (productResult != null && productResult.getId() != null) {
                                Log.i(TAG, "Actualizando el producto... " + productResult.getId());
                                product.setIsSync("S");
                                if ( Database.productDao.updateProduct(product.getId(), product) ) {
                                    Log.i(TAG, "Actualizado a S. " + productResult.getId());
                                } else {
                                    Log.e(TAG, "No se pudo actualizar localmente a S. " + productResult.getId());
                                }

                            }
                        }
                    });
                    thread.start();
                }
            }
        }


    }
}
