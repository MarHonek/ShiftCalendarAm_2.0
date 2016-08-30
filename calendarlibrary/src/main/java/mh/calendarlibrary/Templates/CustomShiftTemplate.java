package mh.calendarlibrary.Templates;

/**
 * Created by Martin on 28.08.2016.
 */
public class CustomShiftTemplate {

    protected int day;
    protected int month;
    protected int year;
    protected int accID;

    public CustomShiftTemplate( int day, int month, int year, int accID)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.accID = accID;
    }

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    public int getAccID()
    {
        return accID;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setAccID(int accID)
    {
        this.accID = accID;
    }
}
