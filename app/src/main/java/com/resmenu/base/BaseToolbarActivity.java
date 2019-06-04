package com.resmenu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.resmenu.R;
import com.resmenu.customViews.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseToolbarActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    CustomTextView mToolbarTitle;

    @BindView(R.id.ibmycart)
    ImageView mImageViewCart;

    @BindView(R.id.ibsearch)
    ImageView mImageSearch;

    @Nullable
    @BindView(R.id.toolbar_main)
    protected Toolbar mToolbar;

    private ISearchImageClicked mSearchImageClicked;
    private ICartImageClicked mCartImageClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_toolbar);
    }

    public void showTitle(String title){
        if (mToolbarTitle != null){
            mToolbarTitle.setVisibility(View.VISIBLE);
            mToolbarTitle.setText(title);
        }
    }

    public void showSearchImage(final int id,ISearchImageClicked searchImageClicked){
        if (mImageSearch != null) {
            mImageSearch.setVisibility(View.VISIBLE);
            mImageSearch.setImageResource(id);
            this.mSearchImageClicked =  searchImageClicked;
        }
    }

    public void showCartImage(final int id,ICartImageClicked cartImageClicked){
        if (mImageViewCart != null) {
            mImageViewCart.setVisibility(View.VISIBLE);
            mImageViewCart.setImageResource(id);
            this.mCartImageClicked =  cartImageClicked;
        }
    }

    public interface ISearchImageClicked{
        void onSearchImageClicked();
    }

    public interface ICartImageClicked{
        void onCartImageClicked();
    }
}
