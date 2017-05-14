
import java.awt.Container;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.Statement;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Quizz extends JFrame {

	private static final long serialVersionUID = 1L;
	static JPanel contentPane;
	static String quizzselect;
	static Container ct;
	Thread t;
	Thread t1;
	Thread t2;
	Thread t3;
	public static int a = 1000 ;
	public static int b =0;
	static JTextArea textArea_1;
	static JTextArea textArea;
	static JTextArea connecte;
	static JButton btnLancerQuizz;
	static JList list;
	static Quizz frame;
	static Quizz frame1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Quizz();
					frame.setVisible(true);
					ct = frame.getContentPane();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Quizz() throws ClassNotFoundException, SQLException, InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lblVeuillezSlectionnerUn = new JLabel("<html>Veuillez s\u00E9lectionner un quizz ci dessous<br>pour commencer une partie:<html>");
		Connection conn = Connectionsql.getInstance1();
		Statement state = (Statement) conn.createStatement();
		String sql1 = "CREATE DATABASE IF NOT EXISTS mydb";
		state.executeUpdate(sql1);
		String sql2 = "CREATE DATABASE IF NOT EXISTS resultats";
		state.executeUpdate(sql2);
		DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		Vector<String> vect=new Vector<String>();	
		while (rs.next()) {	
			vect.addElement(rs.getString(3));
		}
		list = new JList(vect);
		JScrollPane scroll = new JScrollPane(list); 
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				quizzselect = (String) list.getSelectedValue();
				if(quizzselect == null ){
					System.out.println("bloc bloc");
				}
				else{
					quizzselect = (String) list.getSelectedValue();
					int optionquizz = JOptionPane.showConfirmDialog(null, "Voulez-vous lancer le quizz '"+quizzselect+"' ?", "Lancement du quizz '"+quizzselect , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(optionquizz == JOptionPane.OK_OPTION){
						ct.removeAll();
						setBounds(100, 100, 800, 500);
						connecte = new JTextArea();
						JScrollPane scroll1 = new JScrollPane(connecte); 
						textArea = new JTextArea();
						JScrollPane scrollaera = new JScrollPane(textArea); 
						textArea_1 = new JTextArea();
						JScrollPane scrollaera_1 = new JScrollPane(textArea_1); 
						ServerSocket ss;
						try {
							ss = Communication.initconnexion();
							t = new Thread( new Connexion(ss));
							t.start();	 
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						btnLancerQuizz = new JButton("Lancer quizz");
						btnLancerQuizz.setEnabled(false);
						btnLancerQuizz.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								Lancequizz.lq = 1;
								Connexion.a = Connexion.a - 1000;
							}
						});
						GroupLayout gl1_contentPane = new GroupLayout(contentPane);
						gl1_contentPane.setHorizontalGroup(
								gl1_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl1_contentPane.createSequentialGroup()
										.addGroup(gl1_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(scrollaera, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
												.addComponent(scrollaera_1, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
												.addGroup(gl1_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(btnLancerQuizz)
														.addComponent(scroll1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE))
														.addContainerGap())
								);
						gl1_contentPane.setVerticalGroup(
								gl1_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl1_contentPane.createSequentialGroup()
										.addGroup(gl1_contentPane.createParallelGroup(Alignment.BASELINE)
												.addGroup(gl1_contentPane.createSequentialGroup()
														.addComponent(scrollaera, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(scrollaera_1, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE))
														.addGroup(gl1_contentPane.createSequentialGroup()
																.addComponent(scroll1, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
																.addComponent(btnLancerQuizz)))
																.addGap(290))
								);
						contentPane.setLayout(gl1_contentPane);
					}
				}
			}

		}
				);

		JButton btnNewButton = new JButton("Ajouter/Supprimer/Modifier un quizz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Modifquizz modifquizz = new Modifquizz();
					modifquizz.setVisible(true);
					Modifquizz.cp = modifquizz.getContentPane();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addContainerGap()
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
												.addGap(20)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(lblVeuillezSlectionnerUn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
														.addContainerGap(245, Short.MAX_VALUE))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblVeuillezSlectionnerUn, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnNewButton)
						.addContainerGap(184, Short.MAX_VALUE))
				);
		contentPane.setLayout(gl_contentPane);
	}
}
