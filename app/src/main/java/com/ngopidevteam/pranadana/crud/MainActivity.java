package com.ngopidevteam.pranadana.crud;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngopidevteam.pranadana.crud.adapter.MyAdapter;
import com.ngopidevteam.pranadana.crud.model.DatanyaItem;
import com.ngopidevteam.pranadana.crud.model.ResponseInsert;
import com.ngopidevteam.pranadana.crud.model.ResponseMahasiswa;
import com.ngopidevteam.pranadana.crud.model.User;
import com.ngopidevteam.pranadana.crud.network.ConfigRetrofit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_mahasiswa)
    RecyclerView rvMahasiswa;
    EditText edtNim;
    EditText edtNama;
    EditText edtJurusan;
    DatabaseReference reference;
    FirebaseUser user;


    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.toolbaaar)
    Toolbar toolbaaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbaaar);

        rvMahasiswa = findViewById(R.id.rv_mahasiswa);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInflater();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getDataMahasiswa();
//        postDataMahasiswa();
//        updateDataMahasiswa();
//        deleteDataMahasiswa();
    }

    private void dialogInflater() {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.inputan_mahasiswa, null);

        edtNim = v.findViewById(R.id.edt_nim_inputan);
        edtNama = v.findViewById(R.id.edt_nama_inputan);
        edtJurusan = v.findViewById(R.id.edt_jurusan_inputan);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setView(v);
        dialog.setCancelable(false);
        dialog.setTitle("Masukkan data : ");

        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {


                String nim = edtNim.getText().toString().trim();
                String nama = edtNama.getText().toString().trim();
                String jurusan = edtJurusan.getText().toString().trim();

                postDataMahasiswa(dialog, nim, nama, jurusan);

            }
        });

        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteDataMahasiswa() {
        ConfigRetrofit.getInstance().deleteMahasiswa("0").enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                String message = response.body().getPesan();
                int status = response.body().getStatus();
            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {

            }
        });
    }

    private void updateDataMahasiswa() {
        ConfigRetrofit.getInstance().updateMahasiswa(
                "1",
                "12398",
                "Budi",
                "Tanjung Priok"
        ).enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getPesan();
                    int status = response.body().getStatus();
                }
            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postDataMahasiswa(final DialogInterface dialog, String nim, String nama, String jurusan) {
        ConfigRetrofit.getInstance().postMahasiswa(nim, nama, jurusan)
                .enqueue(new Callback<ResponseInsert>() {
                    @Override
                    public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getPesan();
                            int status = response.body().getStatus();

                            if (status == 1) {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                recreate();
                            } else {
                                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInsert> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDataMahasiswa() {

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading", false);

        ConfigRetrofit.getInstance().getMahasiswa().enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    List<DatanyaItem> data = response.body().getDatanya();
                    int status = response.body().getStatus();
                    if (status == 1) {
                        MyAdapter adapter = new MyAdapter(MainActivity.this, data);
                        rvMahasiswa.setAdapter(adapter);
                        rvMahasiswa.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
