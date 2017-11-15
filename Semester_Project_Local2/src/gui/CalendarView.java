package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CalendarView
  extends JPanel
{
  private DAYS[] arrayOfDays = DAYS.values();
  private MONTHS[] arrayOfMonths = MONTHS.values();
  private GregorianCalendar cal;
  private int width;
  private int height;
  private JLabel currMonth;
  private JButton selButton;
  JButton currButton;
  int dateSelected;
  private ButtonColorStrat colorStrat;
  
  public CalendarView(GregorianCalendar cal, int width, int height, ButtonColorStrat colorStrat)
  {
    this.colorStrat = colorStrat;
    this.cal = cal;
    this.height = height;
    this.width = width;
    
    setLayout(new BoxLayout(this, 3));
    
    setPreferredSize(new Dimension(width, height));
    setMaximumSize(new Dimension(width, height));
    
    this.currMonth = new JLabel(this.arrayOfMonths[cal.get(2)] + "-- " + cal.get(1));
    this.currMonth.setAlignmentX(0.5F);
    this.currMonth.setForeground(Color.green);
    
    add(this.currMonth);
    
    add(drawCalendar(cal));
  }
  
  public CalendarView(GregorianCalendar cal, int w, int h)
  {
    this(cal, w, h, new DefaultColorStrat());
  }
  
  public void select(JButton b, int d)
  {
    if (this.selButton != null) {
      this.colorStrat.color(this.selButton, this.dateSelected, this.cal, false, Color.black);
    }
    this.selButton = b;
    this.dateSelected = d;
    this.colorStrat.color(this.selButton, this.dateSelected, this.cal, true);
  }
  
  private void repaintCalendar()
  {
    this.currMonth.setText(this.arrayOfMonths[this.cal.get(2)].toString() + "; " + this.cal.get(1));
    repaint();
    remove(1);
    add(drawCalendar(this.cal));
  }
  
  public void prevDay()
  {
    this.cal.add(5, -1);
    select(this.currButton, this.cal.get(5));
    repaintCalendar();
  }
  
  public void nextDay()
  {
    this.cal.add(5, 1);
    select(this.currButton, this.cal.get(5));
    repaintCalendar();
  }
  
  private JPanel drawCalendar(Calendar c)
  {
    int initialDay = c.get(5);
    c.set(5, 1);
    int buttonsAdded = 0;
    
    int nRows = 6;
    if (c.getActualMaximum(5) == 31)
    {
      if ((c.get(7) == 6) || (c.get(7) == 7)) {
        nRows = 7;
      }
    }
    else if (c.getActualMaximum(5) == 30)
    {
      if (c.get(7) == 7) {
        nRows = 7;
      }
    }
    else {
      nRows = 6;
    }
    
    JPanel calDays = new JPanel(new GridLayout(nRows, 7));
    int w = (int)(this.width / 1.2D);
    int h = (int)(this.height / 1.2D);
    calDays.setPreferredSize(new Dimension(w, h));
    calDays.setMaximumSize(new Dimension(w, h));
    
    for (int j = 0; j < this.arrayOfDays.length; buttonsAdded++)
    {
      JButton kButton = new JButton();
      kButton.setText(this.arrayOfDays[j].toString());
      kButton.setBackground(Color.black);
      kButton.setForeground(Color.gray);
      kButton.setEnabled(false);
//		Add font here?      
      
      calDays.add(kButton);j++;
    }
    
    int start = c.get(7) - 1;
    for (int j = 0; j < start; buttonsAdded++)
    {
      JButton dummyButton = new JButton("");
      dummyButton.setEnabled(false);
      dummyButton.setBackground(Color.BLACK);
      calDays.add(dummyButton);j++;
    }
    
    for (int i = 1; i <= c.getActualMaximum(5); buttonsAdded++)
    {
      final JButton dayButton = new JButton();
      dayButton.setText("" + i);
      dayButton.setBackground(Color.green);
      dayButton.setForeground(Color.gray);
      calDays.add(dayButton);
      final int day = i;
      this.currButton = dayButton;
      dayButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          CalendarView.this.cal.set(5, day);
          CalendarView.this.currButton = dayButton;
          CalendarView.this.select(CalendarView.this.currButton, CalendarView.this.cal.get(5));
        }
      });
      boolean selected = (day == this.dateSelected) || ((this.dateSelected > c.getActualMaximum(5)) && 
        (day == c.getActualMaximum(5)));
      
      this.colorStrat.color(dayButton, day, this.cal, selected, Color.black);
      if (selected) {
        this.selButton = dayButton;
      }
      i++;
    }
    
    for (; buttonsAdded < nRows * 7; buttonsAdded++)
    {
      JButton dummyButton = new JButton("");
      dummyButton.setEnabled(false);
      dummyButton.setBackground(Color.BLACK);
      calDays.add(dummyButton);
    }
    c.set(5, initialDay);
    
    return calDays;
  }
}