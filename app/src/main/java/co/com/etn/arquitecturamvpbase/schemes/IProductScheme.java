package co.com.etn.arquitecturamvpbase.schemes;

/**
 * Created by Lisandro Gómez on 10/7/17.
 */

public interface IProductScheme {

    String PRODUCT_TABLE = "products";
    String COLUMN_ID = "_id";
    String COLUMN_PRODUCT_NAME = "product_name";
    String COLUMN_PRODUCT_DESCRIPTION = "product_description";
    String COLUMN_PRODUCT_QUANTITY = "product_quantity";
    String COLUMN_PRODUCT_PRICE = "product_price";
    String COLUMN_PRODUCT_IS_SYNC = "product_is_sync";

    String PRODUCT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
            + COLUMN_PRODUCT_DESCRIPTION + " TEXT, "
            + COLUMN_PRODUCT_QUANTITY + " TEXT, "
            + COLUMN_PRODUCT_PRICE + " TEXT "
            + COLUMN_PRODUCT_IS_SYNC + " TEXT "
            + ");";


    String [] PRODUCT_COLUMNS = new String[]{
            COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_DESCRIPTION, COLUMN_PRODUCT_QUANTITY, COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_IS_SYNC
    };

    String DROP_TABLE_IF_EXIST = "DROP TABLE IF EXISTS  " + IProductScheme.PRODUCT_TABLE;

    String PRODUCT_ALTER_ADD_COLUMN_IS_SYNC_V3 = "ALTER TABLE " + PRODUCT_TABLE +
            " ADD " + COLUMN_PRODUCT_IS_SYNC +" TEXT;";

    String UPDATE_ACTUAL_PRODUCTS_TO_NO_SYNC_V3 = "UPDATE " + IProductScheme.PRODUCT_TABLE + " SET " +
            COLUMN_PRODUCT_IS_SYNC + "='N'";


}
