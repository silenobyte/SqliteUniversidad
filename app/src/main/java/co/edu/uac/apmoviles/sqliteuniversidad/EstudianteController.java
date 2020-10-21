package co.edu.uac.apmoviles.sqliteuniversidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EstudianteController {
    private BaseDatos bd;
    private Context c;
    public EstudianteController(Context c) {
        this.bd = new BaseDatos(c,1);
        this.c = c;
    }
    public long agregarEstudiante(Estudiante e){
        try{
            SQLiteDatabase sql = bd.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DefDB.col_codigo, e.getCodigo());
            cv.put(DefDB.col_nombre, e.getNombre());
            cv.put(DefDB.col_programa, e.getPrograma());
            long id = sql.insert(DefDB.tabla_est,null,cv);

            return id;
        }
        catch (Exception ex){
            return 0;
        }
    }

    public boolean modificar_estudiante (String codigo_viejo , String codigo_nuevo ,String nombre, String programa  ){
        SQLiteDatabase sql = bd.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(DefDB.col_codigo,codigo_nuevo);
        cv.put(DefDB.col_nombre,nombre);
        cv.put(DefDB.col_programa,programa);

        return sql.update(DefDB.tabla_est,cv,DefDB.col_codigo+"='"+codigo_viejo+"'",null)>0;

    }

    public  boolean Eliminar_estudiante(String codigo){
        SQLiteDatabase sql= bd.getWritableDatabase();
       return sql.delete(DefDB.tabla_est,DefDB.col_codigo+"='"+codigo+"'",null )>0;

    }

    public Estudiante buscar_Estudiante(String codigo ){
        SQLiteDatabase data = bd.getReadableDatabase();

        Cursor cur = data.query(DefDB.tabla_est,null,DefDB.col_codigo+"='"+codigo+"'",null,null,null,null);

            cur.moveToFirst();
            try {
                String cod = cur.getString(0);
                String nom = cur.getString(1);
                String pro = cur.getString(2);
                return new Estudiante(cod, nom, pro);
            }catch (Exception e ){
                return  null ;
            }

    }



    public ArrayAdapter get_adapter_nombre_codigo(){
        ArrayList<String> estudiante = new ArrayList<>() ;

        Cursor c = allEstudiantes();
        if (c != null) {
            String cadena = "";
            while (c.moveToNext()) {
                cadena = ("Codigo :"+c.getString(0)+"  Nombre:"+ c.getString(1));
                estudiante.add(cadena);
            }

        }

        return new ArrayAdapter(this.c,android.R.layout.simple_list_item_1,estudiante);


    }

    public ArrayList<String>  allCodigos(){
        ArrayList<String> codigos= new ArrayList<>();
        Cursor c = allEstudiantes();
        if (c != null) {
            String cadena = "";
            while (c.moveToNext()) {
                cadena = (c.getString(0));
                codigos.add(cadena);
            }

        }
        return  codigos;
    }

    public Cursor allEstudiantes(){
        try{
            SQLiteDatabase data = bd.getReadableDatabase();
            Cursor cur = data.query(DefDB.tabla_est,null,null,null,null,null,null);
            return cur;
        }
        catch(Exception ex){
            Toast.makeText(c,"Error Consulta",Toast.LENGTH_LONG).show();
           return null;
        }
    }

}
