package com.ngopidevteam.pranadana.crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ngopidevteam.pranadana.crud.model.ResponseMahasiswa;
import com.ngopidevteam.pranadana.crud.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteActivity extends AppCompatActivity {

    @BindView(R.id.edt_nim)
    EditText edtNim;
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_jurusan)
    EditText edtJurusan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
        String nama = getIntent().getStringExtra("nama");
        String nim = getIntent().getStringExtra("nim");
        String jurusan = getIntent().getStringExtra("jurusan");

        edtNim.setText(nim);
        edtNama.setText(nama);
        edtJurusan.setText(jurusan);
    }

    @OnClick({R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String nimMhs = edtNim.getText().toString().trim();
                String namaMhs = edtNama.getText().toString().trim();
                String jurusanMhs = edtJurusan.getText().toString().trim();

                if (namaMhs.isEmpty() || nimMhs.isEmpty() || jurusanMhs.isEmpty()) {
                    Toast.makeText(this, "Fields tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    updateMahasiswa(nimMhs, namaMhs, jurusanMhs);
                }
            break;
            case R.id.btn_delete:
                deleteMahasiswa();
        }
    }

    private void deleteMahasiswa() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Confirmation");
        dialog.setMessage("Apakah anda yakin menghapus data ?");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigRetrofit.getInstance().deleteMahasiswa(id)
                        .enqueue(new Callback<ResponseMahasiswa>() {
                            @Override
                            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                                if (response.isSuccessful()){
                                    String pesan = response.body().getPesan();
                                    int status = response.body().getStatus();

                                    if (status == 1){
                                        Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UpdateDeleteActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {

                            }
                        });
                dialog.dismiss();
            }
        });

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateMahasiswa(final String nimMhs, final String namaMhs, final String jurusanMhs) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Confirmation");
        dialog.setMessage("Apakah anda yakin mengupdate data ?");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigRetrofit.getInstance().updateMahasiswa(id,  nimMhs, namaMhs, jurusanMhs)
                        .enqueue(new Callback<ResponseMahasiswa>() {
                            @Override
                            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                                if (response.isSuccessful()){
                                    String pesan = response.body().getPesan();
                                    int status = response.body().getStatus();

                                    if (status == 1){
                                        Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UpdateDeleteActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {

                            }
                        });
                dialog.dismiss();
            }
        });

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
