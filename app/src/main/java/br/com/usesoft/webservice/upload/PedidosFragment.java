package br.com.usesoft.webservice.upload;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import br.com.usesoft.usesales.Pedido;
import br.com.usesoft.usesales.UsesalesContract;
import br.com.usesoft.usesales.UsesalesContract.Pedidos;

/**
 * Created by Eduardo Lucas on 26/04/2016.
 */
public class PedidosFragment extends FragmentActivity {

    public static final String TAG = PedidosFragment.class.getSimpleName();

    // Parametros vindos do chamador
    int codigoEmpresa;
    int codigoVendedor;
    String dataInicial;
    String dataFinal;
    Cursor mCursor;

    String url_pedido = "http://191.251.199.232/pedidos";
    String url_pedido_Item = "http://191.251.199.232/pedidositens";

    private ContentResolver mContentResolver;

    public PedidosFragment(int codigoEmpresa, int codigoVendedor, String dataInicial, String
            dataFinal) {
        this.codigoEmpresa = codigoEmpresa;
        this.codigoVendedor = codigoVendedor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public PedidosFragment taskPedidos() {
        // Grava Pedidos no Web Service
        new PostPedidosTask().execute();
        return null;
    }


    public Pedido geraCorpoPedido() {

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
                Pedidos.PEDIDOS_VALORTOTAL};

        String selection = Pedidos.PEDIDOS_DATAPEDIDO + " BETWEEN '" +
                dataInicial + "' AND '" + dataFinal + "' AND " +
                Pedidos.PEDIDOS_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + Pedidos.PEDIDOS_CODIGOVENDEDOR +
                " = '" + codigoVendedor + "'";

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
        BigDecimal valorTotal;


        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PEDIDOS, projection, selection, null, null);
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
                    seriePedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_SERIE));
                    status = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_STATUS));
                    subSeriePedido = mCursor.getString(mCursor.getColumnIndex(Pedidos.PEDIDOS_SUBSERIE));
                    valorTotal = BigDecimal.valueOf(new Double(mCursor.getDouble(mCursor
                            .getColumnIndex(Pedidos.PEDIDOS_VALORTOTAL))));

                    Pedido pedido = new Pedido(null, numeroPedido, tipoPedido, seriePedido,
                            subSeriePedido, dataPedido, horaPedido, codigoCliente, codigoVendedor,
                            codigoFormaPagamento, codigoPrazoPagamento, Long.valueOf(codigoEmpresa),
                            valorTotal, origem, status, "");

                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    restTemplate.postForLocation(url_pedido, pedido, Pedido.class);

                    return pedido;

                } while (mCursor.moveToNext());


            } else {
                // TODO
            }
        }

        return null;
    }

    // Task para gravar os Pedidos
    private class PostPedidosTask extends AsyncTask<Void, Void, Pedido> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(PedidosFragment.this, "Aguarde", "Enviando Pedidos...",
                    false, true);
        }

        @Override
        protected Pedido doInBackground(Void... params) {

            geraCorpoPedido();

            return null;
        }

        @Override
        protected void onPostExecute(Pedido pedido) {
            dialog.dismiss();
        }
    }

}
