package mh.shiftcalendaram.calendarHeader;



import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import mh.shiftcalendaram.R;

public class FragmentHeaderChangeble extends Fragment {
	
	TextView nameOfShift, kindOfShift;
	LinearLayout nameOfShiftLin;
	int positionOfShifts;
	int positionOfCustom;

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {

	        View v = inflater.inflate(R.layout.main_fragment_custom, container, false);

			/*nameOfShiftLin = (LinearLayout)v.findViewById(R.id.linearLayout_nameOfShifts);
			nameOfShift = (TextView)v.findViewById(R.id.textView_nameOfShifts);
			kindOfShift = (TextView)v.findViewById(R.id.textView_kindOfShift);

			nameOfShift.setText("");
			kindOfShift.setText("");
			

			SharedPreferences pref = getActivity().getSharedPreferences("shift", Context.MODE_PRIVATE);
			positionOfCustom = pref.getInt("positionOfCustom", -2);
			ArrayList<String> listOf = StaticShiftTemplate.getStringArray();
			
			Database data = new Database(getActivity());
			List<ShiftTemplate> custom = data.getShifts();

			nameOfShift.setText(custom.get(positionOfCustom).getTitle());
			kindOfShift.setText(listOf.get(custom.get(positionOfCustom).getPosition()) + " - " + custom.get(positionOfCustom).getShortTitle());
			int[] colors = new int[]{custom.get(positionOfCustom).getColor(),Color.WHITE};
			GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
			nameOfShiftLin.setBackgroundDrawable(gd);*/

	        return v;
	    }
}
