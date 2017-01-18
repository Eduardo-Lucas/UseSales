package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 30/05/2016.
 */
public class Dalmar0010 {
    public static final String TAG = Dalmar0010.class.getSimpleName();

    private SQLiteDatabase db;

    public Dalmar0010(SQLiteDatabase db) {
        this.db = db;
    }

    public void InsereCliente() {
        Log.d(TAG, "Inserindo Clientes de Antonio...");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 2156, 'JOSE PEDRO LINS DE ARAUJO', 'MERC PAIS E FILHOS', 'J', 034193979000183, '99126114', '', '', 35, 'RUA LUIZ VIANA', '', 'CENTRO', 'ACAJUTIBA', 48360000, 'BA', '7534342560', '', '', 00, 000, 0.00, 0.00, '', '')");
    }
}
