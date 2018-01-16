package app.test.my.swipable;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int UNSELECTED_ALPHA = (int) (255 * 0.70F);
    private static final int BASE_ALPHA = 255 - UNSELECTED_ALPHA;

    // views
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // global fields
    private ViewPagerAdapter pagerAdapter;
    int previousOffSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_strip);
        viewPager = findViewById(R.id.pager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.header_title));
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset == 0 && positionOffsetPixels == 0) {
            return;
        }

        float offset = positionOffset;

        int lightPosition;
        int darkPosition;
        if (previousOffSet < positionOffsetPixels) {

            // we are moving to the right
            lightPosition = position;
            darkPosition = position + 1;
        } else if (previousOffSet > positionOffsetPixels) {

            // moving to left, reverse offset
            positionOffset = 1 - positionOffset;

            lightPosition = position + 1;
            darkPosition = position;
        } else {

            // same place return
            return;
        }

        animateTextOpacity(lightPosition, darkPosition, positionOffset);
        tabLayout.setScrollPosition(position, offset, true);
        previousOffSet = positionOffsetPixels;
    }

    private void animateTextOpacity(int lightPosition, int darkPosition, float positionOffset) {

        // get the views to be made light or dark
        TextView textViewLight = (TextView) ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(lightPosition)).getChildAt(1);
        TextView textViewDark = (TextView) ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(darkPosition)).getChildAt(1);

        int alpha = Math.round(UNSELECTED_ALPHA * positionOffset);
        int lightAlpha = BASE_ALPHA + (UNSELECTED_ALPHA - alpha);
        int darkAlpha = BASE_ALPHA + alpha;

        // set color based on alpha
        textViewLight.setTextColor(ColorUtils.setAlphaComponent(getResources().getColor(R.color.tabSelectTextColor), lightAlpha));
        textViewDark.setTextColor(ColorUtils.setAlphaComponent(getResources().getColor(R.color.tabSelectTextColor), darkAlpha));
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        // reset colors
        if (state == ViewPager.SCROLL_STATE_IDLE) {

            int position = tabLayout.getSelectedTabPosition();

            // set selected color
            TextView textViewLight = (TextView) ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(position)).getChildAt(1);
            textViewLight.setTextColor(getResources().getColor(R.color.tabSelectTextColor));

            // set normal tab color
            if (position + 1 < tabLayout.getTabCount()) {
                textViewLight = (TextView) ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(position + 1)).getChildAt(1);
                textViewLight.setTextColor(getResources().getColor(R.color.tabTextColor));
            }

            // set normal tab color
            if (position - 1 > 0) {
                textViewLight = (TextView) ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(position - 1)).getChildAt(1);
                textViewLight.setTextColor(getResources().getColor(R.color.tabTextColor));
            }
        }
    }
}
