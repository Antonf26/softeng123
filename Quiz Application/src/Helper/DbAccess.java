package Helper;


import QuizApp.Core.Quiz;
import QuizApp.Core.Answer;
import QuizApp.Core.Question;
import java.sql.*;
import QuizRunner.*;
import QuizApp.Core.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds methods to access database to retrieve/store data.
 * 
 */
public class DbAccess {
    /**
     * Instantiates database connection;
     */
    private static  void getConnection()
    {
try
{
    

        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/quizAppDb", "test", "test");
        con.setAutoCommit(true);
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
    
    /**
     * Returns user type and id upon successful login, and -1,-1 upon failed login
     * @param username - Username supplied at login
     * @param password - Password supplied at login
     * @return Array of two integers. First one relates to usertype, the seocnd is the userID
     */
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
            return new int[]{-1,-1}; //Login FAILED!
            }   
}
    public static User LoginUser(String username, String password)
    {
    try
    {
       String query = String.format("SELECT * FROM APPUSERS WHERE USERNAME = '%s' AND PASSWORD  ='%s'", username, password);
       
       User loginUser = new User();
       ResultSet rs = getQueryResults(query);
       if (rs.next())
       {
           loginUser.userName = rs.getString("UserName");
           loginUser.fullName = rs.getString("FullName");
           loginUser.dbId = rs.getInt("UserId");
           int utype = rs.getInt("usertypeid");
           loginUser.utype = User.UserType.values()[utype-1];
           return loginUser;
       }
       return null;
       
    }
    catch (SQLException ex)
    {
        return null;
    }
    }
    
    
    /**
     * Gets a list of available quizzes and their details (without questions!)
     * @return Array of Quiz objects.
     */
    public static Quiz[] getQuizzes()
    {
        try {
        String query = String.format("SELECT * FROM QUIZ");
        ResultSet rs = getQueryResults(query);
        List<Quiz> quizzes = new ArrayList<Quiz>();
        while (rs.next())
        {
            Quiz q = new Quiz(rs.getString("QUIZTITLE"), rs.getInt("QUIZID"));
            q.timeLimit = rs.getInt("TimeAllowed");
            q.available = rs.getBoolean("Available");
            q.feedbackAvailable = rs.getBoolean("FeedbackAvailable");
            q.timeOutBehaviour = rs.getInt("TimeOutBehaviour");
            q.showPracticeQuestion = rs.getBoolean("ShowPracticeQuestion");
            q.navigationEnabled = rs.getBoolean("NAVIGATIONENABLED");
            q.randomiseQuestions = rs.getBoolean("RANDOMISEQUESTIONS");
            quizzes.add(q);
        }
        return quizzes.toArray(new Quiz[quizzes.size()]);
        }
        catch(SQLException se)
                {
                    return null;
                }
        
    }
    
    /**
     * Gets a list of questions (with answers) given the quiz's DBId. Typical use may be to first get the quiz, and then call this,
     * assigning the result to Quiz.questionList/
     * @param QuizDbId - Database id associated with quiz. Is stored in the property of the quiz
     * @return - List of question objects.
     */
    public static List<Question> getQuizQuestions(int QuizDbId)
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
    
    /**
     * Returns a list of question objects for ALL questions stored - useful for quiz generation I'd presume. 
     * Optional ability to return only validated questions. 
     * @return 
     */
    public static List<Question> getAllQuestions()
    {
        return getAllQuestions(false);
    }
    public static List<Question> getAllQuestions(boolean OnlyValidated)
    {
        try
        {
            List<Question> allQuestions = new ArrayList<Question>();
            String query = "Select QuestionId from Question";
            if (OnlyValidated)
            {
                query += " Where IsValidated = True";
            }
            ResultSet rs = getQueryResults(query);
            List<Integer> QuestionIds = new ArrayList<Integer>();
            while (rs.next())
            {
                QuestionIds.add(rs.getInt("QuestionId"));
            }
            for (int qid : QuestionIds)
            {
                allQuestions.add(getQuestionData(qid));
            }
            return allQuestions;
        }
        catch (SQLException ex)
        {
            return null;
        }
        
    }
    /**
     * Stores results of a user taking a quiz
     * @param QuizId - Database Id of the quiz object
     * @param selectedAnswers - Map object of selected answers <questionid, answerid> - this is created by the quizrunner
     * @param UserId - Database Id of the user (usually a student).
     * @return Boolean value indicating if results were saved correctly.
     */

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

    /**
     * Switches a question's status between validated and invalidated 
     * @param QDbId - Question's dbID
     * @param isValid - boolean (true for validated, false for not validated).
     * @return boolean to indicate success/failure
     */
    public static Boolean ToggleQuestionValidation(int QDbId, boolean isValid)
    {
        String statement = String.format("Update question set isValidated = %s where QuestionID = %d", isValid ? "TRUE" : "FALSE", QDbId);
        return runStatement(statement);
    }
    
    /**
     * Updates details of existing quesiton. (Note your quesiton object MUST already be populated with its dbId for this to work). Can add answers and edit existing ones!
     * @param QuestionToUpdate - Takes a question object - will make the data on the db MATCH the state of the question object you pass to it. 
     * @return  boolean to indicate success/failure
     */
    public static Boolean UpdateQuestion(Question QuestionToUpdate)
    {
        List<String> statements = new ArrayList<String>();
        statements.add(String.format("Update Question Set QuestionText = '%s', isValidated = %s where QuestionId = %d", QuestionToUpdate.questionText, QuestionToUpdate.isValidated? "TRUE" : "FALSE", QuestionToUpdate.dbId));

        for (Answer A : QuestionToUpdate.answers)
        {
            if (A.dbId == 0)
            {
              statements.add (String.format("insert into questionanswer (QuestionId, AnswerId, AnswerText, IsCorrect)values "
                      + "(%d, ((select max(AnswerId) from questionanswer where questionid = %d) + 1), '%s', %s)", QuestionToUpdate.dbId, QuestionToUpdate.dbId, A.answerText, A.isCorrect? "TRUE" : "FALSE"));
            }
            else 
            {
              statements.add(String.format ("update questionanswer set AnswerText = '%s', IsCorrect = %s where QuestionId = %d and AnswerID = %d", A.answerText, A.isCorrect? "TRUE" : "FALSE", QuestionToUpdate.dbId, A.dbId ));
            }
        }
        return runStatementBatch(statements);
    }
   
    /**
     * Stores a new question in the database (including answers). Won't link it to a quiz yet. Returns the new question's assigned id! May need re-factoring to ensure
     * AnswerId's are populated.
     * Typical pattern to use may be to create new Question in code and then call this, using the return to populate Question's id.
     * ie:
     * Question newQuestion = new Question("What's a database?");
     * Question.answers.add("A magical chest", false);
     * Question.answers.add("A system of storing data", true);
     * Question.dbId = DbAccess.StoreNewQuestion(newQuestion); - this will store it and give us an Id if we need it.
     * Will return an id of 0 if something breaks in saving it to the database. 
     * @param QuestionToStore - Question object for the new question.
     * @return 
     */
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
    
    
    /**
     * Stores data for a new quiz object in the database. Returns the newly assigned DbId for the Quiz. Typical pattern of use may be
     * Quiz newQuiz = new Quiz("Magical quiz of pixie dust happiness"); 
     * newQuiz.timeLimit = 30;
     * newQuiz.AuthorId = user.UserId 
     * //POPULATE REST OF PROPERTIES AS REQUIRED
     * newQuiz.quizDbId = dbAccess.CreateQuiz(newQuiz); -- this will store it and give us an id. 
     * We need the id to link questions, etc.
     * @param NewQuiz
     * @return 
     */
    public static int CreateQuiz(Quiz NewQuiz)
    {
     
            String statement = String.format("INSERT INTO QUIZ (QUIZTITLE, AVAILABLE, SHOWPRACTICEQUESTION,"
                    + "NAVIGATIONENABLED, TIMEALLOWED, FEEDBACKAVAILABLE, RANDOMISEQUESTIONS, TIMEOUTBEHAVIOUR)"
                    + "VALUES('%s', %s, %s, %s, %d, %s, %s, %d)", NewQuiz.quizTitle, String.valueOf(NewQuiz.available),
                    String.valueOf(NewQuiz.showPracticeQuestion), String.valueOf(NewQuiz.navigationEnabled), 
                    NewQuiz.timeLimit, String.valueOf(NewQuiz.feedbackAvailable), String.valueOf(NewQuiz.randomiseQuestions), 
                    NewQuiz.timeOutBehaviour);
            int QuizId = runStatementGetID(statement);
            return QuizId;
 
    }
    /**
     * Links/unlinks question to/from quiz. This would be used to add/remove questions to a quiz at point of generation. 
     * Takes a quizId and QuestionId (these should be the DbId's)
     * @param QuizId - id of Quiz
     * @param QuestionId - id of Question
     * @param Link - true to link, false to unlink
     * @return - Boolean for success/fail - note this isn't magic. 
     * It'll catch an exception but if you give this wrong id's it won't complain!
     */
    public static boolean ToggleQuizQuestionLink(int QuizId, int QuestionId, boolean Link)
    {
        String statement;
        if (Link)
        {
            statement = String.format("Insert into quizquestion values (%d, %d)", QuizId, QuestionId);
        }
        else
        {
            statement = String.format("Delete from quizquestion where questionid = %d and quizid = %d", QuestionId, QuizId);
        }
        return runStatement(statement);           
    }
    
    /**
     * Fetches data for a single question (including answers), given the Question's database id.
     * @param QDbId
     * @returns populated question object. Would be useful for editing, validation, etc question.
     */
    public static Question getQuestionData(int QDbId)
    {
        try 
        {
        Question q = new Question();
        String qstatement = String.format("Select * from question where questionid = %d", QDbId);
        ResultSet qrs = getQueryResults(qstatement);
        if (qrs.next())
        {
          q.questionText = qrs.getString("QuestionText");
          q.dbId = qrs.getInt("QuestionID");
          q.AuthorId = qrs.getInt("AuthorID");
          q.isValidated = qrs.getBoolean("ISVALIDATED");
          q.isRejected = qrs.getBoolean("ISREJECTED");
        }
        
        String astatement = String.format("Select * from questionanswer where questionid = %d", QDbId);
        ResultSet ars = getQueryResults(astatement);
        while(ars.next())
        {
            String atext = ars.getString("ANSWERTEXT");
            boolean IsCorrect = ars.getBoolean("ISCORRECT");
            int AnswerId = ars.getInt("ANSWERID");
            q.answers.add(new Answer(atext, IsCorrect, AnswerId));
        }
        return q;
        
        }
        catch (SQLException ex)
        {
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
        
    }
    
    ///Private methods below used to directly call to the db and return data if needed. 
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
    private static Boolean runStatementBatch(List<String> statements)
    {
        try
        {
           getConnection();
           Statement stmt = con.createStatement();
           con.setAutoCommit(false);
           for (String statement :statements)
           {
               stmt.addBatch(statement);
           }
           stmt.executeBatch();
           con.commit();
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
    private static int LastIdentity() throws SQLException
    {
        String statement = "values IDENTITY_VAL_LOCAL()";
        ResultSet rs = getQueryResults(statement);
        int lastid = rs.getInt(1);
        
        return lastid; 
    }
    public static Connection con;
}
