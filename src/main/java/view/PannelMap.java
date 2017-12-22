package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.objects.EnumTerritory;


public class PannelMap extends JPanel {
	
	private JLabel label;
	private JLabel[][] tabLabel;

	public PannelMap(ScreenMap screenMap) // ça comment à etre le bordel
	{
		// tester si la map n'est pas crée puis la crée
		
		System.out.println(screenMap.getGame().getTableTerritory().length);
		
		
		setLayout(new GridLayout(screenMap.getGame().getTableTerritory().length,screenMap.getGame().getTableTerritory().length)); 
		
		tabLabel = new JLabel[screenMap.getGame().getTableTerritory().length][screenMap.getGame().getTableTerritory()[0].length];
		
		for (int i = 0;i < tabLabel.length ; i++) 
		{
			for (int j = 0; j < tabLabel[i].length; j++) 
			{
				label = new JLabel(Integer.toString(i)); // imageicon en fonction du territoire ?
				label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
				
				if(screenMap.getGame().getTableTerritory()[i][j] != null)
				{
					switch (screenMap.getGame().getTableTerritory()[i][j].getEnumTerritoryType()) {
					case  PLAIN:
						label.setBackground(Color.GREEN);
						break;
					case MOUNTAIN:
						label.setBackground(Color.YELLOW);
						break;
					case FIELD:
						label.setBackground(Color.blue);
						break;
					default:
						break;
					}
				}

				label.addMouseListener(new ControleurMap(i,j,this,screenMap));
				tabLabel[i][j] = label;
				add(label);
			}
		}
	}
	
	public JLabel[][] getListlabel()
	{
		return tabLabel;
	}
	


}
