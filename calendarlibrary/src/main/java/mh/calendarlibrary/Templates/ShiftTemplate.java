package mh.calendarlibrary.Templates;

public class ShiftTemplate extends ListTemplate {

	String shortName;

	public ShiftTemplate(int ID, String name, String shortName, String color, String desc) {
		super(ID, name, color, desc);
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}
}
