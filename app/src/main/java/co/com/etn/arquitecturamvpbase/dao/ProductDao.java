package co.com.etn.arquitecturamvpbase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import co.com.etn.arquitecturamvpbase.model.Product;
import co.com.etn.arquitecturamvpbase.providers.DbContentProvider;
import co.com.etn.arquitecturamvpbase.schemes.IProductScheme;

/**
 * Created by Lisandro Gómez on 10/7/17.
 */

public class ProductDao extends DbContentProvider implements IProductScheme, IProductDao {

    private Cursor cursor;
    private ContentValues initialValues;

    public ProductDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public ArrayList<Product> fetchAllProducts() {
        ArrayList<Product> productList =  new ArrayList<>();
        cursor = super.query(PRODUCT_TABLE, PRODUCT_COLUMNS,null, null, COLUMN_PRODUCT_NAME);
        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return productList;
    }

    @Override
    public ArrayList<Product> fetchNoSyncProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        cursor = super.query(PRODUCT_TABLE, PRODUCT_COLUMNS, COLUMN_PRODUCT_IS_SYNC + "=?", new String[]{"N"}, COLUMN_PRODUCT_NAME);
        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return productList;
    }

    public void setContentValue(Product product){
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, product.getId());
        initialValues.put(COLUMN_PRODUCT_NAME, product.getName());
        initialValues.put(COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        initialValues.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        initialValues.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        initialValues.put(COLUMN_PRODUCT_IS_SYNC, product.getIsSync());
    }

    public ContentValues getContentValue(){
        return initialValues;
    }

    @Override
    public Boolean createProduct(Product product) {
        setContentValue(product);
        try {
            return super.insert(PRODUCT_TABLE, getContentValue()) >= 0;
            /*
                if(totalInsert == -1){
                    return true:
                }
                return false
            */
        }catch (SQLiteConstraintException e){
            Log.e("DBErrorCreateProduct", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteProduct(String id) {
        try {
            return super.delete(PRODUCT_TABLE, COLUMN_ID +"=?", new String[] {id}) > 0;
        }catch (SQLiteConstraintException ex){
            Log.e("DBErrorDeleteProduct", ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean updateProduct(String id, Product product) {
        setContentValue(product);
        try{
            return super.update(PRODUCT_TABLE, getContentValue(), COLUMN_ID + "=?", new String[] {id}) > 0;
        }catch (SQLiteConstraintException ex){
            Log.e("DBErrorUpdateProduct", ex.getMessage());
            return false;
        }
    }

    @Override
    protected Product cursorToEntity(Cursor cursor) {
        Product product = new Product();
        int idIndex;
        int nameIndex;
        int descriptionIndex;
        int quantityIndex;
        int priceIndex;
        int isSncIndex;

        if(cursor.getColumnIndex(COLUMN_ID) != -1){
            idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            product.setId(cursor.getString(idIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_NAME) != -1){
            nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME);
            product.setName(cursor.getString(nameIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION) != -1){
            descriptionIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION);
            product.setDescription(cursor.getString(descriptionIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY) != -1){
            quantityIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY);
            product.setQuantity(cursor.getString(quantityIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE) != -1){
            priceIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE);
            product.setPrice(cursor.getString(priceIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_IS_SYNC) != -1){
            isSncIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_IS_SYNC);
            product.setIsSync(cursor.getString(isSncIndex));
        }
        return product;
    }
}