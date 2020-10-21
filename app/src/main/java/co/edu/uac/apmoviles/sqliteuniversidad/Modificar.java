package co.edu.uac.apmoviles.sqliteuniversidad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Modificar extends AppCompatActivity {
    EditText edt_codigo, edt_nombre,edt_programa;
    Button btn_cancelar,btn_actualizar ;
    String codigo_viejo ;
    EstudianteController ec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        edt_codigo= findViewById(R.id.edtcodigo);
        edt_nombre= findViewById(R.id.edtnombre);
        edt_programa=findViewById(R.id.edtprograma);
        btn_actualizar=findViewById(R.id.btnactualizar);
        btn_cancelar=findViewById(R.id.btncancelar);
        ec= new EstudianteController(Modificar.this);
        if(getIntent().getExtras()!=null ){
             codigo_viejo  = getIntent().getExtras().getString("id");
            edt_codigo.setText(codigo_viejo);

        }
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Modificar.this, MainActivity.class);
                startActivity(inten);

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo_nuevo = edt_codigo.getText().toString();
                String programa = edt_programa.getText().toString();
                String nombre = edt_nombre.getText().toString();

                if(ec.modificar_estudiante(codigo_viejo,codigo_nuevo,nombre,programa)){
                    Toast.makeText(Modificar.this,"Se modifico correctamente ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Modificar.this, "no se pudo modificar ",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}