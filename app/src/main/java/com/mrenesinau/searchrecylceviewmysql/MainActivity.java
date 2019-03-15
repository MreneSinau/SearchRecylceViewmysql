package com.mrenesinau.searchrecylceviewmysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.mrenesinau.searchrecylceviewmysql.MySQL.SenderReceiver;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    SearchView sv;
    ImageView noDataImg, noNetworkImg;

    String urlAddress = "http://mrene-try.esy.es/android/search/getcari.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        sv = (SearchView) findViewById(R.id.sv);
        noDataImg = (ImageView) findViewById(R.id.nodataImg);
        noNetworkImg = (ImageView) findViewById(R.id.noserver);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SenderReceiver sr = new SenderReceiver(MainActivity.this, query, urlAddress, rv, noDataImg, noNetworkImg);
                sr.execute();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                SenderReceiver sr = new SenderReceiver(MainActivity.this, query, urlAddress, rv, noDataImg, noNetworkImg);
                sr.execute();

                return false;
            }
        });

    }
}
