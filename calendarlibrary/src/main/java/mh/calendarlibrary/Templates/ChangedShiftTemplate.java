package mh.calendarlibrary.Templates;


public class ChangedShiftTemplate extends CustomShiftTemplate{
	
	int shiftID;
	
	public ChangedShiftTemplate(int shiftID, int day, int month, int year, int accID)
	{
		super(day, month, year, accID);
		this.shiftID = shiftID;
	}
	
	public int getShiftID()
	{
		return shiftID;
	}
}
