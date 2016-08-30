package mh.calendarlibrary.Templates;

import android.graphics.Color;

public class ListTemplate {

	int ID;
	String name;
	String color;
	String desc;



	public ListTemplate(int ID, String name, String color, String desc)
	{
		this.ID = ID;
		this.name = name;
		this.desc = desc;
		this.color = color;

	}
	public int getID() {
		return ID;
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
