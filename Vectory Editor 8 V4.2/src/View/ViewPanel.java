/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import ClassOfObjects.SpecialButton;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author demin
 */
public class ViewPanel extends JPanel{
    private SpecialButton MirrorX=new SpecialButton ("MirrorX");
    private SpecialButton MirrorY=new SpecialButton ("MirrorY");
    private SpecialButton Inversion=new SpecialButton ("Inversion");
    public ViewPanel(){ 
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);  
        this.setLayout(layout);
        layout.setAutoCreateGaps(true); 
        layout.setAutoCreateContainerGaps(true); 
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
              .addComponent(MirrorX)
              .addComponent(MirrorY)
              .addComponent(Inversion)
       );
       layout.setVerticalGroup(layout.createParallelGroup()
          .addComponent(MirrorX)
          .addComponent(MirrorY)
          .addComponent(Inversion)
       );
       Border border = BorderFactory.createLineBorder(Color.pink);
       this.setBorder(border);
       setEnabled(false);
    }
}
