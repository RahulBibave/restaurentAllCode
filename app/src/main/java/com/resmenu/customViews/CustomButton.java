package com.resmenu.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.resmenu.R;
import com.resmenu.constants.AppConstants;
import com.resmenu.helper.FontHelper;

public class CustomButton extends AppCompatButton {


    public CustomButton(Context context) {
        super(context);
        initButton();

    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton();
        initFont(context , attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton();
        initFont(context , attrs);

    }

    private void initButton() {
        setAllCaps(false);
    }

    private void initFont(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.textFonts);
        int mTextFont = a.getInteger(R.styleable.textFonts_fontType , AppConstants.Fonts.GOTHAM_BOOK);
        switch (mTextFont){
            case AppConstants.Fonts.GOTHAM_BLACK:
                setTypeface(FontHelper.getGothamBlack(context));
                break;

            case AppConstants.Fonts.GOTHAM_BOLD:
                setTypeface(FontHelper.getGothamBold(context));

                break;
            case AppConstants.Fonts.GOTHAM_BOOK:
                setTypeface(FontHelper.getGothamBook(context));
                break;

            case AppConstants.Fonts.GOTHAM_LIGHT:
                setTypeface(FontHelper.getGothamLight(context));
                break;

            case AppConstants.Fonts.GOTHAM_MEDIUM:
                setTypeface(FontHelper.getGothamMedium(context));
                break;

            case AppConstants.Fonts.GOTHAM_THIN:
                setTypeface(FontHelper.getGothamThin(context));
                break;

            default:
                setTypeface(FontHelper.getGothamMedium(context));
                break;
        }
    }
}
