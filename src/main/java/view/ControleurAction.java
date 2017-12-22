package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurAction implements ActionListener {

	public PanelAction panel;
	
	public ControleurAction(PanelAction panelAction)
	{
		this.panel = panelAction;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.panel.getButtonAttakCity())
		{
			System.out.println("action");
		}
		
		if(e.getSource() == this.panel.getButtonCreateArmy())
		{
			System.out.println("action");
		}
		
		if(e.getSource() == this.panel.getButtonCreateCity())
		{
			System.out.println("action");
		}
		
		if(e.getSource() == this.panel.getButtonMoveArmy())
		{
			System.out.println("action");
		}

	}

}
