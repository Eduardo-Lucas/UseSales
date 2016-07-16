package br.com.usesoft.usesales;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.usesoft.usesales.UsesalesContract.ClientesColumns;
import br.com.usesoft.usesales.UsesalesContract.PedidosColumns;
import br.com.usesoft.usesales.UsesalesContract.ProdutosColumns;
import br.com.usesoft.utils.AtualizaEstoque;


/**
 * Created by Eduardo Lucas on 09/12/2015.
 */
public class EditPedidoItensActivity extends Activity
        implements TextWatcher, LoaderCallbacks<Cursor> {

    public static final String TAG = EditPedidoItensActivity.class.getSimpleName();
    final List<PedidoItensTemp> pedidoItens = new ArrayList<PedidoItensTemp>();
    Cursor mCursor;
    ArrayAdapter<PedidosItensTemp> adapter;
    ArrayList<PedidosItensTemp> itemList;
    private AutoCompleteTextView mDescricaoProduto;
    // Variaveis Data e Hora do Pedido
    private String dataPedido, horaPedido;
    private SimpleDateFormat dateFormatter, timeFormatter;
    private Double valorRegistro = 0.00;
    private TextView mCodigoProduto, mNomeProduto, mQuantidade, mPrecoUnitario, mPercentualDesconto,
            mquantidadeItens, mValorTotalPedido, mEstoqueProduto;
    private Button btnAddItems, btnCloseOrder, btnPesquisaCampos;
    private BigDecimal precoFinalAcumulado = BigDecimal.valueOf(0.00);
    private int quantidadeItens = 0;
    // Campos Globais
    private TextView mCodigoEmpresa, mNomeEmpresa, mCodigoUsuario, mNomeUsuario, mCodigoCliente, mNomeCliente;
    // Campos AutoComplete
    // O campo nomeCliente deixou de ser AutoComplete pois, com o codigo informado, aparece o nome.
    private SimpleCursorAdapter mAdapter0; // (AutoCompleteTextView) Produtod
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_pedidositens);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        timeFormatter = new SimpleDateFormat("hh:mm:ss");

        itemList = new ArrayList<PedidosItensTemp>();
        adapter = new ArrayAdapter<PedidosItensTemp>(this, R.layout.pedidositenslist, R.id.detail,
                itemList);
        ListView listV = (ListView) findViewById(R.id.detail);
        listV.setAdapter(adapter);

        listV.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);
                return true;
            }
        });

        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();
        final String nomeEmpresa = globalVariables.getNomeEmpresa();
        final int codigoUsuario = globalVariables.getUsuario_id();
        final String nomeUsuario = globalVariables.getNomeUsuario();


        mCodigoEmpresa = (TextView) findViewById(R.id.codigoEmpresa_tv);
        mNomeEmpresa = (TextView) findViewById(R.id.nomeEmpresa_tv);
        mCodigoUsuario = (TextView) findViewById(R.id.codigoRepresentante_tv);
        mNomeUsuario = (TextView) findViewById(R.id.nomeRepresentante_tv);
        mCodigoCliente = (TextView) findViewById(R.id.codigoCliente_tv);
        mNomeCliente = (TextView) findViewById(R.id.nomeCliente_tv);
        mValorTotalPedido = (TextView) findViewById(R.id.valorTotalPedido_tv);
        mquantidadeItens = (TextView) findViewById(R.id.quantidadeItens_tv);
        mEstoqueProduto = (TextView) findViewById(R.id.estoqueProduto_tv);

        mCodigoEmpresa.setText(String.valueOf(codigoEmpresa));
        mNomeEmpresa.setText(nomeEmpresa);
        mCodigoUsuario.setText(String.valueOf(codigoUsuario));
        mNomeUsuario.setText(nomeUsuario);

        mCodigoProduto = (TextView) findViewById(R.id.codigoProduto_tv);
        mNomeProduto = (TextView) findViewById(R.id.nomeProduto_tv);
        mQuantidade = (TextView) findViewById(R.id.quantidade_tv);
        mPrecoUnitario = (TextView) findViewById(R.id.precoUnitario_tv);
        //mPercentualDesconto = (TextView) findViewById(R.id.percentualDesconto_tv);

        // INÍCIO - Pega as informaçoes do Corpo do Pedido
        Intent intent = getIntent();
        final String iTipoPedido = intent.getStringExtra(PedidosColumns.PEDIDOS_TIPO_PEDIDO);
        final String iCodigoCliente = intent.getStringExtra(ClientesColumns.CLIENTES_CODIGO);
        String iRazaoSocialCliente = intent.getStringExtra(ClientesColumns.CLIENTES_RAZAO_SOCIAL);
        final String iObservacao = intent.getStringExtra(PedidosColumns.PEDIDOS_OBSERVACAO);
        final int iFormaPagamento = Integer.parseInt(intent.getStringExtra(PedidosColumns.PEDIDOS_FORMAPAGAMENTO));
        final int iPrazoPagamento = Integer.parseInt(intent.getStringExtra(PedidosColumns.PEDIDOS_PRAZOPAGAMENTO));


        if (iCodigoCliente != null) {
            mCodigoCliente.setText(iCodigoCliente);
            mNomeCliente.setText(iRazaoSocialCliente);
        }
        // FIM - Pega as informaçoes do Corpo do Pedido


        // LISTA O CONTEUDO DA TELA DE PRODUTOS
        mCodigoProduto.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(EditPedidoItensActivity.this, ProductListActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return false;
            }
        });


        mContentResolver = EditPedidoItensActivity.this.getContentResolver();

        mCodigoProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCodigoProduto.getText().toString() != null) {
                    mNomeProduto.setText(pegaNomeProduto());
                    mEstoqueProduto.setText(pegaEstoqueProduto());
                    mPrecoUnitario.setText(pegaPrecoProduto());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        SetNomeProduto();     // AutoCompleteTextView

        // LISTA O CONTEUDO DA TABELA DE PRODUTOS
        mNomeProduto.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mNomeProduto.setText("%");
                return false;
            }
        });


        btnPesquisaCampos = (Button) findViewById(R.id.search);
        btnPesquisaCampos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDescricaoProduto != null) {
                    String codigoProduto = pegaCodigoProduto();
                    if (codigoProduto != null) {
                        mCodigoProduto.setText(pegaCodigoProduto());
                        mEstoqueProduto.setText(pegaEstoqueProduto());
                        int novoEstoque = Integer.parseInt(mEstoqueProduto.getText().toString());
                        if (novoEstoque <= 0) {
                            mEstoqueProduto.setTextColor(Color.RED);
                        } else {
                            mEstoqueProduto.setTextColor(Color.GREEN);
                        }
                        mPrecoUnitario.setText(pegaPrecoProduto());
                    } else {
                        Toast.makeText(EditPedidoItensActivity.this, "Escolha uma descriçao da " +
                                "Lista de Produtos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnAddItems = (Button) findViewById(R.id.addItemsButton);
        btnAddItems.setOnClickListener(new OnClickListener() {


            int clickCounter = 0;

            @Override
            public void onClick(View v) {

                //if (validaCodigoProduto() || validaDescricaoProduto()) {

                    if (isValid()) {
                        if (ProdutoJaInformado(mCodigoProduto.getText().toString())) {
                            mCodigoProduto.requestFocus();
                            Toast.makeText(getApplicationContext(), "O Produto " + mCodigoProduto
                                    .getText().toString() + " " + "já foi adicionado" +
                                    ".", Toast.LENGTH_LONG).show();
                            mCodigoProduto.setText("");
                            mQuantidade.setText("");

                        } else {
                            // Volta os campos para ter . no lugar da ,

                            // CAMPO DE DESCONTO DESABILITADO TEMPORARIAMENTE
                            /*
                            String percentualDesconto = null;
                            if (mPercentualDesconto.getText().toString().length() == 0 ||
                                    mPercentualDesconto.getText().toString() == null) {
                                mPercentualDesconto.setText("0");
                            }

                            percentualDesconto = mPercentualDesconto.getText().toString();
                            percentualDesconto = percentualDesconto.replace(",", ".");
                            */

                            String percentualDesconto = "0";

                            String preco = mPrecoUnitario.getText().toString();
                            preco = preco.replace(",", ".");
                            String quantidade = mQuantidade.getText().toString();
                            quantidade = quantidade.replace(",", ".");


                            PedidoItensTemp linha = new PedidoItensTemp(mCodigoProduto.getText().toString(),
                                    percentualDesconto, preco, quantidade);
                            // Esse Array vai suportar os Itens do Pedido na Tela
                            pedidoItens.add(linha);

                            // Transforma as String em Double com 2 casas decimais
                            BigDecimal quantity = new BigDecimal(quantidade);
                            quantity = quantity.setScale(2, RoundingMode.CEILING);
                            BigDecimal price = new BigDecimal(preco);
                            price = price.setScale(2, RoundingMode.CEILING);
                            BigDecimal precoItem = quantity.multiply(price);
                            precoItem = precoItem.setScale(2, RoundingMode.CEILING);

                            BigDecimal percentage = new BigDecimal(percentualDesconto);
                            percentage = percentage.setScale(2, RoundingMode.CEILING);
                            percentage = percentage.divide(BigDecimal.valueOf(100));
                            percentage = percentage.setScale(2, RoundingMode.CEILING);

                            BigDecimal vlrDesconto = (precoItem.multiply(percentage));
                            vlrDesconto = vlrDesconto.setScale(2, RoundingMode.CEILING);

                            BigDecimal precoFinal = precoItem.subtract(vlrDesconto);
                            precoFinal = precoFinal.setScale(2, RoundingMode.CEILING);

                            precoFinalAcumulado = precoFinalAcumulado.add(precoFinal);
                            quantidadeItens += 1;


                            mValorTotalPedido.setText(String.valueOf(precoFinalAcumulado));
                            mquantidadeItens.setText(String.valueOf(quantidadeItens));


                            String precoUnitario = String.valueOf(mPrecoUnitario.getText().toString());
                            precoUnitario = precoUnitario.replace(",", ".");


                            PedidosItensTemp newDetail =
                                    new PedidosItensTemp(mCodigoProduto.getText().toString(), mNomeProduto
                                            .getText().toString(), mQuantidade.getText().toString(),
                                            precoUnitario, percentualDesconto); //mPercentualDesconto.getText().toString());
                            // add new item to arraylist
                            itemList.add(newDetail);
                            // notify listview of data changed
                            adapter.notifyDataSetChanged();


                            // Limpa os campos
                            mCodigoProduto.setText("");
//                            mPercentualDesconto.setText("");
                            mPrecoUnitario.setText("");
                            mQuantidade.setText("");

                            mCodigoProduto.requestFocus();
                        }
                    }
                // }
            }
        });

        btnCloseOrder = (Button) findViewById(R.id.fechaPedidoButton);
        btnCloseOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (possuiItens()) {


                    // Adicionando PEDIDO
                    ContentValues valuesp = new ContentValues();
                    valuesp.put(PedidosColumns.PEDIDOS_TIPO_PEDIDO, iTipoPedido);
                    valuesp.put(PedidosColumns.PEDIDOS_SERIE, "MAT");
                    valuesp.put(PedidosColumns.PEDIDOS_SUBSERIE, "TAB");
                    Calendar newDate = Calendar.getInstance();
                    dataPedido = dateFormatter.format(newDate.getTime());
                    horaPedido = timeFormatter.format(newDate.getTime());
                    valuesp.put(PedidosColumns.PEDIDOS_NUMERO_PEDIDO, 0); // DEPOIS EH ATUALIZADO
                    valuesp.put(PedidosColumns.PEDIDOS_DATAPEDIDO, dataPedido);
                    valuesp.put(PedidosColumns.PEDIDOS_HORAPEDIDO, horaPedido);
                    valuesp.put(PedidosColumns.PEDIDOS_CODIGOCLIENTE, iCodigoCliente);
                    valuesp.put(PedidosColumns.PEDIDOS_CODIGOVENDEDOR, codigoUsuario);
                    valuesp.put(PedidosColumns.PEDIDOS_FORMAPAGAMENTO, iFormaPagamento);
                    valuesp.put(PedidosColumns.PEDIDOS_PRAZOPAGAMENTO, iPrazoPagamento);
                    valuesp.put(PedidosColumns.PEDIDOS_CODIGO_EMPRESA, codigoEmpresa);
                    valuesp.put(PedidosColumns.PEDIDOS_VALORTOTAL, String.valueOf(precoFinalAcumulado));
                    valuesp.put(PedidosColumns.PEDIDOS_ORIGEM, "T");
                    valuesp.put(PedidosColumns.PEDIDOS_STATUS, "cadastrado");
                    valuesp.put(PedidosColumns.PEDIDOS_OBSERVACAO, iObservacao);
                    Uri returnedp = mContentResolver.insert(UsesalesContract.URI_TABLE_PEDIDOS, valuesp);


                    valuesp.put(PedidosColumns.PEDIDOS_NUMERO_PEDIDO, String.valueOf(returnedp.getLastPathSegment()));
                    int recordsUpdated = mContentResolver.update(UsesalesContract.URI_TABLE_PEDIDOS, valuesp, null, null);


                    // Adicionando ITENS DE PEDIDOS
                    ContentValues values = new ContentValues();

                    for (int i = 0; i < pedidoItens.size(); i++) {
                        values.put(UsesalesContract.PedidosItensColumns.PEDIDOSITENS_CODIGO_EMPRESA, codigoEmpresa);
                        values.put(UsesalesContract.PedidosItensColumns
                                .PEDIDOSITENS_CODIGO_PRODUTO, pedidoItens.get(i).getTcodigoProduto());
                        values.put(UsesalesContract.PedidosItensColumns.PEDIDOSITENS_ID_PEDIDO, returnedp.getLastPathSegment());
                        values.put(UsesalesContract.PedidosItensColumns
                                .PEDIDOSITENS_PERCENTUAL_DESCONTO, pedidoItens.get(i).gettPercentualDesconto());
                        values.put(UsesalesContract.PedidosItensColumns
                                .PEDIDOSITENS_PRECO_UNITARIO, pedidoItens.get(i).gettPrecoUnitario());
                        values.put(UsesalesContract.PedidosItensColumns.PEDIDOSITENS_QUANTIDADE,
                                pedidoItens.get(i).gettQuantidade());
                        Uri returned = mContentResolver.insert(UsesalesContract.URI_TABLE_PEDIDOSITENS, values);

                        // Atualiza Estoque do Produto baixando estoque
                        AtualizaEstoque atualizaEstoque = new AtualizaEstoque
                                (getApplicationContext(),
                                        codigoEmpresa,
                                        pedidoItens.get(i).getTcodigoProduto(), "S",
                                        pedidoItens.get(i).gettQuantidade());
                        atualizaEstoque.AtualizaProduto();

                    }
                    Toast.makeText(getApplicationContext(), "Pedido de Número: " + returnedp
                            .getLastPathSegment() + " gravado com " +
                            "sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditPedidoItensActivity.this, AddPedidoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    mCodigoProduto.requestFocus();
                    Toast.makeText(getApplicationContext(), "Informe dados válidos.", Toast
                            .LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private boolean isValid() {
        if (mCodigoProduto.getText().toString().length() == 0 ||
                mCodigoProduto.getText().toString() == null ||
                mQuantidade.getText().toString().length() == 0 ||
                mQuantidade.getText().toString() == null ||
                mPrecoUnitario.getText().toString().length() == 0 ||
                mPrecoUnitario.getText().toString() == null) {

            if (mNomeProduto.getText().toString().length() == 0 ||
                    mNomeProduto.getText().toString() == null) {
                Toast.makeText(getApplicationContext(), "Informe o código ou a descrição do " +
                                "Produto",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                mCodigoProduto.setText(pegaCodigoProduto());

                if (mQuantidade.getText().toString().length() == 0 ||
                        mQuantidade.getText().toString() == null) {
                    mQuantidade.setText("1");
                }

                mPrecoUnitario.setText(pegaPrecoProduto());
                if (mPrecoUnitario.getText().toString().length() == 0 ||
                        mPrecoUnitario.getText().toString() == null) {
                    Toast.makeText(getApplicationContext(), "Preço do Produto não foi informado!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }


        }

        return true;
    }

    private boolean possuiItens() {
        if (itemList.size() >= 1) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Adicione Itens ao Pedido.", Toast
                    .LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 0: // (AutoCompleteTextView) Clientes
                mAdapter0.swapCursor(data);
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
                projection = new String[]{ProdutosColumns.PRODUTOS_ID,
                        ProdutosColumns.PRODUTOS_DESCRICAO};
                selection = ProdutosColumns.PRODUTOS_CODIGO_EMPRESA + " = " +
                        codigoEmpresa;
                sortBy = "_id DESC";
                cursorLoader = new CursorLoader(this, UsesalesContract.URI_TABLE_PRODUTOS, projection,
                        selection, null, sortBy);
                return cursorLoader;
            default:
                throw new IllegalArgumentException("Unknow id" + id);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case 0: // (AutoCompleteTextView) Produtos
                mAdapter0.swapCursor(null);
                break;
            default:
                throw new IllegalArgumentException("Unknow id" + loader.getId());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Toast.makeText(getApplicationContext(), "Valor de s: " + s, Toast.LENGTH_LONG);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Dont care
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Dont care


    }

    public String pegaNomeProduto() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {ProdutosColumns.PRODUTOS_DESCRICAO,
                ProdutosColumns.PRODUTOS_CODIGO,
                ProdutosColumns.PRODUTOS_PRECO};

        String selection = ProdutosColumns.PRODUTOS_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + ProdutosColumns.PRODUTOS_CODIGO +
                " LIKE '" + mCodigoProduto.getText().toString() + "%'";

        String codigoProduto = null;
        String nomeProduto = null;
        String precoProduto = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PRODUTOS, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    codigoProduto = mCursor.getString(mCursor.getColumnIndex(
                            ProdutosColumns.PRODUTOS_CODIGO));
                    if (codigoProduto.equals(mCodigoProduto.getText().toString())) {
                        nomeProduto = mCursor.getString(mCursor.getColumnIndex(
                                ProdutosColumns.PRODUTOS_DESCRICAO));
                        precoProduto = mCursor.getString(mCursor.getColumnIndex(
                                ProdutosColumns.PRODUTOS_PRECO));
                        // Troca o ponto pela vírgula
                        precoProduto = precoProduto.replace(".", ",");
                        mPrecoUnitario.setText(precoProduto);
                        btnAddItems.setEnabled(true);
                        //mQuantidade.requestFocus();
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                // Desliga botão de adicionar item
                btnAddItems.setEnabled(false);
                nomeProduto = "Produto NÃO EXISTE!";
            }
        }
        return nomeProduto;
    }

    private boolean ProdutoJaInformado(String codigoProdutoTela) {

        String codigoProdutoArray = null;
        boolean retorno = false;

        for (int i = 0; i < pedidoItens.size(); i++) {
            codigoProdutoArray = pedidoItens.get(i).getTcodigoProduto();
            if (codigoProdutoArray.equals(codigoProdutoTela)) {
                // Já existe um mesmo código informado
                retorno = true;
                break;
            } else {
                // É a primeira vez que o código é informado
                retorno = false;
            }
        }
        return retorno;
    }

    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                EditPedidoItensActivity.this);
        final PedidosItensTemp detalhe = itemList.get(deletePosition);
        alert.setTitle("Apagar Linha");
        alert.setMessage("Você quer apagar essa linha: " + detalhe + " ?");
        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Pega os valores para refazer os calculos e abater do totalizador da tela

                BigDecimal preco = new BigDecimal(detalhe.getPrecoUnitario());
                BigDecimal quantidade = new BigDecimal(detalhe.getQuantidade());
                BigDecimal desconto = new BigDecimal(detalhe.getPercentualDesconto());

                BigDecimal precoItem = quantidade.multiply(preco);
                precoItem = precoItem.setScale(2, RoundingMode.CEILING);


                desconto = desconto.divide(BigDecimal.valueOf(100));
                desconto = desconto.setScale(2, RoundingMode.CEILING);

                BigDecimal vlrDesconto = (precoItem.multiply(desconto));
                vlrDesconto = vlrDesconto.setScale(2, RoundingMode.CEILING);

                BigDecimal precoFinal = precoItem.subtract(vlrDesconto);
                precoFinal = precoFinal.setScale(2, RoundingMode.CEILING);

                precoFinalAcumulado = precoFinalAcumulado.subtract(precoFinal);
                quantidadeItens -= 1;
                mquantidadeItens.setText(String.valueOf(quantidadeItens));
                mValorTotalPedido.setText(String.valueOf(precoFinalAcumulado));


                pedidoItens.remove(deletePosition);
                itemList.remove(deletePosition);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCELA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }

    public void SetNomeProduto() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        // INICIO PEGA NOME PRODUTO
        mDescricaoProduto = (AutoCompleteTextView) findViewById(R.id.nomeProduto_tv);

        String[] projection = new String[]{ProdutosColumns.PRODUTOS_DESCRICAO,
                ProdutosColumns.PRODUTOS_ID};
        int[] target = new int[]{R.id.descricaoProduto, R.id.produtoId};
        getLoaderManager().initLoader(0, null, this);
        mAdapter0 = new SimpleCursorAdapter(this, R.layout.produtos_list, null,
                projection, target, 0);

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
                return getProdutos(partialItemName, codigoEmpresa);
            }
        });

        mDescricaoProduto.setAdapter(mAdapter0);
        mDescricaoProduto.setThreshold(1);

        // FIM PEGA NOME PRODUTO
    }

    Cursor getProdutos(String partialName, int codigoEmpresa) {
        // Look for partialName either in display name (person name) or in email
        final String filter =
                ProdutosColumns.PRODUTOS_DESCRICAO + " LIKE '" + partialName + "%' AND " +
                        ProdutosColumns.PRODUTOS_CODIGO_EMPRESA + " = " + codigoEmpresa;
        String[] PROJECTION = new String[]{ProdutosColumns.PRODUTOS_DESCRICAO,
                ProdutosColumns.PRODUTOS_ID};
        String sortOrder = ProdutosColumns.PRODUTOS_DESCRICAO + " ASC";
        // Now make a Cursor containing the contacts that now match partialName as per "filter".
        return mContentResolver.query(UsesalesContract.URI_TABLE_PRODUTOS, PROJECTION, filter, null,
                sortOrder);

    }

    private String pegaCodigoProduto() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {ProdutosColumns.PRODUTOS_DESCRICAO,
                ProdutosColumns.PRODUTOS_CODIGO};

        String selection = ProdutosColumns.PRODUTOS_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + ProdutosColumns.PRODUTOS_DESCRICAO + " = '" +
                mDescricaoProduto.getText().toString() + "'";

        String codigoProduto = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PRODUTOS, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String nomeProduto = mCursor.getString(mCursor.getColumnIndex(
                            ProdutosColumns.PRODUTOS_DESCRICAO));
                    if (nomeProduto.equals(mDescricaoProduto.getText().toString())) {
                        codigoProduto = mCursor.getString(mCursor.getColumnIndex(
                                ProdutosColumns.PRODUTOS_CODIGO));

                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "Essa Descrição NÃO EXISTE!",
                                Toast.LENGTH_LONG).show();
                    }
                } while (mCursor.moveToNext());

            } else {
                codigoProduto = null;
            }
        }

        return codigoProduto;

    }

    public String pegaEstoqueProduto() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {ProdutosColumns.PRODUTOS_DESCRICAO,
                ProdutosColumns.PRODUTOS_SALDO};

        String selection = ProdutosColumns.PRODUTOS_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + ProdutosColumns.PRODUTOS_DESCRICAO + " LIKE '" +
                mDescricaoProduto.getText().toString() + "%'";

        String saldoProduto = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PRODUTOS, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String nomeProduto = mCursor.getString(mCursor.getColumnIndex(
                            ProdutosColumns.PRODUTOS_DESCRICAO));
                    if (nomeProduto.equals(mDescricaoProduto.getText().toString())) {
                        saldoProduto = mCursor.getString(mCursor.getColumnIndex(
                                ProdutosColumns.PRODUTOS_SALDO));
                        // Troca o ponto pela vírgula
                        saldoProduto = saldoProduto.replace(".", ",");

                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                saldoProduto = null;
            }
        }

        return saldoProduto;
    }

    private String pegaPrecoProduto() {
        final MyGlobalVariables globalVariables = (MyGlobalVariables) getApplicationContext();
        final int codigoEmpresa = globalVariables.getCodigoEmpresa();

        String[] projection = {ProdutosColumns.PRODUTOS_DESCRICAO,
                ProdutosColumns.PRODUTOS_PRECO};

        String selection = ProdutosColumns.PRODUTOS_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + ProdutosColumns.PRODUTOS_DESCRICAO + " LIKE '" +
                mDescricaoProduto.getText().toString() + "%'";

        String precoProduto = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_PRODUTOS, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String nomeProduto = mCursor.getString(mCursor.getColumnIndex(
                            ProdutosColumns.PRODUTOS_DESCRICAO));
                    if (nomeProduto.equals(mDescricaoProduto.getText().toString())) {
                        precoProduto = mCursor.getString(mCursor.getColumnIndex(
                                ProdutosColumns.PRODUTOS_PRECO));
                        // Troca o ponto pela vírgula
                        precoProduto = precoProduto.replace(".", ",");

                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                precoProduto = null;
            }
        }

        return precoProduto;

    }

    public boolean validaCodigoProduto() {
        if (mCodigoProduto != null) {
            // Valida pelo Codigo do Produto
            String testeCodigo = pegaNomeProduto();
            if (testeCodigo != null) {
                mDescricaoProduto.setText(testeCodigo);
                btnAddItems.setEnabled(true);
            } else {
                Toast.makeText(getApplicationContext(), "Esse Código NÃO EXISTE!", Toast.LENGTH_LONG).show();
                btnAddItems.setEnabled(false);
                return false;
            }
        }
        return true;
    }

    public boolean validaDescricaoProduto() {
        if (mDescricaoProduto != null) {
            // Valida pela Descricao do Produto
            String codigo = pegaCodigoProduto();
            if (codigo != null) {
                mCodigoProduto.setText(codigo);
                btnAddItems.setEnabled(true);
            } else {
                Toast.makeText(getApplicationContext(), "Esse Código NÃO EXISTE!", Toast.LENGTH_LONG).show();
                btnAddItems.setEnabled(false);
                return false;
            }
        }
        return true;
    }

    // Classe de Itens temporaria para suportar os itens digitados
    public class PedidoItensTemp {
        public String tcodigoProduto, tPercentualDesconto, tPrecoUnitario, tQuantidade;


        public PedidoItensTemp() {
        }

        public PedidoItensTemp(String tcodigoProduto,
                               String tPercentualDesconto,
                               String tPrecoUnitario,
                               String tQuantidade) {

            this.tcodigoProduto = tcodigoProduto;
            this.tPercentualDesconto = tPercentualDesconto;
            this.tPrecoUnitario = tPrecoUnitario;
            this.tQuantidade = tQuantidade;
        }

        public String getTcodigoProduto() {
            return tcodigoProduto;
        }

        public String gettPercentualDesconto() {
            return tPercentualDesconto;
        }

        public String gettPrecoUnitario() {
            return tPrecoUnitario;
        }

        public String gettQuantidade() {
            return tQuantidade;
        }
    }


}
