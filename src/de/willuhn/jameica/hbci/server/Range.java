/**********************************************************************
 *
 * Copyright (c) 2004 Olaf Willuhn
 * All rights reserved.
 * 
 * This software is copyrighted work licensed under the terms of the
 * Jameica License.  Please consult the file "LICENSE" for details. 
 *
 **********************************************************************/

package de.willuhn.jameica.hbci.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.willuhn.jameica.hbci.HBCI;
import de.willuhn.jameica.system.Application;
import de.willuhn.jameica.util.DateUtil;
import de.willuhn.util.I18N;

/**
 * Bean mit moeglichen Zeitraeumen.
 */
public abstract class Range
{
  private final static transient I18N i18n = Application.getPluginLoader().getPlugin(HBCI.class).getResources().getI18N();
  
  /**
   * Bekannte Zeitraeume.
   */
  public final static List<Range> KNOWN = new ArrayList<Range>()
  {{
    add(new LastSevenDays());
    add(new LastThirtyDays());
    add(new ThisWeek());
    add(new LastWeek());
    add(new SecondLastWeek());
    add(new ThisMonth());
    add(new LastMonth());
    add(new SecondLastMonth());
    add(new Last12Months());
    add(new ThisQuarter());
    add(new LastQuarter());
    add(new SecondLastQuarter());
    add(new ThisYear());
    add(new LastYear());
    add(new SecondLastYear());
  }};
  
  /**
   * Versucht den Range anhand des Identifiers zu ermitteln.
   * @param name der Name des Range.
   * @return der Range oder NULL, wenn er nicht gefunden wurde.
   */
  public static Range byId(String name)
  {
    if (name == null)
      return null;
    
    for (Range r:KNOWN)
    {
      if (r.getId().equals(name))
        return r;
    }
    
    return null;
  }
  
  /**
   * Berechnet das Start-Datum.
   * @return das Start-Datum.
   */
  public abstract Date getStart();
  
  /**
   * Berechnet das End-Datum.
   * @return das End-Datum.
   */
  public abstract Date getEnd();
  
  /**
   * Liefert einen Identifier fuer den Range.
   * @return Identifier fuer den Range.
   */
  public String getId()
  {
    return this.getClass().getSimpleName();
  }
  
  /**
   * Erzeugt einen neuen Kalender, der als Basis fuer die Berechnung dient.
   * @return einen neuen Kalender, der als Basis fuer die Berechnung dient.
   */
  protected Calendar createCalendar()
  {
    return Calendar.getInstance(Locale.GERMANY);
  }
  
  /**
   * Berechnet diese Woche.
   */
  public static class ThisWeek extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Woche: Diese");
    }
  }
  
  /**
   * Zeitraum fuer die letzten 7 Tage.
   */
  public static class LastSevenDays extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    @Override
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.DATE,-7);
      Date d = cal.getTime();
      return DateUtil.startOfDay(d);
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    @Override
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      Date d = cal.getTime();
      return DateUtil.endOfDay(d);
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
      return i18n.tr("Letzte 7 Tage");
    }
  }

  /**
   * Zeitraum fuer die letzten 30 Tage.
   */
  public static class LastThirtyDays extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    @Override
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.DATE,-30);
      Date d = cal.getTime();
      return DateUtil.startOfDay(d);
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    @Override
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      Date d = cal.getTime();
      return DateUtil.endOfDay(d);
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
      return i18n.tr("Letzte 30 Tage");
    }
  }

  /**
   * Berechnet letzte Woche.
   */
  public static class LastWeek extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(new ThisWeek().getStart());
      cal.add(Calendar.WEEK_OF_YEAR,-1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.add(Calendar.DATE,6);
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Woche: Letzte");
    }
  }

  /**
   * Berechnet vorletzte Woche.
   */
  public static class SecondLastWeek extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(new ThisWeek().getStart());
      cal.add(Calendar.WEEK_OF_YEAR,-2);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.add(Calendar.DATE,6);
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Woche: Vorletzte");
    }
  }

  /**
   * Berechnet diesen Monat.
   */
  public static class ThisMonth extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Monat: Dieser");
    }
  }
  
  /**
   * Berechnet den letzten Monat.
   */
  public static class LastMonth extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.MONTH,-1);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Monat: Letzter");
    }
  }
  
  /**
   * Berechnet den vorletzten Monat.
   */
  public static class SecondLastMonth extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.MONTH,-2);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Monat: Vorletzter");
    }
  }

  /**
   * Berechnet den Zeitraum der letzten 12 Monate.
   */
  public static class Last12Months extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.MONTH,-12);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Monat: Letzte 12");
    }
  }

  /**
   * Berechnet dieses Quartal.
   */
  public static class ThisQuarter extends Range
  {
    private final static int[] quarters = {0, 0, 0, 3, 3, 3, 6, 6, 6, 9, 9, 9};
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.MONTH,quarters[cal.get(Calendar.MONTH)]);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.add(Calendar.MONTH,2);
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Quartal: Dieses");
    }
  }

  /**
   * Berechnet letztes Quartal.
   */
  public static class LastQuarter extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(new ThisQuarter().getStart());
      cal.add(Calendar.MONTH,-3);
      return cal.getTime();
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.add(Calendar.MONTH,2);
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Quartal: Letztes");
    }
  }

  /**
   * Berechnet vorletztes Quartal.
   */
  public static class SecondLastQuarter extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(new LastQuarter().getStart());
      cal.add(Calendar.MONTH,-3);
      return cal.getTime();
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.setTime(this.getStart());
      cal.add(Calendar.MONTH,2);
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Quartal: Vorletztes");
    }
  }

  /**
   * Berechnet dieses Jahr.
   */
  public static class ThisYear extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.MONTH,Calendar.JANUARY);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.set(Calendar.MONTH,Calendar.DECEMBER);
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Jahr: Dieses");
    }
  }

  /**
   * Berechnet letztes Jahr.
   */
  public static class LastYear extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.YEAR,-1);
      cal.set(Calendar.MONTH,Calendar.JANUARY);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.YEAR,-1);
      cal.set(Calendar.MONTH,Calendar.DECEMBER);
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Jahr: Letztes");
    }
  }

  /**
   * Berechnet vorletztes Jahr.
   */
  public static class SecondLastYear extends Range
  {
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getStart()
     */
    public Date getStart()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.YEAR,-2);
      cal.set(Calendar.MONTH,Calendar.JANUARY);
      cal.set(Calendar.DAY_OF_MONTH,1);
      return DateUtil.startOfDay(cal.getTime());
    }
    
    /**
     * @see de.willuhn.jameica.hbci.server.Range#getEnd()
     */
    public Date getEnd()
    {
      Calendar cal = this.createCalendar();
      cal.add(Calendar.YEAR,-2);
      cal.set(Calendar.MONTH,Calendar.DECEMBER);
      cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
      return DateUtil.endOfDay(cal.getTime());
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
      return i18n.tr("Jahr: Vorletztes");
    }
  }
}


