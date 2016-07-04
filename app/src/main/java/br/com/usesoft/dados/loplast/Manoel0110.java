package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 30/05/2016.
 */
public class Manoel0110 {
    public static final String TAG = Manoel0110.class.getSimpleName();

    private SQLiteDatabase db;

    public Manoel0110(SQLiteDatabase db) {
        this.db = db;
    }

    public void InsereCliente() {
        Log.d(TAG, "Inserindo Clientes de Manoel...");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 15848, 'MARIA ELVIRA CORREIA DE JESUS DA SILVA ME', 'MARIA ELVIRA CORREIA DE JESUS DA SILVA ME', 'J', 020975212000100, '119336674', 'CENT', 'CLI', 110, 'RUA JOAO DE SOUZA BACELAR', '', 'CENTRO', 'ENTRE RIOS', 48180000, 'BA', '9700 2938 MARIA', '9131 7687 MESSI', '', 00, 000, 0.00, 0.00, '', '')");
    }
}
