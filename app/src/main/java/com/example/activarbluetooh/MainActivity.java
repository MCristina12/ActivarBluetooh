package com.example.activarbluetooh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
    }

    public void habilitarBluetooth(View v) {

        solicitarPermiso();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //si hay o no bluettoth
        if (bluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "No tienes bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) {// si no esta habilitado
            Intent habilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                startActivityForResult(habilitarBluetoothIntent, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
                return;
            }

        }
    }

    public boolean checarStatusPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if(resultado == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    public void solicitarPermiso(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this, "El permiso ya fue otorgado, si deseas desactivar, puedes ir a ajustes", Toast.LENGTH_SHORT).show();
        }else{//SOLICITAR PERMISO
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //cuando ya se da el permiso
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                if(checarStatusPermiso()){
                    Toast.makeText(MainActivity.this, "Ya está activo el permiso para Bluetooth", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No está activo el permiso para Bluetooth", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}