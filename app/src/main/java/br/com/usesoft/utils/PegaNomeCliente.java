package br.com.usesoft.utils;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import br.com.usesoft.usesales.UsesalesContract;

/**
 * Created by eduar on 20/05/2016.
 */
public class PegaNomeCliente extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    Cursor mCursor;
    private ContentResolver mContentResolver = PegaNomeCliente.this.getContentResolver();
    private int codigoCliente, codigoEmpresa;

    public PegaNomeCliente() {
    }

    public PegaNomeCliente(int codigoCliente, int codigoEmpresa) {
        this.codigoCliente = codigoCliente;
        this.codigoEmpresa = codigoEmpresa;
    }

    public String pegaRazaoSocial() {

        String[] projection = {UsesalesContract.ClientesColumns.CLIENTES_RAZAO_SOCIAL,
                UsesalesContract.ClientesColumns.CLIENTES_CODIGO};

        String selection = UsesalesContract.ClientesColumns.CLIENTES_CODIGO_EMPRESA + " = " +
                codigoEmpresa + " AND " + UsesalesContract.ClientesColumns.CLIENTES_CODIGO +
                " = " + codigoCliente;

        String codigoCliente = null;
        String nomeCliente = null;

        mCursor = mContentResolver.query(UsesalesContract.URI_TABLE_CLIENTES, projection, selection,
                null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    final String codigoClienteCursor = mCursor.getString(mCursor.getColumnIndex(
                            UsesalesContract.ClientesColumns.CLIENTES_CODIGO));
                    if (codigoClienteCursor.equals(codigoCliente)) {
                        nomeCliente = mCursor.getString(mCursor.getColumnIndex(
                                UsesalesContract.ClientesColumns.CLIENTES_RAZAO_SOCIAL));
                        break;
                    }
                } while (mCursor.moveToNext());

            } else {
                nomeCliente = "Esse código NÃO exite";
            }
        }
        return nomeCliente;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
