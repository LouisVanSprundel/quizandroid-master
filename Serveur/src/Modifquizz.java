
import java.awt.Container;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.mysql.jdbc.DatabaseMetaData;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Modifquizz extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList list;
	static Container cp;
	private JScrollPane scroller;
	int c = 10;
	static int pq = 1;
	static int pq1 = 1;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Modifquizz frame = new Modifquizz();
					frame.setVisible(true);
					cp = frame.getContentPane();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Modifquizz() throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 330, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lblNewLabel = new JLabel("Choisissez un quizz à modifier ou supprimer:");
		Connection conn = Connectionsql.getInstance1();
		DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		final Vector<String> vect=new Vector<String>();		
		while (rs.next()) {	
			vect.addElement(rs.getString(3));
		}
		list = new JList(vect);
		JScrollPane scroll = new JScrollPane(list);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Quizz.quizzselect = (String) list.getSelectedValue();
				if(Quizz.quizzselect == null ){	
				}
				else{
					String[] choix = {"modifier", "supprimer", "annuler"};
					JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
					int rang = JOptionPane.showOptionDialog(null, "Vous avez sélectionné '"+Quizz.quizzselect+"'",
							"Que voulez vous faire ?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[2]);
					if(rang == 0){
						Modifquizz2 modifquizz2 = null;
						try {
							modifquizz2 = new Modifquizz2();

						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
						modifquizz2.setVisible(true);
					}
					if(rang == 1){
						try {
							JOptionPane jop1 = new JOptionPane();
							JOptionPane.showMessageDialog(null, "Supprimer", "Quizz '"+Quizz.quizzselect+"' supprimer", JOptionPane.INFORMATION_MESSAGE);   
							Connection conn = Connectionsql.getInstance1();
							Statement state = conn.createStatement();
							String sql = "DROP TABLE `mydb`.`"+Quizz.quizzselect+"`";
							state.executeUpdate(sql);
							DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
							ResultSet rs = md.getTables(null, null, "%", null);
							list.removeAll();

							DefaultListModel listModel = new DefaultListModel();
							while (rs.next()) {	
								listModel.addElement( rs.getString(3));
							}
							list.setModel(listModel);
							Quizz.list.setModel(listModel);
							Quizz.frame.repaint();

						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}

				}
			}

		});


		JButton btnAjouterUnNouveau = new JButton("Ajouter un nouveau quizz");
		btnAjouterUnNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JTextField textField;
				Modifquizz.cp.removeAll();
				setBounds(400, 100, 600, 150);
				JLabel lblVeuillezcrireLe = new JLabel("Veuillez \u00E9crire le nom de votre nouveau quizz :");
				textField = new JTextField();
				textField.setColumns(10);	
				JButton btnSuivant = new JButton("Suivant");
				btnSuivant.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(textField.getText().length()==0){
							JOptionPane jop1 = new JOptionPane();
							JOptionPane.showMessageDialog(null, "Veuillez saisir un nom pour votre quizz !", "quizz non saisi", JOptionPane.INFORMATION_MESSAGE);
						}
						try {
							Connection conn = Connectionsql.getInstance1();
							Statement state = conn.createStatement();
							Quizz.quizzselect = textField.getText();
							String sql = "CREATE  TABLE IF NOT EXISTS `mydb`.`"+Quizz.quizzselect+"` ( `id` INT NOT NULL,  `question` TEXT NOT NULL , `reponse1` TEXT NOT NULL , `reponse2` TEXT NOT NULL , `reponse3` TEXT NOT NULL ,  `reponse4` TEXT NOT NULL ,  `corrige` TINYINT NOT NULL , PRIMARY KEY (`id`) )";
							state.executeUpdate(sql);
							pq = 0;
							Ajouter_question ajouter_question = new Ajouter_question();
							ajouter_question.setVisible(true);
							dispose();
							try {									
								DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
								ResultSet rs = md.getTables(null, null, "%", null);
								list.removeAll();
								DefaultListModel listModel1 = new DefaultListModel();
								while (rs.next()) {	
									listModel1.addElement( rs.getString(3));
								}
								Quizz.list.setModel(listModel1);
								Quizz.frame.repaint();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();	
						} catch (SQLException e) {
							e.printStackTrace();
						}

					}
				});

				JButton btnAnnuler = new JButton("Annuler");
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				GroupLayout g2_contentPane = new GroupLayout(contentPane);
				g2_contentPane.setHorizontalGroup(
						g2_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(g2_contentPane.createSequentialGroup()
								.addGroup(g2_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblVeuillezcrireLe)
										.addGroup(g2_contentPane.createSequentialGroup()
												.addContainerGap()
												.addGroup(g2_contentPane.createParallelGroup(Alignment.TRAILING)
														.addGroup(g2_contentPane.createSequentialGroup()
																.addComponent(btnAnnuler)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnSuivant))
																.addComponent(textField, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))))
																.addContainerGap(252, Short.MAX_VALUE))
						);
				g2_contentPane.setVerticalGroup(
						g2_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(g2_contentPane.createSequentialGroup()
								.addComponent(lblVeuillezcrireLe)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(g2_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnSuivant)
										.addComponent(btnAnnuler))
										.addContainerGap(286, Short.MAX_VALUE))
						);
				contentPane.setLayout(g2_contentPane);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)

								.addComponent(btnAjouterUnNouveau)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(19, Short.MAX_VALUE))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnAjouterUnNouveau)
						.addContainerGap(24, Short.MAX_VALUE))
				);
		contentPane.setLayout(gl_contentPane);

	}

}
