package sacalapp.hay_chico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jarvic on 21/02/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table usuario(codigo int primary key,nombre text,posicion text,edad text,calificacion text,ciudad text,nick text,email text,estatus text,usuario_id text,pierna text,estatus_jugador text,Bienvenida text,sexo text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //db.execSQL("create table usuario(codigo int primary key,email text)");
        db.execSQL("drop table if exists usuario");
    }


}


