import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class UserPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPage frame = new UserPage();
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
	public UserPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lbl_username = new JLabel("");
		lbl_username.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lbl_username.setBounds(209, 35, 218, 42);
		String s=SignInPage.tf_username.getText();
		lbl_username.setText("Welcome "  + s);
		
		contentPane.add(lbl_username);
		
		JButton btnNewButton = new JButton("Show Balance");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 CheckBalance c= new CheckBalance();
			 c.setVisible(true);
			 dispose();
			}
		});
		btnNewButton.setBounds(61, 111, 140, 42);
		contentPane.add(btnNewButton);
		
		JButton btnDepositMoney = new JButton("Deposit Money");
		btnDepositMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Deposit d= new Deposit();
				d.setVisible(true);
				dispose();
			}
		});
		btnDepositMoney.setBounds(421, 111, 140, 42);
		contentPane.add(btnDepositMoney);
		
		JButton btnWithdrawMoney = new JButton("Withdraw Money");
		btnWithdrawMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Withdraw w= new Withdraw();
				w.setVisible(true);
				dispose();
			}
		});
		btnWithdrawMoney.setBounds(61, 194, 140, 42);
		contentPane.add(btnWithdrawMoney);
		
		JButton btnPayMoney = new JButton("Pay Bills");
		btnPayMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PayBills p= new PayBills();
				p.setVisible(true);
				dispose();
			}
		});
		btnPayMoney.setBounds(61, 269, 140, 42);
		contentPane.add(btnPayMoney);
		
		JButton btnTransferMoney = new JButton("Transfer Money");
		btnTransferMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transfer t= new Transfer();
				t.setVisible(true);
				dispose();
			}
		});
		btnTransferMoney.setBounds(421, 194, 140, 42);
		contentPane.add(btnTransferMoney);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage m=new SignInPage();
				m.setVisible(true);
				dispose();
			}
		});
		btnLogOut.setBounds(421, 269, 140, 42);
		contentPane.add(btnLogOut);
	}

}
