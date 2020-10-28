package com.example.myapplication;

import java.util.Random;

public class Project {
    public static long projectId;
    public static String projectName;
    public static String clientCode;
    public static String devCode;
    public Feedback feedback;


    public Project(long projectId, String projectName, String clientCode, String devCode){
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientCode = clientCode;
        this.devCode = devCode;
        feedback = new Feedback();
    }

    public static String generateCode(boolean isClient){
        Random random = new Random();
        String code = "";
        for(int i = 0; i < 6; i ++) {
            int digit = random.nextInt(10);

            if(i == 5){
                if(isClient){
                    if(digit % 2 != 1)
                        digit = (digit + 1) % 10;
                }else{
                    if(digit % 2 != 0){
                        digit = (digit + 1) % 10;
                    }
                }
            }
            code += digit;
        }
        return code;
    }

    public String getProjectName(){
        return this.projectName;
    }

    public String getClientCode(){
        return this.clientCode;
    }

    public String getDevCode(){
        return this.devCode;
    }

    public long getProjectId(){
        return this.projectId;
    }

    public String toString(){
        return "Project Id: " + projectId;
    }

}
