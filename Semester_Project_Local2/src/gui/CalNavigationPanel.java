package gui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CalNavigationPanel
  extends JPanel
{
  private JButton todayBtn;
  private JButton viewBtn;
  private JButton nextBtn;
  private JButton prevBtn;
  
  public CalNavigationPanel()
  {
    this.viewBtn = new JButton("View");
    this.viewBtn.setBackground(Color.black);
    this.viewBtn.setForeground(Color.green);
    this.prevBtn = new JButton("<");
    this.prevBtn.setBackground(Color.black);
    this.prevBtn.setForeground(Color.green);
    this.nextBtn = new JButton(">");
    this.nextBtn.setBackground(Color.black);
    this.nextBtn.setForeground(Color.green);
    
    add(this.prevBtn);
    add(this.viewBtn);
    add(this.nextBtn);
  }
  
  public JButton getNextButton()
  {
    return this.nextBtn;
  }
  
  public JButton getViewButton()
  {
    return this.viewBtn;
  }
  
  public JButton getTodayButton()
  {
    return this.todayBtn;
  }
  
  public JButton getPrevButton()
  {
    return this.prevBtn;
  }
}
