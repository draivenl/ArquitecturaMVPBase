package co.com.etn.arquitecturamvpbase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import co.com.etn.arquitecturamvpbase.helper.IValidateInternet;
import co.com.etn.arquitecturamvpbase.model.DeleteProductResponse;
import co.com.etn.arquitecturamvpbase.presenter.DetailProductPresenter;
import co.com.etn.arquitecturamvpbase.repository.IProductsRepository;
import co.com.etn.arquitecturamvpbase.view.activity.IDetailProductView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by draiven on 9/23/17.
 */


@RunWith(MockitoJUnitRunner.class)
public class DetailPrenenterTest {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    IProductsRepository productsRepository;

    @Mock
    IDetailProductView detailProductView;

    DetailProductPresenter detailProductPresenter;

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
    public void methodDeleteProductShowCallRepositoryDeleteProductInRepository(){
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus(true);

        String id = "kajshdfkja";

        when(productsRepository.deleteProduct(id)).thenReturn(deleteProductResponse);
        detailProductPresenter.deleteProductRepository(id);

        Assert.assertTrue(deleteProductResponse.isStatus());
        verify(detailProductView).showToast(R.string.success_deleted);
        verify(detailProductView, never()).showAlertDialogError(R.string.error_deleted);


    }

    @Test
    public void methodDeleteProductShowCallRepositoryDeleteProductInRepositoryFalse(){
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus(false);

        String id = "kajshdfkja";

        when(productsRepository.deleteProduct(id)).thenReturn(deleteProductResponse);
        detailProductPresenter.deleteProductRepository(id);

        Assert.assertFalse(deleteProductResponse.isStatus());
        verify(detailProductView).showAlertDialogError(R.string.error_deleted);
        verify(detailProductView, never()).showToast(R.string.success_deleted);


    }



}
