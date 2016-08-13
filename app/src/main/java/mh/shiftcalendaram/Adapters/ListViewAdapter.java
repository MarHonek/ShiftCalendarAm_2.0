package mh.shiftcalendaram.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mh.shiftcalendaram.R;
import mh.shiftcalendaram.Templates.ListTemplate;
import mh.shiftcalendaram.Templates.ShiftTemplate;

/**
 * Created by Martin on 01.08.2016.
 */
public class ListViewAdapter extends BaseAdapter {


    TextView name, shortName, desc;
    LinearLayout circle;

    Context context;
    ArrayList<ShiftTemplate> list;

    public ListViewAdapter(Context context, ArrayList<ShiftTemplate> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.listview_item, null);

        //TODO: refactoring názvů
        name = (TextView)v.findViewById(R.id.textView_shift_listview_name);
        shortName = (TextView)v.findViewById(R.id.textView_shift_list_shortName);
        desc = (TextView)v.findViewById(R.id.textView_shift_listview_desc);
        circle = (LinearLayout)v.findViewById(R.id.linearLayout_circle);

        name.setText(list.get(i).getName());
        shortName.setText(list.get(i).getShortName());
        GradientDrawable background = (GradientDrawable) circle.getBackground();
        background.setColor(list.get(i).getColor());

        return v;
    }
}
