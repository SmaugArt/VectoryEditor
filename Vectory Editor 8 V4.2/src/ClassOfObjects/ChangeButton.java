/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassOfObjects;

import View.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author user
 */
public class ChangeButton extends JButton implements ActionListener {
    MainWindow base; 
    public String type = "Rotation";

    public ChangeButton(String type, MainWindow base) {
        this.base = base; 
        this.type = type;
        this.addActionListener(this);  
    }

    @Override public void actionPerformed(ActionEvent e) {
        //base.ActiveShape=null;
        switch(this.type) {
           case "Rotation": 
	         base.typeOfOperation=2;
	      break;
	   case "ClickObject": 
	         base.typeOfOperation=3;
	      break;
           case "HolstScale and Move": 
	         base.typeOfOperation=4;
	      break;
           case "Deformation": 
	         base.typeOfOperation=5;
	      break;
        }
        if (base.positionSelectedShape>=base.ShapeAL.size()-1 & base.positionSelectedShape<=base.ShapeAL.size()-1 & base.positionSelectedShape>=0){
            base.drawPanel.drawBuffer(base.ShapeAL, base.ActiveShape);     //1 раз отрисовываю
            base.drawPanel.repaint();
        }
    } 
}
