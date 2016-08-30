package mh.calendarlibrary.Templates;


public class AccountTemplate extends ListTemplate{

	int shiftSchemeID;
	String shiftSchemeGroup;

	public AccountTemplate(int ID, String name, String shiftSchemeGroup, int shiftSchemeID, String color, String desc) {
		super(ID, name, color, desc);
		this.shiftSchemeID = shiftSchemeID;
		this.shiftSchemeGroup = shiftSchemeGroup;
	}

	public String getShiftSchemeGroup() {
		return shiftSchemeGroup;
	}

	public int getShiftSchemeID() {
		return shiftSchemeID;
	}
}
