package com.mrenesinau.searchrecylceviewmysql.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mrenesinau.searchrecylceviewmysql.R;

public class MyHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;

    public MyHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.namaTxt);
    }
}
