/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ShapeArrayList;
import java.awt.Color;

/**
 *
 * @author demin
 */
public interface Properties {
    public void UpgradeComboBox ();
    public void loadProperties(int id_Shape);  //загружает настройки из выбранного id шейпа
    public void applyTextFieldChange (); //применяет основные изменения
    public void deleteShape (int id_Shape); 
    public void changeMaxX (int id_Shape, double NewMaxX);
    public void changeMinX (int id_Shape, double NewMinX);
    public void changeMinY (int id_Shape, double NewMinY);
    public void changeMaxY (int id_Shape, double NewMaxY); 
    public void changeName (int id_Shape, String NewName);
    public void changeVisible (int id_Shape);
    public void changeID (int id_Shape ,int new_id);
}
