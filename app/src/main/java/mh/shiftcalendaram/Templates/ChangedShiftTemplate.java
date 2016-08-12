package mh.shiftcalendaram.Templates;


import android.graphics.Color;

public class ChangedShiftTemplate{
	
	int shiftID;
	int day;
	int month;
	int year;
	int accID;
	
	public ChangedShiftTemplate(int shiftID, int day, int month, int year, int accID)
	{
		this.shiftID = shiftID;
		this.day = day;
		this.month = month;
		this.year = year;
		this.accID = accID;
	}
	
	public int getShiftID()
	{
		return shiftID;
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
}
