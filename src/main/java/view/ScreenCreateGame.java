package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import domain.objects.Game;
import domain.objects.Player;
import job.JobGame;
import persistence.mapper.GameMapper;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

public class ScreenCreateGame extends JFrame {
	
	private String type;
	JobGame jobGame;
	public ScreenCreateGame(Player player)
	{

		
		setSize(500,300);
	    setLocation(100,100); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Parametrer votre partie");
		
		setLayout(new GridLayout(8,2));
		
		String[] tab = {"Mountain","Field","Plains" };
		type = "Mountain";
		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
		
		JLabel label0 = new JLabel("Nom de la partie");
		label0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JTextField text0 = new JTextField(20);
		add(label0);
		add(text0);
		
		JLabel label1 = new JLabel("Type de partie");
		label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JComboBox combo1 = new JComboBox(tab);
		combo1.addItemListener(new ItemState());
		add(label1);
		add(combo1);
		
		JLabel label2 = new JLabel("Nombre de tours");
		label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JFormattedTextField text2 = new JFormattedTextField(formatter);
		add(label2);
		add(text2);
		
		JLabel label3 = new JLabel("Taille de la carte au carré");
		label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JFormattedTextField text1 = new JFormattedTextField(formatter);
		add(label3);
		add(text1);
		
		JLabel label4 = new JLabel("Max joueur");
		label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JFormattedTextField text4 = new JFormattedTextField(formatter);
		add(label4);
		add(text4);
		
		JLabel label5 = new JLabel("Ressource par joueur");
		label5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JFormattedTextField text5 = new JFormattedTextField(formatter);
		add(label5);
		add(text5);
		
		JLabel label6 = new JLabel("Ressource par tour");
		label6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		JFormattedTextField text6 = new JFormattedTextField(formatter);
		add(label6);
		add(text6);
		
		JPanel pan = new JPanel();
		JLabel jl = new JLabel("");
		JButton valider = new JButton("Valider");
		pan.add(valider);
		add(jl);
		add(pan);
		
		valider.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(text0.getText() + "\n" + type + "\n" + text2.getValue() + "\n" + text1.getValue()+ "\n" + text4.getValue()+ "\n" + text5.getValue() + "\n" + text6.getValue());
				//MenuSetting setting = new MenuSetting(0, text0.getText(),type,(int) text2.getValue(),(int) text1.getValue(),(int) text4.getValue(),(int) text5.getValue(),(int) text6.getValue(),null);
				
				jobGame = new JobGame();
				jobGame.createGame(text0.getText(),player,(int) text2.getValue(),"WAITING",(int) text6.getValue(),12);
				
				System.out.println("test");
				//new ScreenMap(game);	
			}
		});
		
	}
	
	public void fermerAppli()
	{
		super.dispose();
	}
	
	class ItemState implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			type = (String) e.getItem();
			
		}
		
	}

}
