import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Statement;
import java.awt.Font;

public class CheckBalance extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckBalance frame = new CheckBalance();
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
	public CheckBalance() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Check Balance From:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(120, 136, 146, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lbl_Balance = new JLabel("");
		lbl_Balance.setBounds(320, 174, 72, 14);
		contentPane.add(lbl_Balance);
		
		JRadioButton rb_checking = new JRadioButton("Checking");
		rb_checking.setFont(new Font("Tahoma", Font.PLAIN, 13));
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
		
		
		
		
		rb_checking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rb_checking.isSelected())
				{
					rb_saving.setSelected(false);
				}
			}
		});
		rb_checking.setBounds(431, 132, 109, 23);
		contentPane.add(rb_checking);
		
		rb_saving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rb_saving.isSelected())
				{
					rb_checking.setSelected(false);
				}
			}
		});
		rb_saving.setBounds(320, 132, 72, 23);
		contentPane.add(rb_saving);
		
		
		
		JButton btnShowBalance = new JButton("Show Balance");
		btnShowBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(rb_saving.isSelected())
				{
					try{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
						java.sql.Statement stmt=conn.createStatement();
						String sqlstmt="Select Initial_Balance from saving_account where UserName=?";
						PreparedStatement pst=conn.prepareStatement(sqlstmt);
						pst.setString(1,SignInPage.tf_username.getText());
						ResultSet rs=pst.executeQuery();
						if(rs.next()) {
							lbl_Balance.setText(rs.getString("Initial_Balance"));
						}
						conn.close();

					}
						catch(Exception e4){
							
							System.out.println(e4.toString());
						}
				}
				if(rb_checking.isSelected())
				{
					try{
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_system","root","");
						java.sql.Statement stmt=conn.createStatement();
						String sqlstmt="Select Initial_Balance from checking_account where UserName=?";
						PreparedStatement pst=conn.prepareStatement(sqlstmt);
						pst.setString(1,SignInPage.tf_username.getText());
						ResultSet rs=pst.executeQuery();
						if(rs.next()) {
							lbl_Balance.setText(rs.getString("Initial_Balance"));
						}
						conn.close();

					}
						catch(Exception e5){
							
							System.out.println(e5.toString());
						}
				}
			}
		});
		btnShowBalance.setBounds(80, 242, 122, 33);
		contentPane.add(btnShowBalance);
		
		JLabel lblNewLabel_1 = new JLabel("Your balance is:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(120, 174, 109, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnCancel = new JButton("Back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				UserPage u=new UserPage();
				u.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(289, 242, 103, 33);
		contentPane.add(btnCancel);
		String s=SignInPage.tf_username.getText();
		JLabel label = new JLabel("Welcome "+s);
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		label.setBounds(210, 44, 218, 42);
		contentPane.add(label);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage m=new SignInPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(461, 242, 109, 33);
		contentPane.add(btnLogout);
		
		
	}

}
