package com.mrenesinau.searchrecylceviewmysql.MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mrenesinau.searchrecylceviewmysql.Recycler.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser extends AsyncTask<Void, Void, Integer> {

    String data;
    RecyclerView rv;
    Context c;

    ArrayList<String> names = new ArrayList<>();

    public Parser(Context c, String data, RecyclerView rv) {
        this.c = c;
        this.data = data;
        this.rv = rv;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if (integer == 1) {
            rv.setAdapter(new MyAdapter(c, names));

        } else {
            Toast.makeText(c, "Unable to parse", Toast.LENGTH_SHORT).show();
        }
    }

    private int parse() {
        try {
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            names.clear();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String name = jo.getString("Name");
                names.add(name);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
