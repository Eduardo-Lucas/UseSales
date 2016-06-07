package br.com.usesoft.webservice.upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import br.com.usesoft.usesales.MyGlobalVariables;
import br.com.usesoft.usesales.R;
import br.com.usesoft.webservice.download.AtualizaTabelaPrecos;


/**
 * Created by Eduardo Lucas on 15/03/2016.
 */
public class UploadActivity extends FragmentActivity {

    public static final String TAG = UploadActivity.class.getSimpleName();
    // Campos Globais
    private TextView mCodigoEmpresa, mNomeEmpresa, mCodigoUsuario, mNomeUsuario, mMensagem;

    private Button btnDownload, btnUpload, btnFormas, btnWebService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfere_arquivos);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final String nomeEmpresa = globalVariables.getNomeEmpresa();
        final int codigoUsuario = globalVariables.getUsuario_id();
        final String nomeUsuario = globalVariables.getNomeUsuario();

        mCodigoEmpresa = (TextView) findViewById(R.id.codigoEmpresa_tv);
        mNomeEmpresa = (TextView) findViewById(R.id.nomeEmpresa_tv);
        mCodigoUsuario = (TextView) findViewById(R.id.codigoRepresentante_tv);
        mNomeUsuario = (TextView) findViewById(R.id.nomeRepresentante_tv);
        mMensagem = (TextView) findViewById(R.id.mesagemErro_tv);

        mCodigoEmpresa.setText(String.valueOf(codigoEmpresa));
        mNomeEmpresa.setText(nomeEmpresa);
        mCodigoUsuario.setText(String.valueOf(codigoUsuario));
        mNomeUsuario.setText(nomeUsuario);

        btnUpload = (Button) findViewById(R.id.btnUploadPedidos);
        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, EnviarPedidosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

        btnUpload = (Button) findViewById(R.id.btnUploadFormas);
        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, EnviarFormasPagamentoActivity
                        .class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

        btnDownload = (Button) findViewById(R.id.btnDownloadDados);
        btnDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, AtualizaTabelaPrecos.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnWebService = (Button) findViewById(R.id.btnAtualizaWebService);
        btnWebService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, AtualizaWebService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



    }



}
