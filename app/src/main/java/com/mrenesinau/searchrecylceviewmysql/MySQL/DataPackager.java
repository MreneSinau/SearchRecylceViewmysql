package com.mrenesinau.searchrecylceviewmysql.MySQL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class DataPackager {

    String query;

    public DataPackager(String query) {
        this.query = query;
    }

    public String packData() {
        JSONObject jo = new JSONObject();
        StringBuffer queryString = new StringBuffer();

        try {
            jo.put("Query", query);

            Boolean firstValue = true;

            Iterator it = jo.keys();

            do {
                String key = it.next().toString();
                String value = jo.get(key).toString();

                if (firstValue) {
                    firstValue = false;
                } else {
                    queryString.append("&");
                }

                queryString.append(URLEncoder.encode(key, "UTF-8"));
                queryString.append("=");
                queryString.append(URLEncoder.encode(value, "UTF-8"));

            } while (it.hasNext());

            return queryString.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
