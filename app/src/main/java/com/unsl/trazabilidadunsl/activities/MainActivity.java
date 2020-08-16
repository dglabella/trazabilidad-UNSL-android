package com.unsl.trazabilidadunsl.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unsl.trazabilidadunsl.R;
import com.unsl.trazabilidadunsl.controllers.AccessController;
import com.unsl.trazabilidadunsl.controllers.RegisterController;
import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.views.AccessView;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import java.util.Iterator;
import java.util.List;
import org.jasypt.util.text.StrongTextEncryptor;

public class MainActivity extends AppCompatActivity implements AccessView, RegisterView, ErrorView
{
    public static String API_HOSTNAME = "http://104.198.43.227:8080/";

    private TextView selectedAccess;
    private static AccessController accessController;
    private static RegisterController registerController;
    private ListView accessList;
    private Acceso access;
    private StrongTextEncryptor encryptor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.accessController = new AccessController(this, this);
        MainActivity.registerController = new RegisterController(this, this);

        this.encryptor = new StrongTextEncryptor();
        this.encryptor.setPassword("159753zseqsc");

        this.selectedAccess = findViewById(R.id.selectedAccess);

        final Button iniciar = findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(false);

                intentIntegrator.initiateScan();
            }
        });
        iniciar.setEnabled(false);
        this.accessList = findViewById(R.id.accessList);
        this.accessList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedAccess.setText(accessList.getItemAtPosition(i).toString());
                access = (Acceso) accessList.getItemAtPosition(i);
                iniciar.setEnabled(true);
            }
        });

        //search for access online
        MainActivity.accessController.getAccesses();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null && result.getContents()!=null)
        {
            //Log.d("SCAN RESULT -------", result.getContents() );
            //Hacer algo con el resultado
            String decryptedData = encryptor.decrypt(result.getContents());
            String [] splitedData = decryptedData.split("-");
            personaController.iniciarRegistro(Integer.parseInt(splitedData[0]));
        }
        else
        {
            //Log.d("ERROR ------","cannot scan");
            //El resultado no llego
        }
    }

    @Override
    public void manageAccessObtained(List<Acceso> access)
    {
        Acceso a;
        Acceso [] accessDescriptions = new Acceso[access.size()];

        Iterator<Acceso> accessIterator = access.iterator();
        int i = 0;
        while (accessIterator.hasNext())
        {
            accessDescriptions[i] = accessIterator.next();
            //Log.d("MESSAGE ----------- ", accessDescriptions[i].getDescripcion());
            i++;
        }

        ArrayAdapter<Acceso> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, accessDescriptions);
        accessList.setAdapter(adapter);
    }


    @Override
    public void anotherResponse(int code)
    {

    }

    @Override
    public void registerDone()
    {

    }

    @Override
    public void error(String message)
    {

    }
}