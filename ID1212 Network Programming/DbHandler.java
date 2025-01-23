/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbHandler {
    
     Statement state;

    public DbHandler() 
    {
        try {
            Context initContext = new InitialContext();
            Context envContext
                    = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");

            Connection conn = ds.getConnection();
            state = conn.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public User[] getUsers(){
        /*User[] users = new User[2];
        users[0] = new User();
        users[0].setUsername("ada@kthse");
        users[0].setPassword("12345");
        users[1] = new User();
        users[1].setUsername("beda@kth.se");
        users[1].setPassword("qwerty");
        return users;*/
        User[] users = null;
        try {
            String script = "SELECT COUNT(*) FROM users";
            ResultSet res = state.executeQuery(script);
            res.next();
            users = new User[res.getInt(1)];
            script = "SELECT * FROM users";
            res = state.executeQuery(script);
            int i = 0;
            while (res.next()) {
                User user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                users[i++] = user;
            }
        } catch (Exception e) 
        {
            System.out.println(e);
        }
        return users;
    }
    
    public boolean validate(User user){
        /*for(User tmp : getUsers()){
            if(tmp.getUsername() == u.getUsername() && tmp.getPassword() == u.getPassword())
                return true;
        }
        return false;*/
         try {
            String script = "SELECT COUNT(*) FROM users WHERE username= '" + user.getUsername() + "' AND password=MD5('" + user.getPassword() + "')";
            ResultSet res = state.executeQuery(script);
            if (res.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
    public Quiz[] getQuizzes() {
    Quiz[] quizzes = null;
        try {
            String script = "SELECT COUNT(*) FROM quizzes";
            ResultSet res = state.executeQuery(script);
            res.next();
            quizzes = new Quiz[res.getInt(1)];
            script = "SELECT * FROM quizzes";
            res = state.executeQuery(script);
            int i = 0;
            while (res.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(res.getInt("id"));
                quiz.setSubject(res.getString("subject"));
                quizzes[i++] = quiz;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return quizzes;
    }
        public Question[] getQuestions(int quizId) {
            Question[] questions = null;
        try {
            String script = "SELECT COUNT(*) FROM questions INNER JOIN selector "
                    + "ON questions.id = selector.question_id WHERE quiz_id = " + quizId;
            ResultSet res = state.executeQuery(script);
            res.next();
            questions = new Question[res.getInt(1)];
            script = "SELECT * FROM questions INNER JOIN selector "
                    + "ON questions.id = selector.question_id WHERE quiz_id = " + quizId;
            res = state.executeQuery(script);
            int i = 0;
            while (res.next()) {
                Question question = new Question(res.getString("text"), res.getString("options"), res.getString("answer"));
                questions[i++] = question;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return questions;
}
        private void updateScore(User user, int score, int quizId) {
        try {
            String script = "SELECT id FROM users WHERE username='" + user.getUsername() + "'";
            ResultSet res = state.executeQuery(script);
            res.next();
            int userId = res.getInt("id");
            script = "SELECT COUNT(*) FROM results WHERE user_id='" + userId + "' AND quiz_id='" + quizId + "'";
            res = state.executeQuery(script);
            res.next();
            if (res.getInt(1) == 0) {
                script = "INSERT INTO results(user_id, quiz_id, score) VALUES(" + userId + "," + quizId + "," + score + ")";
                state.executeUpdate(script);
            } else {
                script = "UPDATE results SET score=" + score + " WHERE user_id='" + userId + "' AND quiz_id='" + quizId + "'";
                state.executeUpdate(script);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
        
public Result[] getResult(User user) {
        Result[] results = null;
        try {
            String script = "SELECT id FROM users WHERE username='" + user.getUsername() + "'";
            ResultSet res = state.executeQuery(script);
            res.next();
            int userId = res.getInt("id");
            script = "SELECT COUNT(*) FROM quizzes";
            res = state.executeQuery(script);
            res.next();
            results = new Result[res.getInt(1)];
            script = "SELECT * FROM results WHERE user_id=" + userId + " ORDER BY quiz_id";
            res = state.executeQuery(script);
            int i = 0;
            while (res.next()) {
                Result result = new Result();
                result.setQuizId(res.getInt("quiz_id"));
                result.setScore(res.getInt("score"));
                results[i++] = result;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }
}
        
