package br.com.usesoft.webservice.download;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import br.com.usesoft.usesales.R;

public class AtualizaTabelaPrecos extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_tabela_precos);

        final TextView status = (TextView) findViewById(R.id.status);

        Button btnAtualiza = (Button) findViewById(R.id.btnAtualizarTabelaPrecos);
        btnAtualiza.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                status.setText("Por favor, aguarde...");
                //DownloadClientes downloadClientes = new DownloadClientes();
                //downloadClientes.AtualizaClientes();
                status.setText("Pronto. Favor consultar a Lista de Clientes");
            }
        });


    }
}
