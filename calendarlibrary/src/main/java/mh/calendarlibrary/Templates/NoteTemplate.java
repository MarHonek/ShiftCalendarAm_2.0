package mh.calendarlibrary.Templates;


public class NoteTemplate extends CustomShiftTemplate {

		String notes;
	
		public NoteTemplate(int day, int month, int year, String notes, int accID)
		{
			super(day, month, year, accID);
			this.notes = notes;
		}
		
		public String getNotes()
		{
			return notes;
		}

}
