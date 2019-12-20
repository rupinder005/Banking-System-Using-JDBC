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
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class PayBills extends JFrame {

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
					PayBills frame = new PayBills();
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
	public PayBills() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPayBillFrom = new JLabel("Pay Bill From :");
		lblPayBillFrom.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPayBillFrom.setBounds(167, 164, 86, 24);
		contentPane.add(lblPayBillFrom);
		
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
		checking_radiobtn.setBounds(461, 164, 134, 24);
		contentPane.add(checking_radiobtn);
		
		
		saving_radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saving_radiobtn.isSelected())
				{
					checking_radiobtn.setSelected(false);
				}
			}
		});
		saving_radiobtn.setBounds(321, 165, 121, 23);
		contentPane.add(saving_radiobtn);
		
		
		
		JLabel lblPayBillTo = new JLabel("Pay Bill To :");
		lblPayBillTo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPayBillTo.setBounds(167, 212, 75, 24);
		contentPane.add(lblPayBillTo);
		
		tf_Account = new JTextField();
		tf_Account.setBounds(321, 212, 142, 29);
		contentPane.add(tf_Account);
		tf_Account.setColumns(10);
		
		tf_Amount = new JTextField();
		tf_Amount.setBounds(321, 261, 142, 29);
		contentPane.add(tf_Amount);
		tf_Amount.setColumns(10);
		
		JLabel lblEnterAmount = new JLabel("Enter Amount:");
		lblEnterAmount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEnterAmount.setBounds(167, 262, 86, 17);
		contentPane.add(lblEnterAmount);
		
		JButton btnPay = new JButton("Pay");
		btnPay.addActionListener(new ActionListener() {
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
							JOptionPane.showMessageDialog(null,"You don't have enough balance to pay this Bill!!");
							
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
							}
							else if(rs2.next())
							{
								String query2="Update checking_account set Initial_Balance=Initial_Balance+'"+tf_Amount.getText()+ "' where Account_No='"+tf_Account.getText()+"'";
								PreparedStatement pst2=conn.prepareStatement(query2); 
								pst2.execute();
								pst2.close();
							}
							PreparedStatement pst=conn.prepareStatement(query); 

							pst.execute();
							pst.close();
							JOptionPane.showMessageDialog(null,"Bill payed succesfully!!");
							tf_Account.setText("");
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
							JOptionPane.showMessageDialog(null,"You don't have enough balance to pay this bill!!");
							
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
					}
					else if(rss2.next())
					{
						String query2="Update checking_account set Initial_Balance=Initial_Balance+'"+tf_Amount.getText()+ "' where Account_No='"+tf_Account.getText()+"'";
						PreparedStatement pst2=conn.prepareStatement(query2); 
						pst2.execute();
						pst2.close();
					}
					PreparedStatement pst=conn.prepareStatement(query); 
					pst.execute();
					pst.close();
					JOptionPane.showMessageDialog(null,"Bill payed succesfully!!");
					tf_Account.setText("");
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
		btnPay.setBounds(69, 327, 86, 36);
		contentPane.add(btnPay);
		
		JButton btnCancel = new JButton("Back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserPage u=new UserPage();
				u.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(290, 327, 86, 36);
		contentPane.add(btnCancel);
		
		
		String s=SignInPage.tf_username.getText();
		JLabel label = new JLabel("Welcome "+s);
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(245, 82, 218, 42);
		contentPane.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage m=new SignInPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(509, 327, 86, 36);
		contentPane.add(btnLogout);
	}
}
