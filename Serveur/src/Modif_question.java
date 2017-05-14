
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Modif_question extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	int cor = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Modif_question frame = new Modif_question();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Modif_question() throws SQLException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lblVeuillezEntrerVotre = new JLabel("Veuillez entrer votre question:");
		textField = new JTextField();
		textField.setText(Modifquizz2.resultmodif.getString("question"));
		textField.setColumns(10);
		JLabel lblEntrezLesQuatres = new JLabel("Entrez les quatres r\u00E9ponses possibles:");	
		JLabel lblRponse = new JLabel("r\u00E9ponse 1:");
		textField_1 = new JTextField();
		textField_1.setText(Modifquizz2.resultmodif.getString(3));
		textField_1.setColumns(10);
		JLabel lblRponse_1 = new JLabel("r\u00E9ponse 2:");
		textField_2 = new JTextField();
		textField_2.setText(Modifquizz2.resultmodif.getString(4));
		textField_2.setColumns(10);
		JLabel lblRponse_2 = new JLabel("r\u00E9ponse 3:");
		textField_3 = new JTextField();
		textField_3.setText(Modifquizz2.resultmodif.getString(5));
		textField_3.setColumns(10);
		JLabel lblRponse_3 = new JLabel("r\u00E9ponse 4:");
		textField_4 = new JTextField();
		textField_4.setText(Modifquizz2.resultmodif.getString(6));
		textField_4.setColumns(10);
		JLabel lblChoisissezLaBonne = new JLabel("Choisissez la bonne r\u00E9ponse:");
		final JRadioButton rdbtnRponse = new JRadioButton("r\u00E9ponse 1");
		final JRadioButton rdbtnRponse_1 = new JRadioButton("r\u00E9ponse 2");
		final JRadioButton rdbtnRponse_2 = new JRadioButton("r\u00E9ponse 3");
		final JRadioButton rdbtnRponse_3 = new JRadioButton("r\u00E9ponse 4");
		final ButtonGroup reponse = new ButtonGroup();
		reponse.add(rdbtnRponse);
		reponse.add(rdbtnRponse_1);
		reponse.add(rdbtnRponse_2);
		reponse.add(rdbtnRponse_3);
		JButton btnAjouter = new JButton("Modifier");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a =0;
				if(textField.getText().length()==0 || textField_1.getText().length()==0 || textField_2.getText().length()==0 || textField_3.getText().length()==0 || textField_4.getText().length()==0 ){
					JOptionPane jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Vous n'avez pas rempli tout les champs !", "champs non remplis", JOptionPane.INFORMATION_MESSAGE);
					a = 1;
				}	
				if (rdbtnRponse.isSelected() == true) cor = 1;
				if (rdbtnRponse_1.isSelected() == true) cor = 2;
				if (rdbtnRponse_2.isSelected() == true) cor = 3;
				if (rdbtnRponse_3.isSelected() == true) cor = 4;
				if(cor == 0){
					JOptionPane jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une réponse !", "réponse non sélectionnée", JOptionPane.INFORMATION_MESSAGE);
					a = 1;
				}
				if(a == 0){
				try{
					String question= textField.getText();
					String rep1= textField_1.getText();
					String rep2= textField_2.getText();
					String rep3= textField_3.getText();
					String rep4= textField_4.getText();
					Modifquizz2.resultmodif.moveToCurrentRow();
					Modifquizz2.resultmodif.updateString("question", question);
					Modifquizz2.resultmodif.updateString("reponse1",rep1);
					Modifquizz2.resultmodif.updateString("reponse2",rep2);
					Modifquizz2.resultmodif.updateString("reponse3",rep3);
					Modifquizz2.resultmodif.updateString("reponse4",rep4);
					Modifquizz2.resultmodif.updateInt("corrige",cor);
					Modifquizz2.resultmodif.updateRow();
					dispose();	
					int b =1;		
					if(Modifquizz.pq == 1){
					DefaultListModel listModel = new DefaultListModel();
					Modifquizz2.resultmodif.first();
					listModel.addElement(b+")"+Modifquizz2.resultmodif.getString("question"));
					b++;
					while (Modifquizz2.resultmodif.next()) {	
						listModel.addElement(b+")"+Modifquizz2.resultmodif.getString("question"));
						b++;
						}
					Modifquizz2.list1.setModel(listModel);
					}
					else{
						if(Modifquizz.pq1 == 1){
						Modifquizz2 modifquizz2 = new Modifquizz2();
						modifquizz2.setVisible(true);
						}
						else{
							DefaultListModel listModel = new DefaultListModel();
							Modifquizz2.resultmodif.first();
							listModel.addElement(b+")"+Modifquizz2.resultmodif.getString("question"));
							b++;
							while (Modifquizz2.resultmodif.next()) {	
								listModel.addElement(b+")"+Modifquizz2.resultmodif.getString("question"));
								b++;
								}
							Modifquizz2.list1.setModel(listModel);
						}
					}
					Modifquizz.pq =1;
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				
				}
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblVeuillezEntrerVotre)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEntrezLesQuatres)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblRponse_3)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_4))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblRponse_2)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_3))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblRponse_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_2))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblRponse)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblChoisissezLaBonne)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(60)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnRponse_2)
									.addGap(18)
									.addComponent(rdbtnRponse_3))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnRponse)
									.addGap(18)
									.addComponent(rdbtnRponse_1)))))
					.addContainerGap(54, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(335, Short.MAX_VALUE)
					.addComponent(btnAjouter))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblVeuillezEntrerVotre)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEntrezLesQuatres)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRponse)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRponse_1)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRponse_2)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRponse_3)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblChoisissezLaBonne)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnRponse)
								.addComponent(rdbtnRponse_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnRponse_2)
								.addComponent(rdbtnRponse_3))
							.addContainerGap(14, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAjouter))))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
