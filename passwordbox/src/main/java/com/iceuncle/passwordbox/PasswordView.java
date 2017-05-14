package com.iceuncle.passwordbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tianyang on 2017/4/27.
 */

public class PasswordView extends RelativeLayout {
    private EditText editText;
    private TextView[] textViews;
    private View[] dividerViews;
    //存储密码字符
    private StringBuffer stringBuffer = new StringBuffer();
    private final int PWD_LENGTH = 6;
    private int count = 6;
    //密码字符串
    private String strPassword;
    //背景
    private LayerDrawable themeLeftDrawable, themeMidDrawable, themeRightDrawable,
            normalLeftDrawable, normalMidDrawable, normalRightDrawable;

    //线宽
    private int lineWidth;
    //边框角度
    private int corners;
    //背景色
    private int backgroundColor = Color.WHITE;
    //选中色
    private int chosenColor;
    //默认边框色
    private int normalBorderColor;

    private int textSize;


    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        chosenColor = ContextCompat.getColor(context, R.color.password_chosen_color);
        normalBorderColor = ContextCompat.getColor(context, R.color.password_border_color);
        lineWidth = dip2px(context, 1);

        initCustomAttrs(context, attrs);
        initView(context);
        initHanders();
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {

        if (attr == R.styleable.PasswordView_pwdViewLineWidth) {
            lineWidth = typedArray.getDimensionPixelSize(attr, lineWidth);
        } else if (attr == R.styleable.PasswordView_pwdViewCorners) {
            corners = typedArray.getDimensionPixelSize(attr, corners);
        } else if (attr == R.styleable.PasswordView_pwdBackgroundColor) {
            backgroundColor = typedArray.getColor(attr, backgroundColor);
        } else if (attr == R.styleable.PasswordView_pwdChosenColor) {
            chosenColor = typedArray.getColor(attr, chosenColor);
        } else if (attr == R.styleable.PasswordView_pwdNormalBorderColor) {
            normalBorderColor = typedArray.getColor(attr, normalBorderColor);
        } else if (attr == R.styleable.PasswordView_pwdTextSize) {
            textSize = typedArray.getDimensionPixelSize(attr, textSize);
            Log.d("qqq", "textSize  " + textSize);
        }
    }


    private void initView(Context context) {
        View view = View.inflate(context, R.layout.password_view, this);

        textViews = new TextView[PWD_LENGTH];
        dividerViews = new View[PWD_LENGTH - 1];

        editText = (EditText) view.findViewById(R.id.item_edittext);
        textViews[0] = (TextView) view.findViewById(R.id.item_password_tv1);
        textViews[1] = (TextView) view.findViewById(R.id.item_password_tv2);
        textViews[2] = (TextView) view.findViewById(R.id.item_password_tv3);
        textViews[3] = (TextView) view.findViewById(R.id.item_password_tv4);
        textViews[4] = (TextView) view.findViewById(R.id.item_password_tv5);
        textViews[5] = (TextView) view.findViewById(R.id.item_password_tv6);

        dividerViews[0] = view.findViewById(R.id.divider_v1);
        dividerViews[1] = view.findViewById(R.id.divider_v2);
        dividerViews[2] = view.findViewById(R.id.divider_v3);
        dividerViews[3] = view.findViewById(R.id.divider_v4);
        dividerViews[4] = view.findViewById(R.id.divider_v5);

        editText.setCursorVisible(false);//将光标隐藏

        GradientDrawable gradientThemeLeft1 = new GradientDrawable();
        gradientThemeLeft1.setCornerRadii(new float[]{corners, corners, 0, 0, 0, 0, corners, corners});
        gradientThemeLeft1.setColor(chosenColor);
        GradientDrawable gradientThemeLeft2 = new GradientDrawable();
        gradientThemeLeft2.setCornerRadii(new float[]{corners, corners, 0, 0, 0, 0, corners, corners});
        gradientThemeLeft2.setColor(backgroundColor);
        Drawable[] layersThemeLeft = {gradientThemeLeft1, gradientThemeLeft2};
        themeLeftDrawable = new LayerDrawable(layersThemeLeft);
        themeLeftDrawable.setLayerInset(0, 0, 0, 0, 0);
        themeLeftDrawable.setLayerInset(1, lineWidth, lineWidth, 0, lineWidth);


        GradientDrawable gradientThemeMid1 = new GradientDrawable();
        gradientThemeMid1.setColor(chosenColor);
        GradientDrawable gradientThemeMid2 = new GradientDrawable();
        gradientThemeMid2.setColor(backgroundColor);
        Drawable[] layersThemeMid = {gradientThemeMid1, gradientThemeMid2};
        themeMidDrawable = new LayerDrawable(layersThemeMid);
        themeMidDrawable.setLayerInset(0, 0, 0, 0, 0);
        themeMidDrawable.setLayerInset(1, 0, lineWidth, 0, lineWidth);


        GradientDrawable gradientThemeRight1 = new GradientDrawable();
        gradientThemeRight1.setCornerRadii(new float[]{0, 0, corners, corners, corners, corners, 0, 0});
        gradientThemeRight1.setColor(chosenColor);
        GradientDrawable gradientThemeRight2 = new GradientDrawable();
        gradientThemeRight2.setCornerRadii(new float[]{0, 0, corners, corners, corners, corners, 0, 0});
        gradientThemeRight2.setColor(backgroundColor);
        Drawable[] layersThemeRight = {gradientThemeRight1, gradientThemeRight2};
        themeRightDrawable = new LayerDrawable(layersThemeRight);
        themeRightDrawable.setLayerInset(0, 0, 0, 0, 0);
        themeRightDrawable.setLayerInset(1, 0, lineWidth, lineWidth, lineWidth);


        GradientDrawable gradientNormalLeft1 = new GradientDrawable();
        gradientNormalLeft1.setCornerRadii(new float[]{corners, corners, 0, 0, 0, 0, corners, corners});
        gradientNormalLeft1.setColor(normalBorderColor);
        GradientDrawable gradientNormalLeft2 = new GradientDrawable();
        gradientNormalLeft2.setCornerRadii(new float[]{corners, corners, 0, 0, 0, 0, corners, corners});
        gradientNormalLeft2.setColor(backgroundColor);
        Drawable[] layersNormalLeft = {gradientNormalLeft1, gradientNormalLeft2};
        normalLeftDrawable = new LayerDrawable(layersNormalLeft);
        normalLeftDrawable.setLayerInset(0, 0, 0, 0, 0);
        normalLeftDrawable.setLayerInset(1, lineWidth, lineWidth, 0, lineWidth);


        GradientDrawable gradientNormalMid1 = new GradientDrawable();
        gradientNormalMid1.setColor(normalBorderColor);
        GradientDrawable gradientNormalMid2 = new GradientDrawable();
        gradientNormalMid2.setColor(backgroundColor);
        Drawable[] layersNormalMid = {gradientNormalMid1, gradientNormalMid2};
        normalMidDrawable = new LayerDrawable(layersNormalMid);
        normalMidDrawable.setLayerInset(0, 0, 0, 0, 0);
        normalMidDrawable.setLayerInset(1, 0, lineWidth, 0, lineWidth);


        GradientDrawable gradientNormalRight1 = new GradientDrawable();
        gradientNormalRight1.setCornerRadii(new float[]{0, 0, corners, corners, corners, corners, 0, 0});
        gradientNormalRight1.setColor(normalBorderColor);
        GradientDrawable gradientNormalRight2 = new GradientDrawable();
        gradientNormalRight2.setCornerRadii(new float[]{0, 0, corners, corners, corners, corners, 0, 0});
        gradientNormalRight2.setColor(backgroundColor);
        Drawable[] layersNormalRight = {gradientNormalRight1, gradientNormalRight2};
        normalRightDrawable = new LayerDrawable(layersNormalRight);
        normalRightDrawable.setLayerInset(0, 0, 0, 0, 0);
        normalRightDrawable.setLayerInset(1, 0, lineWidth, lineWidth, lineWidth);


        textViews[0].setBackgroundDrawable(themeLeftDrawable);
        textViews[1].setBackgroundDrawable(normalMidDrawable);
        textViews[2].setBackgroundDrawable(normalMidDrawable);
        textViews[3].setBackgroundDrawable(normalMidDrawable);
        textViews[4].setBackgroundDrawable(normalMidDrawable);
        textViews[5].setBackgroundDrawable(normalRightDrawable);


        dividerViews[0].setBackgroundColor(chosenColor);
        ViewGroup.LayoutParams lp = dividerViews[0].getLayoutParams();
        lp.width = lineWidth;
        dividerViews[0].setLayoutParams(lp);
        for (int i = 1; i < PWD_LENGTH - 1; i++) {
            ViewGroup.LayoutParams lpi = dividerViews[i].getLayoutParams();
            lpi.width = lineWidth;
            dividerViews[i].setLayoutParams(lpi);
            dividerViews[i].setBackgroundColor(normalBorderColor);
        }

        if (textSize != 0)
            for (int i = 0; i < PWD_LENGTH; i++) {
                textViews[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }

    }

    private void initHanders() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //重点   如果字符不为""时才进行操作
                if (!editable.toString().equals("")) {
                    if (stringBuffer.length() == 6) {
                        //当密码长度大于5位时edittext置空
                        editText.setText("");
                        return;
                    } else {
                        //将文字添加到StringBuffer中
                        stringBuffer.append(editable);
                        editText.setText("");//添加后将EditText置空  造成没有文字输入的错局
                        count = stringBuffer.length();//记录stringbuffer的长度
                        strPassword = stringBuffer.toString();
                        if (stringBuffer.length() == PWD_LENGTH) {
                            //文字长度位6   则调用完成输入的监听
                            stringBuffer.setLength(0);
                            for (int i = 0; i < PWD_LENGTH; i++) {
                                textViews[i].setText("");
                            }
                            if (inputCompleteListener != null) {
                                inputCompleteListener.inputComplete();
                            }
                        }
                        upDateView(stringBuffer.length());
                    }

                    for (int i = 0; i < stringBuffer.length(); i++) {
                        textViews[i].setText("●");
                    }
                }
            }
        });

        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }
        });

    }


    public boolean onKeyDelete() {
        if (count == 0) {
            count = 6;
            return true;
        }
        if (stringBuffer.length() > 0) {
            //删除相应位置的字符
            stringBuffer.delete((count - 1), count);
            count--;
            strPassword = stringBuffer.toString();
            textViews[stringBuffer.length()].setText("");
            upDateView(stringBuffer.length());
        }
        return false;
    }


    public void upDateView(int length) {
        for (int i = 0; i < PWD_LENGTH - 1; i++) {
            dividerViews[i].setBackgroundColor(normalBorderColor);
        }


        textViews[0].setBackgroundDrawable(normalLeftDrawable);
        textViews[1].setBackgroundDrawable(normalMidDrawable);
        textViews[2].setBackgroundDrawable(normalMidDrawable);
        textViews[3].setBackgroundDrawable(normalMidDrawable);
        textViews[4].setBackgroundDrawable(normalMidDrawable);
        textViews[5].setBackgroundDrawable(normalRightDrawable);

        if (length == 0) {
            textViews[0].setBackgroundDrawable(themeLeftDrawable);
            dividerViews[length].setBackgroundColor(chosenColor);
        } else if (length == PWD_LENGTH - 1) {
            textViews[PWD_LENGTH - 1].setBackgroundDrawable(themeRightDrawable);
            dividerViews[length - 1].setBackgroundColor(chosenColor);
        } else {
            textViews[length].setBackgroundDrawable(themeMidDrawable);
            dividerViews[length - 1].setBackgroundColor(chosenColor);
            dividerViews[length].setBackgroundColor(chosenColor);
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getStrPassword() {
        return strPassword;
    }

    public void setContent(String content) {
        editText.setText(content);
    }

}
