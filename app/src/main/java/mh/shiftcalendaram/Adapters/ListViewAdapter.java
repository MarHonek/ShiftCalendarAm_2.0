package mh.shiftcalendaram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import mh.shiftcalendaram.R;
import mh.shiftcalendaram.Templates.ListTemplate;

/**
 * Created by Martin on 01.08.2016.
 */
public class ListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<ListTemplate> list;

    public ListViewAdapter(Context context, ArrayList<ListTemplate> list) {
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

        return v;
    }
}
