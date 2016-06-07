package br.com.usesoft.webservice.upload;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.usesoft.usesales.MyGlobalVariables;
import br.com.usesoft.usesales.Pedido;
import br.com.usesoft.usesales.PedidoItem;
import br.com.usesoft.usesales.PedidosListActivity;
import br.com.usesoft.usesales.R;
import br.com.usesoft.usesales.UsesalesContract;
import br.com.usesoft.usesales.UsesalesContract.Pedidos;
import br.com.usesoft.usesales.UsesalesContract.PedidosColumns;
import br.com.usesoft.usesales.UsesalesContract.PedidosItens;


/**
 * Created by Eduardo Lucas on 21/03/2016.
 */
public class EnviarPedidosActivity extends FragmentActivity
        implements OnClickListener {

    public static final String TAG = EnviarPedidosActivity.class.getSimpleName();
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

    String url_pedido = "http://191.251.199.232/pedidos";
    String url_pedidoItem = null;

    String iTipoPedido;
    private ContentResolver mContentResolver;
    private SimpleCursorAdapter mAdapter;
    // Variaveis Data e Hora do Pedido
    private String dataPedido, horaPedido;
    // Campos Globais
    private TextView mCodigoEmpresa, mNomeEmpresa, mCodigoUsuario, mNomeUsuario, mMensagem;
    private RadioGroup mTipoPedido;
    private RadioButton mPedido;
    private RadioButton mPrePedido;
    private TextView mNumeroPedido;
    private TextView mSeriePedido;
    private TextView mSubseriePedido;
    private TextView mCodigoCliente;
    private TextView mNomeCliente;
    private TextView mDataPedido;
    private TextView mHoraPedido;
    private TextView mCodigoFormaPagamento;
    private TextView mDescricaoFormaPagamento;
    private TextView mCodigoPrazoPagamento;
    private TextView mDescricaoPrazoPagamento;
    private TextView mValorTotal;
    private Button btnDownload, btnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edita_pedido);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        iNumeroPedido = intent.getStringExtra(PedidosColumns.PEDIDOS_ID);
        iTipoPedido = intent.getStringExtra(PedidosColumns.PEDIDOS_TIPO_PEDIDO);
        String iSeriePedido = intent.getStringExtra(PedidosColumns.PEDIDOS_SERIE);
        String iSubseriePedido = intent.getStringExtra(PedidosColumns.PEDIDOS_SUBSERIE);
        int iCodigoCliente = intent.getIntExtra(PedidosColumns.PEDIDOS_CODIGOCLIENTE, 0);
        String iDataPedido = intent.getStringExtra(PedidosColumns.PEDIDOS_DATAPEDIDO);
        String iHoraPedido = intent.getStringExtra(PedidosColumns.PEDIDOS_HORAPEDIDO);
        int iCodigoFormaPagamento = intent.getIntExtra(PedidosColumns.PEDIDOS_FORMAPAGAMENTO, 0);
        int iCodigoPrazoPagamento = intent.getIntExtra(PedidosColumns.PEDIDOS_PRAZOPAGAMENTO, 0);
        String ivalorTotal = intent.getStringExtra(PedidosColumns.PEDIDOS_VALORTOTAL);
        final String iStatus = intent.getStringExtra(PedidosColumns.PEDIDOS_STATUS);

        mTipoPedido = (RadioGroup) findViewById(R.id.tipoPedido_rg);
        mPedido = (RadioButton) findViewById(R.id.radio0);
        mPrePedido = (RadioButton) findViewById(R.id.radio1);

        if (iTipoPedido.equals("Pedido")) {
            mPedido.setChecked(true);
        } else {
            mPrePedido.setChecked(true);
        }

        mNumeroPedido = (TextView) findViewById(R.id.numeroPedido_tv);
        mSeriePedido = (TextView) findViewById(R.id.seriePedido_tv);
        mSubseriePedido = (TextView) findViewById(R.id.subseriePedido_tv);
        mCodigoCliente = (TextView) findViewById(R.id.codigoCliente_tv);
        mNomeCliente = (TextView) findViewById(R.id.nomeCliente_tv);
        mDataPedido = (TextView) findViewById(R.id.dataPedido_tv);
        mHoraPedido = (TextView) findViewById(R.id.horaPedido_tv);
        mValorTotal = (TextView) findViewById(R.id.valorTotal_tv);
        mCodigoFormaPagamento = (TextView) findViewById(R.id.codigoFormaPagamento_tv);
        mDescricaoFormaPagamento = (TextView) findViewById(R.id.descricaoFormaPagamento_tv);
        mCodigoPrazoPagamento = (TextView) findViewById(R.id.codigoPrazoPagamento_tv);
        mDescricaoPrazoPagamento = (TextView) findViewById(R.id.descricaoPrazoPagamento_tv);

        sFragmentManager = getSupportFragmentManager();

        mNumeroPedido.setText(iNumeroPedido);
        mSeriePedido.setText(iSeriePedido);
        mSubseriePedido.setText(iSubseriePedido);
        mCodigoCliente.setText(String.valueOf(iCodigoCliente));
        //mNomeCliente.setText(pegaNomeCliente());
        mDataPedido.setText(iDataPedido);
        mHoraPedido.setText(iHoraPedido);
        mCodigoFormaPagamento.setText(String.valueOf(iCodigoFormaPagamento));
        //mDescricaoFormaPagamento.setText(pegaNomeFormaPagamento());
        mCodigoPrazoPagamento.setText(String.valueOf(iCodigoPrazoPagamento));
        //mDescricaoPrazoPagamento.setText(pegaNomePrazoPagamento());
        mValorTotal.setText(ivalorTotal);

        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final String nomeEmpresa = globalVariables.getNomeEmpresa();
        final int codigoUsuario = globalVariables.getUsuario_id();
        final String nomeUsuario = globalVariables.getNomeUsuario();

        mContentResolver = EnviarPedidosActivity.this.getContentResolver();

        btnUpload = (Button) findViewById(R.id.savePedidoButton);
        btnUpload.setText("Enviar Pedido...");
        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    if (isOnline()) {
                        if (iStatus.equals("enviado")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(EnviarPedidosActivity.this);
                            alert.setTitle("Reenvio de Pedido");
                            alert.setMessage("Quer enviar esse Pedido de novo?");
                            alert.setPositiveButton("Reenvia Pedido", new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    taskPedidos();
                                }
                            });
                            alert.setNegativeButton("Cancela Reenvio", new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            alert.show();

                        } else {
                            taskPedidos();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Dispositivo sem conexão disponível", Toast
                                .LENGTH_LONG).show();
                    }
                }

            }

        });


    }

    private boolean isValid() {
        if (iNumeroPedido != null) {
            if (iTipoPedido.equals("Pedido")) {

                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Antes de enviar, troque o tipo de " +
                        "Pré-Pedido para Pedido.", Toast
                        .LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sem Identificador do Pedido", Toast
                    .LENGTH_LONG).show();
            return false;
        }
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

    public EnviarPedidosActivity taskPedidos() {
        // Grava Pedidos no Web Service
        new PostPedidosTask().execute();
        return null;
    }


    // Task para gravar os Pedidos
    private class PostPedidosTask extends AsyncTask<Void, Void, List<Pedido>> {

        ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(EnviarPedidosActivity.this, "Aguarde", "Enviando Pedido...",
                    false, true);
        }

        @Override
        protected List<Pedido> doInBackground(Void... params) {

            String[] projection = {Pedidos.PEDIDOS_ID,
                    Pedidos.PEDIDOS_TIPO_PEDIDO,
                    Pedidos.PEDIDOS_CODIGO_EMPRESA,
                    Pedidos.PEDIDOS_CODIGOCLIENTE,
                    Pedidos.PEDIDOS_FORMAPAGAMENTO,
                    Pedidos.PEDIDOS_PRAZOPAGAMENTO,
                    Pedidos.PEDIDOS_CODIGOVENDEDOR,
                    Pedidos.PEDIDOS_DATAPEDIDO,
                    Pedidos.PEDIDOS_HORAPEDIDO,
                    Pedidos.PEDIDOS_ORIGEM,
                    Pedidos.PEDIDOS_SERIE,
                    Pedidos.PEDIDOS_STATUS,
                    Pedidos.PEDIDOS_SUBSERIE,
                    Pedidos.PEDIDOS_OBSERVACAO,
                    Pedidos.PEDIDOS_VALORTOTAL};

            final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
            final int codigoEmpresa = globalVariables.getCodigoEmpresa();
            final int codigoUsuario = globalVariables.getUsuario_id();
            final String dataInicial = globalVariables.getDataInicial();
            final String dataFinal = globalVariables.getDataFinal();


            String selection = Pedidos.PEDIDOS_ID + " = " + iNumeroPedido;

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            Long numeroPedido;               // Número do Pedido
            String tipoPedido;                 // Pedido ou Pré-Pedido
            // Codigo da Empresa vem do Context
            int codigoCliente;
            int codigoFormaPagamento;
            int codigoPrazoPagamento;
            int codigoVendedor;
            String dataPedido;
            String horaPedido;
            String origem;
            String seriePedido;
            String status;
            String subSeriePedido;
            String valorTotal;
            String observacao;

            String sortedBy = Pedidos.PEDIDOS_DATAPEDIDO + " ASC";

            List<Pedido> entries = new ArrayList<Pedido>();

            mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PEDIDOS, projection, selection, null, sortedBy);
            if (mCursor != null) {
                if (mCursor.moveToFirst()) {
                    do {
                        numeroPedido = mCursor.getLong(mCursor.getColumnIndex(Pedidos.PEDIDOS_ID));
                        tipoPedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_TIPO_PEDIDO));
                        codigoCliente = mCursor.getInt(mCursor.getColumnIndex(Pedidos.PEDIDOS_CODIGOCLIENTE));
                        codigoFormaPagamento = mCursor.getInt(mCursor.getColumnIndex(Pedidos.PEDIDOS_FORMAPAGAMENTO));
                        codigoPrazoPagamento = mCursor.getInt(mCursor.getColumnIndex(Pedidos.PEDIDOS_PRAZOPAGAMENTO));
                        codigoVendedor = mCursor.getInt(mCursor.getColumnIndex(Pedidos.PEDIDOS_CODIGOVENDEDOR));
                        dataPedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_DATAPEDIDO));
                        horaPedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_HORAPEDIDO));
                        origem = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_ORIGEM));
                        observacao = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_OBSERVACAO));
                        seriePedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_SERIE));
                        status = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_STATUS));
                        subSeriePedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_SUBSERIE));
                        valorTotal = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_VALORTOTAL));

                        BigDecimal novoValorTotal = new BigDecimal(valorTotal);

                        Pedido pedido = new Pedido(null, numeroPedido, tipoPedido, seriePedido,
                                subSeriePedido, dataPedido, horaPedido, codigoCliente,
                                codigoVendedor, codigoFormaPagamento, codigoPrazoPagamento,
                                Long.valueOf(codigoEmpresa), novoValorTotal, origem, status, observacao);

                        // Set the username and password for creating a Basic Auth request
                        HttpAuthentication authHeader = new HttpBasicAuthentication("usesoft", "uses0ft10!");
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAuthorization(authHeader);
                        requestHeaders.setContentType(MediaType.valueOf("application/json"));
                        HttpEntity<Pedido> requestEntity = new HttpEntity<Pedido>(requestHeaders);
                        // End of Set the username and password for creating a Basic Auth request

                        // ENVIA O CORPO DO PEDIDO
                        try {
                            RestTemplate restTemplate = new RestTemplate();
                            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                            //location = restTemplate.postForLocation(url_pedido, pedido, Pedido.class);
                            //URI location = restTemplate.postForLocation(url_pedido, pedido, requestEntity);

                            URI location = restTemplate.postForLocation(url_pedido, pedido, requestEntity);

                            url_pedidoItem = location+"/pedidositens";

                        } catch (HttpClientErrorException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                        }
                        // FIM ENVIA O CORPO DO PEDIDO

                        entries.add(pedido);

                        // ENVIA OS ITENS DO PEDIDO AQUI MESMO
                        String[] projection1 = {PedidosItens.PEDIDOSITENS_ID,
                                PedidosItens.PEDIDOSITENS_ID_PEDIDO,
                                PedidosItens.PEDIDOSITENS_CODIGO_PRODUTO,
                                PedidosItens.PEDIDOSITENS_PRECO_UNITARIO,
                                PedidosItens.PEDIDOSITENS_QUANTIDADE,
                                PedidosItens.PEDIDOSITENS_PERCENTUAL_DESCONTO};

                        String selection1 = PedidosItens.PEDIDOSITENS_ID_PEDIDO + " = " +
                                numeroPedido;

                        //Long codigoEmpresa;
                        //Long numeroPedido;
                        String codigoProduto;
                        String quantidade;
                        String precoUnitario;
                        String percentualDesconto;

                        String sortedBy1 = PedidosItens.PEDIDOSITENS_ID + " ASC";

                        List<PedidoItem> entries1 = new ArrayList<PedidoItem>();

                        mCursor1 = mContentResolver.query(UsesalesContract
                                        .URI_TABLE_PEDIDOSITENS, projection1,
                                selection1, null, sortedBy1);
                        if (mCursor1 != null) {
                            if (mCursor1.moveToFirst()) {
                                do {
                                    codigoProduto = mCursor1.getString(mCursor1.getColumnIndex
                                            (PedidosItens.PEDIDOSITENS_CODIGO_PRODUTO));
                                    quantidade = mCursor1.getString(mCursor1.getColumnIndex(PedidosItens.PEDIDOSITENS_QUANTIDADE));
                                    precoUnitario = mCursor1.getString(mCursor1.getColumnIndex(PedidosItens.PEDIDOSITENS_PRECO_UNITARIO));
                                    percentualDesconto = mCursor1.getString(mCursor1.getColumnIndex(PedidosItens.PEDIDOSITENS_PERCENTUAL_DESCONTO));

                                    BigDecimal bdQuantidade = new BigDecimal(quantidade);
                                    BigDecimal bdPrecoUnitario = new BigDecimal(precoUnitario);
                                    BigDecimal bdPercentualDesconto = new BigDecimal(percentualDesconto);

                                    PedidoItem pedidoItem = new PedidoItem(null, Long.valueOf(codigoEmpresa),
                                             codigoProduto, bdQuantidade, bdPrecoUnitario, bdPercentualDesconto);

                                    entries1.add(pedidoItem);

                                    // Set the username and password for creating a Basic Auth request
                                    HttpAuthentication authHeader1 = new HttpBasicAuthentication("usesoft", "uses0ft10!");
                                    HttpHeaders requestHeaders1 = new HttpHeaders();
                                    requestHeaders1.setAuthorization(authHeader);
                                    requestHeaders1.setContentType(MediaType.valueOf("application/json"));
                                    HttpEntity<PedidoItem> requestEntity1 = new HttpEntity<PedidoItem>(requestHeaders1);
                                    // End of Set the username and password for creating a Basic Auth request

                                    // ENVIA OS ITENS DO PEDIDO
                                    try {
                                        RestTemplate restTemplate1 = new RestTemplate();
                                        restTemplate1.getMessageConverters().add(new
                                                MappingJackson2HttpMessageConverter());
                                        restTemplate1.getMessageConverters().add(new
                                                StringHttpMessageConverter());
                                        // TODO Trocar o numeroPedido Pelo numero gerado na API
                                        //url_pedidoItem = location+"/"+"pedidositens";
                                        restTemplate1.postForLocation(url_pedidoItem, pedidoItem,
                                                PedidoItem.class);

                                    } catch (HttpClientErrorException e) {
                                        Log.e(TAG, e.getLocalizedMessage(), e);
                                    }
                                    // ENVIA OS ITENS DO PEDIDO


                                } while (mCursor1.moveToNext());
                            }

                        }

                        // FIM DO ENVIA OS ITENS DO PEDIDO AQUI MESMO

                        // todo Ler os itens do web service que acabaram de ser enviados, para
                        // todo saber se todos os que foram enviados chegaram corretamente ao destino


                    } while (mCursor.moveToNext());

                } else {
                    // TODO
                }

            }
            return entries;

        }

        @Override
        protected void onPostExecute(List<Pedido> pedido) {

            if (dialog != null)
            {
                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            if (pedido != null) {

                Toast.makeText(EnviarPedidosActivity.this, "Pedidos numero " + iNumeroPedido +
                        " enviado com sucesso", Toast.LENGTH_SHORT).show();
                // Muda o status do Pedido para enviado
                ContentValues values = new ContentValues();
                values.put(PedidosColumns.PEDIDOS_STATUS, "enviado");
                Uri uri = Pedidos.buildPedidoUri(iNumeroPedido);
                int recordsUpdated = mContentResolver.update(uri, values, null, null);
                // Direciona a atividade para a lista de Pedidos
                Intent intent = new Intent(EnviarPedidosActivity.this, PedidosListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }
    }

}






