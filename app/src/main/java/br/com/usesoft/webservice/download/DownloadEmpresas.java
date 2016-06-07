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

import br.com.usesoft.usesales.Empresa;
import br.com.usesoft.usesales.UsesalesContract;
import br.com.usesoft.usesales.UsesalesContract.EmpresasColumns;

/**
 * Created by Eduardo Lucas on 02/04/2016.
 */
public class DownloadEmpresas {

    Cursor mCursor;
    private ContentResolver mContentResolver;

    public static ArrayList<Empresa> getListProductFromTextFile(String filePath) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader bReader = null;
        ArrayList<Empresa> listResult = new ArrayList<Empresa>();
        try {
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis);
            bReader = new BufferedReader(isr);
            // Get line coming from Text File
            String line = null;
            // Array in order to save each product
            String[] strEmpresa = null;

            // Loop to get all data from the text file
            while (true) {
                int i = 1;
                // Get 1 line
                line = bReader.readLine();
                // Check to see if line is empty. If so, exit loop
                if (line == null) {
                    break;
                } else {
                    strEmpresa = line.split(";");
                    listResult.add(new Empresa((long) i, strEmpresa[0],
                            strEmpresa[1], Integer.parseInt(strEmpresa[2]),
                            strEmpresa[3]));
                    i += 1;
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

    public void AtualizaEmpresas() {
        // Get list of products from text file
        ArrayList<Empresa> listEmpresa = getListProductFromTextFile("C:\\TEMP\\Empresasv1.txt");

        ContentValues values = new ContentValues();

        // Insert list into Database
        for (int i = 0; i < listEmpresa.size(); i++) {
            values.put(EmpresasColumns.EMPRESAS_CODIGO_EMPRESA, listEmpresa.get(i).getCodigo());
            values.put(EmpresasColumns.EMPRESAS_CNPJ, listEmpresa.get(i).getCnpj());
            values.put(EmpresasColumns.EMPRESAS_NOME_EMPRESA, listEmpresa.get(i).getNome());
            values.put(EmpresasColumns.EMPRESAS_ATIVA, listEmpresa.get(i).getAtiva());
            Uri returned = mContentResolver.insert(UsesalesContract.URI_TABLE_EMPRESAS, values);
            System.out.println("Insert sucessfully recorded: " + (i + 1));
        }
    }
}


