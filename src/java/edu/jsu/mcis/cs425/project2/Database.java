
package edu.jsu.mcis.cs425.project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Database {
    
    public HashMap getUserInfo(String username) throws SQLException{
        
        HashMap<String, String> results = null;
        
        //.. 
        try {
            Connection conn = getConnection();
            
            String query = "SELECT * FROM user WHERE username = ?";
            
            PreparedStatement pstatement = conn.prepareStatement(query);
            pstatement.setString(1, username);
            
            boolean hasresults = pstatement.execute();
                
            if ( hasresults ) {
                
                ResultSet resultSet = pstatement.getResultSet();
                
                if (resultSet.next()){
                    System.out.println("test 1");
                    int id = resultSet.getInt("id");
                    String displayname = resultSet.getString("displayname");
                    results = new HashMap();
                    results.put("userid",String.valueOf(id));
                    results.put("displayname", displayname);
                }
                
            }

        }
        
        catch(SQLException e){
            e.printStackTrace();
            
        }
        
        return results;
        
    }
    
    public String getSkillsListAsHTML(int id){
        Connection conn;
        String query;
        PreparedStatement pstatement;
        boolean hasresults;
        ResultSet resultSet;
        StringBuilder s = new StringBuilder();
        
        try {
            conn = getConnection();
            query = "SELECT * FROM skills";
            pstatement = conn.prepareStatement(query);
            hasresults = pstatement.execute();
            if(hasresults){
                resultSet = pstatement.getResultSet();
                while(resultSet.next()){
                    
                    int userid = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    
                    s.append("<input type=\"checkbox\" name=\"skills\" value=\"");
                    s.append(userid).append("\"");
                    s.append("id=\"skills_").append(userid).append("\">");
                    s.append("<label for=\"skills_").append(userid).append("\" checked>");
                    s.append(description).append("</label><br>");
                    
                }
            }
        
        }
        catch(SQLException e) { 
            e.printStackTrace(); 
        }
        
        return s.toString();
    
    }
    
    public String getJobsListAsHTML(int id){
        Connection conn;
        String query;
        PreparedStatement pstatement;
        boolean hasresults;
        ResultSet resultSet;
        StringBuilder s = new StringBuilder();
        
        try {
            conn = getConnection();
            query = "SELECT DISTINCT jobs.id, jobs.name, " +
                "IF ((SELECT COUNT(*) > 0 FROM applicants_to_jobs " +
                "AS status " +
                "FROM jobs " +
                "WHERE jobsid = jobs.id AND userid = '1'), 'checked', '') " +
                "JOIN skills_to_jobs " +
                "ON jobs.id = skills_to_jobs.jobsid " +
                "WHERE skills_to_jobs.skillsid IN " +
                "(SELECT skillsid FROM applicants_to_skills " +
                "WHERE userid = '1');";
            pstatement = conn.prepareStatement(query);
            hasresults = pstatement.execute();
            if(hasresults){
                resultSet = pstatement.getResultSet();
                while(resultSet.next()){
                    
                    int userid = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    
                    s.append("<input type=\"checkbox\" name=\"jobs\" value=\"");
                    s.append(userid).append("\"");
                    s.append("id=\"jobs_").append(userid).append("\">");
                    s.append("<label for=\"jobs_").append(userid).append("\">");
                    s.append(description).append("</label><br>");
                    
                }
            }
        
        }
        catch(SQLException e) { 
            e.printStackTrace(); 
        }
        
        return s.toString();
    
    }
    
    public void setSkillsList(int id, String[] skills) {
        try {
            Connection conn = getConnection();
            String query = "DELETE FROM applicants_to_skills WHERE userId = '" + id + "';";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.execute(query);
            String values = "";
            
            for (int i = 0; i < skills.length; i++){
                values += "(" + id + ", " + Integer.parseInt(skills[i]) + ")";
                if ((i + 1) < skills.length)
                {
                    values += ", ";
                }
                else {
                    values += ";";
                }
            }
            query = "INSERT INTO applicants_to_skills (userId, skillsid) VALUES " + values;
            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setJobsList(int id, String[] jobs) {
        try {
            Connection conn = getConnection();
            String query = "DELETE FROM applicants_to_jobs WHERE userId = '" + id + "';";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.execute(query);
            
            String values = "";
            for (int i = 0; i < jobs.length; i++){
                values += "(" + id + ", " + Integer.parseInt(jobs[i]) + ")";
                if ((i + 1) < jobs.length)
                {
                    values += ", ";
                }
                else {
                    values += ";";
                }
            }
            query = "INSERT INTO applicants_to_jobs (userId, jobsid) VALUES " + values;
            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    public Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception e) { 
            e.printStackTrace();
        }
        
        return conn;

    }


    
    
    
    
}
