import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.ResultSetMetaData;


public class Questions implements Runnable {

	static Object console;
	Socket socketduserveur;
	int y;
	String table;


	public Questions(Socket ss,String table){
		socketduserveur = ss;	
		this.table = table;
	}

	public void run() {


		String rep;
		int b;
		int a = 0;
		int c= 1 ;
		String pseudo;
		String score;
		String nbr;
		BufferedReader in = null ;
		PrintWriter out;
		try{
			Connexion.c = Connexion.b;
			Connection conn = Connectionsql.getInstance1();	
			Statement state = conn.createStatement();
			ResultSet result3 = state.executeQuery("SELECT * FROM " +table);
			in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));
			out = new PrintWriter(socketduserveur.getOutputStream());
			pseudo = in.readLine();
			Quizz.connecte.append(pseudo+"\n");
			Quizz.textArea_1.append(pseudo+" s'est connecté"+"\n");		
			while(Lancequizz.lq == 0){ // attente du lancement du quizz
				Thread.sleep(1);
			}
			out.println(Connexion.nbrquestions);
			out.flush();
			while(result3.next()){         
				out.print(c+")"+result3.getString("question")+"\n");
				out.flush();
				for(int i = 3; i <= 6; i++){  
					out.print(result3.getString(i)+"\n");
					out.flush();	
				}
				if((Connexion.c % Connexion.b) == 0){
					Connexion.c++;
					Quizz.textArea.append(c+") "+result3.getString("question")+"\n");
					for(int i = 3; i <= 6; i++){ 
						Quizz.textArea.append("     " + (i-2) +"- " + result3.getString(i)+"\n");
					}
					Quizz.textArea.append("\n");
				}
				Connexion.c ++;
				rep = in.readLine();
				out.flush();			        
				b = Integer.parseInt(rep);			       
				if(b == result3.getInt("corrige")){
					out.println("Bonne reponse");
					out.flush();
					a++;
					Quizz.textArea_1.append(pseudo+" a bien répondu, son score est de "+ a +"/"+c+".\n");
				}
				else{
					out.println("Mauvaise reponse, la bonne reponse etait "+result3.getString((result3.getInt("corrige")+2)));
					out.flush();
					Quizz.textArea_1.append(pseudo+" a mal répondu, son score est de "+ a +"/"+c+".\n");
				}
				out.println("Votre score est de "+ a +"/"+c+".");
				out.flush();
				c++;
			}
			score =   Integer.toString(a); 
			nbr =  Integer.toString(c-1);
			Connection conn2 = Connectionsql.getInstance2();
			Statement state2 = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet result2 = state2.executeQuery("SELECT * FROM resultats");
			result2.moveToInsertRow();
			result2.updateString("pseudo", pseudo);
			result2.updateString("score", score);
			result2.updateString("nbr_questions", nbr);
			result2.insertRow();
			Connexion.a++;
			while(Resultats.lr == 0){ //attends que tous les participants aient fini
				Thread.sleep(100);			
			}
			out.println("ATTENDRE");
			out.flush();
			out.println(Resultats.aff_resultats());
			out.flush();
			out.println("BLOCBLOC");
			out.flush();
			JOptionPane jop = new JOptionPane();   
			while(Connexion.ex == 0){ //effectue ce code seulement dans un seul client
				Connexion.ex++;
				Quizz.textArea_1.append(Resultats.aff_resultats());
				int option = jop.showConfirmDialog(null, "Voulez vous enregistrer les résultats sous format excel ?", "Resultats sous excel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(option == JOptionPane.OK_OPTION){
					JOptionPane jop3 = new JOptionPane(), jop2 = new JOptionPane();
					String nomfichier = jop3.showInputDialog(null, "Veuillez saisir un nom de fichier", "Nom de fichier", JOptionPane.QUESTION_MESSAGE);
					Reuslatstoexcel mysqlToXls = new Reuslatstoexcel();
					mysqlToXls.generateXls(nomfichier+".xls");
					jop2.showMessageDialog(null,nomfichier+" enregistré", "Enregistré", JOptionPane.INFORMATION_MESSAGE);
					String sql = "DROP TABLE `resultats`.`resultats`";
					state.executeUpdate(sql);
				}
				else{
					String sql = "DROP TABLE `resultats`.`resultats`";
					state.executeUpdate(sql);
				}

			}
		} catch (InterruptedException ex) { 
			Thread.currentThread().interrupt();
		}catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


}
