package mh.shiftcalendaram.Templates;


public class NoteTemplate {

		int day;
		int month;
		int year;
		int accID;
		String notes;
	
		public NoteTemplate(int day, int month, int year, String notes, int accID)
		{
			this.day = day;
			this.month = month;
			this.year = year;
			this.accID = accID;
			this.notes = notes;
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
		
		public String getNotes()
		{
			return notes;
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
