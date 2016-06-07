package br.com.usesoft.webservice.download;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import br.com.usesoft.usesales.UsesalesContract;
import br.com.usesoft.usesales.UsesalesContract.UsuariosColumns;
import br.com.usesoft.usesales.Usuario;

/**
 * Created by Eduardo Lucas on 02/04/2016.
 */
public class DownloadUsuarios {

    Cursor mCursor;
    private ContentResolver mContentResolver;

    public static ArrayList<Usuario> getListProductFromTextFile(String filePath) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        ArrayList<Usuario> listResult = new ArrayList<Usuario>();
        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis);
            bReader = new BufferedReader(isr);
            // Get line coming from Text File
            String line = null;
            // Array in order to save each product
            String[] strUsuario = null;

            // Loop to get all data from the text file
            while (true) {
                int i = 1;
                // Get 1 line
                line = bReader.readLine();
                // Check to see if line is empty. If so, exit loop
                if (line == null) {
                    break;
                } else {
                    strUsuario = line.split(";");
                    listResult.add(new Usuario(Long.valueOf(i), strUsuario[0],
                            Integer.parseInt(strUsuario[1]),
                            Long.valueOf(strUsuario[2]), strUsuario[3],
                            strUsuario[4], strUsuario[5]));

                }
            }


        } catch (Exception e) {
            System.out.println("Erro lendo o arquivo.");
            e.printStackTrace();

        } finally {
            // close file
            try {
                if (bReader != null) {
                    bReader.close();
                    fis.close();
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return listResult;
    }

    public void AtualizaUsuario() {
        // Get list of products from text file
        ArrayList<Usuario> listUsuario = getListProductFromTextFile("C:\\TEMP\\Usuariosv1.txt");

        ContentValues values = new ContentValues();

        // Insert list into Database
        for (int i = 0; i < listUsuario.size(); i++) {
            values.put(UsuariosColumns.USUARIOS_NOME_COMPLETO, listUsuario.get(i).getNomeCompleto
                    ());
            values.put(UsuariosColumns.USUARIOS_EMAIL, listUsuario.get(i).getEmail());
            values.put(UsuariosColumns.USUARIOS_CODIGO_EMPRESA, listUsuario.get(i).getCodigoEmpresa());
            values.put(UsuariosColumns.USUARIOS_PASSWORD, listUsuario.get(i).getPassword());
            Uri returned = mContentResolver.insert(UsesalesContract.URI_TABLE_USUARIOS, values);
            System.out.println("Insert sucessfully recorded: " + (i + 1));
        }

    }
}
