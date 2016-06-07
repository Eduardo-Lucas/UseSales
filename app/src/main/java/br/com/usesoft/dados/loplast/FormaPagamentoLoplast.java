package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 02/06/2016.
 */
public class FormaPagamentoLoplast {
    private static final String TAG = FormaPagamentoLoplast.class.getSimpleName();

    SQLiteDatabase db;

    public FormaPagamentoLoplast(SQLiteDatabase db) {
        this.db = db;
    }

    public void InsereFormaPagamento() {
        Log.d(TAG, "Inserindo Formas de Pagamento...");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 1, 'Dinheiro', 544)");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 2, 'Cheque', 544)");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 3, '***', 544)");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 4, 'Boleto Bancario', 544)");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 5, 'Sem financeiro', 544)");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 7, 'BONIFICACAO', 544)");
        db.execSQL("    insert into FORMASPAGAMENTO (_id, codigoFormaPagamento, descricaoPagamento, codigoempresa) values (null, 99, 'Devolucao de cliente', 544)");
    }
}
