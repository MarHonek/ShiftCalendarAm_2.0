package mh.calendarlibrary.Templates;

public class ShiftTemplate extends ListTemplate {



	public ShiftTemplate(String name, String shortName, String color, String desc) {
		super(name, shortName, color, desc);
	}

	public String getShortName() {
		return desc;
	}
}
