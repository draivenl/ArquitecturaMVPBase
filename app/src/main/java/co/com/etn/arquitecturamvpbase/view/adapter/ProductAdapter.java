package co.com.etn.arquitecturamvpbase.view.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.model.Product;

/**
 * Created by draiven on 9/16/17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private ArrayList<Product> productArrayList;
    private Activity context;
    private Product product;
    private TextView name;

    public ProductAdapter(@NonNull Activity context, int resource, ArrayList<Product> productArrayList) {
        super(context, resource, productArrayList);
        this.context = context;
        this.productArrayList = productArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        this.product = this.productArrayList.get(position);
        loadView(convertView);
        name.setText(product.getName());
        if ("S".equals(product.getIsSync())) {
            name.setTextColor(ContextCompat.getColor( convertView.getContext(), R.color.colorPrimaryDark));
        }

        return convertView;

    }

    private void loadView(View convertView) {
        name = (TextView) convertView.findViewById(R.id.item_name_product);
    }
}
