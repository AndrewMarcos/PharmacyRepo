package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MorcosS on 5/31/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_Movies = "CREATE TABLE "
            + "Order_Items" + "(" + "Order_ID" + " INTEGER PRIMARY KEY," + "Image_String"
            + " STRING," + "Message STRING)";

    public DBHelper(Context context) {
        super(context, "Order_Items", null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Movies);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOrder (int Order_ID, String Image_String,String Message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Order_ID",Order_ID);
        values.put("Image_String", Image_String);
        values.put("Message",Message);

        long movie_row = db.insert("Order_Items", null, values);
        db.close(); // Closing database connection
        if (movie_row==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getOrder() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * "  + " FROM Order_Items ";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null || ! c.moveToFirst()) return null;
        return c;

    }



}
