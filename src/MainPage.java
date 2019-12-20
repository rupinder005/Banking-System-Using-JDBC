import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
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
	public MainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomeTo = new JLabel("WELCOME TO JAVA BANK");
		lblWelcomeTo.setForeground(new Color(220, 20, 60));
		lblWelcomeTo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblWelcomeTo.setBounds(181, 33, 243, 38);
		contentPane.add(lblWelcomeTo);
		
		JButton btnNewButton_1 = new JButton("SIGN IN");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				SignInPage s=new SignInPage();
				s.setVisible(true);
				dispose();
				
				
			}
			
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBackground(new Color(95, 158, 160));
		btnNewButton_1.setBounds(230, 192, 136, 38);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("CREATE ACCOUNT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				newUserInfo n=new newUserInfo();
				n.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton.setToolTipText("Click here for new account\r\n");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBackground(new Color(95, 158, 160));
		btnNewButton.setBounds(230, 126, 136, 38);
		contentPane.add(btnNewButton);
	}
}
