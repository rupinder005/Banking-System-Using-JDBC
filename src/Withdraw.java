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

public class Withdraw extends JFrame {

	private JPanel tf_withdraw;
	private JTextField tf_Amount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Withdraw frame = new Withdraw();
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
	public Withdraw() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		tf_withdraw = new JPanel();
		tf_withdraw.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(tf_withdraw);
		tf_withdraw.setLayout(null);
		
		JLabel lblWidhdrawFrom = new JLabel("Widhdraw From :");
		lblWidhdrawFrom.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWidhdrawFrom.setBounds(146, 151, 103, 26);
		tf_withdraw.add(lblWidhdrawFrom);
		
		JRadioButton saving_radiobtn = new JRadioButton("Saving");
		saving_radiobtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		JRadioButton checking_radiobtn = new JRadioButton("Checking");
		checking_radiobtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
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
				saving_radiobtn.setEnabled(false);
			}
			if(!rs2.next())
			{
				checking_radiobtn.setEnabled(false);
			}
			conn.close();
		}
		catch(Exception e){
			
			System.out.println(e.toString());
		}
		
		
		
		
		saving_radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saving_radiobtn.isSelected())
				{
					checking_radiobtn.setSelected(false);
				}
			}
		});
		saving_radiobtn.setBounds(320, 153, 109, 23);
		tf_withdraw.add(saving_radiobtn);
		
		
		checking_radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checking_radiobtn.isSelected())
				{
					saving_radiobtn.setSelected(false);
				}
			}
		});
		checking_radiobtn.setBounds(431, 153, 109, 23);
		tf_withdraw.add(checking_radiobtn);
		
		JLabel lblWidhdrawAmount = new JLabel("Widhdraw Amount :");
		lblWidhdrawAmount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWidhdrawAmount.setBounds(148, 222, 120, 26);
		tf_withdraw.add(lblWidhdrawAmount);
		
		tf_Amount = new JTextField();
		tf_Amount.setBounds(320, 224, 120, 26);
		tf_withdraw.add(tf_Amount);
		tf_Amount.setColumns(10);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saving_radiobtn.isSelected())
				{
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
					java.sql.Statement stmt=conn.createStatement();
					
					String sqlstmt="Select Initial_Balance from saving_account where UserName='"+SignInPage.tf_username.getText()+"'";
					ResultSet rs=stmt.executeQuery(sqlstmt);
					Float tf=Float.parseFloat(tf_Amount.getText());
					
					while(rs.next())
					{
						Float bal=Float.parseFloat(rs.getString("Initial_Balance"));
						if(bal<tf)
						{
							JOptionPane.showMessageDialog(null,"You don't have enough balance to withdraw this amount!!");
							tf_Amount.setText("");
							
						}
						else
						{
							String query="Update saving_account set Initial_Balance=Initial_Balance-'"+tf_Amount.getText()+ "' where UserName='"+SignInPage.tf_username.getText()+"'";
							PreparedStatement pst=conn.prepareStatement(query); 
							pst.execute();
							pst.close();
							JOptionPane.showMessageDialog(null,"Amount Withdrawn Successfully!!");
							tf_Amount.setText("");
						}
						
					}
					
					
					
				}
				catch(Exception e6)
				{
					System.out.println(e6.toString());
				}
				}
				else if(checking_radiobtn.isSelected())
				{
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
					
					java.sql.Statement stmt=conn.createStatement();
					String sqlstmt="Select Initial_Balance from checking_account where UserName='"+SignInPage.tf_username.getText()+"'";
					ResultSet rs=stmt.executeQuery(sqlstmt);
					Float tf=Float.parseFloat(tf_Amount.getText());
					
					while(rs.next())
					{
						Float bal=Float.parseFloat(rs.getString("Initial_Balance"));
						if(bal<tf)
						{
							JOptionPane.showMessageDialog(null,"You don't have enough balance to withdraw this amount!!");
							tf_Amount.setText("");
							
						}
						else
						{
							String query="Update checking_account set Initial_Balance=Initial_Balance-'"+tf_Amount.getText()+ "' where UserName='"+SignInPage.tf_username.getText()+"'";
							PreparedStatement pst=conn.prepareStatement(query); 
							pst.execute();
							pst.close();
							JOptionPane.showMessageDialog(null,"Amount Withdrawn Successfully!!");
							tf_Amount.setText("");
						}
						
					}
					
					
					
				}
				catch(Exception e6)
				{
					System.out.println(e6.toString());
				}
				}
					
				
				
			}
		});
		btnWithdraw.setBounds(57, 316, 89, 37);
		tf_withdraw.add(btnWithdraw);
		
		JButton btnCancel = new JButton("Back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserPage u=new UserPage();
				u.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(283, 313, 103, 42);
		tf_withdraw.add(btnCancel);
		
		String s=SignInPage.tf_username.getText();
		JLabel label = new JLabel("Welcome "+s);
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(222, 67, 218, 42);
		tf_withdraw.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage m=new SignInPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(502, 316, 89, 37);
		tf_withdraw.add(btnLogout);
	}

}
