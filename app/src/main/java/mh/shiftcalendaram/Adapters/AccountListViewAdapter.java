package mh.shiftcalendaram.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

import mh.calendarlibrary.Schemes;
import mh.calendarlibrary.Templates.AccountTemplate;
import mh.calendarlibrary.Templates.ShiftTemplate;
import mh.shiftcalendaram.R;

/**
 * Created by Martin on 28.08.2016.
 */
public class AccountListViewAdapter extends BaseAdapter{

    TextView name, desc;
    ImageView icon;
    LinearLayout circle;

    Context context;
    ArrayList<AccountTemplate> list;
    ArrayList<String> schemes;

    public AccountListViewAdapter(Context context, ArrayList<AccountTemplate> list) {
        this.context = context;
        this.list = list;
        schemes = Schemes.getStringArray();
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
        View v = vi.inflate(R.layout.listview_item_account, null);

        //TODO: refactoring názvů
        name = (TextView)v.findViewById(R.id.textView_shift_listview_name);
        icon = (ImageView)v.findViewById(R.id.imageView_list_item_account_icon);
        desc = (TextView)v.findViewById(R.id.textView_shift_listview_desc);
        circle = (LinearLayout)v.findViewById(R.id.linearLayout_circle);

        name.setText(list.get(i).getName());
        desc.setText(schemes.get(list.get(i).getShiftSchemeID()) + " - " + list.get(i).getShiftSchemeGroup());
        GradientDrawable background = (GradientDrawable) circle.getBackground();
        background.setColor(list.get(i).getColor());
        return v;
    }
}
