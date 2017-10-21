package co.com.etn.arquitecturamvpbase.view.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.Constants;
import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.presenter.ProductPresenter;
import co.com.etn.arquitecturamvpbase.view.BaseFragment;
import co.com.etn.arquitecturamvpbase.view.activity.DetailActivity;
import co.com.etn.arquitecturamvpbase.view.activity.IProductView;
import co.com.etn.arquitecturamvpbase.view.activity.ProductActivity;
import co.com.etn.arquitecturamvpbase.view.adapter.ProductAdapter;

/**
 * Created by Lisandro Gómez on 10/14/17.
 */

public class ProductFragment extends BaseFragment<ProductPresenter> implements IProductView {

    private ListView productList;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout productSwip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product, container, false);

        setPresenter(new ProductPresenter());
        getPresenter().inject(this, getValidateInternet());
        productList = (ListView) view.findViewById(R.id.product_listView);
        productList.setNestedScrollingEnabled(true);


        //getBaseActivity().createProgressDialog();

        loadEvents(view);

        return view;
    }

    private void loadEvents(View view) {
        productSwip= (SwipeRefreshLayout) view.findViewById(R.id.products_swip);
        productSwip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getListProduct();
            }
        });
    }

    @Override
    public void showProductList(final ArrayList<Product> productArrayList) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                productSwip.setRefreshing(false);
                callAdapter(productArrayList);

            }
        });

    }

    @Override
    public void showNotConnected() {
        getBaseActivity().showToast("No Conectado");

    }

    @Override
    public void showAlertDialogInternet(final int error, final int validate_internet) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                productSwip.setRefreshing(false);
                getBaseActivity().getShowAlertDialog().showAlertDialog(error, validate_internet, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getPresenter().getListProduct();
                    }
                }, R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showAlertDialogLocalProducts();
//                        finish();
                    }
                });
            }
        });

    }

    @Override
    public void showAlertError(int error, int error_retrofit) {

    }

    @Override
    public void showAlertDialogLocalProducts() {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                productSwip.setRefreshing(false);
                getBaseActivity().getShowAlertDialog().showAlertDialog(R.string.alert, R.string.product_local_message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getPresenter().createThreadProduct(false);
                    }
                }, R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getBaseActivity().finish();
                    }
                });
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        productSwip.setRefreshing(true);
        getPresenter().getListProduct();
    }


    public void callAdapter(final ArrayList<Product> productArrayList) {
        productAdapter = new ProductAdapter(getBaseActivity(), R.id.product_listView, productArrayList);
        productList.setAdapter(productAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT, productArrayList.get(position));
                intent.putExtra(Constants.ITEM_EDIT, false);
                startActivity(intent);

            }
        });
        productList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.ITEM_PRODUCT, productArrayList.get(position));
                intent.putExtra(Constants.ITEM_EDIT, true);
                startActivity(intent);
                return false;
            }
        });
    }



}
