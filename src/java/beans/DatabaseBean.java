/*
 * @(#)DatabaseBean.java 1.0 2019/12/13
 */
package beans;


import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;


/**
 * This class gets the connection to the Database 
 * 
 * @author  Marcel van der Westhuizen
 * @since   2019/12/13
 * @version 1.0
 */

public class DatabaseBean implements Serializable {
    
    String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=Debugged;" +
            "user=Admin;password=password;";
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private Connection conn;
    Statement stat;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String password;
    private String securityAnsw;
    private String[][] feed;
    private String[][] comment;
    private String[][] replyFeed;
    private boolean[][] success;
    
    
    public DatabaseBean(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(dbURL);
        }catch(SQLException sqle){
            System.out.println("SQL Exception thrown: " + sqle.getMessage());
        }catch(ClassNotFoundException cnfe){
            System.out.println("Class Not Found Exception thrown: " + cnfe.getMessage());
        } 
    }
    
    /**
     * Gets the connection to the database.
     * 
     * @return Connection to the database
     */
    public Connection getConnection(){
        return conn; 
    }
    
    /**
     * Checks database to see if username already exists
     * 
     * @param username contains the username the user entered into the submission page
     * @return Returns true or false depending on whether the username already exists in the database
     */
    public boolean checkUsername(String username){
        boolean exists = false;
        
        try{
            stat = conn.createStatement();
            ResultSet check = stat.executeQuery("SELECT COUNT(*) FROM Users WHERE username='" + username + "'");
            
            while(check.next()){
                int value = check.getInt(1);
                
                if(value == 0){
                    exists = true;
                }
            }
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
           sqle.printStackTrace();
        }  
        
         return exists;
    }
    
    /**
     * Checks the database if the email already exists
     * 
     * @param email contains the email the user entered into the submission page
     * @return Returns true or false depending on whether the email already exists in the database 
     */
    public boolean checkEmail(String email){
        boolean exists = false;
        
        try{
            stat = conn.createStatement();
            ResultSet check = stat.executeQuery("SELECT COUNT(*) From Users WHERE email='" + email + "'");
            
            while(check.next()){
                int value = check.getInt(1);
                
                if(value == 0){
                    exists = true;
                }
            }
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
           sqle.printStackTrace();
        }  
        
         return exists;
    }
    
    /**
     * Check whether the passwords that were entered match
     * 
     * @param password first password entered
     * @param confirmPassw confirmation password entered 
     * @return Returns true or false depending on whether the passwords match
     */
    public boolean checkPassw(String password, String confirmPassw){
        boolean matches = false;
        
        if(password.equals(confirmPassw)){
            matches = true;
        }
        
         return matches;
    }

    public String[][] getFeed() {
        
        try{
            int rows = 0;
            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Topics");

            while(count.next()){
                rows = count.getInt(1);
            }

            stat = conn.createStatement();
            ResultSet topics = stat.executeQuery(   "SELECT Topics.topic, Topics.descrip, Users.username FROM Topics "
                                                +   "JOIN Users ON Users.id = Topics.topicUser "
                                                +   "ORDER BY Topics.dateCreated");

            feed = new String[rows][3];

            for(int i = 0 ; i < rows ; i++){
                topics.next();

                feed[i][0] = topics.getString(1);
                feed[i][1] = topics.getString(2);
                feed[i][2] = topics.getString(3);
            }
       }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
       }     
        
        return feed;
    }

    public boolean setPassword(String username, String password, String passwConfirm) {
        DatabaseBean dbBean = new DatabaseBean();
        boolean check = dbBean.checkPassw(password, passwConfirm);
        
        if(check){
            try{
                stat = conn.createStatement();
                stat.executeUpdate( "UPDATE Users "
                                +   "SET passw='" + password + "' "
                                +   "WHERE username='" + username + "'");

            }catch(SQLException sqle){
               System.out.println("SQL Exception occurred: " + sqle.getMessage());
               sqle.printStackTrace();
            }
        }
        
        return password.equals(passwConfirm);
    }

    public String[][] getSerachResult(String search) {
        try{    
            int rows = 0;
            boolean valid = true;

            System.out.println(search);

            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Topics WHERE topic LIKE '%" + search + "%'");

            while(count.next()){
                rows = count.getInt(1);

                if(rows == 0){
                    rows = 1;
                    valid = false;
                }
            }

            stat = conn.createStatement();
            ResultSet topics = stat.executeQuery(   "SELECT Topics.topic, Topics.descrip, Users.username FROM Topics " 
                                                +   "JOIN Users ON Users.id = Topics.topicUser "
                                                +   "WHERE topic LIKE '%" + search + "%' "
                                                +   "ORDER BY Topics.dateCreated");

            feed = new String[rows][3];

            if(valid == true){
                for(int i = 0 ; i < rows ; i++){
                    topics.next();

                    feed[i][0] = topics.getString(1);
                    feed[i][1] = topics.getString(2);
                    feed[i][2] = topics.getString(3);
                }
            }else{
                feed[0][0] = "There are no topics that match your search";
                feed[0][1] = "";
                feed[0][2] = "";      
            }
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        } 
        
        return feed;
    }

    public String[][] getTopic(String topic) {
        try{
            int rows = 1;

            stat = conn.createStatement();
            ResultSet topics = stat.executeQuery(   "SELECT Topics.id, Topics.topic, Topics.descrip, Users.username FROM Topics " 
                                                +   "JOIN Users ON Users.id = Topics.topicUser "
                                                +   "WHERE topic = '" + topic + "' "
                                                +   "ORDER BY Topics.dateCreated");

            feed = new String[rows][4];

            while(topics.next()){
                feed[0][0] = Integer.toString(topics.getInt(1));
                feed[0][1] = topics.getString(2);
                feed[0][2] = topics.getString(3);
                feed[0][3] = topics.getString(4);
            }
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        }     
 
        return feed;
    }

    public String[][] getTopicComment(String topic) {
        try{
            int rows = 0;
            boolean valid = true;

            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Comments WHERE topicId=" + Integer.parseInt(topic));

            while(count.next()){

                rows = count.getInt(1);

                if(rows == 0){
                    rows = 1;
                    valid = false;
                }
            }

            stat = conn.createStatement();
            ResultSet comments = stat.executeQuery( "SELECT Comments.id, Comments.descrip, Users.username FROM Comments "
                                                +   "JOIN Users ON Users.id = Comments.commentUser "
                                                +   "WHERE Comments.topicId=" + Integer.parseInt(topic) + " "
                                                +   "ORDER BY Comments.dateCreated");



            feed = new String[rows][3];
            feed[0][0] = "-1";

            if(valid){    
                 for(int i = 0 ; i < rows ; i++){
                    comments.next();

                    feed[i][0] = Integer.toString(comments.getInt(1));
                    feed[i][1] = comments.getString(2);
                    feed[i][2] = comments.getString(3);
                }
            }
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        }
        
        return feed;
    }
    
    public String[][] getComment(int comment) {
        try{
            int rows = 0;
            boolean valid = true;

            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Comments WHERE id=" + comment);

            while(count.next()){

                rows = count.getInt(1);

                if(rows == 0){
                    rows = 1;
                    valid = false;
                }
            }
            
            stat = conn.createStatement();
            ResultSet comments = stat.executeQuery( "SELECT Comments.descrip, Users.username FROM Comments "
                                                +   "JOIN Users ON Users.id = Comments.commentUser "
                                                +   "WHERE Comments.id=" + comment + " "
                                                +   "ORDER BY Comments.dateCreated");



            this.comment = new String[1][2];
            this.comment[0][0] = "-1";
            
            if(valid){
                for(int i = 0 ; i < 1 ; i++){
                   comments.next();

                   this.comment[i][0] = comments.getString(1);
                   this.comment[i][1] = comments.getString(2);
                }  
            }    
            
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        }
        
        return this.comment;
    }

    public String[][] getCommentReplies(int comment) {
        try{
            int rows = 0;
            boolean valid = true;

            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Replies WHERE CommentId=" + comment);

            while(count.next()){

                rows = count.getInt(1);

                if(rows == 0){
                    rows = 1;
                    valid = false;
                }
            }

            stat = conn.createStatement();
            ResultSet replies = stat.executeQuery(  "SELECT Replies.descrip, Users.username FROM Replies "
                                                +   "JOIN Users ON Users.id = Replies.replyUser "
                                                +   "WHERE Replies.commentId=" + comment + " "
                                                +   "ORDER BY Replies.dateCreated");



            replyFeed = new String[rows][2];
            replyFeed[0][0] = "-1";

            if(valid){    
                 for(int i = 0 ; i < rows ; i++){
                    replies.next();

                    replyFeed[i][0] = replies.getString(1);
                    replyFeed[i][1] = replies.getString(2);
                }
            }
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        }
        
        return replyFeed;
    }

    public boolean[][] logginCheck(String email, String password) {
        try{
            int rows = 0;
            boolean valid = true;

            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Users WHERE email='" + email + "'");
                                
            while(count.next()){

                rows = count.getInt(1);

                if(rows == 0){
                    rows = 1;
                    valid = false;
                    
                    success = new boolean[rows][2];
                    success[0][0] = false;
                    success[0][1] = false;
                }else{
                    
                    success = new boolean[rows][2];
                    success[0][0] = true;
                    success[0][1] = false;
                }
            }
            
            stat = conn.createStatement();
            ResultSet users = stat.executeQuery("SELECT passw FROM Users WHERE email='" + email + "'");
            
            if(valid){
                while(users.next()){
                    this.password = users.getString(1);
                }

                if(this.password.equals(password)){
                    success[0][1] = true;
                }else{
                    success[0][1] = false; 
                }
            }    
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
           sqle.printStackTrace();
        }
        
        return success;
    }

    public boolean[][] userCheck(String email, String answ) {
        try{
            int rows = 0;
            boolean valid = true;

            stat = conn.createStatement();
            ResultSet count = stat.executeQuery("SELECT COUNT(*) FROM Users WHERE email='" + email + "'");
                                
            while(count.next()){

                rows = count.getInt(1);

                if(rows == 0){
                    rows = 1;
                    valid = false;
                    
                    success = new boolean[rows][2];
                    success[0][0] = false;
                    success[0][1] = false;
                }else{
                    
                    success = new boolean[rows][2];
                    success[0][0] = true;
                    success[0][1] = false;
                }
            }
            
            stat = conn.createStatement();
            ResultSet users = stat.executeQuery("SELECT securityAnsw FROM Users WHERE email='" + email + "'");
            
            if(valid){
                while(users.next()){
                    this.securityAnsw = users.getString(1);
                }

                if(this.securityAnsw.equals(answ)){
                    success[0][1] = true;
                }
            }    
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
           sqle.printStackTrace();
        }
        
        return success;
    }

    public int addComment(String comment, String topic, String user) {
        int topicId = 0;
        int userId = 0;
        int result = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        
        try{
            stat = conn.createStatement();
            ResultSet topicSet = stat.executeQuery("SELECT id From Topics WHERE topic='" + topic + "'");

            if(topicSet.next()){
                topicId = topicSet.getInt(1);
            }

            stat = conn.createStatement();
            ResultSet userSet = stat.executeQuery("SELECT id From Users WHERE username='" + user + "'");
            
            if(userSet.next()){
                userId = userSet.getInt(1);
            }
            
            stat = conn.createStatement();
            
            result = stat.executeUpdate( "INSERT INTO Comments "
                                        +    "VALUES ('" + comment + "', '" + formatDate + "', " + topicId + ", " + userId + ")");
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
           sqle.printStackTrace();
        }    
        
        return result;
    }

    public int addReply(int comment, String reply, String user) {
        int userId = 0;
        int result = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        
        try{

            stat = conn.createStatement();
            ResultSet userSet = stat.executeQuery("SELECT id From Users WHERE username='" + user + "'");
            
            if(userSet.next()){
                userId = userSet.getInt(1);
            }
            
            stat = conn.createStatement();
            
            result = stat.executeUpdate( "INSERT INTO Replies "
                                    +    "VALUES ('" + reply + "', '" + formatDate + "', " + comment + ", " + userId + ")");
            return result;
            
        }catch(SQLException sqle){
            System.out.println("SQL Exception occurred: " + sqle.getMessage());
            sqle.printStackTrace();
            return result;
        }
        
    }

    public String getUsername(String email) {
        try{

            stat = conn.createStatement();
            ResultSet username = stat.executeQuery( "SELECT username FROM Users " 
                                                +   "WHERE email = '" + email + "' ");

            while(username.next()){
                this.username = username.getString(1);
            }
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        }     
 
        return this.username;
    }

    public void addTopic(String topic, String desc, String user) {
        int userId = 0;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        
        try{
            stat = conn.createStatement();
            ResultSet userSet = stat.executeQuery("SELECT id From Users WHERE username='" + user + "'");
            
            if(userSet.next()){
                userId = userSet.getInt(1);
            }
            
            stat = conn.createStatement();
            
            stat.executeUpdate( "INSERT INTO Topics "
                            +   "VALUES ('" + topic + "', '" + desc + "', '" + formatDate + "', " + userId + ")");
            
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
           sqle.printStackTrace();
        }
    }

    public String addUser(String username, String email, String name, String surname, String password, String confirmPassw, String securityAnsw) {
        DatabaseBean dbBean = new DatabaseBean();
        boolean resUser = dbBean.checkUsername(username);
        boolean resEmail = dbBean.checkEmail(email);
        boolean resPassw = dbBean.checkPassw(password, confirmPassw);
        boolean regexUser = Pattern.matches("[A-Z][a-z]+", username);
        boolean regexEmail = Pattern.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$", email);
        boolean regexName = Pattern.matches("[A-Z][a-z]+", name);
        boolean regexSurn = Pattern.matches("[[A-Z][a-z]+\\s]+", surname);
        
        String message = "";
        
        if(!resUser){
            message = "That username is already in use.";
        }else if(!resEmail){
            message = "That email is already in use.";
        }else if(!resPassw){
            message = "The passwords you entered doesn't match";
        }else if(!regexUser){
            message = "Username must be one word and and start with a Capital letter";
        }else if(!regexEmail){
            message = "Content of email field must be in an email format";
        }else if(!regexName){
            message = "Name must be one word and and start with a Capital letter";
        }else if(!regexSurn){
            message = "Surname can have multiple parts but each part must start with a capital";
        }
        
        if(resUser&&resEmail&&resPassw&&regexUser&&regexEmail&&regexName&&regexSurn){
            try{
                stat = conn.createStatement();
                stat.executeUpdate( "INSERT INTO Users "
                                +   "VALUES ('" + username + "', '" + email + "', '" + name + "', '" + surname + "', '" + password + "', '" + securityAnsw + "')");
                
                message = "Thank you for signing up to our service " + name + " " + surname;
                
            }catch(SQLException sqle){
               System.out.println("SQL Exception occurred: " + sqle.getMessage());
               sqle.printStackTrace();
            }
        }
        
        return message;
    }

    public boolean[] editUser(int id, String username, String email, String name, String surname, String password, String confirmPassw, String securityAnsw) {
        DatabaseBean dbBean = new DatabaseBean();
        boolean[] checks = new boolean[2];
        checks[0] = false;
        
        try{
            stat = conn.createStatement();
            ResultSet set = stat.executeQuery("SELECT username FROM Users WHERE id='" + id + "'");
            
            set.next();
            this.username = set.getString(1);
            
            if(!this.username.equals(username)){
                stat.executeUpdate("UPDATE Users "
                                +  "SET username='" + username + "' "
                                +  "WHERE id=" + id);
                checks[0] = true;
            }
            
            set = stat.executeQuery("SELECT email FROM Users WHERE id='" + id + "'");
            
            set.next();
            this.email= set.getString(1);
            
            if(!this.email.equals(email)){
                stat.executeUpdate("UPDATE Users "
                                +  "SET email='" + email + "' "
                                +  "WHERE id=" + id);
                checks[0] = true;
            }
            
            set = stat.executeQuery("SELECT name FROM Users WHERE id='" + id + "'");
            
            set.next();
            this.name = set.getString(1);
            
            if(!this.name.equals(name)){
                stat.executeUpdate("UPDATE Users "
                                +  "SET name='" + name + "' "
                                +  "WHERE id=" + id);
                checks[0] = true;
            }
            
            set = stat.executeQuery("SELECT surname FROM Users WHERE id='" + id + "'");
            
            set.next();
            this.surname = set.getString(1);
            
            if(!this.surname.equals(surname)){
                stat.executeUpdate("UPDATE Users "
                                +  "SET surname='" + surname + "' "
                                +  "WHERE id=" + id);
                checks[0] = true;
            }
            
            checks[1] = dbBean.checkPassw(password, confirmPassw);
            
            if(checks[1] && !password.equals("")){
                set = stat.executeQuery("SELECT passw FROM Users WHERE id='" + id + "'");

                set.next();
                this.password = set.getString(1);

                if(!this.password.equals(password)){
                    stat.executeUpdate("UPDATE Users "
                                    +  "SET passw='" + password + "' "
                                    +  "WHERE id=" + id);
                    checks[0] = true;
                }
            }
            
            set = stat.executeQuery("SELECT securityAnsw FROM Users WHERE id='" + id + "'");
            
            set.next();
            this.securityAnsw = set.getString(1);
            
            if(!this.securityAnsw.equals(securityAnsw)){
                stat.executeUpdate("UPDATE Users "
                                +  "SET securityAnsw='" + securityAnsw + "' "
                                +  "WHERE id=" + id);
                checks[0] = true;
            }
        }catch(SQLException sqle){
            System.out.println("SQL Exception thrown: " + sqle.getMessage());
        }
        
        return checks;
    }

    public String[] getUser(String username) {
        String[] user = new String[6];
        
        try{
            stat = conn.createStatement();
            ResultSet userSet = stat.executeQuery( "SELECT * FROM Users "
                                                +   "WHERE username = '" + username + "'");

            for(int i = 0 ; i < user.length ; i++){
                userSet.next();

                user[0] = userSet.getString(1);
                user[1] = userSet.getString(2);
                user[2] = userSet.getString(3);
                user[3] = userSet.getString(4);
                user[4] = userSet.getString(5);
                user[5] = userSet.getString(7);
            }
                
        }catch(SQLException sqle){
           System.out.println("SQL Exception occurred: " + sqle.getMessage());
        } 
        
        return user;
    }
}  
    
  
    