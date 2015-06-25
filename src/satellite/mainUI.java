package satellite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class mainUI extends JFrame{
	DBaction db = new DBaction();
	public mainUI()
	{
		
	    BorderLayout borderLayout = new BorderLayout(0,30);
		this.setLayout(borderLayout);
		JButton login = new JButton("µ«¬Ω");
		JLabel text = new JLabel("Œ¿–«‘§æØœµÕ≥");
		login.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		final JTextField id = new JTextField(15);
		final JPasswordField password = new JPasswordField(15);
		FlowLayout f1 = new FlowLayout(FlowLayout.LEFT,50,0);
		JPanel p1=new JPanel(f1);
		p1.add(new JLabel("’À∫≈£∫"));
		id.setHorizontalAlignment(JTextField.LEFT);
		p1.add(id);
		JPanel p2=new JPanel(new FlowLayout(FlowLayout.LEFT,50,0));
		p2.add(new JLabel("√‹¬Î£∫"));
		password.setHorizontalAlignment(JTextField.LEFT);
		p2.add(password); 
		JPanel p3 = new JPanel(new GridLayout(2,1));
		p3.add(p1);
		p3.add(p2);
		add(p3,borderLayout.CENTER);
		JPanel login_button =new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
		JPanel text_show = new JPanel(new FlowLayout(FlowLayout.CENTER));
		login.setFont(new Font("",Font.BOLD,15));
		text.setFont(new Font("",Font.BOLD,20));
		text_show.add(text);
		login_button.add(login);
		add(login_button, borderLayout.SOUTH);
		add(text_show,borderLayout.NORTH);
		this.pack();
		this.setTitle("Satallite System login");
		this.setSize(400,250);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
		login.addActionListener(
					
				
				new ActionListener(){
						public void actionPerformed(ActionEvent e){
							String userid = id.getText();
							String passwd = password.getText();
							if(userid.isEmpty()||passwd.isEmpty())
							{
								JOptionPane.showMessageDialog(rootPane, "«Î ‰»ÎÕÍ’˚µƒ’À∫≈∫Õ√‹¬Î£°");	
							}
							else
							{
								
									if(db.logincheck(userid, passwd))
									{
										dispose();
										loginUI ui2 = new loginUI();
										
										
									}
									else
									{
										JOptionPane.showMessageDialog(rootPane, "«Î ‰»Î’˝»∑µƒ’À∫≈£¨√‹¬Î£°");
										id.setText("");
										password.setText("");
									}
								} 
								
								
								
								
										
								
							}
						}
							
				);

	}

	public static void main(String[] args) 
	{
		mainUI frame=new mainUI();	
	}
	

}
