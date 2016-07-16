package br.com.usesoft.dados.loplast;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by eduar on 02/06/2016.
 */
public class UsuariosLoplast {
    private static final String TAG = UsuariosLoplast.class.getSimpleName();

    SQLiteDatabase db;

    public UsuariosLoplast(SQLiteDatabase db) {
        this.db = db;
    }

    public void InsereUsuario() {
        Log.d(TAG, "Inserindo Usuarios...");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'DINOELHO ROCHA', 'dinoelhorocha2@gmail.com', 'd', 544, 31)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'JOSE SOUSA DE OLIVEIRA', 'sousaloplast@gmail.com', 'S', 544, 410)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'JULIIENE BATISTA SOUZA', 'julienebs@hotmail.com', 'J', 544, 30)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'ROSALINA', 'rosa.vendasjg@gmail.com', 'R', 544, 480)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'HERON PASSOS', 'heronpassosalianca@hotmail.com', 'HERON', 544, 29)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'USESOFT DO BRASIL INFORMATICA LTDA', 'nfe@loplastdistribuidora.com.br', '11', 544,   1)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'JULIANA', 'nfe@loplast.net', 'JULIANA', 544,   3)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'PAULA', 'nfe@loplast.net', '8227', 544,   4)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'KELLY', 'nfe@loplast.net', '1516', 544,   6)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'ROQUELITA', 'nfeletronica@usesoft.com.br', '1234', 544,   7)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'PEDRO NETO', 'nfeletronica@usesoft.com.br', '070929', 544,   8)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'RODRIGO', 'nfeletronica@usesoft.com.br', 'RRS', 544,   11)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'CARLA', 'nfeletronica@usesoft.com.br', '031091', 544,   19)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'GABRIEL', 'nfeletronica@usesoft.com.br', '12345', 544,   20)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'GEANY', 'nfeletronica@usesoft.com.br', '700824', 544,   21)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'PLINIO', 'nfe@loplastdistribuidora.com.br', 'PLIMOBRU', 544,   23)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'DANIELE - CONTABILIDADE', 'nfe@loplastdistribuidora.com.br', 'daniele', 544,   26)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'VANESSA', 'nfe@loplastdistribuidora.com.br', 'vanessa', 544,   27)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'ADALTO', 'nfe@loplastdistribuidora.com.br', 'adalto', 544,   28)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'ISAMARA SALES SANTOS', 'isasales.fsa@gmail.com', 'ISA', 544,   70)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'ANTONIO OLIVEIRA DOS SANTOS', 'netobroasantos10@gmail.com', 'NETO', 544,   100)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'MANOEL MESSIAS CARNEIRO ROCHA', 'nfe@loplastdistribuidora.com.br', 'MANOEL', 544,   110)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'JOAO MARIVAL DE MORAIS PEDREIRA JUNIOR', 'jm_fsa@hotmail.com', 'J', 544,   160)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'THIAGO LAGES VIEIRA SILVA', 'tiago-vieira22@hotmail.com', 'THIAGO', 544,   250)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'ADELSON RODRIGUES SILVA', 'adelson.chinatown@gmail.com', 'A', 544,   260)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'JURANDYR SANTOS BARROS', 'jurandyrloplast@gmail.com', 'J', 544,   290)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'CIDIONY DA SILVA RIBEIRO', 'nfe@loplastdistribuidora.com.br', 'CIDIONY', 544,   330)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'EDSON JORGE CORREIA PAZ', 'edsoncorreiapaz@hotmail.com', 'E', 544,   340)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'JOAO DE DEUS LIMA', 'joaoloplast52@gmail.com', 'J', 544,   520)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'MARCOS WINDSON FRANCA', 'diwindsonproducoes@hotmail.com', 'M', 544,   610)");
        db.execSQL("insert into USUARIOS (_id, nome_completo, email,  password, codigoempresa, codigo) values (null, 'GEORGE LUIZ DE SOUZA SANTOS', 'georgeloplastssa@gmail.com', 'G', 544,   760)");
    }
}
