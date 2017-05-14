import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.ResultSetMetaData;

public class Connectionsql{

  private static String user = "root";
  private static String passwd = "louis";

  public static Connection getInstance1() throws ClassNotFoundException{
	  //se connecte à la base 'mydb'
	  Connection connect = null;
    if(connect == null){
      try {
    	  String url = "jdbc:mysql://localhost:3306/mydb";
    	Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection(url, user, passwd);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }      
    return connect;
  }  
  
  public static Connection getInstance2() throws ClassNotFoundException{
	//se connecte à la base 'resultats'
	  Connection connect = null;
	    if(connect == null){
	      try {
	    	  String url = "jdbc:mysql://localhost:3306/resultats";
	    	Class.forName("com.mysql.jdbc.Driver");
	        connect = DriverManager.getConnection(url, user, passwd);
	      } catch (SQLException e) {
	        e.printStackTrace();
	      }
	    }      
	    return connect;
	  } 

  public static int getnbrquestions() throws SQLException, ClassNotFoundException{
	  	int nbr;
	  	Connection conn = Connectionsql.getInstance1();	
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM " +Quizz.quizzselect);
		result.last();
		nbr = result.getRow();
		result.beforeFirst();
		return nbr;
  }
 
}
		
		