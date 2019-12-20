import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.sql.*;
import java.util.*;
import javax.swing.JPasswordField;

public class newUserInfo extends JFrame {

	private JPanel contentPane;
	private JTextField tf_FullName;
	private JTextField tf_ContactNo;
	private JTextField tf_Address;
	private JButton button;
	private JRadioButton Saving;
	private JRadioButton Checking;
	private JLabel label;
	public static JTextField tf_Sav_Balance;
	private JTextField tf_Check_Balance;
	private JLabel lblIntialBalance;
	private JLabel lblEmailAddress_1;
	private JTextField tf_EmailAdd;
	private JPasswordField tf_Password;
	private JButton btnCancel;
	private JLabel lblCreateUsername;
	private JTextField UserName_tf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newUserInfo frame = new newUserInfo();
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
	public newUserInfo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Full Name");
		lblNewLabel.setBounds(99, 130, 72, 25);
		contentPane.add(lblNewLabel);
		
		tf_FullName = new JTextField();
		tf_FullName.setBounds(319, 132, 86, 20);
		contentPane.add(tf_FullName);
		tf_FullName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Contact Number");
		lblNewLabel_1.setBounds(99, 166, 94, 25);
		contentPane.add(lblNewLabel_1);
		
		tf_ContactNo = new JTextField();
		tf_ContactNo.setBounds(319, 169, 86, 20);
		contentPane.add(tf_ContactNo);
		tf_ContactNo.setColumns(10);
		
		tf_Address = new JTextField();
		tf_Address.setBounds(319, 199, 86, 20);
		contentPane.add(tf_Address);
		tf_Address.setColumns(10);
		
		JLabel lblEmailAddress = new JLabel("Address");
		lblEmailAddress.setBounds(99, 197, 72, 25);
		contentPane.add(lblEmailAddress);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(99, 301, 72, 25);
		contentPane.add(lblPassword);
		
		JLabel lblAccountType = new JLabel("Account Type");
		lblAccountType.setBounds(98, 58, 95, 25);
		contentPane.add(lblAccountType);
		
		button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Saving.isSelected())
				{
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
					String sqlstmt="insert into saving_account (Initial_Balance,Full_Name,Contact_No,Address,Email_Address,Password,UserName) values(?,?,?,?,?,?,?)";
					PreparedStatement pst=conn.prepareStatement(sqlstmt);
					pst.setString(1,tf_Sav_Balance.getText() );
					pst.setString(2,tf_FullName.getText() );
					pst.setString(3,tf_ContactNo.getText() );
					pst.setString(4,tf_Address.getText() );
					pst.setString(5,tf_EmailAdd.getText() );
					pst.setString(6,tf_Password.getText() );
					pst.setString(7,UserName_tf.getText() );
					
					pst.execute();
					pst.close();
					
					conn.close();

				}
					catch(Exception e1){
						
						System.out.println(e1.toString());
					}
				}
				if(Checking.isSelected())
				{
					try{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
						String sqlstmt="insert into checking_account (Initial_Balance,Full_Name,Contact_No,Address,Email_Address,Password,UserName) values(?,?,?,?,?,?,?)";
						PreparedStatement pst=conn.prepareStatement(sqlstmt);
						pst.setString(1,tf_Check_Balance.getText() );
						pst.setString(2,tf_FullName.getText() );
						pst.setString(3,tf_ContactNo.getText() );
						pst.setString(4,tf_Address.getText() );
						pst.setString(5,tf_EmailAdd.getText() );
						pst.setString(6,tf_Password.getText() );
						pst.setString(7,UserName_tf.getText() );
						
						pst.execute();
						pst.close();
						
						conn.close();

					}
						catch(Exception e1){
							
							System.out.println(e1.toString());
						}
				}
				JOptionPane.showMessageDialog(null,"Your Account is created Succefully..Click OK to Sign in!!");
				MainPage m=new MainPage();
				m.setVisible(true);
				dispose();
			}
		});
		button.setBounds(144, 358, 89, 23);
		contentPane.add(button);
		
		Saving = new JRadioButton("Saving");
		Saving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Saving.isSelected())
				{
					tf_Sav_Balance.setVisible(true);
					lblIntialBalance.setVisible(true);
				}
				else if(!Saving.isSelected())
				{
					tf_Sav_Balance.setVisible(false);
				}
			}
		});
		Saving.setBounds(315, 58, 109, 23);
		contentPane.add(Saving);
		
		Checking = new JRadioButton("Checking");
		Checking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Checking.isSelected())
				{
					tf_Check_Balance.setVisible(true);
					lblIntialBalance.setVisible(true);
				}
				else if(!Checking.isSelected())
				{
					tf_Check_Balance.setVisible(false);
				}
			}
		});
		Checking.setBounds(425, 59, 109, 23);
		contentPane.add(Checking);
		
		label = new JLabel("WELCOME TO JAVA BANK");
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(200, 11, 243, 38);
		contentPane.add(label);
		
		tf_Sav_Balance = new JTextField();
		tf_Sav_Balance.setColumns(10);
		tf_Sav_Balance.setBounds(319, 101, 86, 20);
		tf_Sav_Balance.setVisible(false);
		contentPane.add(tf_Sav_Balance);
		
		tf_Check_Balance = new JTextField();
		tf_Check_Balance.setColumns(10);
		tf_Check_Balance.setBounds(434, 101, 86, 20);
		tf_Check_Balance.setVisible(false);
		contentPane.add(tf_Check_Balance);
		
		lblIntialBalance = new JLabel("Intial Balance");
		lblIntialBalance.setBounds(99, 94, 86, 25);
		lblIntialBalance.setVisible(false);
		contentPane.add(lblIntialBalance);
		
		lblEmailAddress_1 = new JLabel("Email Address");
		lblEmailAddress_1.setBounds(99, 228, 72, 25);
		contentPane.add(lblEmailAddress_1);
		
		tf_EmailAdd = new JTextField();
		tf_EmailAdd.setColumns(10);
		tf_EmailAdd.setBounds(319, 230, 86, 20);
		contentPane.add(tf_EmailAdd);
		
		tf_Password = new JPasswordField();
		tf_Password.setBounds(319, 301, 86, 20);
		contentPane.add(tf_Password);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			MainPage m=new MainPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(316, 358, 89, 23);
		contentPane.add(btnCancel);
		
		lblCreateUsername = new JLabel("UserName");
		lblCreateUsername.setBounds(99, 267, 72, 25);
		contentPane.add(lblCreateUsername);
		
		UserName_tf = new JTextField();
		UserName_tf.setColumns(10);
		UserName_tf.setBounds(319, 269, 86, 20);
		contentPane.add(UserName_tf);
	}
}
