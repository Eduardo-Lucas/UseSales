package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 30/05/2016.
 */
public class Plinio0023 {
    public static final String TAG = Plinio0023.class.getSimpleName();

    private SQLiteDatabase db;

    public Plinio0023(SQLiteDatabase db) {
        this.db = db;
    }

    public void InsereCliente() {
        Log.d(TAG, "Inserindo Clientes de Plinio...");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 15188, 'BARRA GRANDE COMERCIO DE ALIMENTOS LTDA EPP', 'KOCORICO', 'J', 023749163000177, '129103158', '', '', 23, 'AVENIDA ALMIRANTE MARQUES DE LEAO', '', 'BARRA', 'SALVADOR', 40140230, 'BA', '30162064', '', '991030910', 00, 000, 0.00, 0.00, '', '')");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 16292, 'CARBALLO FARO INPORTACAO E DISTRIBUICAO LTDA', 'CARBALLO', 'J', 012765924000168, '020898300', '', '', 23, 'RUA DOUTOR ALTINO TEIXEIRA', 'QUADRA F LOTE 15 16', 'PORTO SECO PIRAJA', 'SALVADOR', 02089830, 'BA', '986584072', '', '34225266', 04, 030, 0.00, 0.00, '', '')");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 16316, 'RACING BURGUE LANCHONETE BAR E RESTAURANTE LTDA ME', 'RACING BURGUE LANCHONET', 'J', 001092742000157, '043754449', 'CENT', 'CLI', 23, 'ESTRADA DAS PEDRINHAS', 'LOJA 03', 'IMBUI', 'SALVADOR', 41720340, 'BA', '32317126', '', '', 00, 000, 0.00, 0.00, '', '')");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 16388, 'HOTELARIA ACCOR BRASIL LTDA', 'IBIS SALVADOR HANGAR AEROPORTO', 'J', 009967852017283, '111060515', 'CENT', 'CLI', 23, 'AVENIDA LUIS VIANA', 'TORRE I', 'SAO CRISTOVAO', 'SALVADOR', 41500300, 'BA', '35052200', '', '', 00, 000, 0.00, 0.00, '', '')");
        db.execSQL("insert into clientes (_id, codigoEmpresa, codigo, razaoSocial, nomeFantasia, tipo, cnpjcpf, inscricao, regiao, grupo, codigoVendedor, endereco, complemento, bairro, cidade, cep, uf, telefone1, telefone2, telefoneCelular, tipoPagamento, vencimento, limiteCreditoPedido, limiteGeralCredito, status, justificaBloqueio) values (null, 544, 17252, 'POINT DO ARVOREDO ALIMENTOS LTDA ME', 'QUIOSQUE TERAPIA', 'J', 010620446000172, '79670860', 'CENT', 'CLI', 23, 'AVENIDA JORJE AMADO', 'BOX16', 'IMBUI', 'SALVADOR', 41720040, 'BA', '988885648', '', '', 00, 000, 0.00, 0.00, '', '')");
    }
}
