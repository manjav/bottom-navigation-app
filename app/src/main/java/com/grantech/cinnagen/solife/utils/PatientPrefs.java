package com.grantech.cinnagen.solife.utils;

public class PatientPrefs
{
    private static PatientPrefs _instance;
    public PersianCalendar startDate;
    public PersianCalendar maintainDate;
    private PatientPrefs() { }
    public static PatientPrefs getInstance()
    {
        if( _instance == null )
        {
            _instance = new PatientPrefs();
            _instance.startDate = new PersianCalendar();
            _instance.maintainDate = new PersianCalendar();
        }
        return _instance;
    }
}
