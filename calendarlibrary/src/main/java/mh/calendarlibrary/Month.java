package mh.calendarlibrary;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mh.calendarlibrary.Templates.ChangedShiftTemplate;
import mh.calendarlibrary.Templates.NoteTemplate;
import mh.calendarlibrary.Templates.ShiftTemplate;

/**
 * Representation of a mMonth on a calendar.
 *
 * @author Vincent Cheung (coolingfall@gmail.com)
 */
class Month implements Parcelable {
	private static final int DAYS_IN_WEEK = 7;
	private static final int mTotalWeeks = 6;

	private final int mYear;
	private final int mMonth;
	private final int mDay;

	private int mDelta;
	private int mTotalDays;
	private boolean mIsMonthOfToday;
	private List<MonthDay> mMonthDayList = new ArrayList<>();

	int schemeID = -1;
	String schemeGroup;
	int accountID = -1;
	ArrayList<NoteTemplate> notes;
	ArrayList<ChangedShiftTemplate> changedShifts;
	ArrayList<ShiftTemplate> shifts;

	/**
	 * Parcelable Stuff.
	 */
	private Month(Parcel in) {
		this(in.readInt(), in.readInt(), in.readInt());
	}

	/**
	 * The constructor for mMonth.
	 *
	 * @param year mYear
	 * @param month mMonth
	 * @param day mDay of mMonth
	 */
	protected Month(int year, int month, int day) {
		mYear = year;
		mMonth = month;
		mDay = day;
	}

	protected void setScheme(int schemeID, String schemeGroup) {
		this.schemeID = schemeID;
		this.schemeGroup = schemeGroup;
		addMonthDay(mYear, mMonth, mDay);
	}


	protected void setScheme(int accountID, int schemeID, String schemeGroup,ArrayList<NoteTemplate> notes, ArrayList<ChangedShiftTemplate> changeShifts, ArrayList<ShiftTemplate> shifts) {
		this.accountID = accountID;
		this.schemeID = schemeID;
		this.schemeGroup = schemeGroup;
		this.notes = notes;
		this.changedShifts = changeShifts;
		this.shifts = shifts;
		addMonthDay(mYear, mMonth, mDay);
	}


	private boolean isTodayNote(ArrayList<NoteTemplate> notes, Calendar calendar) {
		if(notes != null) {
			for (int i = 0; i < notes.size(); i++) {
				if (notes.get(i).getYear() == calendar.get(Calendar.YEAR) && notes.get(i).getMonth() == calendar.get(Calendar.MONTH) && notes.get(i).getDay() == calendar.get(Calendar.DAY_OF_MONTH)) {
					return true;
				}
			}
		}
		return false;
	}

	private int getChangedShiftListIndex(ArrayList<ChangedShiftTemplate> changedShifts, Calendar calendar) {
		if(changedShifts != null) {
			for (int i = 0; i < changedShifts.size(); i++) {
				if (changedShifts.get(i).getYear() == calendar.get(Calendar.YEAR) && changedShifts.get(i).getMonth() == calendar.get(Calendar.MONTH) && changedShifts.get(i).getDay() == calendar.get(Calendar.DAY_OF_MONTH)) {
					for(int j = 0; j < shifts.size();j++) {
						if(changedShifts.get(i).getShiftID() == shifts.get(j).getID()) {
							return j;
						}
					}

				}
			}
		}
		return -1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mYear);
		dest.writeInt(mMonth);
		dest.writeInt(mDay);
		dest.writeInt(schemeID);
	}

	/**
	 * Flatten this object in to a Parcel.
	 */
	public static final Creator<Month> CREATOR = new Creator<Month>() {
		public Month createFromParcel(Parcel in) {
			return new Month(in);
		}

		public Month[] newArray(int size) {
			return new Month[size];
		}
	};

	/* generate working calendar */
	private Calendar generateWorkingCalendar(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		mIsMonthOfToday = isMonthOfToday(calendar);
		mTotalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, 1);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		dayOfWeek -= 1;
		if(dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		mDelta = dayOfWeek - 1;
		calendar.add(Calendar.DATE, -mDelta);

		return calendar;
	}

	/* add mMonth mDay into list */
	private void addMonthDay(int year, int month, int day) {
		Calendar calendar = generateWorkingCalendar(year, month, day);

		Schemes schemes = new Schemes(calendar.getTimeInMillis(), schemeID, "A");

		for (int i = 0; i < mTotalWeeks; i++) {
			for (int j = 0; j < DAYS_IN_WEEK; j++) {
				MonthDay monthDay = new MonthDay(calendar);
				monthDay.setShift(schemes.getShift());
				monthDay.setDayNote(isTodayNote(notes, calendar));

				int shiftIndex = getChangedShiftListIndex(changedShifts,calendar);
				if(shiftIndex != -1) {
					monthDay.setChangedShift(shifts.get(shiftIndex).getShortName(), shifts.get(shiftIndex).getColor());
				}
				int currentDays = i * DAYS_IN_WEEK + j;
				monthDay.setCheckable(!(currentDays < mDelta ||
					currentDays >= mTotalDays + mDelta));
				if (currentDays < mDelta) {
					monthDay.setDayFlag(MonthDay.PREV_MONTH_DAY);
				} else if (currentDays >= mTotalDays + mDelta) {
					monthDay.setDayFlag(MonthDay.NEXT_MONTH_DAY);
				}
				mMonthDayList.add(monthDay);
				calendar.add(Calendar.DATE, 1);
				schemes.incrementDay();
			}
		}
	}

	/* to check if current mMonth is the mMonth of today for given calendar */
	private boolean isMonthOfToday(Calendar calendar) {
		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(System.currentTimeMillis());

		return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
			calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH);
	}

	/**
	 * Get total weeks in current mMonth.
	 *
	 * @return total weeks
	 */
	protected int getWeeksInMonth() {
		return mTotalWeeks;
	}

	/**
	 * Get {@link MonthDay} in current mMonth according to index.
	 *
	 * @param index index in mMonth view
	 * @return {@link MonthDay}
	 */
	protected MonthDay getMonthDay(int index) {
		return mMonthDayList.size() <= index ? null : mMonthDayList.get(index);
	}

	protected int getDay(int index) {
		return Integer.parseInt(mMonthDayList.get(index).getSolarDay());
	}

	/**
	 * Get {@link MonthDay} in current mMonth according to x index and y index in mMonth view.
	 *
	 * @param xIndex x index in mMonth view
	 * @param yIndex y index in mMonth view
	 * @return {@link MonthDay}
	 */
	protected MonthDay getMonthDay(int xIndex, int yIndex) {
		return getMonthDay(xIndex * DAYS_IN_WEEK + yIndex);
	}

	/**
	 * Get the mYear of current mMonth.
	 *
	 * @return mYear
	 */
	protected int getYear() {
		return mYear;
	}

	/**
	 * Get current mMonth.
	 *
	 * @return current mMonth
	 */
	protected int getMonth() {
		return mMonth;
	}

	/**
	 * To check if current mMonth is the mMonth of today.
	 *
	 * @return true if was, otherwise return false
	 */
	protected boolean isMonthOfToday() {
		return mIsMonthOfToday;
	}

	/**
	 * Get the index of mDay in current mMonth.
	 *
	 * @param day the mDay in current mMonth
	 * @return the index of mDay
	 */
	protected int getIndexOfDayInCurMonth(int day) {
		for (int i = 0; i < mMonthDayList.size(); i++) {
			MonthDay monthDay = mMonthDayList.get(i);
			if (monthDay.isCheckable() &&
				monthDay.getCalendar().get(Calendar.DAY_OF_MONTH) == day) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Get the index of today if today was in current mMonth.
	 *
	 * @return the index of today if was in current mMonth, otherwise return -1
	 */
	protected int getIndexOfToday() {
		if (!mIsMonthOfToday) {
			return -1;
		}

		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(System.currentTimeMillis());
		return getIndexOfDayInCurMonth(today.get(Calendar.DAY_OF_MONTH));
	}

	private int startWeekFromMonday(int dayOfWeek) {
		dayOfWeek -= 1;
		if(dayOfWeek < 0)
			dayOfWeek = 6;
		return dayOfWeek;
	}
}
