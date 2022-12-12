package cl.tobar.mapv304;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import OpenHelper1.SQLite_OpenHelper;

public class MainActivity2  extends AppCompatActivity {

    Button btnGrabarUsu;
    EditText txtNomUsu, txtCorUsu, txtPasUsu;

    SQLite_OpenHelper helper = new SQLite_OpenHelper(this,"BD1",null,1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        btnGrabarUsu=(Button) findViewById(R.id.btnRegistrarUsu);
        txtNomUsu=(EditText) findViewById(R.id.txtNomUsu);
        txtCorUsu=(EditText) findViewById(R.id.txtCorUsu);
        txtPasUsu=(EditText) findViewById(R.id.txtPasUsu);


        btnGrabarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.abrir();
                helper.insertarReg(String.valueOf(txtNomUsu.getText()),
                        String.valueOf(txtCorUsu.getText()),
                        String.valueOf(txtPasUsu.getText()));
                helper.cerrar();

                Toast.makeText(getApplicationContext(),"Registro Almacenado con Exito"
                        ,Toast.LENGTH_LONG).show();

                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });
    }
}
