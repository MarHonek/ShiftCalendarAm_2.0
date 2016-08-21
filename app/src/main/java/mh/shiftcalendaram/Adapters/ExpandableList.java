package mh.shiftcalendaram.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.andexert.expandablelayout.library.ExpandableLayoutItem;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;

/**
 * Created by Martin on 20.08.2016.
 */
public class ExpandableList extends ExpandableLayoutListView {

    private Integer position = -1;
    private boolean expand = true;

    public ExpandableList(Context context)
    {
        super(context);
        setOnScrollListener(new OnExpandableLayoutScrollListener());
    }

    public ExpandableList(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnScrollListener(new OnExpandableLayoutScrollListener());
    }

    public ExpandableList(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setOnScrollListener(new OnExpandableLayoutScrollListener());
    }

    public void setExpand(boolean isExpand) {
        this.expand = isExpand;
    }

    @Override
    public boolean performItemClick(View view, int position, long id) {
        this.position = position;

        for (int index = 0; index < getChildCount(); ++index)
        {
            if (index != (position - getFirstVisiblePosition()))
            {

                ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) getChildAt(index).findViewWithTag(ExpandableLayoutItem.class.getName());

                currentExpandableLayout.hide();
            }
        }

        ExpandableLayoutItem expandableLayout = (ExpandableLayoutItem) getChildAt(position - getFirstVisiblePosition()).findViewWithTag(ExpandableLayoutItem.class.getName());

        if(expand == true) {
            if (expandableLayout.isOpened())
                expandableLayout.hide();
            else
                expandableLayout.show();
        }
        else expandableLayout.hide();

        return super.performItemClick(view, position, id);
    }
}
