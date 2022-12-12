package cl.tobar.mapv304;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import OpenHelper1.SQLite_OpenHelper;

public class MainActivity extends AppCompatActivity {

    TextView tvRegistrese;
    Button btnIngresar;

    SQLite_OpenHelper helper=new SQLite_OpenHelper(this,"BD1",null,1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegistrese=(TextView) findViewById(R.id.tvRegistrese);

        tvRegistrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(i);

            }
        });

        btnIngresar= findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(view -> {
            TextInputLayout txtusu= findViewById(R.id.txtUsuario);
            TextInputLayout txtxpas= findViewById(R.id.txtPassword);

            try {
                Cursor cursor=helper.ConsultarUsuPas
                        (txtusu.getEditText().getText().toString(),txtxpas.getEditText().getText().toString());

                if(cursor.getCount()>0){
                    Intent i= new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario y/o Pass Incorrectos",
                            Toast.LENGTH_LONG).show();
                }
                txtusu.getEditText().setText("");
                txtxpas.getEditText().setText("");
                txtusu.findFocus();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
