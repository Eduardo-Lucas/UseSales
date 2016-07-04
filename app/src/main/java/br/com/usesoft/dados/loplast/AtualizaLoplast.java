package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 02/06/2016.
 */
public class AtualizaLoplast {
    public static final String TAG = AtualizaLoplast.class.getSimpleName();

    private SQLiteDatabase db;

    public AtualizaLoplast(SQLiteDatabase db) {
        this.db = db;
    }

    public void InsereEmpresa() {
        Log.d(TAG, "Inserindo Empresa...");
        db.execSQL("insert into EMPRESAS (_id, codigo, nome, cnpj, ativa) values (null, 544, " +
                "'LOPLAST COMERCIO DE LOUCAS E PLASTICOS', 013050828000104, 'S') ");
    }

    public void InsereUsuarios() {
        UsuariosLoplast usuarios = new UsuariosLoplast(db);
        usuarios.InsereUsuario();
    }

    public void InsereClientes() {
        Juliene0030 juliene = new Juliene0030(db);
        juliene.InsereCliente();
        Heron0029 heron = new Heron0029(db);
        heron.InsereCliente();
        Isamara0070 isamara = new Isamara0070(db);
        isamara.InsereCliente();
        Neto0100 neto = new Neto0100(db);
        neto.InsereCliente();
        Junior0160 junior = new Junior0160(db);
        junior.InsereCliente();
        Thiago0250 thiago = new Thiago0250(db);
        thiago.InsereCliente();
        Adelson0260 adelson = new Adelson0260(db);
        adelson.InsereCliente();
        Jurandyr0290 jurandyr = new Jurandyr0290(db);
        jurandyr.InsereCliente();
        Edson0340 edson = new Edson0340(db);
        edson.InsereCliente();
        Rosa0480 rosa = new Rosa0480(db);
        rosa.InsereCliente();
        JoaoDeDeus0520 joao = new JoaoDeDeus0520(db);
        joao.InsereCliente();
        MarcosWindson0610 marcos = new MarcosWindson0610(db);
        marcos.InsereCliente();
        George0760 george = new George0760(db);
        george.InsereCliente();
        Plinio0023 plinio = new Plinio0023(db);
        plinio.InsereCliente();
        Manoel0110 manoel = new Manoel0110(db);
        manoel.InsereCliente();
        Cidiony0330 cidiony = new Cidiony0330(db);
        cidiony.InsereCliente();
    }

    public void InsereFormasPagamento() {
        FormaPagamentoLoplast formaPagamento = new FormaPagamentoLoplast(db);
        formaPagamento.InsereFormaPagamento();
    }

    public void InserePrazosPagamento() {
        PrazoPagamentoLoplast prazoPagamento = new PrazoPagamentoLoplast(db);
        prazoPagamento.InserePrazoPagamento();
    }

    public void InsereProdutos() {
        ProdutosLoplast produto = new ProdutosLoplast(db);
        produto.InsereProduto();
    }

}


