package mh.calendarlibrary;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Martin on 24.08.2016.
 */
public class Holidays {

    int mDay;
    int mMonth;
    int mYear;
    String name;

    public Holidays(long milisec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milisec);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
    }

    public Holidays(int mDay, int mMonth)
    {
        this.mDay = mDay;
        this.mMonth = mMonth;
    }

    public Holidays(int mDay, int mMonth, String name)
    {
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.name = name;
    }

    public static ArrayList<Holidays> getHolidayList()
    {
        ArrayList<Holidays> list = new ArrayList<Holidays>();
        list.add(new Holidays(1, 0, "Den obnovy samostatného českého státu"));
        list.add(new Holidays(1, 4, "Svátek práce"));
        list.add(new Holidays(8, 4, "Den vítězství"));
        list.add(new Holidays(5, 6, "Den slovanských věrozvěstů Cyrila a Metoděje"));
        list.add(new Holidays(6, 6, "Den upálení mistra Jana Husa"));
        list.add(new Holidays(28, 8, "Den české státnosti"));
        list.add(new Holidays(28, 9, "Den vzniku samostatného československého státu"));
        list.add(new Holidays(17, 10, "Den boje za svobodu a demokracii"));
        list.add(new Holidays(24, 11, "Štědrý den"));
        list.add(new Holidays(25, 11, "1. svátek vánoční"));
        list.add(new Holidays(26, 11, "2. svátek vánoční"));
        return list;
    }

    private int getmDay()
    {
        return mDay;
    }

    private int getmMonth()
    {
        return mMonth;
    }

    private String getName()
    {
        return name;
    }

    public static DateTime EasterSunday(int year)
    {
        int day = 0;
        int month = 0;

        int g = year % 19;
        int c = year / 100;
        int h = (c - (int)(c / 4) - (int)((8 * c + 13) / 25) + 19 * g + 15) % 30;
        int i = h - (int)(h / 28) * (1 - (int)(h / 28) * (int)(29 / (h + 1)) * (int)((21 - g) / 11));

        day   = i - ((year + (int)(year / 4) + i + 2 - c + (int)(c / 4)) % 7) + 28;
        month = 3;

        if (day > 31)
        {
            month++;
            day -= 31;
        }

        return new DateTime(year, month, day,0,0);
    }



    public String getNameByDay() {
        name = null;
        ArrayList<Holidays> holidays = getHolidayList();
        for (int i = 0; i < holidays.size();i++) {
            if (holidays.get(i).getmDay() == mDay && holidays.get(i).getmMonth() == mMonth) {
                name = holidays.get(i).getName();
                return name;
            }
        }
        DateTime easterMonday = EasterSunday(mYear).plusDays(1);

        if(easterMonday.getDayOfMonth() == mDay && easterMonday.getMonthOfYear()-1 == mMonth) {
            name = "Velikonoční pondělí";
            return name;
        }
        DateTime goodFriday = EasterSunday(mYear).minusDays(2);
        if(goodFriday.getDayOfMonth() == mDay && goodFriday.getMonthOfYear()-1 == mMonth) {
            name = "Velký pátek";
            return name;
        }
        return null;
    }

    public boolean isTodayHoliday() {
        if(getNameByDay() == null) {
            return false;
        } else {
            return true;
        }
    }
}
