package com.example.myapplication.engine;


import androidx.annotation.NonNull;

import com.example.myapplication.activity.JoinProjectActivity;
import com.example.myapplication.element.Project;
import com.example.myapplication.element.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JoinProject {
    FirebaseDatabase firebase;
    DatabaseReference db_ref_project, db_ref_roles;

    JoinProjectActivity activity;
    boolean isExist, isValid, isDeveloper;
    String projectName, inviteCode, username, projectId, roles;
    List<Project> projectList;
    List<Application> appList;


    public JoinProject(JoinProjectActivity activity){
        firebase = FirebaseDatabase.getInstance();
        db_ref_project = firebase.getReference("Project");
        db_ref_roles = firebase.getReference("Roles");
        this.activity = activity;
        retrieveDatabase();
    }

    public void joinProject(String inviteCode, String username){
        this.inviteCode = inviteCode;
        this.username = username;

        if(checkInviteCode(inviteCode))
            joinProjectInDatabase();
    }

    public void applyProject(String projectName, String roles, String username){
        this.projectName = projectName;
        this.roles = roles;
        this.username = username;

        if(checkProjectName(projectName)){
            appList.add(new Application(username, roles));
            submitApplication();
        }
    }

    public void setProjectValue(Project project){
        this.projectName = project.projectName;
        this.projectId = Long.toString(project.projectId);
    }

    private void retrieveDatabase(){
        db_ref_project.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                projectList = new ArrayList<>();
                appList = new ArrayList<>();
                for(DataSnapshot snap : snapshot.getChildren()){
                        String name = snap.child("projectName").getValue().toString();
                        long projectId = Long.parseLong(snap.getKey());
                        String clientCode = snap.child("clientCode").getValue().toString();
                        String devCode = snap.child("devCode").getValue().toString();

                        for(DataSnapshot appSnap : snap.child("Application").getChildren()){
                            String username = appSnap.child("username").getValue().toString();
                            String roles = appSnap.child("roles").getValue().toString();
                            appList.add(new Application(username, roles));
                        }

                        Project project = new Project(projectId, name, clientCode, devCode);
                        projectList.add(project);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkInviteCode(String inviteCode){
        boolean isExist = false;

        for(Project project : projectList){
            if(project.clientCode.equals(inviteCode)){
                activity.setProjectValue(project);
                setProjectValue(project);
                isValid = true;
                isDeveloper = false;
                break;
            }else if(project.devCode.equals(inviteCode)){
                activity.setProjectValue(project);
                setProjectValue(project);
                isValid = true;
                isDeveloper = true;
                break;
            }
        }

        if(!isExist){
            activity.setErrText("The invitation code is invalid");
        }

        return isExist;
    }

    private boolean checkProjectName(String projectName){
        for(Project project : projectList){
            if(project.projectName.equals(projectName)){
                setProjectValue(project);
                return true;
            }
        }

        activity.setErrText("The project does not exists");

        return false;
    }


    public void joinProjectInDatabase(){
//        db_ref_project.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                isValid = false;
//                for(DataSnapshot snap : snapshot.getChildren()){
//                    try{
//                        String name = snap.child("projectName").getValue().toString();
//                        if(!projectName.equals(name)){
//                            continue;
//                        }
//                        long projectId = Long.parseLong(snap.getKey());
//
//                        String clientCode = snap.child("clientCode").getValue().toString();
//                        String devCode = snap.child("devCode").getValue().toString();
//                        Project project = new Project(projectId, name, clientCode, devCode);
//                        if(inviteCode.equals(clientCode)) {
//                            activity.setProjectValue(project);
//                            setProjectValue(project);
//                            isValid = true;
//                            isDeveloper = false;
//                        }else if (inviteCode.equals(devCode)){
//                            activity.setProjectValue(project);
//                            setProjectValue(project);
//                            isValid = true;
//                            isDeveloper = true;
//                        }
//                    }catch(Exception exception){
//                        exception.printStackTrace();
//                    }
//                }
//                if(!isValid){
//                    activity.setErrText("Either Project Does Not Exist or Invitation Code is Not Match");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        db_ref_roles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isExist = false;
                if(isValid){
                    isExist = snapshot.child(projectId).child(username).exists();
                    if(!isExist){
                        String userRole = "client";
                        if(isDeveloper){
                            userRole = "developer";
                        }

                        db_ref_roles.child(projectId).child("ProjectName").setValue(projectName);
                        db_ref_roles.child(projectId).child(username).child("Roles").setValue(userRole);

                        activity.finishJoin(isDeveloper);
                    }else{
                        activity.setErrText("The user already join the project");
                    }
                }
//                if (isExist) {
//                    activity.setErrText("The User already join the project");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void submitApplication(){
        db_ref_project.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Application app = new Application(username, roles);
                db_ref_project.child(projectId).child("Application").setValue(appList);
                System.out.println(projectName);
//                activity.finish()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
