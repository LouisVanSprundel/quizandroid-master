package com.example.quizz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.util.Log;

public class Connection_thread implements Runnable {

	private String adresse;
	private String pseudo;
	public static ArrayList<String> ret = new ArrayList<String>();
	public static PrintWriter out;
	public static BufferedReader in;
	public static int nb_questions;
	public static int index=0;
	public static String score;
	public static String fin_attente;
	public static int nb_questions1;
	static StringBuilder str = new StringBuilder() ;
	
	public Connection_thread (String adresse, String pseudo){
		this.adresse = adresse;
		this.pseudo = pseudo;

	}

	@Override
	public void run() {
		
		String fin;
		int a = 1;
	
		try {
			Socket socket = new Socket(adresse,2009);	//adresse IP actuelle du serveur 
 
			in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			out.println(pseudo);
			out.flush();
			nb_questions = Integer.parseInt(in.readLine());
			nb_questions1 = nb_questions -1;
			
			//On lit et stocke les strings questions et réponses du serveur 

			for(index = 0 ; index<(nb_questions)*7 ;index++){
				ret.add(in.readLine());
				
			}	
			
			
				fin_attente = in.readLine();

				while(a == 1){
				fin = in.readLine();
				if("BLOCBLOC".equals(fin)){
					a = 0;
				}
				else{
					str.append(fin);
					str.append("\n");
				}
				
			}
			score = str.toString();

			
			
			
		} 

		catch (Exception ex) {
			Log.e("ConnectionTask","Failure !0",ex);
		}



	}
}

