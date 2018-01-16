package view;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScreenConnection extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScreenConnection()
	{
		JPanel pp = new JPanel();
		
	    setSize(600,150);
	    setLocation(100,100); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ecran connexion");
		
		JPanel panel = new VerifPwd();
		
		pp.add(panel);
			
		pp.setLayout(new FlowLayout()); 
		
		add(pp);
		setContentPane(pp);  
	}
}
