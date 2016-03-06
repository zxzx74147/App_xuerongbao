package com.wazxb.zhuxuebao.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import com.alibaba.sdk.android.util.Md5Utils;
import com.wazxb.zhuxuebao.R;
import com.wazxb.zhuxuebao.databinding.InputTextViewBinding;
import com.wazxb.zhuxuebao.util.IDUtil;
import com.wazxb.zhuxuebao.util.RequestCode;
import com.wazxb.zhuxuebao.util.ZXDataPickerHelper;
import com.zxzx74147.devlib.utils.ZXStringUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhengxin on 16/3/1.
 */
public class InputTextView extends LinearLayout {


    private InputTextViewBinding mBinding = null;
    private boolean mIsNotNull = false;
    private String mHint = null;
    private String mRegex = null;
    private String mKey = null;
    private final int PHONE_REQUEST_ID = IDUtil.genID() + RequestCode.REQUEST_PICK_PHONE;
    private String mPickType = null;
    private String mErrorHint;
    private int inputType;


    public InputTextView(Context context) {
        super(context);
        init(null);
    }

    public InputTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public InputTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public String getKey() {
        return mKey;
    }

    public String getText() {

        String text = mBinding.edit.getText().toString();
        if (!ZXStringUtil.checkString(text)) {
            return text;
        }
        if ((inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0) {
            text = Md5Utils.md5Digest(text.getBytes());
        }
        if ("sex".equals(mPickType)) {
            if ("男".equals(text)) {
                return "1";
            } else if ("女".equals(text)) {
                return "2";
            }
        } else if ("phone".equals(mPickType)) {
            text = ZXStringUtil.formatPhone(text);
        }
        return text;
    }

    public String getError() {
        return mErrorHint;
    }

    public boolean isNotNull() {
        return mIsNotNull;
    }

    public boolean getIsFilled() {
        return ZXStringUtil.checkString(mBinding.edit.getText().toString());
    }

    public boolean isReady() {
        if (mIsNotNull && ZXStringUtil.checkString(getText()) && checkRegex()) {
            return true;
        }
        if (!mIsNotNull && !ZXStringUtil.checkString(getText())) {
            return true;
        }
        return false;
    }

    private boolean checkRegex() {
        return ZXStringUtil.matchRegex(getText(), mRegex);
    }


    private void init(AttributeSet attrs) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this.getContext()), R.layout.input_text_view, this, true);
        setGravity(Gravity.CENTER);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.InputTextView, 0, 0);
            mIsNotNull = a.getBoolean(R.styleable.InputTextView_not_null, false);
            boolean mIsShowNotNull = a.getBoolean(R.styleable.InputTextView_show_not_null, mIsNotNull);
            if (mIsShowNotNull) {
                mBinding.notNull.setVisibility(VISIBLE);
            } else {
                mBinding.notNull.setVisibility(INVISIBLE);
            }
            mPickType = a.getString(R.styleable.InputTextView_pick_type);
            mKey = a.getString(R.styleable.InputTextView_post_key);
            String label = a.getString(R.styleable.InputTextView_label_text);
            mBinding.label.setText(label);
            inputType = a.getInt(R.styleable.InputTextView_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
            mBinding.edit.setInputType(inputType);
            mRegex = a.getString(R.styleable.InputTextView_match_regex);
            mHint = a.getString(R.styleable.InputTextView_edit_hint);
            mErrorHint = a.getString(R.styleable.InputTextView_error_hint);
            if (!ZXStringUtil.checkString(mErrorHint)) {
                mErrorHint = mHint;
            }
            mBinding.edit.setHint(mHint);
            int drawableID = a.getResourceId(R.styleable.InputTextView_input_right_drawable, 0);
            if (drawableID != 0) {
                mBinding.rightImg.setBackgroundResource(drawableID);
            }

            String edit = a.getString(R.styleable.InputTextView_input_edit_text);
            mBinding.edit.setText(edit);
            int width = a.getDimensionPixelSize(R.styleable.InputTextView_label_width, 0);
            if (width > 0) {
                ViewGroup.LayoutParams lp = mBinding.labelLayout.getLayoutParams();
                lp.width = width;
            }

            a.recycle();
        }

        if ("phone".equals(mPickType)) {
            mBinding.rightImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            ContactsContract.Contacts.CONTENT_URI);
                    ((Activity) getContext()).startActivityForResult(intent, PHONE_REQUEST_ID);
                }
            });
        } else if ("year".equals(mPickType)) {
            mBinding.edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZXDataPickerHelper.selectYear(getContext(), new ZXDataPickerHelper.IDateSelected() {
                        @Override
                        public void onSelected(int year, int month, int day) {
                            mBinding.edit.setText(year + "年");
                        }
                    });
                }
            });
        } else if ("day".equals(mPickType)) {
            mBinding.edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> sexs = new LinkedList<>();
                    sexs.add("男");
                    sexs.add("女");
                    ZXDataPickerHelper.selectDay(getContext(), new ZXDataPickerHelper.IDateSelected() {

                        @Override
                        public void onSelected(int year, int month, int day) {
                            mBinding.edit.setText(year + "-" + month + "-" + day);
                        }
                    });
                }
            });
        } else if ("sex".equals(mPickType)) {
            mBinding.edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> sexs = new LinkedList<>();
                    sexs.add("男");
                    sexs.add("女");
                    ZXDataPickerHelper.selectItem(getContext(), sexs, new ZXDataPickerHelper.IItemSelected() {
                        @Override
                        public void onSelected(String item) {
                            mBinding.edit.setText(item);
                        }
                    });
                }
            });

        }

    }

    public void addTextChanged(TextWatcher watcher) {
        mBinding.edit.addTextChangedListener(watcher);
    }

    public void setContent(String edit) {
        mBinding.edit.setText(edit);
    }

    public void onPhoneSelected(int phoneId, Uri contactData) {
        if (phoneId != PHONE_REQUEST_ID || contactData == null) {

            return;
        }

        Cursor cursor = ((Activity) getContext()).managedQuery(contactData, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            try {
                String phone = getContactPhone(cursor);
                phone = ZXStringUtil.formatPhone(phone);
                if (ZXStringUtil.checkString(phone)) {
                    mBinding.edit.setText(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //获取联系人电话
    private String getContactPhone(Cursor cursor) {

        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        //System.out.print(phoneNum);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = ((Activity) getContext()).getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            //int phoneCount = phones.getCount();
            //allPhoneNum = new ArrayList<String>(phoneCount);
            if (phones.moveToFirst()) {
                // 遍历所有的电话号码
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    String phoneNumber = phones.getString(index);
                    switch (phone_type) {
                        case 2:
                            phoneResult = phoneNumber;
                            break;
                    }
                    //allPhoneNum.add(phoneNumber);
                }
                if (!phones.isClosed()) {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }
}
