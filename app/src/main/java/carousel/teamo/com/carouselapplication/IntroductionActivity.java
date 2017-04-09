package carousel.teamo.com.carouselapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static carousel.teamo.com.carouselapplication.LaunchActivity.INTRO_COMPLETED;
import static carousel.teamo.com.carouselapplication.LaunchActivity.MyPREFERENCES;

public class IntroductionActivity extends AppCompatActivity implements IntroductionFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
    CustomPagerAdapter customPagerAdapter;
    SharedPreferences sharedPreferences;
    boolean endNow = false;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        textView = (TextView) findViewById(R.id.textView6);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == customPagerAdapter.getCount() - 1) {
                    if (endNow) {
                        sharedPreferences.edit().putInt(INTRO_COMPLETED, 1).commit();
                        startActivity(new Intent(IntroductionActivity.this, LaunchActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == customPagerAdapter.getCount() - 2) {
                    endNow = true;
                    textView.setVisibility(View.VISIBLE);
                } else if (position != customPagerAdapter.getCount() - 1) {
                    endNow = false;
                    textView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        customPagerAdapter.addFragment(
                IntroductionFragment
                        .newInstance("SignIn to Use the App", "Use Facebook to get login the app",
                                R.drawable.facebook_image));
        customPagerAdapter.addFragment(
                IntroductionFragment
                        .newInstance("Add Games", "Add names of games you are playing",
                                R.drawable.game_image));
        customPagerAdapter.addFragment(
                IntroductionFragment
                        .newInstance("Add Scores", "Add Scores to share with friends to see who wins",
                                R.drawable.score_image));
        customPagerAdapter.addFragment(new Fragment());
        viewPager.setAdapter(customPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
