package mh.shiftcalendaram.Templates;


public class AccountTemplate extends ListTemplate{

	int shiftSchemeID;

	public AccountTemplate(String name, String shiftSchemeGroup, int shiftSchemeID, String color) {
		super(name, shiftSchemeGroup, color);
		this.shiftSchemeID = shiftSchemeID;
	}

	public String getShiftSchemeGroup() {
		return desc;
	}

	public int getShiftSchemeID() {
		return shiftSchemeID;
	}
}
