package mh.shiftcalendaram.calendarHeader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mh.shiftcalendaram.R;

public class FragmentDefault extends Fragment {

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	         return inflater.inflate(R.layout.main_fragment_default, container, false);
	        
	 }      
	   	

	
}
