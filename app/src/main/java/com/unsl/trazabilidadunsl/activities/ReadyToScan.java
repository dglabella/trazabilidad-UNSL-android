package com.unsl.trazabilidadunsl.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unsl.trazabilidadunsl.R;
import com.unsl.trazabilidadunsl.controllers.AccessController;
import com.unsl.trazabilidadunsl.controllers.RegisterController;
import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.models.Persona;
import com.unsl.trazabilidadunsl.models.Registro;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import org.jasypt.util.text.StrongTextEncryptor;
import java.util.Objects;

public class ReadyToScan extends AppCompatActivity implements RegisterView, ErrorView
{
    private TextView selectedAccess;
    private Acceso access;
    private static AccessController accessController;
    private static RegisterController registerController;
    private ListView accessList;
    private StrongTextEncryptor encryptor;
    private Persona personScanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_scan);

        Intent thisIntent = this.getIntent();
        String[] splitedData = Objects.requireNonNull(thisIntent.getStringExtra("access")).split("-");
        this.access = new Acceso();
        this.access.setId(Integer.parseInt(splitedData[0]));
        this.access.setDescripcion(splitedData[1]);

        this.selectedAccess = findViewById(R.id.accessSelected);
        this.selectedAccess.setText(this.access.getDescripcion());

        this.encryptor = new StrongTextEncryptor();
        this.encryptor.setPassword("159753zseqsc");

        //this.encryptor2 = new StandardPBEStringEncryptor();
        //this.encryptor2.setPassword("159753zseqsc");

        final Button initScan = findViewById(R.id.initScan);
        initScan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(access != null)
                {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(ReadyToScan.this);
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.initiateScan();
                }
                else
                {
                    Toast.makeText(ReadyToScan.this, "No hay acceso seleccionado, vuelva atras.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null && result.getContents()!=null)
        {
            /*
            String decryptedData = encryptor.decrypt(result.getContents());
            String [] splitedData = decryptedData.split("-");
            this.personScanned = new Persona();
            this.personScanned.setId(Integer.parseInt(splitedData[0]));
            this.personScanned.setDni(Integer.parseInt(splitedData[1]));
            this.personScanned.setNombre(splitedData[2]);
            this.personScanned.setApellido(splitedData[3]);
            this.registerController.createRegister(personScanned.getId());
            */
            /*
            this.personScanned = new Persona();
            this.personScanned.setId(9999);
            this.personScanned.setDni(99999999);
            this.personScanned.setNombre("Fake");
            this.personScanned.setApellido("Person");
            this.registerController.createRegister(personScanned.getId());
            */
            Toast.makeText(ReadyToScan.this, result.getContents(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("ERROR ------","cannot scan");
            //El resultado no llego
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(ReadyToScan.this,"He Resumido", Toast.LENGTH_LONG).show();
    }

    @Override
    public void anotherResponse(int code)
    {
        Toast.makeText(ReadyToScan.this, "UNSPECTED RESPONSE CODE: "+code, Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerDone(Registro register)
    {
        Toast.makeText(ReadyToScan.this, "Registro realizado: "+
                this.personScanned.getDni()+" "+this.personScanned.getNombre()+" "+
                this.personScanned.getApellido(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void error(String message)
    {
        Toast.makeText(ReadyToScan.this, "ERROR: "+message, Toast.LENGTH_LONG).show();
    }
}