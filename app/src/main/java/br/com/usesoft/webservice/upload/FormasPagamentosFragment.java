package br.com.usesoft.webservice.upload;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import br.com.usesoft.usesales.FormasPagamento;

/**
 * Created by Eduardo Lucas on 26/04/2016.
 */
public class FormasPagamentosFragment extends FragmentActivity {

    public static final String TAG = FormasPagamentosFragment.class.getSimpleName();

    // Parametros vindos do chamador
    int codigoEmpresa;

    URI response;
    Cursor mCursor;
    String url = "http://192.168.1.201:8080/formaspagamentos";
    private ProgressDialog dialog;
    private ContentResolver mContentResolver;

    public FormasPagamentosFragment(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
    //String url = "http://10.0.3.2:8080/formaspagamentos";   -- LOCALHOST

    public FormasPagamentosFragment taskPedidos() {
        // Grava Pedidos no Web Service
        new PostFormasTask().execute();
        return null;
    }

    public URI geraCorpoFormas() {

        Long codigo = Long.valueOf(1);

        FormasPagamento formasPagamento = new FormasPagamento();
        formasPagamento.setCodigoEmpresa(codigo);
        formasPagamento.setCodigoFormaPagamento(88);
        formasPagamento.setDescricaoPagamento("Teste de Web Service");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        response = restTemplate.postForLocation(url, FormasPagamento.class);

        return response;
    }

    // Task para gravar os Pedidos
    private class PostFormasTask extends AsyncTask<Void, Void, FormasPagamento> {

        @Override
        protected void onPreExecute() {
//            dialog = ProgressDialog.show( getActivity()  , "Processa Pedidos", "Por favor," +
//                    " aguarde...", false, true);
        }

        @Override
        protected FormasPagamento doInBackground(Void... params) {

            geraCorpoFormas();

            return null;
        }

        @Override
        protected void onPostExecute(FormasPagamento formasPagamento) {
            System.out.println("Novo recurso em: " + response);
        }
    }

}
