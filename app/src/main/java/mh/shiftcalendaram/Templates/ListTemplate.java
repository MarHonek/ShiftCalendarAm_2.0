package mh.shiftcalendaram.Templates;

import android.graphics.Color;

public class ListTemplate {

	String name;
	String color;
	String shortName;
	String desc;


	public ListTemplate(String name, String shortName, String color, String desc)
	{
		this.name = name;
		this.desc = desc;
		this.shortName = shortName;
		this.color = color;

	}

	public String getName()
	{
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getColorHex() {
		return color;
	}

	public int getColor() {
		return Color.parseColor(color);
	}

}
