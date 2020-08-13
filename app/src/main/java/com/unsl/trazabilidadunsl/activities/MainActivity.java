package com.unsl.trazabilidadunsl.activities;

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

import com.unsl.trazabilidadunsl.R;
import com.unsl.trazabilidadunsl.controllers.AccessController;
import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.views.AccessView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AccessView
{
    public static String API_HOSTNAME = "http://104.198.43.227:8080/";

    private TextView selectedAccess;
    private static AccessController accessController;
    private ListView accessList;
    private Acceso access;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.accessController = new AccessController(this);

        this.selectedAccess = findViewById(R.id.selectedAccess);

        Button iniciar = findViewById(R.id.iniciar);
        iniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Intent para scanner
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                intent.putExtra("accessId", access.getId());
                startActivity(intent);
            }
        });

        this.accessList = findViewById(R.id.accessList);
        this.accessList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedAccess.setText(accessList.getItemAtPosition(i).toString());
                access = (Acceso) accessList.getItemAtPosition(i);
            }
        });

        //search for access online
        MainActivity.accessController.getAccess();
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
            //a = accessIterator.next();
            accessDescriptions[i] = accessIterator.next();
            //accessDescriptions[i] = a.getDescripcion();
            Log.d("MESSAGE ------------------>", accessDescriptions[i].getDescripcion());
            i++;
        }

        ArrayAdapter<Acceso> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, accessDescriptions);
        accessList.setAdapter(adapter);
    }

    @Override
    public void error(String message)
    {

    }

    @Override
    public void anotherResponse(int code)
    {

    }
}