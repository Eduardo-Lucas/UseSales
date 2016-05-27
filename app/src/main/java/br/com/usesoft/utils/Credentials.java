package br.com.usesoft.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;


/**
 * Created by eduar on 18/05/2016.
 */
public class Credentials {

    public Credentials() {
    }

    public String encodeCredentials() throws UnsupportedEncodingException {

        String plainCredentials = "usesoft:uses0ft10!";
        byte[] plainCredsBytes  = plainCredentials.getBytes("UTF-8");
        String base64CredsBytes = Base64.encodeToString(plainCredsBytes, Base64.DEFAULT);

        return base64CredsBytes;
    }
}
