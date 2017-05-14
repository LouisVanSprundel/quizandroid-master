
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public  class Connexion implements Runnable {

	static Thread t1;
	static Thread t2;
	static Thread t3;
	public static int a = 1000 ;
	public static int b =0;
	public static int c =0;
	static ServerSocket ss1;	
	static int ex =0;
	static int nbrquestions;
	public Connexion(ServerSocket ss) {
		ss1 = ss; 
	}

	public void run() {
		try {  
			Connection conn = Connectionsql.getInstance2();
			Statement state = conn.createStatement();
			String sql = "CREATE  TABLE IF NOT EXISTS `resultats`.`resultats` (`id` INT(11) NOT NULL AUTO_INCREMENT ,`pseudo` TEXT NULL, `score` INT(11) NULL DEFAULT NULL ,`nbr_questions` INT(11) NULL DEFAULT NULL , PRIMARY KEY (`id`) )";
			state.executeUpdate(sql);
			ServerSocket socketserver = ss1;
			t3 = new Thread( new Resultats());
			t3.start();
			nbrquestions = Connectionsql.getnbrquestions();
			while (Lancequizz.lq== 0){ //tant que le quizz n'est pas lancé
				Socket SocketduServeur  = socketserver.accept(); //établie la connection serveur client 
				t1 = new Thread( new Questions(SocketduServeur, Quizz.quizzselect)); //cette classe est lancéé pour chaque client qui vient de e connecter
				t1.start();	
				b++; // compte le nombre de client
				if(b == 1){
					t2 = new Thread( new Lancequizz()); //débloque le bouton lancer quizz
					t2.start(); 
				}
			}			 
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}

