package mh.calendarlibrary;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;

/**
 * Representation one mDay in a mMonth.
 *
 * @author Vincent Cheung (coolingfall@gmail.com)
 */
public final class MonthDay implements Parcelable {
	protected static final int PREV_MONTH_DAY = 1;
	protected static final int NEXT_MONTH_DAY = 2;

	private int mDay;
	private String mLunarDay;
	private boolean mIsHoliday;
	private boolean mIsWeekend;
	private boolean mIsCheckable = true;
	private boolean mIsToday;
	private int mDayFlag;
	private Calendar mCalendar;
	private Schemes schemes;

	String shift;
	Holidays holidays;

	boolean isHoliday;

	/**
	 * The constructor of mMonth mDay.
	 *
	 * @param calendar {@link Calendar}
	 */
	public MonthDay(Calendar calendar) {

		copy(calendar);


		mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
		mIsWeekend = dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
		mIsToday = isToday(mCalendar);

		isHoliday = holidays.isTodayHoliday();
		Log.v("dd", "ok");

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mCalendar.getTimeInMillis());
	}

	public static final Creator<MonthDay> CREATOR = new Creator<MonthDay>() {
		public MonthDay createFromParcel(Parcel source) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(source.readLong());
			return new MonthDay(calendar);
		}

		public MonthDay[] newArray(int size) {
			return new MonthDay[size];
		}
	};

	/* copy calendar to mMonth mDay */
	private void copy(Calendar calendar) {
		mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(calendar.getTimeInMillis());

		holidays = new Holidays(calendar.getTimeInMillis());
	}

	protected void setShift(String shift) {
		this.shift = shift;
	}

	/* to check if the given calendar was today */
	private boolean isToday(Calendar calendar) {
		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(System.currentTimeMillis());

		return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
			calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
			calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Get the string of solar mDay of current mDay.
	 *
	 * @return solar mDay string
	 */
	protected String getSolarDay() {
		return Integer.toString(mDay);
	}

	protected boolean isTodayHoliday() {
		return isHoliday;
	}


	protected String getShift() {
		return shift;

	}

	/**
	 * Get the string of lunar mDay of current mDay.
	 *
	 * @return lunar mDay string
	 */
	protected String getLunarDay() {
		return mLunarDay;
	}

	/**
	 * To check if current mDay was weekend.
	 *
	 * @return true if was, otherwise return false
	 */
	protected boolean isWeekend() {
		return mIsWeekend;
	}

	/**
	 * To check if lunar mDay is holiday or solar term.
	 *
	 * @return true if was holiday, otherwise return false
	 */
	protected boolean isHoliday() {
		return mIsHoliday;
	}

	/**
	 * To check if current mDay if the first mDay in current mMonth.
	 *
	 * @return true if was first mDay, otherwise return false
	 */
	protected boolean isFirstDay() {
		return mCalendar.get(Calendar.DAY_OF_MONTH) == 1 && mIsCheckable;
	}

	/**
	 * To set current mDay checkable or not.
	 *
	 * @param checkable true or false
	 */
	protected void setCheckable(boolean checkable) {
		mIsCheckable = checkable;
	}

	/**
	 * To check if current mDay was checkable.
	 *
	 * @return true if checkable, otherwise return false
	 */
	protected boolean isCheckable() {
		return mIsCheckable;
	}

	/**
	 * Set the flag of current mDay.
	 *
	 * @param flag {@link #PREV_MONTH_DAY}, {@link #NEXT_MONTH_DAY}
	 */
	protected void setDayFlag(int flag) {
		mDayFlag = flag;
	}

	/**
	 * Get the flag of current mDay.
	 *
	 * @return the flag {@link #PREV_MONTH_DAY}, {@link #NEXT_MONTH_DAY}
	 */
	protected int getDayFlag() {
		return mDayFlag;
	}

	/**
	 * To check if current mDay was today.
	 *
	 * @return true if was today, otherwise return false
	 */
	public boolean isToday() {
		return mIsToday;
	}

	/**
	 * Get {@link Calendar} for current mDay.
	 *
	 * @return {@link Calendar}
	 */
	public Calendar getCalendar() {
		return mCalendar;
	}


	public String getMonthStr(int month) {
		String[] monthList = new String[] {"Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"};
		return monthList[month];
	}
}
