package br.com.usesoft.usesales;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.usesoft.usesales.UsesalesContract.ClientesColumns;
import br.com.usesoft.usesales.UsesalesContract.FormasPagamentoColumns;
import br.com.usesoft.usesales.UsesalesContract.PrazosPagamentoColumns;


/**
 * Created by Eduardo Lucas on 09/12/2015.
 */
public class EditPedidoActivity extends FragmentActivity
        implements OnClickListener, TextWatcher,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = EditPedidoActivity.class.getSimpleName();
    // Init Static Members
    static int MAX_COUNT = 256;
    private static FragmentManager sFragmentManager;
    Cursor mCursor;
    private RadioGroup mTipoPedido;
    private RadioButton mPedido;
    private RadioButton mPrePedido;
    private RadioButton itemMarcado;
    private ContentResolver mContentResolver;
    // Campos na Tela
    private TextView mCodigoFormaDePagamento, mCodigoPrazoDePagamento, mValorTotalPedido, mObservacao, mContador;
    private Spinner tipoPed;  // Tipo de Pedido: Pré ou Pedido
    // Variaveis Data e Hora do Pedido
    private String dataPedido, horaPedido;

    // Campos Globais
    private TextView mCodigoEmpresa, mNomeEmpresa, mCodigoUsuario, mNomeUsuario,
            mCodigoCliente;

    // Campos AutoComplete
    // O campo nomeCliente deixou de ser AutoComplete pois, com o codigo informado, aparece o nome.
    private SimpleCursorAdapter mAdapter0; // (AutoCompleteTextView) Clientes
    private SimpleCursorAdapter mAdapter1; // (AutoCompleteTextView) Formas de Pagamento
    private SimpleCursorAdapter mAdapter2; // (AutoCompleteTextView) Prazos de Pagamento


    private AutoCompleteTextView mDescricaoFormaDePagamento, mDescricaoPrazoDePagamento, mNomeCliente;
    private SimpleDateFormat dateFormatter, timeFormatter;
    private DatePickerDialog dataPedidoPickerDialog;
    private Button btnSavePedido, btnAddItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pedidos);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final String nomeEmpresa = globalVariables.getNomeEmpresa();
        final int codigoUsuario = globalVariables.getUsuario_id();
        final String nomeUsuario = globalVariables.getNomeUsuario();


        sFragmentManager = getSupportFragmentManager();

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        timeFormatter = new SimpleDateFormat("hh:mm:ss");

        mCodigoEmpresa = (TextView) findViewById(R.id.codigoEmpresa_tv);
        mNomeEmpresa = (TextView) findViewById(R.id.nomeEmpresa_tv);

        mCodigoUsuario = (TextView) findViewById(R.id.codigoRepresentante_tv);
        mNomeUsuario = (TextView) findViewById(R.id.nomeRepresentante_tv);
        mCodigoCliente = (TextView) findViewById(R.id.codigoCliente_tv);
        mCodigoFormaDePagamento = (TextView) findViewById(R.id.codigoFormaPagamento_tv);
        mCodigoPrazoDePagamento = (TextView) findViewById(R.id.codigoPrazoPagamento_tv);
        mObservacao = (TextView) findViewById(R.id.observacao_tv);
        mContador = (TextView) findViewById((R.id.contadorLabel));
        mContador.setText(Integer.toString(MAX_COUNT));

        Intent intent = getIntent();
        String iCodigoCliente = intent.getStringExtra(UsesalesContract.Pedidos.PEDIDOS_CODIGOCLIENTE);
        String iCodigoFormaPagamento = intent.getStringExtra(UsesalesContract.Pedidos.PEDIDOS_FORMAPAGAMENTO);
        String iCodigoPrazoPagamento = intent.getStringExtra(UsesalesContract.Pedidos.PEDIDOS_PRAZOPAGAMENTO);

        // Attached Listener to Edit Text Widget
        mObservacao.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {

                // Display Remaining Character with respective color
                int count = MAX_COUNT - s.length();
                mContador.setText(Integer.toString(count));
                mContador.setTextColor(Color.GREEN);
                if (count < 10) {
                    mContador.setTextColor(Color.YELLOW);
                }
                if (count < 0) {
                    mContador.setTextColor(Color.RED);
                }

            }
        });


        mCodigoEmpresa.setText(String.valueOf(codigoEmpresa));
        mNomeEmpresa.setText(nomeEmpresa);
        mCodigoUsuario.setText(String.valueOf(codigoUsuario));
        mNomeUsuario.setText(nomeUsuario);

//        // DESLIGADO
//        // INÍCIO - Pega as informaçoes do registro clicado da lista de Clientes = listView
//        Intent intent = getIntent();
//        int iCodigoCliente = intent.getIntExtra(UsesalesContract.ClientesColumns
//                .CLIENTES_CODIGO, 0);
//        String iCodigoFormaPagamento = intent.getStringExtra(FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO);
//
//        if (iCodigoCliente > 0) { // Nesse caso, veio clicado da lista de Clientes
//            mCodigoCliente.setText(String.valueOf(iCodigoCliente));
//            //mNomeCliente.setText(iRazaoSocialCliente);
//            // Muda o cursor para o campo a seguir
//            mCodigoFormaDePagamento.requestFocus();
//        }
//        // FIM - Pega as informaçoes que vieram da lista de Clientes clicadas = listView
//
//
//        // INÍCIO - Pega as informaçoes do registro clicado da lista de FORMAS DE PAGAMENTOS
//
//
//        if (iCodigoFormaPagamento != null) { // Nesse caso, veio clicado da lista de Clientes
//            mCodigoFormaDePagamento.setText(iCodigoFormaPagamento);
//            //mNomeCliente.setText(iRazaoSocialCliente);
//            // Muda o cursor para o campo a seguir
//            mCodigoPrazoDePagamento.requestFocus();
//        }
        // FIM - Pega as informaçoes que vieram da lista de Clientes clicadas = listView

        mTipoPedido = (RadioGroup) findViewById(R.id.tipoPedido_rg);
        mPedido = (RadioButton) findViewById(R.id.radio0);
        mPrePedido = (RadioButton) findViewById(R.id.radio1);

        mTipoPedido.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // get selected radio button from radioGroup
                int selectedId = mTipoPedido.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                itemMarcado = (RadioButton) findViewById(selectedId);

                Toast.makeText(EditPedidoActivity.this, "Tipo: " +
                        itemMarcado.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
             CLIQUE LONGO NOS CAMPOS:
             - CODIGO CLIENTE
             - CODIGO FORMA DE PAGAMENTO
             - CODIGO PRAZO DE PAGAMENTO
         */
        mCodigoCliente.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(EditPedidoActivity.this, ClientListActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        });

        mCodigoFormaDePagamento.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(EditPedidoActivity.this, FormasPagamentoListActivity
                        .class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        });

        mCodigoPrazoDePagamento.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(EditPedidoActivity.this, PrazosPagamentoListActivity
                        .class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        });


        mCodigoCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCodigoCliente.getText().toString() != null) {
                    mNomeCliente.setText(pegaNomeCliente());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mNomeCliente.getText().toString().equals("Esse código NÃO existe") ||
                        mNomeCliente.getText().toString().isEmpty() || mNomeCliente.getText()
                        .toString() == null) {
                    mCodigoCliente.requestFocus();
                } else {
                    mCodigoFormaDePagamento.requestFocus();
                }

            }
        });


        mCodigoFormaDePagamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCodigoFormaDePagamento.getText().toString() != null) {
                    mDescricaoFormaDePagamento.setText(pegaNomeFormaPagamento());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mDescricaoFormaDePagamento.getText().toString().equals("Esse código NÃO " +
                        "existe") ||
                        mDescricaoFormaDePagamento.getText().toString().isEmpty() || mDescricaoFormaDePagamento.getText()
                        .toString() == null) {
                    mCodigoFormaDePagamento.requestFocus();
                } else {
                    mCodigoPrazoDePagamento.requestFocus();
                }
            }
        });

        mCodigoPrazoDePagamento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCodigoPrazoDePagamento.getText().toString() != null) {
                    mDescricaoPrazoDePagamento.setText(pegaNomePrazoPagamento());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mDescricaoPrazoDePagamento.getText().toString().equals("Esse código NÃO " +
                        "existe") ||
                        mDescricaoPrazoDePagamento.getText().toString().isEmpty() ||
                        mDescricaoPrazoDePagamento.getText().toString() == null) {
                    mCodigoPrazoDePagamento.requestFocus();
                    //btnAddItems.setEnabled(false);

                } else {
                    //btnAddItems.setEnabled(true);
                }


            }
        });

        SetNomeCliente();     // AutoCompleteTextView

        SetFormaPagamento(); // AutoCompleteTextView

        SetPrazoPagamento(); // AutoCompleteTextView


        // LISTA O CONTEUDO DA TABELA DE CLIENTES
        mNomeCliente.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mNomeCliente.setText("%");
                return false;
            }
        });

        // LISTA O CONTEUDO DA TABELA DE FORMAS DE PAGAMENTO
        mDescricaoFormaDePagamento.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDescricaoFormaDePagamento.setText("%");
                return false;
            }
        });

        // LISTA O CONTEUDO DA TABELA DE PRAZO DE PAGAMENTO
        mDescricaoPrazoDePagamento.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDescricaoPrazoDePagamento.setText("%");
                return false;
            }
        });


        mContentResolver = EditPedidoActivity.this.getContentResolver();

        btnAddItems = (Button) findViewById(R.id.btnAdicionarItens);

        btnAddItems.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCodigos()) {
                    Intent intent = new Intent(EditPedidoActivity.this, AddPedidoItensActivity.class);
                    intent.putExtra(UsesalesContract.PedidosColumns.PEDIDOS_TIPO_PEDIDO,
                            itemMarcado.getText().toString());
                    intent.putExtra(ClientesColumns.CLIENTES_CODIGO, mCodigoCliente.getText().toString());
                    intent.putExtra(ClientesColumns.CLIENTES_RAZAO_SOCIAL, mNomeCliente.getText().toString());
                    intent.putExtra(UsesalesContract.PedidosColumns.PEDIDOS_FORMAPAGAMENTO, mCodigoFormaDePagamento.getText().toString());
                    intent.putExtra(UsesalesContract.PedidosColumns.PEDIDOS_PRAZOPAGAMENTO, mCodigoPrazoDePagamento.getText().toString());
                    intent.putExtra(UsesalesContract.PedidosColumns.PEDIDOS_OBSERVACAO, mObservacao.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean validaCodigos() {
        boolean result = false;
        // get selected radio button from radioGroup
        int selectedId = mTipoPedido.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        itemMarcado = (RadioButton) findViewById(selectedId);

        if (mNomeCliente.getText().toString() != null && mNomeCliente.getText().toString()
                .length() != 0) {
            mCodigoCliente.setText(String.valueOf(pegaCodigoCliente()));
            if (mCodigoCliente.getText().toString().equals('0')) {
                Toast.makeText(getApplicationContext(), "Esse código NÃO existe!", Toast.LENGTH_SHORT).show();
                result = false;
            } else {
                result = true;
            }
        } else {
            mNomeCliente.setText(pegaNomeCliente());
            if (mNomeCliente.getText().toString().equals("Esse código NÃO existe!")) {
                Toast.makeText(getApplicationContext(), "Escolha um código da Lista", Toast.LENGTH_SHORT).show();
                result = false;
            } else {
                result = true;
            }
        }

        if (mDescricaoFormaDePagamento.getText().toString() != null && mDescricaoFormaDePagamento
                .getText().toString().length() != 0) {
            mCodigoFormaDePagamento.setText(String.valueOf(pegaCodigoFormasPagamento()));
        } else {
            mDescricaoFormaDePagamento.setText(pegaNomeFormaPagamento());
        }

        if (mDescricaoPrazoDePagamento.getText().toString() != null &&
                mDescricaoPrazoDePagamento.getText().toString().length() != 0) {
            mCodigoPrazoDePagamento.setText(String.valueOf(pegaCodigoPrazosPagamento()));
        } else {
            mDescricaoPrazoDePagamento.setText(pegaNomePrazoPagamento());
        }

        return result;
    }


    private boolean someDataEntered() {
        return mCodigoCliente.getText().toString().length() > 0 ||
                mCodigoCliente.getText().toString() != null ||
                mCodigoFormaDePagamento.getText().toString().length() > 0 ||
                mCodigoFormaDePagamento.getText().toString() != null ||
                mCodigoPrazoDePagamento.getText().toString().length() > 0 ||
                mCodigoPrazoDePagamento.getText().toString() != null;
    }

    @Override
    public void onBackPressed() {
        if (someDataEntered()) {
            PedidosDialog dialog = new PedidosDialog();
            Bundle args = new Bundle();
            args.putString(PedidosDialog.DIALOG_TYPE, PedidosDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        } else {
            super.onBackPressed();
        }
    }

    private void setDateTimePedido() {

        Calendar newDate = Calendar.getInstance();
        String dataPedido = dateFormatter.format(newDate.getTime());
        String horaPedido = timeFormatter.format(newDate.getTime());

        System.out.println("Data do Pedido: " + dataPedido);
        System.out.println("Hora do Pedido: " + horaPedido);

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 0: // (AutoCompleteTextView) Clientes
                mAdapter0.swapCursor(data);
                break;
            case 1: // (AutoCompleteTextView) Formas de Pagamento
                mAdapter1.swapCursor(data);
                break;
            case 2: // (AutoCompleteTextView) Formas de Pagamento
                mAdapter2.swapCursor(data);
                break;
            default:
                throw new IllegalArgumentException("Unknow loader" + loader.getId());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final int usuarioId = globalVariables.getUsuario_id();

        String[] projection;
        String selection;
        String sortBy;
        CursorLoader cursorLoader;

        switch (id) {
            case 0:
                projection = new String[]{ClientesColumns.CLIENTES_ID,
                        ClientesColumns.CLIENTES_CODIGO,
                        ClientesColumns.CLIENTES_RAZAO_SOCIAL};
                selection = ClientesColumns.CLIENTES_CODIGO_EMPRESA + " = " +
                        codigoEmpresa +
                        " AND " + ClientesColumns.CLIENTES_VENDEDOR + " = " + usuarioId;
                sortBy = "_id DESC";
                cursorLoader = new CursorLoader(this, UsesalesContract.URI_TABLE_CLIENTES, projection, selection, null, sortBy);
                return cursorLoader;
            case 1:
                projection = new String[]{FormasPagamentoColumns.FORMASPAGAMENTO_ID,
                        FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO,
                        FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO};
                selection = FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO_EMPRESA + " = " + codigoEmpresa;
                sortBy = "_id DESC";
                cursorLoader = new CursorLoader(this, UsesalesContract.URI_TABLE_FORMASPAGAMENTO, projection, selection, null, sortBy);
                return cursorLoader;
            case 2:
                projection = new String[]{PrazosPagamentoColumns.PRAZOSPAGAMENTO_ID,
                        PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO,
                        PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO};
                selection = PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO_EMPRESA + " = " + codigoEmpresa;
                sortBy = "_id DESC";
                cursorLoader = new CursorLoader(this, UsesalesContract.URI_TABLE_PRAZOSPAGAMENTOS, projection, selection, null, sortBy);
                return cursorLoader;
            default:
                throw new IllegalArgumentException("Unknow id" + id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case 0: // (AutoCompleteTextView) Clientes
                mAdapter0.swapCursor(null);
                break;
            case 1: // (AutoCompleteTextView) Formas de Pagamento
                mAdapter1.swapCursor(null);
                break;
            case 2: // (AutoCompleteTextView) Prazos de Pagamento
                mAdapter2.swapCursor(null);
                break;
            default:
                throw new IllegalArgumentException("Unknow id" + loader.getId());
        }
    }

    Cursor getClientes(String partialName, int codigoEmpresa, int usuarioId) {
        // Look for partialName either in display name (person name) or in email
        final String filter =
                ClientesColumns.CLIENTES_RAZAO_SOCIAL + " LIKE '" + partialName + "%' AND " +
                        ClientesColumns.CLIENTES_CODIGO_EMPRESA + " = " +
                        codigoEmpresa +
                        " AND " + ClientesColumns.CLIENTES_VENDEDOR + " = " +
                        usuarioId;
        String[] PROJECTION = new String[]{ClientesColumns.CLIENTES_RAZAO_SOCIAL,
                ClientesColumns.CLIENTES_CODIGO, ClientesColumns.CLIENTES_ID};

        String sortOrder = ClientesColumns.CLIENTES_RAZAO_SOCIAL + " ASC";

        // Now make a Cursor containing the contacts that now match partialName as per "filter".
        return mContentResolver.query(UsesalesContract.URI_TABLE_CLIENTES, PROJECTION, filter, null, sortOrder);

    }

    Cursor getFormasPagamento(String partialName, int codigoEmpresa) {
        // Look for partialName either in display name (person name) or in email
        final String filter =
                FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO + " LIKE '" + partialName + "%' AND " +
                        FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO_EMPRESA + " = " + codigoEmpresa;
        String[] PROJECTION = new String[]{FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO,
                FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO,
                FormasPagamentoColumns.FORMASPAGAMENTO_ID};
        String sortOrder = FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO + " ASC";
        // Now make a Cursor containing the contacts that now match partialName as per "filter".
        return mContentResolver.query(UsesalesContract.URI_TABLE_FORMASPAGAMENTO, PROJECTION, filter, null, sortOrder);
    }

    Cursor getPrazosPagamento(String partialName, int codigoEmpresa) {
        // Look for partialName either in display name (person name) or in email
        final String filter =
                PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO + " LIKE '" + partialName + "%' AND " +
                        PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO_EMPRESA + " = " + codigoEmpresa;
        String[] PROJECTION = new String[]{PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO,
                PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO,
                PrazosPagamentoColumns.PRAZOSPAGAMENTO_ID};
        String sortOrder = PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO + " ASC";
        // Now make a Cursor containing the contacts that now match partialName as per "filter".
        return mContentResolver.query(UsesalesContract.URI_TABLE_PRAZOSPAGAMENTOS, PROJECTION, filter, null, sortOrder);
    }


    private int pegaCodigoCliente() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final int usuarioId = globalVariables.getUsuario_id();

        String[] projection = {ClientesColumns.CLIENTES_RAZAO_SOCIAL,
                ClientesColumns.CLIENTES_CODIGO};

        String selection = ClientesColumns.CLIENTES_CODIGO_EMPRESA + " = " +
                codigoEmpresa +
                " AND " + ClientesColumns.CLIENTES_VENDEDOR + " = " + usuarioId + " AND " +
                ClientesColumns.CLIENTES_RAZAO_SOCIAL + " = '" + mNomeCliente.getText().toString()
                + "'";

        int codigoCliente = 0;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_CLIENTES, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String nomeCliente = mCursor.getString(mCursor.getColumnIndex(ClientesColumns.CLIENTES_RAZAO_SOCIAL));
                    if (nomeCliente.equals(mNomeCliente.getText().toString())) {
                        codigoCliente = mCursor.getInt(mCursor.getColumnIndex(ClientesColumns.CLIENTES_CODIGO));
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                codigoCliente = 0;
            }
        }
        return codigoCliente;
    }

    private String pegaNomeCliente() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final int usuarioId = globalVariables.getUsuario_id();

        String[] projection = {ClientesColumns.CLIENTES_RAZAO_SOCIAL,
                ClientesColumns.CLIENTES_CODIGO};

        String selection = ClientesColumns.CLIENTES_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + ClientesColumns.CLIENTES_CODIGO +
                " LIKE '" + mCodigoCliente.getText().toString() + "%'" +
                " AND " + ClientesColumns.CLIENTES_VENDEDOR + " = " + usuarioId;

        String codigoCliente = null;
        String nomeCliente = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_CLIENTES, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    codigoCliente = mCursor.getString(mCursor.getColumnIndex(
                            ClientesColumns.CLIENTES_CODIGO));
                    if (codigoCliente.equals(mCodigoCliente.getText().toString())) {
                        nomeCliente = mCursor.getString(mCursor.getColumnIndex(
                                ClientesColumns.CLIENTES_RAZAO_SOCIAL));
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                nomeCliente = "Esse código NÃO existe";
            }
        }
        return nomeCliente;
    }

    private String pegaNomeFormaPagamento() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO,
                FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO};

        String selection = FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + FormasPagamentoColumns
                .FORMASPAGAMENTO_CODIGO + " LIKE '" + mCodigoFormaDePagamento.getText().toString
                () + "%'";

        String codigoFormaPagamento = null;
        String nomeFormaPagamento = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_FORMASPAGAMENTO, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    codigoFormaPagamento = mCursor.getString(mCursor.getColumnIndex(
                            FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO));
                    if (codigoFormaPagamento.equals(mCodigoFormaDePagamento.getText().toString())) {
                        nomeFormaPagamento = mCursor.getString(mCursor.getColumnIndex(
                                FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO));
                        mDescricaoFormaDePagamento.setText(nomeFormaPagamento);
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                nomeFormaPagamento = "Esse código NÃO existe";
            }
        }
        return nomeFormaPagamento;
    }

    private String pegaNomePrazoPagamento() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO,
                PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO};

        String selection = PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO_EMPRESA +
                " = " +
                codigoEmpresa + " AND " + PrazosPagamentoColumns
                .PRAZOSPAGAMENTO_CODIGO + " LIKE '" + mCodigoPrazoDePagamento.getText().toString
                () + "%'";

        String codigoPrazoPagamento = null;
        String nomePrazoPagamento = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PRAZOSPAGAMENTOS, projection,
                selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    codigoPrazoPagamento = mCursor.getString(mCursor.getColumnIndex(
                            PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO));
                    if (codigoPrazoPagamento.equals(mCodigoPrazoDePagamento.getText().toString())) {
                        nomePrazoPagamento = mCursor.getString(mCursor.getColumnIndex(
                                PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO));
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                nomePrazoPagamento = "Esse código NÃO existe";
            }
        }
        return nomePrazoPagamento;
    }


    public void SetNomeCliente() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final int usuarioId = globalVariables.getUsuario_id();

        // INICIO PEGA NOME CLIENTE
        mNomeCliente = (AutoCompleteTextView) findViewById(R.id.nomeCliente_tv);

        String[] projection = new String[]{ClientesColumns.CLIENTES_RAZAO_SOCIAL,
                ClientesColumns.CLIENTES_ID,
                ClientesColumns.CLIENTES_CODIGO};

        int[] target = new int[]{R.id.clienteName, R.id.clienteId, R.id.codigoCliente};

        getLoaderManager().initLoader(0, null, this);
        mAdapter0 = new SimpleCursorAdapter(this, R.layout.clientes_list, null, projection, target, 0);

        mAdapter0.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {

            @Override
            public CharSequence convertToString(Cursor cursor) {
                return cursor.getString(0);
            }
        });

        mAdapter0.setFilterQueryProvider(new FilterQueryProvider() {

            public Cursor runQuery(CharSequence constraint) {
                String partialItemName = null;
                if (constraint != null) {
                    partialItemName = constraint.toString();
                }
                return getClientes(partialItemName, codigoEmpresa, usuarioId);
            }
        });

        mNomeCliente.setAdapter(mAdapter0);
        mNomeCliente.setThreshold(1);

        // FIM PEGA NOME CLIENTE
    }


    public void SetFormaPagamento() {


        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        // INICIO PEGA FORMA PAGAMENTO
        mDescricaoFormaDePagamento = (AutoCompleteTextView) findViewById(R.id.formaPagamento_tv);

        String[] projection = new String[]{FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO,
                FormasPagamentoColumns.FORMASPAGAMENTO_ID};
        int[] target = new int[]{R.id.formaPagamentoDescricao, R.id.formaPagamentoId};
        getLoaderManager().initLoader(1, null, this);
        mAdapter1 = new SimpleCursorAdapter(this, R.layout.formaspagamento_list, null, projection, target, 0);

        mAdapter1.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {

            @Override
            public CharSequence convertToString(Cursor cursor) {
                return cursor.getString(0);
            }
        });

        mAdapter1.setFilterQueryProvider(new FilterQueryProvider() {

            public Cursor runQuery(CharSequence constraint) {
                String partialItemName = null;
                if (constraint != null) {
                    partialItemName = constraint.toString();
                }
                return getFormasPagamento(partialItemName, codigoEmpresa);
            }
        });

        mDescricaoFormaDePagamento.setAdapter(mAdapter1);
        mDescricaoFormaDePagamento.setThreshold(1);

        // FIM PEGA FORMA PAGAMENTO
    }

    public void SetPrazoPagamento() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        //*****************************
        // INICIO PEGA PRAZO PAGAMENTO
        //*****************************
        mDescricaoPrazoDePagamento = (AutoCompleteTextView) findViewById(R.id.prazoPagamento_tv);

        String[] projection = new String[]{PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO,
                PrazosPagamentoColumns.PRAZOSPAGAMENTO_ID};
        int[] target = new int[]{R.id.descricaoPrazoPagamento, R.id.prazoPagamentoId};
        getLoaderManager().initLoader(1, null, this);
        mAdapter2 = new SimpleCursorAdapter(this, R.layout.prazospagamento_list, null, projection, target, 0);

        mAdapter2.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {

            @Override
            public CharSequence convertToString(Cursor cursor) {
                return cursor.getString(0);
            }
        });

        mAdapter2.setFilterQueryProvider(new FilterQueryProvider() {

            public Cursor runQuery(CharSequence constraint) {
                String partialItemName = null;
                if (constraint != null) {
                    partialItemName = constraint.toString();
                }
                return getPrazosPagamento(partialItemName, codigoEmpresa);
            }
        });

        mDescricaoPrazoDePagamento.setAdapter(mAdapter2);
        mDescricaoPrazoDePagamento.setThreshold(1);

        // ++++++++++++++++++++++++++
        // FIM PEGA PRAZO PAGAMENTO
        // ++++++++++++++++++++++++++
    }

    private int pegaCodigoFormasPagamento() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        String[] projection = {FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO,
                FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO};

        String selection = FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO_EMPRESA
                + " = " + codigoEmpresa + " AND " + FormasPagamentoColumns
                .FORMASPAGAMENTO_DESCRICAO + " = '" + mDescricaoFormaDePagamento.getText().toString
                () + "'";

        int codigoFormaPagamento = 0;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_FORMASPAGAMENTO, projection, selection, null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String descricaoFormaPagamento = mCursor.getString(mCursor.getColumnIndex(FormasPagamentoColumns.FORMASPAGAMENTO_DESCRICAO));
                    if (descricaoFormaPagamento.equals(mDescricaoFormaDePagamento.getText().toString())) {
                        codigoFormaPagamento = mCursor.getInt(mCursor.getColumnIndex(FormasPagamentoColumns.FORMASPAGAMENTO_CODIGO));
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                codigoFormaPagamento = 0;
            }
        }
        return codigoFormaPagamento;
    }

    private int pegaCodigoPrazosPagamento() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO,
                PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO};

        String selection = PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO_EMPRESA
                + " = " + codigoEmpresa + " AND " + PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO +
                " = '" + mDescricaoPrazoDePagamento.getText().toString() + "'";

        int codigoPrazoPagamento = 0;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PRAZOSPAGAMENTOS, projection, selection, null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String descricaoPrazoPagamento = mCursor.getString(mCursor.getColumnIndex(PrazosPagamentoColumns.PRAZOSPAGAMENTO_DESCRICAO));
                    if (descricaoPrazoPagamento.equals(mDescricaoPrazoDePagamento.getText()
                            .toString())) {
                        codigoPrazoPagamento = mCursor.getInt(mCursor.getColumnIndex(PrazosPagamentoColumns.PRAZOSPAGAMENTO_CODIGO));
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                codigoPrazoPagamento = 0;
            }
        }
        return codigoPrazoPagamento;
    }

    @Override
    public void afterTextChanged(Editable s) {
        Toast.makeText(getApplicationContext(), "VAlor de s: " + s, Toast.LENGTH_LONG);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Dont care
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Dont care
    }


}
