package mh.shiftcalendaram.Templates;


public class AccountTemplate extends ListTemplate{

	int shiftSchemeID;

	public AccountTemplate(String name, String shiftSchemeGroup, int shiftSchemeID, String color, String desc) {
		super(name, shiftSchemeGroup, color, desc);
		this.shiftSchemeID = shiftSchemeID;
	}

	public String getShiftSchemeGroup() {
		return desc;
	}

	public int getShiftSchemeID() {
		return shiftSchemeID;
	}
}
