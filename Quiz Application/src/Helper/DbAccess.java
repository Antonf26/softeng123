package Helper;


import QuizApp.Core.Quiz;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
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
    private static int runStatementGetID(String statement)
    {
        try
        {
            getConnection();
            Statement st = con.createStatement();
            int numofkey = st.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = st.getGeneratedKeys();
            int key = 0;
            if (keys.next()) {
            key = keys.getInt(1);
            }       
            return key;
        }
        catch (SQLException ex)
        {
            
            return 0;
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
    private static int LastIdentity() throws SQLException
    {
        String statement = "values IDENTITY_VAL_LOCAL()";
        ResultSet rs = getQueryResults(statement);
        int lastid = rs.getInt(1);
        
        return lastid; 
    }
    
    public static Boolean ToggleQuestionValidation(int QDbId, boolean isValid)
    {
        String statement = String.format("Update question set isValidated = %s where QuestionID = %d", isValid ? "TRUE" : "FALSE", QDbId);
        return runStatement(statement);
    }
    
    public static int StoreNewQuestion(Question QuestionToStore)
    {
        try {
        String statement = "INSERT INTO QUESTION (QUESTIONTEXT, ISVALIDATED,AUTHORID) ";
        statement += String.format("VALUES ('%s', %s, %d)", QuestionToStore.questionText, QuestionToStore.isValidated ? "TRUE" : "FALSE", QuestionToStore.AuthorId);
        int QuestionID = runStatementGetID(statement);
        int i= 1;
        statement = "INSERT INTO QUESTIONANSWER (QUESTIONID, ANSWERID, ANSWERTEXT, ISCORRECT) VALUES"; 
        for(Answer a : QuestionToStore.answers)
        {
            statement += String.format("(%d, %d, '%s', %s),", QuestionID, i, a.answerText, a.isCorrect ? "TRUE" : "FALSE");
            i+=1;
        }
        boolean Success = runStatement(statement.substring(0, statement.length()-1));
        if (Success)
        {
            return QuestionID;
        }
        else
        {
            return 0;
        }
        }
        catch (Exception se)
                {
                    return 0;
                }
    }
}
