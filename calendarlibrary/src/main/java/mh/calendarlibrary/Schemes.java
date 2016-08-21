package mh.calendarlibrary;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableInstant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Martin on 11.08.2016.
 */
public class Schemes {

    String title;
    String firstShift;
    String secondShift;
    String thirdShift;
    String forthShift;
    int numberOfShifts;
    static ArrayList<Schemes> listOfShifts;

    int positionOfScheme;
    String schemeType;
    String shift;

    String[] shiftList;

    int shiftIndex;

    /**
     * The default constructor of lunar, create an empty Lunar, don't
     * forget to use {@link #setDate} or {@link #setTimeInMillis}.
     */
    public Schemes(long milisec, int positionOfScheme, String schemeType) {
        this.positionOfScheme = positionOfScheme;
        this.schemeType = schemeType;
        ArrayList<Schemes> schemes = Schemes.createList();
        shiftList= getShiftsList(schemes.get(positionOfScheme).getABCDShifts(schemeType));
        init(milisec);
    }

    /**
     * The constructor of Lunar calendar.
     *
     * @param year the year
     * @param month the month
     * @param day the day
     */
    public Schemes(int year, int month, int day) {
        setDate(year, month, day);
    }

    /**
     * The constructor of Lunar calendar.
     *
     * @param millisec millisecond
     */
    public Schemes(long millisec) {
        init(millisec);
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        init(year == 0 ? System.currentTimeMillis() : calendar.getTimeInMillis());
    }

    public void setTimeInMillis(long millisecond) {
        init(millisecond);
    }

    /* init lunar calendar with millisecond */
    private void init(long millisec) {
        int days = getDaysCountBetweenDates(millisec);

        while(days >= shiftList.length) {
            days -= shiftList.length;
        }
        shift = shiftList[days];
        shiftIndex = days;


    }

    public void incrementDay() {
        shiftIndex++;
        if(shiftIndex >= shiftList.length) {
            shiftIndex = 0;
        }

        shift = shiftList[shiftIndex];
    }

    private String[] getShiftsList(String shifts) {
        String[] shiftList = shifts.split(";");
        return shiftList;
    }

    private int getDaysCountBetweenDates(long millisec) {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(millisec);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);


        DateTime start = new DateTime(2014, 1, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0);
        Days days = Days.daysBetween(start, end);

        return Math.abs(days.getDays());
    }

    public String getShift() {
        return shift;
    }

    private Schemes(String title, String firstShift, String secondShift)
    {
        this.title = title;
        this.firstShift = firstShift;
        this.secondShift = secondShift;
        numberOfShifts = 2;
    }

    private Schemes(String title,String firstShift, String secondShift, String thirdShift)
    {
        this.title = title;
        this.firstShift = firstShift;
        this.secondShift = secondShift;
        this.thirdShift = thirdShift;
        numberOfShifts = 3;
    }

    private Schemes(String title, String firstShift, String secondShift, String thirdShift, String forthShift)
    {
        this.title = title;
        this.firstShift = firstShift;
        this.secondShift = secondShift;
        this.thirdShift = thirdShift;
        this.forthShift = forthShift;
        numberOfShifts = 4;
    }

    public int getNumberOfSchemes()
    {
        return numberOfShifts;
    }

    public static ArrayList<Schemes> createList() {
        listOfShifts = new ArrayList<Schemes>();
        listOfShifts.add(new Schemes("0058-0088", "O;N;N;V;V;R;R;O", "N;V;V;R;R;O;O;N", "V;R;R;O;O;N;N;V", "R;O;O;N;N;V;V;R"));
        listOfShifts.add(new Schemes("0018-0048", "O;R;R;V;V;N;N;O", "R;V;V;N;N;O;O;R", "V;N;N;O;O;R;R;V", "N;O;O;R;R;V;V;N"));
        listOfShifts.add(new Schemes("0428-0458", "N;O;O;V;V;R;R;N", "O;V;V;R;R;N;N;O", "V;R;R;N;N;O;O;V", "R;N;N;O;O;V;V;R"));
        listOfShifts.add(new Schemes("0858-0888, 5858-5888, 0958-0988, 5958-5988", "V;V;R;N", "R;N;V;V", "V;R;N;V", "N;V;V;R"));
        listOfShifts.add(new Schemes("0918-0948, 5918-5948", "V;N;N;V;V;R;R;V", "R;V;V;N;N;V;V;R", "V;R;R;V;V;N;N;V", "N;V;V;R;R;V;V;N"));
        listOfShifts.add(new Schemes("0318-0338", "O;V;V;R;R;O", "V;R;R;O;O;V", "R;O;O;V;V;R"));
        listOfShifts.add(new Schemes("3318-3338", "V;O;O;O;O;O;O;V;V;R;R;R;R;R;R;V;V;N;N;N;N;N;V;V", "V;N;N;N;N;N;V;V;V;O;O;O;O;O;O;V;V;R;R;R;R;R;R;V;", "V;R;R;R;R;R;R;V;V;N;N;N;N;N;V;V;V;O;O;O;O;O;O;V"));
        listOfShifts.add(new Schemes("0218-0238", "R;V;V;R;R;R", "V;R;R;R;R;V", "R;R;R;V;V;R"));
        listOfShifts.add(new Schemes("0268-0288", "O;V;V;R;R;O", "V;R;R;O;O;V", "R;O;O;V;V;R"));
        listOfShifts.add(new Schemes("0818-0828", "V;V;V;R;R;R", "V;V;V;N;N;N"));
        listOfShifts.add(new Schemes("0618-0648", "V;V;V;N;N;N;V;V;V;R;R;R", "V;V;V;R;R;R;V;V;V;N;N;N", "N;N;N;V;V;V;R;R;R;V;V;V", "R;R;R;V;V;V;N;N;N;V;V;V"));
        listOfShifts.add(new Schemes("0838-0848", "R;V;V;R", "V;R;R;V"));
        listOfShifts.add(new Schemes("0368-0348", "R;V;V;O;O;R", "V;O;O;R;R;V", "O;R;R;V;V;O"));
        listOfShifts.add(new Schemes("7438-7458", "O;O;O;V;V;R;R;R;R;R;V;V;N;N;N;N;N;V;V;O;O", "N;N;N;V;V;O;O;O;O;O;V;V;R;R;R;R;R;V;V;N;N", "R;R;R;V;V;N;N;N;N;N;V;V;O;O;O;O;O;V;V;R;R"));
        listOfShifts.add(new Schemes("AMFM", "D;D;O;O;O;N;N;V;V;D;D;D;O;O;N;N;V;V;V;D;D;O;O;N;N;KV;V;V", "O;O;N;N;KV;V;V;D;D;O;O;O;N;N;V;V;D;D;D;O;O;N;N;V;V;V;D;D", "N;N;V;V;V;D;D;O;O;N;N;KV;V;V;D;D;O;O;O;N;N;V;V;D;D;D;O;O", "V;V;D;D;D;O;O;N;N;V;V;V;D;D;O;O;N;N;KV;V;V;D;D;O;O;O;N;N"));
        listOfShifts.add(new Schemes("0918-5948", "V;V;V;V;D;D;N;N", "V;V;R;R;N;N;V;V", "D;D;N;N;V;V;V;V", "N;N;V;V;V;V;D;D"));
        listOfShifts.add(new Schemes("Kontrola", "R;R;NO;NO; ; ; ; ", "NO;NO; ; ; ; ;R;R", " ; ;R;R;NO;NO; ; ", " ; ; ; ;R;R;NO;NO"));

        return listOfShifts;

    }

    public String getTitle()
    {
        return title;
    }

    public String getShiftA()
    {
        return firstShift;
    }

    public String getShiftB()
    {
        return secondShift;
    }

    public String getShiftC()
    {
        return thirdShift;
    }

    public String getShiftD()
    {
        return forthShift;
    }


    public ArrayList<String> getTypesNames(int typeCount) {
        ArrayList<String> typeNames = new ArrayList<>();
        char types = 'A';
        for (int i = 0; i < typeCount;i++) {
            typeNames.add(String.valueOf((char) (types+i)));
        }
        return typeNames;
    }

    public static ArrayList<String> getStringArray()
    {
        ArrayList<Schemes> pernament = Schemes.createList();
        ArrayList<String> getString = new ArrayList<String>();
        for(int i = 0; i < pernament.size();i++)
        {
            getString.add(pernament.get(i).getTitle());
        }
        return getString;
    }

    public static String getStringValueOfTypeSwitch(int value) {
        String strValue = "";
        switch (value) {
            case 0:
                strValue = "A";
                break;
            case 1:
                strValue = "B";
                break;

            case 2:
                strValue = "C";
                break;

            case 3:
                strValue = "D";
                break;
        }
        return strValue;
    }

    public String getABCDShifts(String radio)
    {
        String shiftScheme = null;
        switch(radio)
        {
            case "A": shiftScheme = getShiftA(); break;
            case "B": shiftScheme = getShiftB(); break;
            case "C": shiftScheme = getShiftC(); break;
            case "D": shiftScheme = getShiftD(); break;
        }

        return shiftScheme;
    }
}
