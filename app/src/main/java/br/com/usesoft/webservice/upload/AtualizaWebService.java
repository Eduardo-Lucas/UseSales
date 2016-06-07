package br.com.usesoft.webservice.upload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import br.com.usesoft.usesales.Cliente;
import br.com.usesoft.usesales.Empresa;
import br.com.usesoft.usesales.FormasPagamento;
import br.com.usesoft.usesales.Pedido;
import br.com.usesoft.usesales.PedidoItem;
import br.com.usesoft.usesales.PrazosPagamento;
import br.com.usesoft.usesales.Produto;
import br.com.usesoft.usesales.R;
import br.com.usesoft.usesales.Usuario;

public class AtualizaWebService extends Activity {

    public static final String TAG = AtualizaWebService.class.getSimpleName();

    String url0 = "http://191.251.199.232/empresas";
    String url1 = "http://191.251.199.232/formaspagamentos";
    String url2 = "http://191.251.199.232/prazospagamentos";
    String url3 = "http://191.251.199.232/clientes";
    String url4 = "http://191.251.199.232/produtos";
    String url5 = "http://191.251.199.232/usuarios";
    String url6 = "http://191.251.199.232/pedidos";
    String url7 = "http://191.251.199.232/pedidositens";

    ProgressDialog dialog0, dialog1, dialog2, dialog3, dialog4, dialog5, dialog6, dialog7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_web_service);


        Button iniciaProcesso = (Button) findViewById(R.id.btnProcessaUpload);
        iniciaProcesso.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verifica se há conexão: Wi-Fi ou 3G
                if (!isOnline()) {
                    Toast.makeText(AtualizaWebService.this, "Dispositivo sem Conexão com a Internet.",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(AtualizaWebService.this, "Dispositivo sem Conexão com a Internet.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                taskEmpresas();
                taskFormasPagamentos();
                taskPrazosPagamentos();
                taskClientes();
                taskProdutos();
                taskUsuarios();
                taskPedidos();
                taskPedidosItens();

            }
        });


    }

    public AtualizaWebService taskEmpresas() {
        // Grava Empresa no Web Service
        new PostEmpresasTask().execute();
        return null;
    }

    public AtualizaWebService taskFormasPagamentos() {
        // Grava Forma de Pagamento no Web Service
        new PostFormasPagamentosTask().execute();
        return null;
    }

    public AtualizaWebService taskPrazosPagamentos() {
        // Grava Prazo de Pagamento no Web Service
        new PostPrazosPagamentosTask().execute();
        return null;
    }

    public AtualizaWebService taskClientes() {
        // Grava Cliente no Web Service
        new PostClientesTask().execute();
        return null;
    }

    public AtualizaWebService taskProdutos() {
        // Grava Produto no Web Service
        new PostProdutosTask().execute();
        return null;
    }

    public AtualizaWebService taskUsuarios() {
        // Grava Usuarios no Web Service
        new PostUsuariosTask().execute();
        return null;
    }

    public AtualizaWebService taskPedidos() {
        // Grava Pedidos no Web Service
        new PostPedidosTask().execute();
        return null;
    }

    public AtualizaWebService taskPedidosItens() {
        // Grava Pedidos Itens no Web Service
        new PostPedidosItensTask().execute();
        return null;
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // Task para gravar os Empresas
    private class PostEmpresasTask extends AsyncTask<Void, Void, Empresa> {


        @Override
        protected void onPreExecute() {
            dialog0 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela de Empresas...", false, true);
        }

        @Override
        protected Empresa doInBackground(Void... params) {

            Empresa empresa = new Empresa(null, "true", "87490275000139", 100, "SPRING FOR ANDROID");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url0, empresa, Empresa.class);

            return empresa;
        }

        @Override
        protected void onPostExecute(Empresa empresa) {
            if (empresa != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Empresa: " + empresa.getNome()
                        , Toast.LENGTH_SHORT).show();
            }
            dialog0.dismiss();
        }
    }

    // Task para gravar Formas de Pagamentos
    private class PostFormasPagamentosTask extends AsyncTask<Void, Void, FormasPagamento> {

        @Override
        protected void onPreExecute() {
            dialog1 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela Formas de Pagamentos...", false, true);
        }

        @Override
        protected FormasPagamento doInBackground(Void... params) {

            FormasPagamento formasPagamento = new FormasPagamento(null, 100,
                    "TESTE INCLUSAO FORMA PAGAMENTO", Long.valueOf(1));

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url1, formasPagamento, FormasPagamento.class);

            return formasPagamento;
        }

        @Override
        protected void onPostExecute(FormasPagamento formasPagamento) {

            if (formasPagamento != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Forma de Pagamento: " +
                        formasPagamento.getDescricaoPagamento(), Toast.LENGTH_SHORT).show();

            }
            dialog1.dismiss();

        }
    }

    // Task para gravar Prazos de Pagamentos
    private class PostPrazosPagamentosTask extends AsyncTask<Void, Void, PrazosPagamento> {

        @Override
        protected void onPreExecute() {
            dialog2 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela " +
                    "Prazos de Pagamentos...", false, true);
        }

        @Override
        protected PrazosPagamento doInBackground(Void... params) {

            PrazosPagamento prazosPagamento = new PrazosPagamento(null, 100,
                    "TESTE INCLUSAO PRAZO PAGAMENTO", Long.valueOf(1));

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url2, prazosPagamento, PrazosPagamento.class);

            return prazosPagamento;
        }

        @Override
        protected void onPostExecute(PrazosPagamento prazosPagamento) {

            if (prazosPagamento != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Prazo de Pagamento: " +
                        prazosPagamento.getDescricaoPrazoPagamento(), Toast.LENGTH_SHORT).show();

            }
            dialog2.dismiss();

        }
    }

    // Task para gravar Clientes
    private class PostClientesTask extends AsyncTask<Void, Void, Cliente> {

        @Override
        protected void onPreExecute() {
            dialog3 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela de Clientes...", false, true);
        }

        @Override
        protected Cliente doInBackground(Void... params) {

            Cliente cliente = new Cliente(null, Long.valueOf(1), 200, "LOPLAST",
                    "LOPLAST", "J", "040756983000458", "ISENTO", "", "",
                    70, "AV DO CONTORNO", "", "NOVA BETANIA", "", 59607041, "BA", "", "",
                    "", 0, "", 0, 0, "", "");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url3, cliente, Cliente.class);

            return cliente;
        }

        @Override
        protected void onPostExecute(Cliente cliente) {

            if (cliente != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Cliente: " +
                        cliente.getRazaoSocial(), Toast.LENGTH_SHORT).show();

            }
            dialog3.dismiss();

        }
    }

    // Task para gravar Produtos
    private class PostProdutosTask extends AsyncTask<Void, Void, Produto> {

        @Override
        protected void onPreExecute() {
            dialog4 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela de Produtos...", false, true);
        }

        @Override
        protected Produto doInBackground(Void... params) {


            Produto produto = new Produto(null, Long.valueOf(1), "0001", "",
                    "CONCHA CABO DE MADEIRA TEC-LAR", "TEC-LAR", BigDecimal.valueOf(2.16),
                    BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00), BigDecimal.valueOf(32.00)
                    , "U  1 X 1", "UN",
                    "");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url4, produto, Produto.class);

            return produto;
        }

        @Override
        protected void onPostExecute(Produto produto) {

            if (produto != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Produto: " +
                        produto.getDescricao(), Toast.LENGTH_SHORT).show();

            }
            dialog4.dismiss();

        }
    }

    // Task para gravar Usuarios
    private class PostUsuariosTask extends AsyncTask<Void, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            dialog5 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela de Usuários...", false, true);
        }

        @Override
        protected Usuario doInBackground(Void... params) {


            Usuario usuario = new Usuario(null, "true", 70, Long.valueOf(1),
                    "eduardolucas40@gmail.com", "EDUARDO LUCAS", "123");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url5, usuario, Usuario.class);

            return usuario;
        }

        @Override
        protected void onPostExecute(Usuario usuario) {

            if (usuario != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Usuário: " +
                        usuario.getNomeCompleto(), Toast.LENGTH_SHORT).show();

            }
            dialog5.dismiss();

        }
    }

    // Task para gravar Pedidos
    private class PostPedidosTask extends AsyncTask<Void, Void, Pedido> {

        @Override
        protected void onPreExecute() {
            dialog6 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela de Pedidos...", false, true);
        }

        @Override
        protected Pedido doInBackground(Void... params) {


            Pedido pedido = new Pedido(null, (long) 1000, "PEDIDO", "MAT", "TAB", "04/05/2016",
                    "17:40", 100, 70, 100, 100, Long.valueOf(1), BigDecimal.valueOf(1520.25),
                    "T", "D", "");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url6, pedido, Pedido.class);

            return pedido;
        }

        @Override
        protected void onPostExecute(Pedido pedido) {

            if (pedido != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Pedido: " +
                        pedido.getCodigoCliente(), Toast.LENGTH_SHORT).show();

            }
            dialog6.dismiss();
        }
    }

    // Task para gravar Pedidos Itens
    private class PostPedidosItensTask extends AsyncTask<Void, Void, PedidoItem> {

        @Override
        protected void onPreExecute() {
            dialog7 = ProgressDialog.show(AtualizaWebService.this, "Aguarde...", "Acessando " +
                    "tabela de Pedidos Itens...", false, true);
        }

        @Override
        protected PedidoItem doInBackground(Void... params) {


            PedidoItem pedidoItem = new PedidoItem(null, Long.valueOf(1), "0001",
                    BigDecimal.valueOf(10.5), BigDecimal.valueOf(123.45),
                    BigDecimal.valueOf(17.24));

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForLocation(url7, pedidoItem, PedidoItem.class);

            return pedidoItem;
        }

        @Override
        protected void onPostExecute(PedidoItem pedidoItem) {

            if (pedidoItem != null) {
                Toast.makeText(AtualizaWebService.this, "Novo recurso Item do Pedido adicionado " ,
                        Toast.LENGTH_SHORT).show();

            }
            dialog7.dismiss();

        }
    }


}
