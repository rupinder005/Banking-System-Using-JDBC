import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Transfer extends JFrame {

	private JPanel contentPane;
	private JTextField tf_Account;
	private JTextField tf_Amount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transfer frame = new Transfer();
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
	public Transfer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterAccountNumber = new JLabel("To Account Number  :");
		lblEnterAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterAccountNumber.setBounds(123, 187, 137, 33);
		contentPane.add(lblEnterAccountNumber);
		
		tf_Account = new JTextField();
		tf_Account.setBounds(314, 189, 102, 28);
		contentPane.add(tf_Account);
		tf_Account.setColumns(10);
		
		JLabel lblTransferFrom = new JLabel("Transfer From :");
		lblTransferFrom.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTransferFrom.setBounds(123, 133, 95, 33);
		contentPane.add(lblTransferFrom);
		
		JRadioButton saving_radiobtn = new JRadioButton("Saving Account");
		saving_radiobtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		JRadioButton checking_radiobtn = new JRadioButton("Checking Account");
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
		
		
		
		checking_radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checking_radiobtn.isSelected())
				{
					saving_radiobtn.setSelected(false);
				}
			}
		});
		checking_radiobtn.setBounds(448, 134, 137, 31);
		contentPane.add(checking_radiobtn);
		
		
		saving_radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saving_radiobtn.isSelected())
				{
					checking_radiobtn.setSelected(false);
				}
			}
		});
		saving_radiobtn.setBounds(302, 132, 114, 34);
		contentPane.add(saving_radiobtn);
		
		
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
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
							JOptionPane.showMessageDialog(null,"You don't have enough balance to transfer this amount!!");
							tf_Account.setText("");
							tf_Amount.setText("");
							
						}
						else
						{
							String query="Update saving_account set Initial_Balance=Initial_Balance-'"+tf_Amount.getText()+ "' where UserName='"+SignInPage.tf_username.getText()+"'";
							Statement stmt1=conn.createStatement();
							Statement stmt2=conn.createStatement();
							
							
							String sqlstmt1="Select * from saving_account where Account_No='"+tf_Account.getText()+"'";
							ResultSet rs1=stmt1.executeQuery(sqlstmt1);
							
							String sqlstmt2="Select * from checking_account where Account_No='"+tf_Account.getText()+"'";
							ResultSet rs2=stmt2.executeQuery(sqlstmt2);
							
							if(rs1.next())
							{
								String query1="Update saving_account set Initial_Balance=Initial_Balance+'"+tf_Amount.getText()+ "' where Account_No='"+tf_Account.getText()+"'";
								PreparedStatement pst1=conn.prepareStatement(query1); 
								pst1.execute();
								pst1.close();
								PreparedStatement pst=conn.prepareStatement(query); 
								pst.execute();
								pst.close();
								JOptionPane.showMessageDialog(null,"Amount is transferred succesfully!!");
								tf_Account.setText("");
								tf_Amount.setText("");
							}
							else if(rs2.next())
							{
								String query2="Update checking_account set Initial_Balance=Initial_Balance+'"+tf_Amount.getText()+ "' where Account_No='"+tf_Account.getText()+"'";
								PreparedStatement pst2=conn.prepareStatement(query2); 
								pst2.execute();
								pst2.close();
								PreparedStatement pst=conn.prepareStatement(query); 
								pst.execute();
								pst.close();
								JOptionPane.showMessageDialog(null,"Amount is transferred succesfully!!");
								tf_Account.setText("");
								tf_Amount.setText("");
							}
							else 
							{
								JOptionPane.showMessageDialog(null,"Please enter a valid account!!");
								tf_Account.setText("");
								tf_Amount.setText("");
							}
							
							
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
							JOptionPane.showMessageDialog(null,"You don't have enough balance to transfer this amount!!");
							tf_Account.setText("");
							tf_Amount.setText("");
							
						}
						else
						{
					
					String query="Update checking_account set Initial_Balance=Initial_Balance-'"+tf_Amount.getText()+ "' where UserName='"+SignInPage.tf_username.getText()+"'";
					Statement stmtt=conn.createStatement();
					Statement stmtt2=conn.createStatement();
					
					
					String sqlstmtt="Select * from saving_account where Account_No='"+tf_Account.getText()+"'";
					ResultSet rss=stmtt.executeQuery(sqlstmtt);
					
					String sqlstmtt2="Select * from checking_account where Account_No='"+tf_Account.getText()+"'";
					ResultSet rss2=stmtt2.executeQuery(sqlstmtt2);
					
					if(rss.next())
					{
						String query1="Update saving_account set Initial_Balance=Initial_Balance+'"+tf_Amount.getText()+ "' where Account_No='"+tf_Account.getText()+"'";
						PreparedStatement pst1=conn.prepareStatement(query1); 
						pst1.execute();
						pst1.close();
						PreparedStatement pst=conn.prepareStatement(query); 
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null,"Amount is transferred succesfully!!");
						tf_Account.setText("");
						tf_Amount.setText("");
					}
					else if(rss2.next())
					{
						String query2="Update checking_account set Initial_Balance=Initial_Balance+'"+tf_Amount.getText()+ "' where Account_No='"+tf_Account.getText()+"'";
						PreparedStatement pst2=conn.prepareStatement(query2); 
						pst2.execute();
						pst2.close();
						PreparedStatement pst=conn.prepareStatement(query); 
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null,"Amount is transferred succesfully!!");
						tf_Account.setText("");
						tf_Amount.setText("");
					}
					else 
					{
						JOptionPane.showMessageDialog(null,"Please enter a valid account!!");
						tf_Account.setText("");
						tf_Amount.setText("");
					}
					
					
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
		btnTransfer.setBounds(66, 311, 89, 33);
		contentPane.add(btnTransfer);
		
		JButton btnCancel = new JButton("Back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserPage u=new UserPage();
				u.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(274, 311, 89, 33);
		contentPane.add(btnCancel);
		
		JLabel lblEnterAmount = new JLabel("Enter Amount :");
		lblEnterAmount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterAmount.setBounds(123, 243, 118, 33);
		contentPane.add(lblEnterAmount);
		
		tf_Amount = new JTextField();
		tf_Amount.setColumns(10);
		tf_Amount.setBounds(314, 245, 102, 28);
		contentPane.add(tf_Amount);
		
		String s=SignInPage.tf_username.getText();
		JLabel label = new JLabel("Welcome "+s);
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(220, 47, 218, 42);
		contentPane.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage m=new SignInPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(480, 311, 95, 33);
		contentPane.add(btnLogout);
	}

}
