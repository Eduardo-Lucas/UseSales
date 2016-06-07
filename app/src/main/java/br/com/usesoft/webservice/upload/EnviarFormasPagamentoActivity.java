package br.com.usesoft.webservice.upload;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.usesoft.usesales.MyGlobalVariables;
import br.com.usesoft.usesales.R;


/**
 * Created by Eduardo Lucas on 21/03/2016.
 */
public class EnviarFormasPagamentoActivity extends FragmentActivity
        implements OnClickListener {

    public static final String TAG = EnviarFormasPagamentoActivity.class.getSimpleName();
    private static FragmentManager sFragmentManager;

    public String message = null;

    String sDataInicial, sDataFinal;

    Cursor mCursor;
    Calendar myCalendar = Calendar.getInstance();
    private ContentResolver mContentResolver;
    private SimpleCursorAdapter mAdapter;
    // Variaveis Data e Hora do Pedido
    private String dataPedido, horaPedido;
    // Campos Globais
    private TextView mCodigoEmpresa, mNomeEmpresa, mCodigoUsuario, mNomeUsuario, mMensagem;
    private EditText mDataInicial;
    private EditText mDataFinal;
    private DatePickerDialog dataInicialPickerDialog;
    private DatePickerDialog dataFinalPickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btnDownload, btnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_pedidos);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final String nomeEmpresa = globalVariables.getNomeEmpresa();
        final int codigoUsuario = globalVariables.getUsuario_id();
        final String nomeUsuario = globalVariables.getNomeUsuario();

        sFragmentManager = getSupportFragmentManager();

        mCodigoEmpresa = (TextView) findViewById(R.id.codigoEmpresa_tv);
        mNomeEmpresa = (TextView) findViewById(R.id.nomeEmpresa_tv);
        mCodigoUsuario = (TextView) findViewById(R.id.codigoRepresentante_tv);
        mNomeUsuario = (TextView) findViewById(R.id.nomeRepresentante_tv);
        mMensagem = (TextView) findViewById(R.id.message_tv);
        mDataInicial = (EditText) findViewById(R.id.dataInicial);
        mDataFinal = (EditText) findViewById(R.id.dataFinal);

        mCodigoEmpresa.setText(String.valueOf(codigoEmpresa));
        mNomeEmpresa.setText(nomeEmpresa);
        mCodigoUsuario.setText(String.valueOf(codigoUsuario));
        mNomeUsuario.setText(nomeUsuario);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        Calendar dtInicial = Calendar.getInstance();
        Calendar dtFinal = Calendar.getInstance();


        mDataInicial.setText(dateFormatter.format(dtInicial.getTime()));
        mDataFinal.setText(dateFormatter.format(dtFinal.getTime()));
        setDateTimePedido();

        mContentResolver = EnviarFormasPagamentoActivity.this.getContentResolver();

        btnUpload = (Button) findViewById(R.id.btnUploadPedidos);
        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    if (isOnline()) {
                        FormasPagamentosFragment formasPagamentoFragment = new
                                FormasPagamentosFragment(codigoEmpresa).taskPedidos();

                    } else {
                        Toast.makeText(getApplicationContext(), "Dispositivo sem conexão disponível", Toast
                                .LENGTH_LONG).show();
                    }
                }

            }

        });


    }

    private boolean isValid() {
        sDataInicial = mDataInicial.getText().toString();
        sDataFinal = mDataFinal.getText().toString();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dDataInicial = null;
        try {
            dDataInicial = dateFormatter.parse(sDataInicial);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date dDataFinal = null;
        try {
            dDataFinal = dateFormatter.parse(sDataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dDataInicial.after(dDataFinal) && dDataFinal.before(dDataInicial)) {
            if (dDataInicial.after(dDataFinal)) {
                Toast.makeText(this, "A data inicial NÃO PODE SER MAIOR que a Data Final", Toast
                        .LENGTH_LONG).show();
                mDataInicial.requestFocus();
            } else {
                Toast.makeText(this, "A data final NÃO PODE SER MENOR que a Data Inicial", Toast
                        .LENGTH_LONG).show();
                mDataFinal.requestFocus();
            }
            return false;
        } else {
            // Nesse caso, as datas estão ok!
            return true;
        }
    }

    private void setDateTimePedido() {

        mDataInicial.setOnClickListener(this);
        mDataFinal.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dataInicialPickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDateInicial = Calendar.getInstance();
                newDateInicial.set(year, monthOfYear, dayOfMonth);
                mDataInicial.setText(dateFormatter.format(newDateInicial.getTime()));
                mDataFinal.setText(dateFormatter.format(newDateInicial.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dataFinalPickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDateFinal = Calendar.getInstance();
                newDateFinal.set(year, monthOfYear, dayOfMonth);
                mDataFinal.setText(dateFormatter.format(newDateFinal.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View view) {
        if (view == mDataInicial) {
            dataInicialPickerDialog.show();
        } else if (view == mDataFinal) {
            dataFinalPickerDialog.show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
