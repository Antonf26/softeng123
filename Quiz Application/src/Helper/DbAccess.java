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
    public static int GetUser(String username, String password){
    try
    {
        
        String query = String.format("SELECT * FROM APPUSERS WHERE USERNAME = '%s' AND PASSWORD  ='%s'", username, password);
        
        int utype = 0;
        ResultSet rs = getQueryResults(query);
        while (rs.next())
                {
                    utype = rs.getInt("USERTYPE");
                }
        //con.close(); //might need to close the connection at another point, doing it here throws and excepction with the resultset.
        return utype;
    }
    catch (SQLException se)
            {
            return -1;
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
    public static Connection con;
}
