package com.unsl.trazabilidadunsl.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.unsl.trazabilidadunsl.R;
import com.unsl.trazabilidadunsl.controllers.AccessController;
import com.unsl.trazabilidadunsl.views.AccessView;
import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.views.ErrorView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class accessSelectionActivity extends AppCompatActivity implements AccessView, ErrorView
{
    public static String API_HOSTNAME = "http://104.198.43.227:8080/";
    //public static String API_HOSTNAME = "http://190.114.77.252:8080/"; //Cristian

    private final String PRECONFIG_ACCESS_FILE_NAME = "preConfigAccess.txt";
    private TextView selectedAccess;
    private static AccessController accessController;
    private ListView accessList;
    private static Acceso access;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.encryptor = new StrongTextEncryptor();
        //this.encryptor.setPassword("159753zseqsc");
        //String encryptedData = this.encryptor.encrypt("17-35069255-Danilo-Labella");
        //System.out.println("DATOS ENC ------------------- "+encryptedData);
        //System.out.println("DATOS DEC ------------------- "+this.encryptor2.decrypt(encryptedData));
        //swo0jB5xl7+RetLDsNX2Jpjp+iuiC9CmEGMyr1FfZf8sDLoYaKk2gg==
        //String decryptedData = encryptor.decrypt("swo0jB5xl7+RetLDsNX2Jpjp+iuiC9CmEGMyr1FfZf8sDLoYaKk2gg==");
        //String [] splitedData = decryptedData.split("-");
        //Log.d("ID ----------- ", splitedData[0]+" "+splitedData[1]+" "+splitedData[2]);

        accessSelectionActivity.accessController = AccessController.getInstance(this, this);

        this.selectedAccess = findViewById(R.id.selectedAccess);

        this.accessList = findViewById(R.id.accessList);
        this.accessList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                accessSelectionActivity.access = (Acceso) accessList.getItemAtPosition(i);
                selectedAccess.setText(accessSelectionActivity.access.getDescripcion());

                try
                {
                    savePreConfigAccess(accessSelectionActivity.access);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent readyToScanActivity = new Intent(accessSelectionActivity.this, ReadyToScan.class);
                    readyToScanActivity.putExtra("access", accessSelectionActivity.access.toString());
                    startActivity(readyToScanActivity);
                }
            }
        });

        try
        {
            accessSelectionActivity.access = this.loadPreConfigAccess();
            this.selectedAccess.setText(accessSelectionActivity.access.getDescripcion());
            Intent readyToScanActivity = new Intent(accessSelectionActivity.this, ReadyToScan.class);
            readyToScanActivity.putExtra("access", accessSelectionActivity.access.toString());
            startActivity(readyToScanActivity);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //search for access online
        accessSelectionActivity.accessController.getAccesses();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private Acceso loadPreConfigAccess() throws IOException
    {
        Acceso ret = null;
        FileInputStream fIn = openFileInput(PRECONFIG_ACCESS_FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fIn);

        char[] inputBuffer = new char[100];
        String fileContent = null;

        isr.read(inputBuffer);

        String readString = new String(inputBuffer).trim();
        String [] splitedData = readString.split("-");

        ret = new Acceso();
        ret.setId(Integer.parseInt(splitedData[0]));
        ret.setDescripcion(splitedData[1]);
        ret.setEstado("ACTIVADO");
        return ret;
    }

    private void savePreConfigAccess(Acceso access) throws IOException
    {
        String data = access.toString();
        FileOutputStream fOut = openFileOutput(PRECONFIG_ACCESS_FILE_NAME, MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        osw.write(data);

        osw.flush();
        osw.close();
    }

    public static Acceso getSelectedAccess()
    {
        return accessSelectionActivity.access;
    }

    @Override
    public void manageAccessObtained(List<Acceso> access)
    {
        Acceso [] accessDescriptions = new Acceso[access.size()];

        Iterator<Acceso> accessIterator = access.iterator();
        int i = 0;
        while (accessIterator.hasNext())
        {
            accessDescriptions[i] = accessIterator.next();
            i++;
        }
        ArrayAdapter<Acceso> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, accessDescriptions);
        accessList.setAdapter(adapter);
    }

    @Override
    public void anotherResponse(int code)
    {
        Toast.makeText(accessSelectionActivity.this, "UNSPECTED RESPONSE CODE: "+code, Toast.LENGTH_LONG).show();
    }

    @Override
    public void error(String message)
    {
        Toast.makeText(accessSelectionActivity.this, "ERROR: "+message, Toast.LENGTH_LONG).show();

        List<Acceso> list = new ArrayList<>();
        Acceso aux = new Acceso();
        aux.setId(99);
        aux.setDescripcion("NO HAY ACCESOS CARGADOS");
        aux.setEstado("DESACTIVADO");
        list.add(aux);
        manageAccessObtained(list);
    }
}

    /*
    //Conocer todos los algoritmos de encriptacion soportados en el ambiente
    System.out.println("\n\nCIPHERS\n");
    Set ciphers = Security.getAlgorithms("Cipher");
    Object[] cipherNames = ciphers.toArray();
    for (int i = 0; i < cipherNames.length; i++) {
        System.out.println(Arrays.toString(cipherNames));
    }
    */