
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Resultats implements Runnable {
	static int lr =0;

	public static String aff_resultats() throws ClassNotFoundException, SQLException{
		int a =0;
		int b =0;
		float c =0;
		float c1;
		String s = null;
		StringBuilder str = new StringBuilder();
		str.append("Resultats :\n");
		Connection conn = Connectionsql.getInstance2();
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM resultats");
		while(result.next()){       
			str.append(result.getString("pseudo")+" : "+result.getString("score")+"/"+result.getString("nbr_questions")+"\n");
			s = result.getString("nbr_questions");
		}
		result.beforeFirst();
		while(result.next()){
			a = Integer.parseInt(result.getString("score"));
			c = c+a;
			b++;
		}
		c = c/b;
		c1 =  (float) (Math.floor(c*100))/100;
		str.append("La moyenne est de "+c1+"/"+s);
		s =str.toString();
		return s;	
	}


	public void run() {
		int a =0;

		try {
			while( a == 0){
				Thread.sleep(100);					
				if(Connexion.a == Connexion.b){	 //nombre de participants == nombre de participants ayant fini la partie
					lr++;		//desactve l'attente quand tout les clients ont fini la partie		
					a++;
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
}
