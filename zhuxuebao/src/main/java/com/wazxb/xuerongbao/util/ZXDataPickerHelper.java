package com.wazxb.xuerongbao.util;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.wangjie.wheelview.WheelView;
import com.wazxb.xuerongbao.R;
import com.zxzx74147.devlib.utils.TimerUtil;
import com.zxzx74147.devlib.utils.ZXDialogUtil;

import java.util.LinkedList;
import java.util.List;

import cn.jeesoft.widget.pickerview.CharacterPickerView;

/**
 * Created by zhengxin on 16/3/5.
 */
public class ZXDataPickerHelper {

    public static final int YEAR_CURRENT = TimerUtil.getCurrentYear();
    private static List<String> YEAR = new LinkedList<>();
    private static List<String> MONTH = new LinkedList<>();
    private static List<String> DAY_31 = new LinkedList<>();
    private static List<String> DAY_30 = new LinkedList<>();
    private static List<String> DAY_29 = new LinkedList<>();
    private static List<String> DAY_28 = new LinkedList<>();
    private static List<List<String>> MONTHS = new LinkedList<>();
    private static List<List<List<String>>> DAYS = new LinkedList<>();
    private static SparseArray<Boolean> mTable = new SparseArray<>(7);

    static {
        mTable.put(1, true);
        mTable.put(3, true);
        mTable.put(5, true);
        mTable.put(7, true);
        mTable.put(8, true);
        mTable.put(10, true);
        mTable.put(12, true);
        for (int i = 1; i <= 12; i++) {
            MONTH.add(String.valueOf(i));
        }

        for (int i = 1; i <= 31; i++) {
            DAY_31.add(String.valueOf(i));
            if (i < 31) {
                DAY_30.add(String.valueOf(i));
            }
            if (i < 30) {
                DAY_29.add(String.valueOf(i));
            }
            if (i < 29) {
                DAY_28.add(String.valueOf(i));
            }
        }


        for (int i = 0; i < 30; i++) {
            int year = YEAR_CURRENT - i;
            YEAR.add(String.valueOf(year));
            List<List<String>> per_year = new LinkedList<>();
            for (int j = 1; j <= 12; j++) {
                if (mTable.get(j, false)) {
                    per_year.add(DAY_31);
                } else if (j == 2) {
                    if (isLeapYear(year)) {
                        per_year.add(DAY_29);
                    } else {
                        per_year.add(DAY_28);
                    }
                } else {
                    per_year.add(DAY_30);
                }
            }
            MONTHS.add(MONTH);
            DAYS.add(per_year);
        }
    }

    public static boolean isLeapYear(int year) {

        boolean isLeapYear = false;
        if (year % 4 == 0 && year % 100 != 0) {
            isLeapYear = true;
        } else if (year % 400 == 0) {
            isLeapYear = true;
        }
        return isLeapYear;
    }

    public interface IDateSelected {
        void onSelected(int year, int month, int day);
    }

    public interface IItemSelected {
        void onSelected(String item);
    }

    public static Dialog selectYear(Context context, final IDateSelected listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_date_picker, null);
        final WheelView mPicket = (WheelView) view.findViewById(R.id.date_picker);
        Button mCancel = (Button) view.findViewById(R.id.cancel);
        Button mOk = (Button) view.findViewById(R.id.ok);
        mPicket.setItems(YEAR);

        final Dialog dialog = ZXDialogUtil.showDialog(context, view, null);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int year = mPicket.getSeletedIndex();
                listener.onSelected(year, 0, 0);
            }
        });
        return dialog;
    }

    public static Dialog selectDay(Context context, final IDateSelected listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_item_day_picker, null);
        final CharacterPickerView mPickerYear = (CharacterPickerView) view.findViewById(R.id.custom_picker_view_year);
        final CharacterPickerView mPickerMonth = (CharacterPickerView) view.findViewById(R.id.custom_picker_view_month);
        final CharacterPickerView mPickerDay = (CharacterPickerView) view.findViewById(R.id.custom_picker_view_day);
        Button mCancel = (Button) view.findViewById(R.id.cancel);
        Button mOk = (Button) view.findViewById(R.id.ok);
        mPickerYear.setPicker(YEAR);
        mPickerMonth.setPicker(MONTH);
        mPickerDay.setPicker(DAY_31);

        mPickerYear.setOnOptionChangedListener(new CharacterPickerView.OnOptionChangedListener() {
            @Override
            public void onOptionChanged(CharacterPickerView view, int option1, int option2, int option3) {
                int[] item = mPickerYear.getCurrentItems();
                int year = Integer.valueOf(YEAR.get(item[0]));
                item = mPickerMonth.getCurrentItems();
                int month = Integer.valueOf(MONTH.get(item[0]));
                if (mTable.get(month, false)) {
                    mPickerDay.setPicker(DAY_31);
                } else if (month == 2) {
                    if (isLeapYear(year)) {
                        mPickerDay.setPicker(DAY_29);
                    } else {
                        mPickerDay.setPicker(DAY_28);
                    }
                } else {
                    mPickerDay.setPicker(DAY_30);
                }
            }
        });
        mPickerMonth.setOnOptionChangedListener(new CharacterPickerView.OnOptionChangedListener() {
            @Override
            public void onOptionChanged(CharacterPickerView view, int option1, int option2, int option3) {
                int[] item = mPickerYear.getCurrentItems();
                int year = Integer.valueOf(YEAR.get(item[0]));
                item = mPickerMonth.getCurrentItems();
                int month = Integer.valueOf(MONTH.get(item[0]));
                if (mTable.get(month, false)) {
                    mPickerDay.setPicker(DAY_31);
                } else if (month == 2) {
                    if (isLeapYear(year)) {
                        mPickerDay.setPicker(DAY_29);
                    } else {
                        mPickerDay.setPicker(DAY_28);
                    }
                } else {
                    mPickerDay.setPicker(DAY_30);
                }
            }
        });
        final Dialog dialog = ZXDialogUtil.showDialog(context, view, null);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int[] selectedYear = mPickerYear.getCurrentItems();
                int[] selectedMonth = mPickerMonth.getCurrentItems();
                int[] selectedDay = mPickerDay.getCurrentItems();
                int year = Integer.valueOf(YEAR.get(selectedYear[0]));
                int month = Integer.valueOf(MONTH.get(selectedMonth[1]));
                int day = Integer.valueOf(DAY_31.get(selectedDay[2]));

                listener.onSelected(year, month, day);
            }
        });
        return dialog;

    }

    public static Dialog selectItem(Context context, final List<String> items, final IItemSelected listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_item_picker, null);
        final WheelView mPicker = (WheelView) view.findViewById(R.id.custom_picker_view);
        Button mCancel = (Button) view.findViewById(R.id.cancel);
        Button mOk = (Button) view.findViewById(R.id.ok);
        mPicker.setItems(items);
        final Dialog dialog = ZXDialogUtil.showDialog(context, view, null);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int selected = mPicker.getSeletedIndex();
                String item = items.get(selected);
                listener.onSelected(item);
            }
        });
        return dialog;

    }

    private static NumberPicker.Formatter mYearFormat = new NumberPicker.Formatter() {
        @Override
        public String format(int value) {
            return value + "年";
        }
    };
}
