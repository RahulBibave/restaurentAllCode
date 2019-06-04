package com.resmenu.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.resmenu.R;
import com.resmenu.constants.AppConstants;
import com.resmenu.helper.FontHelper;

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        initFont(context , null);

    }

    public CustomTextView(Context context , AttributeSet attributeSet){
        super(context , attributeSet);
        initFont(context , attributeSet);

    }

    public CustomTextView(Context context , AttributeSet attributeSet , int defaultStyleAttribute){
        super(context , attributeSet , defaultStyleAttribute);
        initFont(context , attributeSet);
    }

    public String getStringText(){
        if (getText() != null){
            return  getText().toString().trim();
        }
        return null;
    }

    private void initFont(Context context, AttributeSet attributeSet) {

        TypedArray array = context.obtainStyledAttributes(attributeSet , R.styleable.textFonts);

        int mTextFont = array.getInteger(R.styleable.textFonts_fontType, AppConstants.Fonts.GOTHAM_BOOK);

        switch (mTextFont) {
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
                setTypeface(FontHelper.getGothamBook(context));
                break;
        }
    }
}
