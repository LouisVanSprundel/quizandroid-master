
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.mysql.jdbc.DatabaseMetaData;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Modifquizz2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JList list1;
	static ResultSet resultmodif;
	int num;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Modifquizz2 frame = new Modifquizz2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Modifquizz2() throws SQLException, ClassNotFoundException {
		int a =1;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(730, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final Connection conn = Connectionsql.getInstance1();		
		final Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		final ResultSet result = state.executeQuery("SELECT * FROM "+Quizz.quizzselect);
		final Vector<String> vect=new Vector<String>();	
		while(result.next()){             	
			vect.addElement(a+")"+result.getString("question")); 
			a++;
		};
		list1 = new JList(vect);
		final ListModel model = list1.getModel();
		JScrollPane scroll1 = new JScrollPane(list1);
		list1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				num = list1.getSelectedIndex();
				if(num == -1 ){

				}
				else{


					String[] choix = {"modifier", "supprimer", "annuler"};
					JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
					int rang = JOptionPane.showOptionDialog(null, "Que voulez vous faire ?",
							"Que voulez vous faire ?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[2]);
					if(rang == 0){

						try {
							resultmodif = state.executeQuery("SELECT * FROM "+Quizz.quizzselect);
							resultmodif.absolute(num+1);
							Modif_question modif_question = new Modif_question();
							modif_question.setVisible(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}

					if(rang == 1){
						try {
							Statement state1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
							ResultSet result1 = state1.executeQuery("SELECT * FROM "+Quizz.quizzselect);
							result1.absolute(num+1);
							result1.deleteRow();
							DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
							int b =1;
							DefaultListModel listModel = new DefaultListModel();	
							result1 = state1.executeQuery("SELECT * FROM "+Quizz.quizzselect);
							if (!result1.next()){
								Modifquizz.pq = 0;
								list1.setModel(listModel);
								System.out.println("coucou");
							}
							else{
								result1.first();
								listModel.addElement(b+")"+result1.getString("question"));
								b++;
								while (result1.next()) {	
									listModel.addElement(b+")"+result1.getString("question"));
									b++;
								}
								list1.setModel(listModel);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						} 
					}
				}
			}	
		});
		JLabel lblSlectionnerUneQuestion = new JLabel("S\u00E9lectionner une question \u00E0 supprimer:");
		JButton btnAjouterUneQuestion = new JButton("Ajouter une question");
		btnAjouterUneQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ajouter_question ajouter_question = new Ajouter_question();
				ajouter_question.setVisible(true);
				try {
					Connection conn = Connectionsql.getInstance1();
					Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
					ResultSet result = state.executeQuery("SELECT * FROM "+Quizz.quizzselect);
					int b =1;
					DefaultListModel listModel = new DefaultListModel();
					if (!result.next()){
						Modifquizz.pq = 0;
						Modifquizz.pq1 = 0;
						list1.setModel(listModel);
					}
					else{
						result.first();
						listModel.addElement(b+")"+result.getString("question"));
						b++;
						while (result.next()) {	
							listModel.addElement(b+")"+result.getString("question"));
							b++;
						}
						list1.setModel(listModel);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}		

			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSlectionnerUneQuestion)
								.addComponent(scroll1, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAjouterUneQuestion))
								.addContainerGap(25, Short.MAX_VALUE))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblSlectionnerUneQuestion)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scroll1, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnAjouterUneQuestion)
						.addContainerGap(49, Short.MAX_VALUE))
				);
		contentPane.setLayout(gl_contentPane);
	}
}
