package com.ngopidevteam.pranadana.crud.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ngopidevteam.pranadana.crud.R;
import com.ngopidevteam.pranadana.crud.UpdateDeleteActivity;
import com.ngopidevteam.pranadana.crud.model.DatanyaItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context context;
    List<DatanyaItem> mData;

    public MyAdapter(Context context, List<DatanyaItem> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.txtNim.setText(mData.get(i).getNim());
        myHolder.txtNama.setText(mData.get(i).getNama());
        myHolder.txtJurusan.setText(mData.get(i).getJurusan());

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDeleteActivity.class);
                intent.putExtra("id", mData.get(i).getId());
                intent.putExtra("nim", mData.get(i).getNim());
                intent.putExtra("nama", mData.get(i).getNama());
                intent.putExtra("jurusan", mData.get(i).getJurusan());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }else{
            return mData.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_nama)
        TextView txtNama;
        @BindView(R.id.txt_nim)
        TextView txtNim;
        @BindView(R.id.txt_jurusan)
        TextView txtJurusan;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
