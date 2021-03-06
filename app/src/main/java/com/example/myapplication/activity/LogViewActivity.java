package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.element.Log;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Session;
import com.example.myapplication.element.User;
import com.example.myapplication.engine.ManageLog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogViewActivity extends AppCompatActivity {

//    FirebaseDatabase firebase;
//    DatabaseReference db_ref;
    Session session;
    List<Log> logList;
    Calendar calendar;
    ManageLog manageLog;
    SimpleDateFormat fmtDate;
    LinearLayout logLayout;
    Log newLog;

    int logId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        session = Session.getInstance();
//        firebase = FirebaseDatabase.getInstance();
//        db_ref = firebase.getReference("Project").child(session.getProjectId()).child("Log");
        manageLog = new ManageLog(this, session.getProjectId());
        calendar = Calendar.getInstance();
        fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        logList = new ArrayList<>();
//        getLogList();
        manageLog.getLogList();

        setupButton();
    }



//    private void getLogList(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap: dataSnapshot.getChildren()){
//                    String date = snap.child("date").getValue().toString();
//                    String content = snap.child("content").getValue().toString();
//                    String username = snap.child("username").getValue().toString();
//
//                    Log tempLog = new Log(date, content, username);
//                    logList.add(tempLog);
//                }
//                setupLogList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void setupLogList(List<Log> logList){
        this.logList = logList;
        logLayout = findViewById(R.id.logList);

        for(int i = 0; i < logList.size(); i ++){
            TextView logView = new TextView(this);
            Log temp = logList.get(i);
            String content = temp.date + ": " + temp.content + ", " + temp.username;

            logView.setText(content);
            logView.setTextSize(20);
            logView.setPadding(5, 5, 5, 5);

            logLayout.addView(logView);
        }
    }

    private void setupButton(){
        Button btSubmit = findViewById(R.id.buttonSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logList.clear();
                logLayout.removeAllViews();
                submit();
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

    private void submit(){
        EditText logEdit = findViewById(R.id.editTextTextMultiLine3);
        String logContent = logEdit.getText().toString();

        String currDate = fmtDate.format(calendar.getTime());
        String username = session.getUserName();

        newLog = new Log(currDate, logContent, username);
        manageLog.addLog(newLog);
//        getLogId();
//
//        addLogToDatabase();
        logEdit.getText().clear();

    }

//    private void getLogId(){
//        db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap: dataSnapshot.getChildren()){
//                    logId = Integer.parseInt(snap.getKey()) + 1 ;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void finishAddLog(){
        Toast.makeText(getApplicationContext(), "New Log is Added", Toast.LENGTH_SHORT).show();
//                recreate();
    }

//    private void addLogToDatabase(){
//
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                db_ref.child(Integer.toString(logId)).setValue(newLog);
//                Toast.makeText(getApplicationContext(), "New Log is Added", Toast.LENGTH_SHORT).show();
////                recreate();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


}
