package edu.jsu.mcis.cs425.project2;

import java.sql.SQLException;
import java.util.HashMap;

public class BeanApplicant {
    
    private String username;
    private String displayname;
    private int id;
    private String[] skills;
    private String[] jobs;

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String[] getJobs() {
        return jobs;
    }

    public void setJobs(String[] jobs) {
        this.jobs = jobs;
    }
    
    public void setSkillsList() {
        Database db = new Database();
        db.setSkillsList(id, skills);
    }

    public void setUserInfo() throws SQLException {

        Database db = new Database();
        HashMap<String, String> userinfo = db.getUserInfo(username);
        id = Integer.parseInt(userinfo.get("userid"));
        displayname = userinfo.get("displayname");
    }
    
    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getSkillsList() {
        Database db = new Database();
        return ( db.getSkillsListAsHTML(id) );
    }
    public void setJobsList() {
        Database db = new Database();
        db.setJobsList(id, skills);
    }
    
    public String getJobsList() {
        Database db = new Database();
        return ( db.getJobsListAsHTML(id) );
    }
    
}