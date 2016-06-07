package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 02/06/2016.
 */
public class PrazoPagamentoLoplast {
    private static final String TAG = FormaPagamentoLoplast.class.getSimpleName();

    SQLiteDatabase db;

    public PrazoPagamentoLoplast(SQLiteDatabase db) {
        this.db = db;
    }

    public void InserePrazoPagamento() {
        Log.d(TAG, "Inserindo Prazos de Pagamento...");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 0, 'A VISTA', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 1, 'A VISTA', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 3, '30 60 90', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 10, '10 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 14, '14 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 15, '15 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 20, '20/40 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 23, '20/30/40 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 30, '3O DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 33, '30/45/60 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 34, '30/45 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 36, '30/60 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 39, '30/60/90 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 45, '45 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 60, '60 DIAS', 544)");
        db.execSQL("insert into PRAZOSPAGAMENTO (_id, codigoPrazoPagamento, descricaoPrazoPagamento,  codigoempresa) values (null, 99, 'NEGOCIACAO', 544)");
    }
}
