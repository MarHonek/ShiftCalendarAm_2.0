package mh.shiftcalendaram;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

/**
 * Created by Martin on 11.08.2016.
 */
public class Colors {

    public static int convertColorToDark(int defaultColor) {

        float[] hsv = new float[3];
        int brandColor = defaultColor;
        Color.colorToHSV(brandColor, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int argbColor = Color.HSVToColor(hsv);
        return argbColor;
    }

}
