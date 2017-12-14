package gui;

import java.awt.Color;
import java.util.GregorianCalendar;

import javax.swing.JButton;

public abstract interface ButtonColorStrat
{
  public abstract void color(JButton paramJButton, int paramInt, GregorianCalendar paramCalendarWithEvents, boolean paramBoolean);
  
  public abstract void color(JButton paramJButton, int paramInt, GregorianCalendar paramCalendarWithEvents, boolean paramBoolean, Color paramColor);
}
