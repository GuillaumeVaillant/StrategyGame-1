package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelAction extends JPanel {
	
	private JButton buttonAttakCity;
	private JButton buttonCreateArmy;
	private JButton buttonCreateCity;
	private JButton buttonMoveArmy;

	public PanelAction(ScreenMap view)
	{
		GridBagConstraints contrainte = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		
		contrainte.gridx = 0;
		contrainte.gridy = 0;
		
		buttonAttakCity = new JButton("Attak City");
		buttonAttakCity.addActionListener(new ControleurAction(this));
		add(buttonAttakCity,contrainte);
		
		contrainte.gridx = 1;
		contrainte.gridy = 0;
		
		buttonCreateArmy = new JButton("Create Army");
		buttonCreateArmy.addActionListener(new ControleurAction(this));
		add(buttonCreateArmy,contrainte);
		
		contrainte.gridx = 2;
		contrainte.gridy = 0;
		
		buttonCreateCity = new JButton("Create City");
		buttonCreateCity.addActionListener(new ControleurAction(this));
		add(buttonCreateCity,contrainte);
		
		contrainte.gridx = 3;
		contrainte.gridy = 0;
		
		buttonMoveArmy = new JButton("Move Army");
		buttonMoveArmy.addActionListener(new ControleurAction(this));
		add(buttonMoveArmy,contrainte);
		
	}

	public JButton getButtonAttakCity() {
		return buttonAttakCity;
	}

	public JButton getButtonCreateArmy() {
		return buttonCreateArmy;
	}

	public JButton getButtonCreateCity() {
		return buttonCreateCity;
	}

	public JButton getButtonMoveArmy() {
		return buttonMoveArmy;
	}

}
