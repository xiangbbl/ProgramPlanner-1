package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity{

    private RecyclerView RecView;
    private ImageAdapter IAdapter;
    private DatabaseReference DB_Ref;
    private List<Image> Limages;
    private ProgressBar pro_Cir;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecView = findViewById(R.id.recycler_vi);
                RecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                RecView.setHasFixedSize(true);
            }
        });*/
        RecView = findViewById(R.id.recycler_vi);
        RecView.setLayoutManager(new LinearLayoutManager(this));
        RecView.setHasFixedSize(true);

        pro_Cir = findViewById(R.id.progress_circular);
        Limages = new ArrayList<>();

        DB_Ref = FirebaseDatabase.getInstance().getReference("Project").child(Long.toString(Project.projectId)).child("Images");
        Read_Images();
        setupImage();
        setupView();
        setupButton();
    }

    private void Read_Images(){
        DB_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Limages.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String imageUrl = snapshot.getValue().toString();
//                    System.out.println(imageUrl);
                    Image images = snapshot.getValue(Image.class);
                    Limages.add(images);
                }
//                System.out.println(Limages.size());
                IAdapter = new ImageAdapter(GraphActivity.this, Limages);
                RecView.setAdapter(IAdapter);
                //IAdapter.notifyDataSetChanged();
                pro_Cir.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                pro_Cir.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setupImage(){

    }

    private void setupView(){

        //ImageView graphList = findViewById(R.id.ImageView);

    }

    private void setupButton(){
        Button btUpload = findViewById(R.id.buttonUpload);
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this, ImageChooser.class));
            }
        });

        Button btDelete = findViewById(R.id.buttonDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGraph();
            }
        });

        Button btBack = findViewById(R.id.buttonBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void uploadGraph(){

    }


    private void deleteGraph(){

    }

}
