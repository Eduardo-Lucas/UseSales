package br.com.usesoft.webservice.download;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

import br.com.usesoft.usesales.Cliente;
import br.com.usesoft.usesales.MyGlobalVariables;
import br.com.usesoft.usesales.R;
import br.com.usesoft.webservice.Constants;


/**
 * Created by Eduardo Lucas on 21/03/2016.
 */
public class DownloadClientesActivity extends FragmentActivity
        implements OnClickListener {

    public static final String TAG = DownloadClientesActivity.class.getSimpleName();
    private static FragmentManager sFragmentManager;

    public String message = null;
    String iNumeroPedido;

    String sDataInicial, sDataFinal;

    Cursor mCursor, mCursor1; // pedido e pedidoItens
    Calendar myCalendar = Calendar.getInstance();

    /*******************************************************************************************
        If you are referring your localhost on your system from the Android emulator then you have
        to use http://10.0.2.2:8080/ Because Android emulator runs inside a Virtual Machine therefore
        here 127.0.0.1 or localhost will be emulator's own loopback address.
     ********************************************************************************************/

    String url_cliente = Constants.CLIENTES_URL;

    private ContentResolver mContentResolver;
    private SimpleCursorAdapter mAdapter;

    // Campos Globais
    private TextView mCodigoEmpresa, mNomeEmpresa, mCodigoUsuario, mNomeUsuario, mMensagem;

    private Button btnDownloadClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadclientes);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        sFragmentManager = getSupportFragmentManager();

        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final String nomeEmpresa = globalVariables.getNomeEmpresa();
        final int codigoUsuario = globalVariables.getUsuario_id();
        final String nomeUsuario = globalVariables.getNomeUsuario();

        mContentResolver = DownloadClientesActivity.this.getContentResolver();

        btnDownloadClientes = (Button) findViewById(R.id.btnDownloadClientes);
        btnDownloadClientes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (isOnline()) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(DownloadClientesActivity.this);
                            alert.setTitle("Download de Clientes");
                            alert.setMessage("Confirma Download de Clientes?");
                            alert.setPositiveButton("Sim", new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    taskClientes();
                                }
                            });
                            alert.setNegativeButton("Não", new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            alert.show();



                    } else {
                        Toast.makeText(getApplicationContext(), "Dispositivo sem conexão disponível", Toast
                                .LENGTH_LONG).show();
                    }


            }

        });


    }



    @Override
    public void onClick(View view) {
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public DownloadClientesActivity taskClientes() {
        // Grava Pedidos no Web Service
        new GetClientesTask().execute();
        return null;
    }

    // Task para gravar os Pedidos
    private class GetClientesTask extends AsyncTask<Void, Void, Cliente[]> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(DownloadClientesActivity.this, "Aguarde", "Enviando Pedido...",
                    false, true);
        }

        @Override
        protected void onPostExecute(Cliente[] clientes) {
            if(clientes != null) {
                // TODO fazer o loop para inserir na tabela de clientes
            }
        }

        @Override
        protected Cliente[] doInBackground(Void... params) {

            final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
            final int codigoEmpresa = globalVariables.getCodigoEmpresa();
            final int codigoUsuario = globalVariables.getUsuario_id();
            final String dataInicial = globalVariables.getDataInicial();
            final String dataFinal = globalVariables.getDataFinal();

            // Set the username and password for creating a Basic Auth request
            HttpAuthentication authHeader = new HttpBasicAuthentication("usesoft", "uses0ft10!");
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setContentType(MediaType.valueOf("application/json"));
            // End of Set the username and password for creating a Basic Auth request

            // CAPTURA O CORPO DA TABELA CLIENTES
            Cliente[] objects = new Cliente[0];
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                ResponseEntity<Cliente[]> responseEntity = restTemplate.getForEntity(url_cliente, Cliente[].class);
                objects = responseEntity.getBody();
                MediaType contentType = responseEntity.getHeaders().getContentType();
                HttpStatus statusCode = responseEntity.getStatusCode();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
            // FIM CAPTURA O CORPO DA TABELA CLIENTES

            return objects;
        }

    }


}









