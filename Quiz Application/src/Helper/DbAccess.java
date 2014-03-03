package Helper;


import java.sql.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anton
 */
import QuizRunner.*;
import Users.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class DbAccess {
 
    public static  void getConnection()
    {
try
{
    

        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/quizAppDb", "test", "test");
}
catch (ClassNotFoundException ce)
{
    System.out.println("Can't find derby driver");
}
catch (SQLException se)
{
    System.out.println(se.getMessage());
}

        
    }
    public static int[] GetUser(String username, String password){
    try
    {
        
        String query = String.format("SELECT * FROM APPUSERS WHERE USERNAME = '%s' AND PASSWORD  ='%s'", username, password);
        
        int[] udetails = new int[2];
        ResultSet rs = getQueryResults(query);
        while (rs.next())
                {
                    udetails[0] = rs.getInt("USERTYPE");
                    udetails[1] = rs.getInt("USERID");
                }
        //con.close(); //might need to close the connection at another point, doing it here throws and excepction with the resultset.
        return udetails;
    }
    catch (SQLException se)
            {
            return new int[]{-1,-1};
            }
    finally
    {
        
    }
    
   
}
    
    public static Quiz[] getQuizzes()
    {
        try {
        String query = String.format("SELECT * FROM QUIZ WHERE AVAILABLE = %s", "TRUE");
        ResultSet rs = getQueryResults(query);
        List<Quiz> quizzes = new ArrayList<Quiz>();
        while (rs.next())
        {
            Quiz q = new Quiz(rs.getString("QUIZTITLE"), rs.getInt("QUIZID"));
            q.dbId  = rs.getInt("QUIZID");
            q.timeLimit = rs.getInt("TimeAllowed");
            quizzes.add(q);
        }
        return quizzes.toArray(new Quiz[quizzes.size()]);
        }
        catch(SQLException se)
                {
                    return null;
                }
        
    }
    public static List<Question> getQuizData(int QuizDbId)
    {
        try
        {
            String query = String.format("select qq.QUIZID, q.QUESTIONID, q.QUESTIONTEXT, q.ISVALIDATED, qa.ANSWERID, qa.ANSWERTEXT, qa.ISCORRECT from Question q right join \n" +
"QuestionAnswer qa on Q.QUESTIONID = \n" +
"qa.QUESTIONID\n" +
"join QuizQuestion qq on qq.QUESTIONID = q.QUESTIONID\n" +
"where qq.QUIZID = %d", QuizDbId);
            ResultSet rs = getQueryResults(query);
            List<Question> questions = new ArrayList<Question>();
            Question q = new Question("");
            int lastqid = 0, currqid =0; 
            List<Answer> answers = new ArrayList<Answer>();
            while(rs.next())
            {
                currqid = rs.getInt("QUESTIONID");
                if (lastqid == 0)
                {
                    q = new Question(rs.getString("QuestionText"));
                    q.dbId = rs.getInt("QuestionID");
                    Answer a = new Answer(rs.getString("AnswerText"), rs.getBoolean("IsCorrect"));
                    a.dbId = rs.getInt("AnswerID");
                    q.answers.add(a);
                }
                else if (lastqid == currqid)
                {
                    Answer a = new Answer(rs.getString("AnswerText"), rs.getBoolean("IsCorrect"));
                    a.dbId = rs.getInt("AnswerID");
                    q.answers.add(a);
                }
                else
                {
                    questions.add(q);
                    q = new Question(rs.getString("QuestionText"));
                    q.dbId = rs.getInt("QuestionID");
                    Answer a = new Answer(rs.getString("AnswerText"), rs.getBoolean("IsCorrect"));
                    a.dbId = rs.getInt("AnswerID");
                    q.answers.add(a);
                }
                lastqid = currqid; 
            }
            questions.add(q);
            return questions;
           
        }
        catch(SQLException se)
        {
            return null;
        }
    }
    
    private static ResultSet getQueryResults(String query) throws SQLException
    {
        getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        //con.close();
        return rs;
    }
    private static Boolean runStatement(String statement) 
    {
        try
        {
            getConnection();
            PreparedStatement st = con.prepareStatement(statement);
            st.executeUpdate();
            return true;
            
        }
        catch (SQLException ex)
        {
            System.out.print(ex.getMessage());
            return false; //something went wrong, will need to be handled!
        }
    }
    public static Connection con;

    public static Boolean saveResults(int QuizId, Map<Integer, Integer> selectedAnswers, int UserId) {
       String Statement = "INSERT INTO QUIZRESULT (QUIZID, QUESTIONID, USERID, ANSWERID) VALUES";
       for (Map.Entry<Integer, Integer> answerpair : selectedAnswers.entrySet())
       {
           String valstring = String.format("(%d, %d, %d, %d),", QuizId, answerpair.getKey(), UserId, answerpair.getValue());
           Statement+= valstring;
       }
       Statement = Statement.substring(0, Statement.length()-1); //cutting of last comma;
       Boolean Success = runStatement(Statement);
       return Success;
               
    }
}
