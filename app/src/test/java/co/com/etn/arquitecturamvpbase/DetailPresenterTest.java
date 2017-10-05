package co.com.etn.arquitecturamvpbase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.helper.IValidateInternet;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.model.ProductResponse;
import co.com.etn.arquitecturamvpbase.presenter.DetailProductPresenter;
import co.com.etn.arquitecturamvpbase.repository.IProductsRepository;
import co.com.etn.arquitecturamvpbase.repository.RepositoryError;
import co.com.etn.arquitecturamvpbase.view.activity.IDetailProductView;

import static android.R.attr.id;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by draiven on 9/23/17.
 */


@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    IProductsRepository productsRepository;

    @Mock
    IDetailProductView detailProductView;

    DetailProductPresenter detailProductPresenter;


    public Product getProduct() {
        Product product = new Product();
        String id = "kajshdfkja";
        String name = "desc";
        String description = "desc";
        String price = "desc";
        String quantity = "desc";


        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setName(name);
        return product;
    }



    @Before
    public void setUp() throws Exception {
        detailProductPresenter = Mockito.spy(new DetailProductPresenter(productsRepository));
        detailProductPresenter.inject(detailProductView, validateInternet);
    }

    //El metodo debe decir que
    @Test
    public void methodDeleteProductWithConnectionShouldCallMethodCreateThreadDeleteProduct(){
        String id = "kajshdfkja";
        when(validateInternet.isConnected()).thenReturn(true);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductPresenter).createThreadDeleteProduct(id);
        // Garantizar que el otro metodo nunca se ejecute
        verify(detailProductView, never()).showAlertDialog(R.string.validate_internet);

    }


    @Test
    public void methodDeleteProductWithoutConnectionShouldShowAlertDialog(){
        String id = "kajshdfkja";
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.deleteProduct(id);
        verify(detailProductView).showAlertDialog(R.string.validate_internet);
        // Garantizar que el otro metodo nunca se ejecute

        verify(detailProductPresenter, never()).createThreadDeleteProduct(id);
    }

    @Test
    public void methodDeleteProductShowCallRepositoryDeleteProductInRepository() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(true);

        String id = "kajshdfkja";

        when(productsRepository.deleteProduct(id)).thenReturn(productResponse);
        detailProductPresenter.deleteProductRepository(id);

        Assert.assertTrue(productResponse.isStatus());
        verify(detailProductView).showToast(R.string.success_deleted);
        verify(detailProductView, never()).showAlertDialogError(R.string.error_deleted);


    }

    @Test
    public void methodDeleteProductShowCallRepositoryDeleteProductInRepositoryFalse() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(false);

        String id = "kajshdfkja";

        when(productsRepository.deleteProduct(id)).thenReturn(productResponse);
        detailProductPresenter.deleteProductRepository(id);

        Assert.assertFalse(productResponse.isStatus());
        verify(detailProductView).showAlertDialogError(R.string.error_deleted);
        verify(detailProductView, never()).showToast(R.string.success_deleted);


    }

    @Test
    public void methodDeleteProductShowAlertWhenRepositoryReturnError() throws RepositoryError{
        String id = "kajshdfkja";
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        when(productsRepository.deleteProduct(id)).thenThrow(repositoryError);
        detailProductPresenter.deleteProductRepository(id);
        verify(detailProductView).showToast(repositoryError.getMessage());

        verify(detailProductView, never()).showToast(R.string.success_deleted);
        verify(detailProductView, never()).showAlertDialogError(R.string.error_deleted);


    }


    @Test
    public void methodUpdateProductWithConnectionShouldCallMethodCreateThreadUpdateProduct(){
        Product product = getProduct();
        when(validateInternet.isConnected()).thenReturn(true);
        detailProductPresenter.updateProduct(product.getId(), product);
        verify(detailProductPresenter).createThreadUpdateProduct(product.getId(), product);
        verify(detailProductView, never()).showAlertDialog(R.string.validate_internet);
    }

    @Test
    public void methodUpdateProductWithoutConnectionShouldShowAlertDialog(){
        Product product = getProduct();
        String id = product.getId();
        when(validateInternet.isConnected()).thenReturn(false);
        detailProductPresenter.updateProduct(id, product);
        verify(detailProductView).showAlertDialog(R.string.validate_internet);
        verify(detailProductPresenter, never()).createThreadUpdateProduct(product.getId(), product);
    }

    @Test
    public void methodUpdateProductShowCallRepositoryUpdateProductInRepository() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(true);

        Product product = getProduct();
        String id = product.getId();

        when(productsRepository.updateProduct(id, product)).thenReturn(productResponse);
        detailProductPresenter.updateProductRepository(id, product);

        Assert.assertTrue(productResponse.isStatus());
        verify(detailProductView).showToast(R.string.success_updated);
        verify(detailProductView, never()).showAlertDialogError(R.string.error_updated);


    }


    @Test
    public void methodUpdateProductShowCallRepositoryUpdateProductInRepositoryFalse() throws RepositoryError {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setStatus(false);

        Product product = getProduct();
        String id = product.getId();

        when(productsRepository.updateProduct(id, product)).thenReturn(productResponse);
        detailProductPresenter.updateProductRepository(id, product);

        Assert.assertFalse(productResponse.isStatus());
        verify(detailProductView).showAlertDialogError(R.string.error_updated);
        verify(detailProductView, never()).showToast(R.string.success_updated);


    }


    @Test
    public void methodUpdateProductShowAlertWhenRepositoryReturnError() throws RepositoryError{
        Product product = getProduct();
        String id = product.getId();

        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        when(productsRepository.updateProduct(id, product)).thenThrow(repositoryError);
        detailProductPresenter.updateProductRepository(id, product);
        verify(detailProductView).showToast(repositoryError.getMessage());

        verify(detailProductView, never()).showToast(R.string.success_updated);
        verify(detailProductView, never()).showAlertDialogError(R.string.error_updated);


    }



}
