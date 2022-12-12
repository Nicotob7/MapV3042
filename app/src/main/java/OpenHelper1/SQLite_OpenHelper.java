package OpenHelper1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLite_OpenHelper extends SQLiteOpenHelper{

    public SQLite_OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table usuarios(_ID integer primary key autoincrement, "+
                "Nombre text, Correo text, Password text) ;";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //Metodo que permite abrir la bd
    public void abrir(){
        this.getWritableDatabase();
    }

    //Metodo que permite cerrar la bd
    public void cerrar(){
        this.close();
    }

    //Metodo que permite insertar registro en la tabla usuarios
    public void insertarReg(String nom, String cor, String pas){
        ContentValues valores = new ContentValues();
        valores.put("Nombre", nom);
        valores.put("Correo", cor);
        valores.put("Password", pas);

        this.getWritableDatabase().insert("usuarios",null,valores);
    }


    //Metodo que permite validar si el usuario existe
    public Cursor ConsultarUsuPas(String usu, String pas)throws SQLException{
        Cursor mcursor = null;
        mcursor=this.getReadableDatabase().query("usuarios", new String[]{"_ID",
                "Nombre","Correo","Password"},"Correo like '"+usu+
                "' and Password='"+pas+"'",null,null,null,null);

        return mcursor;
    }

}
