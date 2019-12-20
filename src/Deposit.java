import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Deposit extends JFrame {

	private JPanel contentPane;
	private JTextField tf_Amount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deposit frame = new Deposit();
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
	public Deposit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Deposit to:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(162, 185, 79, 14);
		contentPane.add(lblNewLabel);
		
		
		
		
		JRadioButton rb_checking = new JRadioButton("Checking");
		rb_checking.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rb_checking.setBounds(421, 181, 79, 23);
		contentPane.add(rb_checking);
		
		JRadioButton rb_saving = new JRadioButton("Saving");
		rb_saving.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
			java.sql.Statement stmt=conn.createStatement();
			java.sql.Statement stmt2=conn.createStatement();
			
			String sqlstmt="Select * from saving_account where UserName='"+SignInPage.tf_username.getText()+"' and Password='"+SignInPage.tf_Password.getText()+"'";
			ResultSet rs=stmt.executeQuery(sqlstmt);
			
			String sqlstmt2="Select * from checking_account where UserName='"+SignInPage.tf_username.getText()+"' and Password='"+SignInPage.tf_Password.getText()+"'";
			ResultSet rs2=stmt2.executeQuery(sqlstmt2);
			
			if(!rs.next())
			{
				rb_saving.setEnabled(false);
			}
			if(!rs2.next())
			{
				rb_checking.setEnabled(false);
			}
			conn.close();
		}
		catch(Exception e){
			
			System.out.println(e.toString());
		}
		
		
		
		rb_saving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rb_saving.isSelected())
				{
					rb_checking.setSelected(false);
				}
			}
		});
		rb_saving.setBounds(316, 181, 79, 23);
		contentPane.add(rb_saving);
		
		
		rb_checking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rb_checking.isSelected())
				{
					rb_saving.setSelected(false);
				}
			}
		});
		
		
		JLabel lblDepositAmount = new JLabel("Deposit Amount");
		lblDepositAmount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDepositAmount.setBounds(162, 232, 108, 14);
		contentPane.add(lblDepositAmount);
		
		tf_Amount = new JTextField();
		tf_Amount.setBounds(316, 226, 119, 23);
		contentPane.add(tf_Amount);
		tf_Amount.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(rb_saving.isSelected())
				{
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
					
					String query="Update saving_account set Initial_Balance='"+tf_Amount.getText()+ "' + Initial_Balance where UserName='"+SignInPage.tf_username.getText()+"'";
					PreparedStatement pst=conn.prepareStatement(query); 
					pst.execute();
					pst.close();
					JOptionPane.showMessageDialog(null,"Amount Deposited Successfully!!");
					tf_Amount.setText("");
					
				}
				catch(Exception e6)
				{
					System.out.println(e6.toString());
				}
				}
				else if(rb_checking.isSelected())
				{
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
					
					String query="Update checking_account set Initial_Balance='"+tf_Amount.getText()+ "' + Initial_Balance where UserName='"+SignInPage.tf_username.getText()+"'";
					PreparedStatement pst=conn.prepareStatement(query); 
					pst.execute();
					pst.close();
					JOptionPane.showMessageDialog(null,"Amount deposited Successfully!!");
					tf_Amount.setText("");
					
				}
				catch(Exception e6)
				{
					System.out.println(e6.toString());
				}
				}
					
				
			}
		});
		btnDeposit.setBounds(123, 299, 89, 37);
		contentPane.add(btnDeposit);
		
		JButton cancel_btn = new JButton("Back");
		cancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				UserPage u=new UserPage();
				u.setVisible(true);
				dispose();
			}
		});
		cancel_btn.setBounds(260, 299, 89, 37);
		contentPane.add(cancel_btn);
		
		String s=SignInPage.tf_username.getText();
		JLabel label = new JLabel("Welcome "+s);
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(217, 80, 218, 42);
		contentPane.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage m=new SignInPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(421, 299, 89, 37);
		contentPane.add(btnLogout);
	}

}
