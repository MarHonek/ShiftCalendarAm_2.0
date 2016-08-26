package mh.calendarlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import mh.calendarlibrary.Database.Database;
import mh.calendarlibrary.Templates.AccountTemplate;

/**
 * Display one mMonth with solar and lunar date on a calendar.
 *
 * @author Vincent Cheung (coolingfall@gmail.com)
 */
@SuppressLint("ViewConstructor")
public final class MonthView extends View {
	private static final int DAYS_IN_WEEK = 7;

	private int mSelectedIndex = -1;

	private float mCalendarTextSize;
	private float mShiftTextSize;
	private float mLunarOffset;
	private float mCircleRadius;

	Database database;
	private Month mMonth;
	private CalendarView mCalendarView;

	private final Region[][] mMonthWithSixWeeks = new Region[6][DAYS_IN_WEEK];
	private Paint mPaint;

	/**
	 * The constructor of mMonth view.
	 *
	 * @param context context to use
	 * @param calendarView {@link CalendarView}
	 */
	public MonthView(Context context, Month month, CalendarView calendarView) {
		super(context);

		database = new Database(context);
		mMonth = month;

		if(calendarView.accountID == -1) {
			mMonth.setScheme(calendarView.getSchemeID(), calendarView.getSchemeGroup());
		} else {
			ArrayList<AccountTemplate> accounts = database.getAccounts();
			mMonth.setScheme(accounts.get(calendarView.accountID).getShiftSchemeID(), accounts.get(calendarView.accountID).getShiftSchemeGroup());
		}
		mCalendarView = calendarView;
		init();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int dayWidth = (int) (w / 7f);
		int dayHeight = dayWidth + dayWidth/5;

		mCircleRadius = dayWidth / 2.2f;

		mCalendarTextSize = h / 35f;
		mPaint.setTextSize(mCalendarTextSize);
		float solarHeight = mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;

		mShiftTextSize = mCalendarTextSize * 2;
		mPaint.setTextSize(mShiftTextSize);
		float lunarHeight = mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;

		mLunarOffset = (Math.abs(mPaint.ascent() + mPaint.descent()) +
			solarHeight + lunarHeight) / 3f;

		initMonthRegion(mMonthWithSixWeeks, dayWidth, dayHeight);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mMonth == null) {
			return;
		}

		canvas.save();
		int weeks = mMonth.getWeeksInMonth();
		Region[][] monthRegion = getMonthRegion();

		for (int i = 0; i < weeks; i++) {
			for (int j = 0; j < DAYS_IN_WEEK; j++) {
				draw(canvas, monthRegion[i][j].getBounds(), i, j);
			}
		}
		canvas.restore();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return true;

		case MotionEvent.ACTION_UP:
			handleClickEvent((int) event.getX(), (int) event.getY());
			return true;

		default:
			return super.onTouchEvent(event);
		}
	}

	/* init mMonth view */
	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
		mPaint.setTextAlign(Paint.Align.CENTER);

		if (mMonth.isMonthOfToday()) {
			mSelectedIndex = mMonth.getIndexOfToday();
		}

		setBackgroundColor(mCalendarView.getMonthBackgroundColor());
	}

	/* init mMonth region with the width and height of mDay */
	private void initMonthRegion(Region[][] monthRegion, int dayWidth, int dayHeight) {
		for (int i = 0; i < monthRegion.length; i++) {
			for (int j = 0; j < monthRegion[i].length; j++) {
				Region region = new Region();
				region.set(j * dayWidth, i * dayHeight, dayWidth +
					j * dayWidth, dayHeight + i * dayHeight);
				monthRegion[i][j] = region;
			}
		}
	}

	/* get mMonth region for current mMonth */
	private Region[][] getMonthRegion() {
		Region[][] monthRegion = mMonthWithSixWeeks;
		return monthRegion;
	}

	/* draw all the text in mMonth view */
	private void draw(Canvas canvas, Rect rect, int xIndex, int yIndex) {
		MonthDay monthDay = mMonth.getMonthDay(xIndex, yIndex);

	//	drawBackground(canvas, rect, monthDay, xIndex, yIndex);
		drawBackground(canvas,rect, monthDay);
		drawCalendarText(canvas, rect, monthDay);
		drawShiftText(canvas,rect, monthDay);
		drawHoliday(canvas, rect, monthDay);
	}

	private void drawBackground(Canvas canvas, Rect rect, MonthDay monthDay) {



        //draw a calendar grid
		mPaint.setColor(mCalendarView.getGridColor());
		canvas.drawRect(rect, mPaint);

		if(monthDay.isToday() && monthDay.isCheckable()) {
			mPaint.setColor(mCalendarView.getTodayIndicatorColor());
			canvas.drawRect(rect.left + 1, rect.top + 1, rect.right - 1, rect.bottom - 1, mPaint);
			mPaint.setColor(mCalendarView.getMonthCheckableBackgroundColor());
			canvas.drawRect(rect.left + 10, rect.top + 10, rect.right - 10, rect.bottom - 10, mPaint);
		} else {

			if (!monthDay.isCheckable()) {
				mPaint.setColor(mCalendarView.getMonthUncheckableBackgroundColor());
			} else {
				mPaint.setColor(mCalendarView.getMonthCheckableBackgroundColor());
			}

			canvas.drawRect(rect.left + 1, rect.top + 1, rect.right - 1, rect.bottom - 1, mPaint);
		}


	}

	/* draw calendar text in mMonth view */
	private void drawCalendarText(Canvas canvas, Rect rect, MonthDay monthDay) {
		if (monthDay == null) {
			return;
		}

		if (!monthDay.isCheckable()) {
			mPaint.setColor(mCalendarView.getUnCheckableColor());
		} else {
			mPaint.setColor(mCalendarView.getSolarTextColor());
		}

		mPaint.setTextSize(mCalendarTextSize);
		canvas.drawText(monthDay.getSolarDay(), rect.right - rect.width() / 4, rect.top + rect.height() / 4, mPaint);
	}

	/* draw lunar text in mMonth view */
	private void drawShiftText(Canvas canvas, Rect rect, MonthDay monthDay) {
		if (monthDay == null) {
			return;
		}

		if (!monthDay.isCheckable()) {
			mPaint.setColor(mCalendarView.getUnCheckableColor());
		} else {
			mPaint.setColor(mCalendarView.getShiftTextColor());
		}

		mPaint.setTextSize(mShiftTextSize);
		canvas.drawText(monthDay.getShift(), rect.centerX(), rect.centerY() + mShiftTextSize/2, mPaint);
	}

	private void drawHoliday(Canvas canvas, Rect rect, MonthDay monthDay) {
		if(monthDay.isTodayHoliday() == true) {
			if(!monthDay.isCheckable()) {
				mPaint.setColor(Color.RED);
			} else {
				mPaint.setColor(Color.BLUE);
			}
			canvas.drawRect(rect.left + 20, rect.top + 20, rect.left + 40, rect.top+40, mPaint);
		}
	}

	/* draw circle for selected mDay */
	/*private void drawBackground(Canvas canvas, Rect rect, MonthDay mDay, int xIndex, int yIndex) {
		if (mDay.isToday()) {
			Drawable background = mLunarView.getTodayBackground();
			if (background == null) {
				drawRing(canvas, rect);
			} else {
				background.setBounds(rect);
				background.draw(canvas);
			}

			return;
		}

		/* not today was selected */
		/*if (mSelectedIndex == -1 && mDay.isFirstDay()) {
			mSelectedIndex = xIndex * DAYS_IN_WEEK + yIndex;
		}

		if (mSelectedIndex / DAYS_IN_WEEK != xIndex ||
			mSelectedIndex % DAYS_IN_WEEK != yIndex) {
			return;
		}

		mPaint.setColor(mLunarView.getCheckedDayBackgroundColor());
		canvas.drawCircle(rect.centerX(), rect.centerY(), mCircleRadius, mPaint);
	}*/

	/* draw ring as background of today */
/*	private void drawRing(Canvas canvas, Rect rect) {
		mPaint.setColor(Color.RED);
		canvas.drawCircle(rect.centerX(), rect.centerY(), mCircleRadius, mPaint);
		mPaint.setColor(mLunarView.getMonthBackgroundColor());
		canvas.drawCircle(rect.centerX(), rect.centerY(), mCircleRadius - 4, mPaint);
	}*/

	/* handle date click event */
	private void handleClickEvent(int x, int y) {
		Region[][] monthRegion = getMonthRegion();
		for (int i = 0; i < monthRegion.length; i++) {
			for (int j = 0; j < DAYS_IN_WEEK; j++) {
				Region region = monthRegion[i][j];
				if (!region.contains(x, y)) {
					continue;
				}

				MonthDay monthDay = mMonth.getMonthDay(i, j);
				if (monthDay == null) {
					return;
				}

				int day = monthDay.getCalendar().get(Calendar.DAY_OF_MONTH);

				if (monthDay.isCheckable()) {
					mSelectedIndex = i * DAYS_IN_WEEK + j;
					performDayClick();
					invalidate();
				} else {
					if (monthDay.getDayFlag() == MonthDay.PREV_MONTH_DAY) {
						mCalendarView.showPrevMonth(day);
					} else if (monthDay.getDayFlag() == MonthDay.NEXT_MONTH_DAY) {
						mCalendarView.showNextMonth(day);
					}
				}
				break;
			}
		}
	}

	/**
	 * Perform mDay click event.
	 */
	protected void performDayClick() {
		MonthDay monthDay = mMonth.getMonthDay(mSelectedIndex);
		mCalendarView.dispatchDateClickListener(monthDay);
	}

	/**
	 * Set selected mDay, the selected mDay will draw background.
	 *
	 * @param day selected mDay
	 */
	protected void setSelectedDay(int day) {
		if (mMonth.isMonthOfToday() && day == 0) {
			mSelectedIndex = mMonth.getIndexOfToday();
		} else {
			int selectedDay = day == 0 ? 1 : day;
			mSelectedIndex = mMonth.getIndexOfDayInCurMonth(selectedDay);
		}

		invalidate();

		if ((day == 0 && mCalendarView.getShouldPickOnMonthChange()) || day != 0) {
			performDayClick();
		}
	}

	public static int get(int index, int month, int year) {
		Month m = new Month(year, month, 1);
		return m.getDay(index);
	}
}
