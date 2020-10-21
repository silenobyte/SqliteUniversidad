package co.edu.uac.apmoviles.sqliteuniversidad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText codigo, nombre, programa;
    Button guardar, buscar , listado,elimininar,modificar;
  //  BaseDatos bd;
    Estudiante e;
    EstudianteController ec;
    ArrayList<String> nombres;
    ListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codigo = findViewById(R.id.edtcodigo);
        nombre = findViewById(R.id.edtnombre);
        programa = findViewById(R.id.edtprograma);
        guardar = findViewById(R.id.btnguardar);
        buscar = findViewById(R.id.btnbuscar);
        listado = findViewById(R.id.btnlistado);
        elimininar= findViewById(R.id.eliminar);
        modificar= findViewById(R.id.modificar);
        guardar.setOnClickListener(this);
        buscar.setOnClickListener(this);
        listado.setOnClickListener(this);
        elimininar.setOnClickListener(this);
        modificar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnbuscar:

                String codigo_buscar= codigo.getText().toString();

                if(!codigo_buscar.isEmpty()){
                ec = new EstudianteController(this);
                Estudiante estudiante_resultado = ec.buscar_Estudiante(codigo_buscar);
                if(estudiante_resultado!=null) {
                    nombre.setText(estudiante_resultado.getNombre());
                    programa.setText(estudiante_resultado.getPrograma());
                }else{
                    Toast.makeText(MainActivity.this , "no se encontro el codigo a buscar ",Toast.LENGTH_LONG).show();
                    nombre.setText("");
                    programa.setText("");

                }}else{
                    Toast.makeText(MainActivity.this,"Ingresar el codigo a buscar",Toast.LENGTH_LONG).show();
                }


                break;

            case R.id.btnguardar:
                ec = new EstudianteController(this);
                e = new Estudiante(codigo.getText().toString(), nombre.getText().toString(), programa.getText().toString());
                ec.agregarEstudiante(e);
                Toast.makeText(this, "Estudiante Agregado", Toast.LENGTH_LONG).show();
                break;


            case R.id.btnlistado:
                ec = new EstudianteController(this);
                Cursor c = ec.allEstudiantes();

                if (c != null) {
                    String cadena = "";
                    while (c.moveToNext()) {
                        cadena = cadena + (c.getString(1))+",";
                    }
                    Toast.makeText(this, cadena, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "No hay datos", Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.eliminar:
                ec = new EstudianteController(this);
                   Crear_dialogo_eliminar(ec.get_adapter_nombre_codigo()).show();
                break;


            case R.id.modificar:
                ec = new EstudianteController(this);
            Crear_dialogo_modificar(ec.get_adapter_nombre_codigo()).show();
            break;


        }
    }

    public Dialog Crear_dialogo_eliminar(ArrayAdapter estudiantes) {
         final ArrayList<String> codigos = ec.allCodigos();
        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setTitle("Eliminar").setAdapter(estudiantes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ec.Eliminar_estudiante(codigos.get(which))){
                    Toast.makeText(MainActivity.this,"Se elimino correctamente",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.this,"no se pudo eliminar",Toast.LENGTH_LONG).show();

                }

            }
        });
        return  builder.create();
    }

    public Dialog Crear_dialogo_modificar(ArrayAdapter estudiantes) {
        final ArrayList<String> codigos = ec.allCodigos();
        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setTitle("Modificar").setAdapter(estudiantes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this ,codigos.get(which),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this , Modificar.class);
                intent.putExtra("id",codigos.get(which));
                startActivity(intent);

            }
        });
        return  builder.create();
    }



}
