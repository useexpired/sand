package com.sand;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.DatePicker;

public class SandActivity extends Activity {

	SandColorGen sandColor;
	RandomPixel pixelRand;
	int drawLoop;
	int daysLived;
	private int bx, by;
	int bDayMM, bDayDD, bDayYY;
	//
	// layout elements
	//
	private TextView txtDate;
	private Button butDate;// , butSand;
	static final int DATE_DIALOG_ID = 0;
	static final int SAND_DIALOG_ID = 1;

	// ---------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// capture our view elements
		txtDate = (TextView) findViewById(R.id.dateDisplay);
		butDate = (Button) findViewById(R.id.pickDate);

		// add a click listener to buttons
		butDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}

		});

		final Calendar cc = Calendar.getInstance();
		bDayYY = cc.get(Calendar.YEAR);
		bDayMM = cc.get(Calendar.MONTH);
		bDayDD = cc.get(Calendar.DAY_OF_MONTH);

		daysLived = 0;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, bDayYY, bDayMM,
					bDayDD);
		}
		return null;
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			bDayYY = year;
			bDayMM = monthOfYear;
			bDayDD = dayOfMonth;

			daysLived = lifeCounting(bDayYY, bDayMM, bDayDD);

			txtDate.setText(new StringBuilder().append(daysLived).append(
					" Days on Earth"));
			if (daysLived > 0) {
				drawOurSand();
			} else {
				txtDate.setText(new StringBuilder()
						.append(" Sorry, we cannot handle people not yet born..."));
			}
		}
	};

	private Paint mPaint;

	// --------------------------------------------------------------------------------
	private void drawOurSand() {
		//
		// show sand matrix
		//
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF00FF00);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(2);

		bx = 10;
		by = 20;
		sandColor = new SandColorGen();
		pixelRand = new RandomPixel(
				((WindowManager) getSystemService(WINDOW_SERVICE))
						.getDefaultDisplay());
		drawLoop = daysLived;
		// drawLoop = 0;

		MyView sandView = new MyView(this);
		setContentView(sandView);
		sandView.requestFocus();

		// setContentView(new MyView(this));

	}

	// ---------------------------------------------------------------
	public void colorChanged(int color) {
		mPaint.setColor(color);
	}

	// ------------------------------------------------------------
	private int lifeCounting(int yy, int mm, int dd) {

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();

		c1.set(yy, mm, dd, 0, 0, 0);
		//
		// time now
		//
		Calendar now = Calendar.getInstance();

		c2.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
				now.get(Calendar.DATE), 0, 0, 0);

		return ((int) daysBetween(c1.getTime(), c2.getTime()));

	}

	// --------------------------------------------------------------------------------
	public static long daysBetween(Date d1, Date d2) {
		long ONE_HOUR = 60 * 60 * 1000L;
		return ((d2.getTime() - d1.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}

	// ---------------------------------------------------------------
	//
	// MyView Class
	//
	// ---------------------------------------------------------------
	public class MyView extends View {

		private Bitmap mBitmap;
		private Canvas mCanvas;
		private Path mPath;
		private Paint mBitmapPaint;
		private Boolean firstdraw;

		// ---------------------------------------------------------------
		public MyView(Context c) {
			super(c);

			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
			firstdraw = true;
			// sandLoop();
		}

		// ---------------------------------------------------------------
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
		}

		// ---------------------------------------------------------------
		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.BLACK);// 0xFFFFFFFF);
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			canvas.drawPath(mPath, mPaint);
			if (firstdraw) {
				sandLoop();
				firstdraw = false;
			}
		}

		// ---------------------------------------------------------------
		private void sandLoop() {

			mPaint.setColor(sandColor.choseSand());
			pixelRand.nextRandom();
			bx = pixelRand.nextX;
			by = pixelRand.nextY;
			mCanvas.drawPoint(bx, by, mPaint);

			drawLoop--;
			invalidate();
			//
			// use timer instead of Thread.sleep() !!!
			//
			final Handler handler = new Handler();
			Timer tt = new Timer();

			tt.schedule(new TimerTask() {
				public void run() {
					handler.post(new Runnable() {
						public void run() {
							if (drawLoop > 0) {
								sandLoop();
							} else {
								/*
								mPaint.setStrokeWidth(30);
								mPaint.setColor(Color.RED);
								mCanvas.drawPoint(10, 10, mPaint);
								invalidate();
								*/
							}
						}
					});
				}
			}, 90);
		}
	}

}
