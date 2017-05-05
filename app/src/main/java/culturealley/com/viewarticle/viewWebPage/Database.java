package culturealley.com.viewarticle.viewWebPage;
/**
 * Created by memanisha on 6/7/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ViewPage.db";
    public static final String TABLE_NAME = "HtmlContent";
    public static final String TITLE = "title";
    public static final String ID = "_id";
    public static final String URL = "url";
    public static final String CONTENT = "Content";
    public static final String TIMESTAMP = "timestamp";
    ArrayList<String> results = new ArrayList<String>();


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 4);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, "
                + TITLE + " TEXT, "
                + URL + " TEXT, "
                + CONTENT + " TEXT, "
                + TIMESTAMP + " TEXT DEFAULT CURRENT_TIMESTAMP)";

        db.execSQL(CREATE_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);


        onCreate(db);

    }



    public int addToDatabase(String url,String title, String content) {
        String Content="";
        int FLAG=1;
        Log.d("URL", "Value: " + url);
        Log.d("CONTENT", "Value: " + content);

        //Checking if URL already exist or not


        try {
            SQLiteDatabase dataBase = this.getReadableDatabase();

            String selection = URL + "=?";
            String[] selectionArgs = {
                    url
            };
            String[] checkurl ={URL};

            Cursor c = dataBase.query(TABLE_NAME, checkurl,selection,selectionArgs , null, null,null);
//                    Cursor c = dataBase.rawQuery("SELECT"+URL+ "FROM"+ TABLE_NAME, null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {

                        Content = c.getString(c.getColumnIndex("url"));
                        Log.d("S2","Value"+Content);
                        if(Content!="")
                            Log.d("before","value"+FLAG);
                        FLAG=0;
                        Log.d("after","value"+FLAG);
                    }while (c.moveToNext());
                }
            }

            c.close();
            dataBase.close();
        }catch(SQLiteException se )
        {
            Log.e(getClass().getSimpleName(), "Already link Exist");
        }

        //For Adding Data to local database
        if(FLAG==1) {
            try {
                SQLiteDatabase dataBase = getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(Database.URL, url);
                values.put(Database.TITLE, url);
                values.put(Database.CONTENT, content);

                long st = dataBase.insert(Database.TABLE_NAME, null, values);
                Log.d("ST", "Value: " + st);

                dataBase.close();
            } catch (SQLiteException se) {
                Log.e(getClass().getSimpleName(), "Could not create the database");
            }
        }
        return FLAG;
    }


    public ArrayList<String> viewFromDatabase()
    {
        results.clear();
        try {
            SQLiteDatabase dataBase = this.getReadableDatabase();

//                        String selection = URL + "=?";
//                        String[] selectionArgs = {
//                                NotificationInfoSession.SYNC_STATUS.NOT_SYNCED.toString()
//                        };
           // String[] columns ={URL};
            String[] columns ={TITLE};
            Cursor c = dataBase.query(TABLE_NAME, columns, null, null
                    , null, null,null);
//                    Cursor c = dataBase.rawQuery("SELECT"+URL+ "FROM"+ TABLE_NAME, null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                       // String url2 = c.getString(c.getColumnIndex("url"));
                        String url2 = c.getString(c.getColumnIndex("title"));
                        results.add("" + url2 );
                        Log.d("TitleText", "Value: " + url2);
                        for (String addurl : results){
                            Log.i("URLNew: ", addurl);

                        }
                    }while (c.moveToNext());
                }
            }

            c.close();
            dataBase.close();

        }catch(SQLiteException se )
        {
            Log.e(getClass().getSimpleName(), "Could not Open the database");
        }

        return results;
    }

    public String ViewHtmlData(String html)
    {      String Content="";
        Log.d("s1","Css"+html);
        try {
            SQLiteDatabase dataBase = this.getReadableDatabase();

            String selection = URL + "=?";
            String[] selectionArgs = {
                    html
            };
            String[] columns ={CONTENT};

            Cursor c = dataBase.query(TABLE_NAME, columns,selection,selectionArgs , null, null,null);
//                    Cursor c = dataBase.rawQuery("SELECT"+URL+ "FROM"+ TABLE_NAME, null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {

                        Content = c.getString(c.getColumnIndex("Content"));
                        Log.d("S2","Value"+Content);

                    }while (c.moveToNext());
                }
            }

            c.close();
            dataBase.close();
        }catch(SQLiteException se )
        {
            Log.e(getClass().getSimpleName(), "Could not fetch the query result");
        }

        return Content;
    }


    public void DeleteData(String url)
    {
        Log.d("delete","ggggg"+url);
        try {
            SQLiteDatabase dataBase = this.getReadableDatabase();

            String selection = URL + "=?";
            String[] selectionArgs = {
                    url
            };
            String[] columns ={CONTENT};

             dataBase.delete(TABLE_NAME, selection,selectionArgs);
             dataBase.close();
        }catch(SQLiteException se )
        {
            Log.e(getClass().getSimpleName(), "Could not delete the query ");
        }


    }

}
