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

public class AccessSelectionActivity extends AppCompatActivity implements AccessView, ErrorView
{
    public static String API_HOSTNAME = "http://104.198.43.227:8080/";
    //public static String API_HOSTNAME = "http://190.114.77.252:8080/"; //Cristian
    private final String PRECONFIG_ACCESS_FILE_NAME = "preConfigAccess.txt";
    private static AccessController accessController;
    private static Acceso access;
    private static boolean hasAccessPreSelected = false;

    private TextView selectedAccess;
    private ListView accessList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccessSelectionActivity.accessController = AccessController.getInstance();
        AccessSelectionActivity.accessController.setAccessView(this);
        AccessSelectionActivity.accessController.setErrorView(this);

        this.selectedAccess = findViewById(R.id.selectedAccess);

        this.accessList = findViewById(R.id.accessList);
        this.accessList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                AccessSelectionActivity.access = (Acceso) accessList.getItemAtPosition(i);
                selectedAccess.setText(AccessSelectionActivity.access.getDescripcion());
                try
                {
                    savePreConfigAccess(AccessSelectionActivity.access);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent readyToScanActivityIntent = new Intent(AccessSelectionActivity.this, ReadyToScanActivity.class);
                    readyToScanActivityIntent.putExtra("access", AccessSelectionActivity.access.toString());
                    startActivity(readyToScanActivityIntent);
                }
            }
        });

        try
        {
            AccessSelectionActivity.access = this.loadPreConfigAccess();
            this.selectedAccess.setText(AccessSelectionActivity.access.getDescripcion());
            this.hasAccessPreSelected = true;
        }
        catch (IOException e)
        {
            this.hasAccessPreSelected = false;
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        AccessSelectionActivity.accessController.getAccesses();

        Intent readyToScanActivityIntent = new Intent(AccessSelectionActivity.this, ReadyToScanActivity.class);

        if(this.hasAccessPreSelected)
        {
            readyToScanActivityIntent.putExtra("access", AccessSelectionActivity.access.toString());
            startActivity(readyToScanActivityIntent);
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
        return AccessSelectionActivity.access;
    }

    public static void setHasAccessPreSelected(boolean val)
    {
        AccessSelectionActivity.hasAccessPreSelected = val;
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
        Toast.makeText(AccessSelectionActivity.this, "UNSPECTED RESPONSE CODE: "+code, Toast.LENGTH_LONG).show();
    }

    @Override
    public void error(String message)
    {
        Toast.makeText(AccessSelectionActivity.this, "ERROR: "+message, Toast.LENGTH_LONG).show();

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