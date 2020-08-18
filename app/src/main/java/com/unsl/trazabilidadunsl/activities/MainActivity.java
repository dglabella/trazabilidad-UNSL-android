package com.unsl.trazabilidadunsl.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.unsl.trazabilidadunsl.models.Registro;
import com.unsl.trazabilidadunsl.views.AccessView;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import java.util.Iterator;
import java.util.List;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

public class MainActivity extends AppCompatActivity implements AccessView, RegisterView, ErrorView
{
    public static String API_HOSTNAME = "http://104.198.43.227:8080/";

    private TextView selectedAccess;
    private static AccessController accessController;
    private static RegisterController registerController;
    private ListView accessList;
    private static Acceso access;
    private StrongTextEncryptor encryptor;
    private StandardPBEStringEncryptor encryptor2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.accessController = AccessController.getInstance(this, this);
        MainActivity.registerController = RegisterController.getInstance(this, this);

        this.encryptor = new StrongTextEncryptor();
        this.encryptor.setPassword("159753zseqsc");

        this.encryptor2 = new StandardPBEStringEncryptor();
        this.encryptor2.setPassword("159753zseqsc");

        this.selectedAccess = findViewById(R.id.selectedAccess);

        final Button iniciar = findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(access != null)
                {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.initiateScan();
                }
                else
                {
                    selectedAccess.setText("PORFAVOR SELECCIONE UN ACCESO");
                }
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

        //Log.d("---", "--------------------------------------------------------------------------------------------------");

        String encryptedData = this.encryptor2.encrypt("17-35069255-Danilo-Labella");
        System.out.println("DATOS ENC ------------------- "+encryptedData);
        System.out.println("DATOS DEC ------------------- "+this.encryptor2.decrypt(encryptedData));

        //swo0jB5xl7+RetLDsNX2Jpjp+iuiC9CmEGMyr1FfZf8sDLoYaKk2gg==
        //String decryptedData = encryptor2.decrypt("swo0jB5xl7+RetLDsNX2Jpjp+iuiC9CmEGMyr1FfZf8sDLoYaKk2gg==");
        //String [] splitedData = decryptedData.split("-");
        //Log.d("ID ----------- ", splitedData[0]+" "+splitedData[1]+" "+splitedData[2]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null && result.getContents()!=null)
        {
            Log.d("SCAN RESULT -------", result.getContents());
            //Hacer algo con el resultado
            //String decryptedData = encryptor.decrypt(result.getContents());
            //String [] splitedData = decryptedData.split("-");
            //Log.d("ID ----------- ", splitedData[0]);
            //personController.initRegister(Integer.parseInt(splitedData[0]));
            //personController.initRegister(42);
        }
        else
        {
            Log.d("ERROR ------","cannot scan");
            //El resultado no llego
        }
    }

    public static Acceso getSelectedAccess()
    {
        return MainActivity.access;
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
    public void registerDone(Registro register)
    {
        Log.d("MESSAGE ------- ","REGISTRO REALIZADO");
    }

    @Override
    public void error(String message)
    {
        Log.d("A VER ----------- ", message);
    }
}