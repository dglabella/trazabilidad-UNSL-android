package com.unsl.trazabilidadunsl.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.unsl.trazabilidadunsl.R;
import android.os.Bundle;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }

    @Override
    public void handleResult(Result result)
    {

    }
}