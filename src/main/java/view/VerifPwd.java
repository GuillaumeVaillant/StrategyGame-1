package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VerifPwd extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPasswordField pwd = null;
	private JButton valider = null;
	public JLabel error = null;
	private JTextField login;
	
	public VerifPwd()
	{
		GridLayout gl = new GridLayout();
		gl.setColumns(2);
		gl.setRows(3);
		setLayout(gl);
		
		JLabel jLabel0 =new JLabel("Login ID : "); 
		this.login = new JTextField(20);
		
		this.error =new JLabel(""); 
		this.valider = new JButton("se connecter");
		
		this.valider.addActionListener(new ControlerVerif (this));
		
		add(jLabel0);
		add(this.login);
		add(this.error);
		add(this.valider);
	}
	
	public String getPassword()
	{
		return String.valueOf(this.pwd.getPassword());
	}
	
	public String getLogin()
	{
		return this.login.getText();
	}
	
	public void setError(String message)
	{
		this.error.setText(message);
	}
}
