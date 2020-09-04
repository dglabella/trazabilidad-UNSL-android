package com.unsl.trazabilidadunsl.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unsl.trazabilidadunsl.R;
import com.unsl.trazabilidadunsl.controllers.RegisterController;
import com.unsl.trazabilidadunsl.controllers.StatisticsController;
import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.models.Estadisticas;
import com.unsl.trazabilidadunsl.models.RegistroCellPhone;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import com.unsl.trazabilidadunsl.views.StatisticsView;
import java.util.Objects;
//import org.jasypt.util.text.StrongTextEncryptor;

public class ReadyToScanActivity extends AppCompatActivity implements RegisterView, StatisticsView, ErrorView
{
    private TextView selectedAccess;
    private TextView stats;
    private TextView total;
    private Button initScan;

    private Acceso access;
    private static RegisterController registerController;
    private static StatisticsController statisticsController;
    //private StrongTextEncryptor encryptor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_scan);

        Intent thisIntent = this.getIntent();
        String[] splitedData = Objects.requireNonNull(thisIntent.getStringExtra("access")).split("-");
        this.access = new Acceso();
        this.access.setId(Integer.parseInt(splitedData[0]));
        this.access.setDescripcion(splitedData[1]);

        this.selectedAccess = findViewById(R.id.accessSelected);
        this.selectedAccess.setText(this.access.getDescripcion());

        this.stats = findViewById(R.id.stats);
        this.total = findViewById(R.id.total);

        ReadyToScanActivity.registerController =  RegisterController.getInstance();
        ReadyToScanActivity.registerController.setRegisterView(this);
        ReadyToScanActivity.registerController.setErrorView(this);

        ReadyToScanActivity.statisticsController = StatisticsController.getInstance();
        ReadyToScanActivity.statisticsController.setStatisticsView(this);
        ReadyToScanActivity.statisticsController.setErrorView(this);

        initScan = findViewById(R.id.initScan);
        initScan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(access != null && access.getId() != 99) //id=99 means error
                {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(ReadyToScanActivity.this);
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setOrientationLocked(true);
                    intentIntegrator.initiateScan();
                }
                else
                {
                    Toast.makeText(ReadyToScanActivity.this, "No hay acceso seleccionado, vuelva atras.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(access != null && access.getId() != 99)
        {
            this.initScan.setEnabled(true);
            ReadyToScanActivity.statisticsController.getStatistics(this.access.getId());
        }
        else
        {
            this.initScan.setEnabled(false);
            Toast.makeText(ReadyToScanActivity.this, "No hay acceso seleccionado, vuelva atras.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        AccessSelectionActivity.setHasAccessPreSelected(false);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null && result.getContents()!=null)
        {
            //System.out.println("\n\n\n"+result.getContents()+"\n\n\n");
            RegistroCellPhone rcp = new RegistroCellPhone();
            rcp.setIdAcceso((long)this.access.getId());
            rcp.setEncryptedData(result.getContents());
            ReadyToScanActivity.registerController.createRegister(rcp);
            //Toast.makeText(ReadyToScanActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("ERROR ------","cannot scan");
            Toast.makeText(ReadyToScanActivity.this, "ERROR, no scan result", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void anotherResponse(int code)
    {
        Toast.makeText(ReadyToScanActivity.this, "UNSPECTED RESPONSE CODE: "+code, Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerDone(RegistroCellPhone registroCellPhone)
    {
        //Toast.makeText(ReadyToScanActivity.this, "Registro realizado: "+
        //        this.personScanned.getDni()+" "+this.personScanned.getNombre()+" "+
        //       this.personScanned.getApellido(), Toast.LENGTH_LONG).show();
        Toast.makeText(ReadyToScanActivity.this, "Registro realizado", Toast.LENGTH_LONG).show();
        ReadyToScanActivity.statisticsController.getStatistics(this.access.getId());
    }

    @Override
    public void error(String message)
    {
        Toast.makeText(ReadyToScanActivity.this, "ERROR: "+message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void reportStatistics(Estadisticas statistics)
    {
        this.stats.setText("Transito: "+statistics.getEnTransito().toString());
        this.total.setText("Ingresos totales: "+statistics.getTotales().toString());
    }
}