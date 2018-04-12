package dondesoi.don_de_soi.ressources;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vitaly on 2/26/2018.
 */

public class SaveData {
    private SaveData(){
        throw new RuntimeException();
    }
    /**
     * lab 6 : save the location into shared preferences
     */
    public static void savePreference(String key, String value, Context context){
        SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getPreferences(String key, Context context, String defaultPref){
        //String defaultPref = "";
        SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        return settings.getString(key, defaultPref);
    }
    /**
     * lab 6 : Store the different operations and results done using the calculator in a sqlite database
     */
    public static SQLiteDatabase createDataBase(String name, Context context){
        return context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        //if does not work do : return openOrCreateDatabase(name,MODE_PRIVATE,null);
    }

    /**
     * included "CREATE TABLE IF NOT EXISTS"
     * @param database
     * @param sql : "TutorialsPoint(Username VARCHAR,Password VARCHAR);"
     */
    public static void createTable(SQLiteDatabase database, String sql){
        String query = "CREATE TABLE IF NOT EXISTS " + sql;
        database.execSQL(query);
    }

    /**
     * included "INSERT INTO"
     * @param database
     * @param sql : "TutorialsPoint VALUES('admin','admin');"
     */
    public static void insertIntoDatabase(SQLiteDatabase database, String sql){
        String query = "INSERT INTO " + sql;
        database.execSQL(query);
    }

    /**
     *
     * @param database
     * @param sql : "Select * from TutorialsPoint ...
     * @return
     */
    public static Cursor fetchDatabase(SQLiteDatabase database, String sql){
        Cursor resultSet = database.rawQuery(sql,null);
        resultSet.moveToFirst();
        return resultSet;
    }
    public static String getStringData(Cursor resultSet){
        String returned = "";
        while(resultSet.moveToNext()){
            returned += resultSet.getString(0) + "\n";
        }
        return returned;
    }
    /*
    //creates the database
    SQLiteDatabase mydatabase = openOrCreateDatabase("My Database",MODE_PRIVATE,null);
    //create table
    mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR);");
    //add values
    mydatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin');");
    //fetching results
    Cursor resultSet = mydatabase.rawQuery("Select * from TutorialsPoint",null);
    resultSet.moveToFirst();
    String username = resultSet.getString(0);
    String password = resultSet.getString(1);
    /**
     * lab 6 : Using menu options, display the content of either information (location or calculations) in a fragment somewhere in your activity
     * paste into oncreate :
     DataFragment dataFragment = new DataFragment();
     getSupportFragmentManager().beginTransaction().add(R.id.data, dataFragment).commit();
     * call DataFragment.onUpdate() to set the new value
     */
}
