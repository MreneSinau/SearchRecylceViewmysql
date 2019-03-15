package com.mrenesinau.searchrecylceviewmysql.MySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class SenderReceiver extends AsyncTask<Void, Void, String> {

    Context c;
    String query;
    ProgressDialog pd;
    String urlAddress;
    RecyclerView rv;
    ImageView noDataImg, noNetworkImg;

    public SenderReceiver(Context c, String query, String urlAddress, RecyclerView rv, ImageView... imageViews) {
        this.c = c;
        this.query = query;
        this.urlAddress = urlAddress;
        this.rv = rv;

        this.noDataImg = imageViews[0];
        this.noNetworkImg = imageViews[1];
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Search");
        pd.setMessage("Searching...Please wait");
        pd.show();

    }

    @Override
    protected String doInBackground(Void... params) {
        return this.sendAndReceive();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        pd.dismiss();

        rv.setAdapter(null);

        if (result != null) {
            if (!result.contains("null")) {

                Parser p = new Parser(c, result, rv);
                p.execute();

                noNetworkImg.setVisibility(View.INVISIBLE);
                noDataImg.setVisibility(View.INVISIBLE);
            } else {
                noNetworkImg.setVisibility(View.INVISIBLE);
                noDataImg.setVisibility(View.VISIBLE);
            }

        } else {
            noNetworkImg.setVisibility(View.VISIBLE);
            noDataImg.setVisibility(View.INVISIBLE);
        }
    }

    private String sendAndReceive() {
        HttpURLConnection con = Connector.connect(urlAddress);
        if (con == null) {
            return null;
        }

        try {
            OutputStream os = con.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new DataPackager(query).packData());

            bw.flush();

            //RELEASE RES
            bw.close();
            os.close();

            //RESPONSE ???
            int responseCode = con.getResponseCode();
            StringBuffer response = new StringBuffer();

            if (responseCode == con.HTTP_OK) {
                InputStream is = con.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;

                if (br != null) {
                    while ((line = br.readLine()) != null) {
                        response.append(line + "n");
                    }
                } else {
                    return null;
                }

                br.close();
                is.close();

                return response.toString();

            } else {
                return String.valueOf(responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
