package mh.shiftcalendaram.Templates;

import android.graphics.Color;

public class ListTemplate {

	String name;
	String color;
	String desc;


	public ListTemplate(String name, String desc, String color)
	{
		this.name = name;
		this.desc = desc;
		this.color = color;

	}

	public String getName()
	{
		return name;
	}

	public String getColorHex() {
		return color;
	}

	public int getColor() {
		return Color.parseColor(color);
	}

}
