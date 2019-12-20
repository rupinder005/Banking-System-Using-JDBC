import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.SpinnerListModel;
import javax.swing.JRadioButton;
import javax.swing.AbstractListModel;
import java.awt.Color;

public class SignInPage extends JFrame {

	private JPanel contentPane;
	public static JTextField tf_username;
	public static JPasswordField tf_Password;
	private JLabel lblEnterYourUser;
	private JButton btnGoBackTo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInPage frame = new SignInPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public SignInPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(174, 144, 95, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(174, 188, 86, 34);
		contentPane.add(lblPassword);
		
		tf_username = new JTextField();
		tf_username.setBounds(350, 151, 132, 25);
		contentPane.add(tf_username);
		tf_username.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
					Statement stmt=conn.createStatement();
					Statement stmt2=conn.createStatement();
					
					String sqlstmt="Select * from saving_account where UserName='"+tf_username.getText()+"' and Password='"+tf_Password.getText()+"'";
					ResultSet rs=stmt.executeQuery(sqlstmt);
					
					String sqlstmt2="Select * from checking_account where UserName='"+tf_username.getText()+"' and Password='"+tf_Password.getText()+"'";
					ResultSet rs2=stmt2.executeQuery(sqlstmt2);
					
					if(rs.next())
					{
						UserPage n=new UserPage();
						n.setVisible(true);
						dispose();
					}
					else if(rs2.next())
					{
						UserPage n=new UserPage();
						n.setVisible(true);
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null,"Login Failed..");
					
			
					
					conn.close();

				}
					catch(Exception e){
						
						System.out.println(e.toString());
					}
				
				
				
			
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(174, 270, 95, 33);
		contentPane.add(btnNewButton);
		
		tf_Password = new JPasswordField();
		tf_Password.setBounds(350, 196, 132, 25);
		contentPane.add(tf_Password);
		
		lblEnterYourUser = new JLabel("Enter your user name and password to sign in");
		lblEnterYourUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblEnterYourUser.setBounds(174, 94, 326, 25);
		contentPane.add(lblEnterYourUser);
		
		btnGoBackTo = new JButton("Cancel");
		btnGoBackTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPage m=new MainPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnGoBackTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGoBackTo.setBounds(337, 270, 118, 33);
		contentPane.add(btnGoBackTo);
		
		JLabel label = new JLabel("WELCOME TO JAVA BANK");
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(212, 44, 243, 38);
		contentPane.add(label);
	}
}
