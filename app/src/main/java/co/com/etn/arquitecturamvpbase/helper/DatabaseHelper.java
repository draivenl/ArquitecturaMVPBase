package co.com.etn.arquitecturamvpbase.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.com.etn.arquitecturamvpbase.schemes.IProductScheme;

/**
 * Created by Lisandro Gómez on 10/7/17.
 */

class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(IProductScheme.PRODUCT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, Constants.DATABASE_NAME + " update version to " + newVersion);


        for (int i = oldVersion; i < newVersion ; i++) {
            upgradeFromVersion(sqLiteDatabase, i);
        }


    }

    private void upgradeFromVersion(SQLiteDatabase sqLiteDatabase, int oldVersion) {
        if (oldVersion <= 0) {
            dropTableProductsIfExist(sqLiteDatabase);
        }
        if (oldVersion == 2) {
            addColumnIsSyncToProducts(sqLiteDatabase);
        }
        if (oldVersion == 3) {
            updateProductsToNoSync(sqLiteDatabase);
        }
        if (oldVersion == 4) {
            dropTableProductsIfExist(sqLiteDatabase);
        }
    }

    private void addColumnIsSyncToProducts(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "add column is_sync to product");
        sqLiteDatabase.execSQL(IProductScheme.PRODUCT_ALTER_ADD_COLUMN_IS_SYNC_V3);

    }

    private void dropTableProductsIfExist(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(IProductScheme.DROP_TABLE_IF_EXIST);

    }

    private void updateProductsToNoSync(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(IProductScheme.UPDATE_ACTUAL_PRODUCTS_TO_NO_SYNC_V3);
    }
}
