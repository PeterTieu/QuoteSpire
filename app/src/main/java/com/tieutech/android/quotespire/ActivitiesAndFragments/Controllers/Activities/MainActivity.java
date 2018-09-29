package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Activities;

import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.FavoritesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes.MyQuotesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures.RandomQuotePicturesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes.RandomQuotesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotePictures.SearchQuotePicturesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SearchQuotesFragment;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.SettingsFragment;
import com.tieutech.android.quotespire.R;



//Main activity - contains the CORE fragments of the app (i.e.
@SuppressWarnings({"WeakerAccess", "RedundantCast"})
public class MainActivity extends AppCompatActivity {


    //================= Declare INSTANCE VARIABLES ==============================================================
    public static String PACKAGE_NAME; //Package name

    //Layout reference variables
    private DrawerLayout mDrawerLayout;         //Layout of the MainActivity

    //Fragment management reference variables
    private FragmentManager mFragmentManager;    //Manager for mFragment removal and addition
    private Fragment mFragment;                  //Fragment to be active on screen (only one is active at a time)

    //Navigation drawer fragment reference variables
    final QuoteOfTheDayFragment mQuoteOfTheDayFragment = new QuoteOfTheDayFragment();
    final SearchQuotesFragment mSearchQuotesFragment = new SearchQuotesFragment();
    final RandomQuotesFragment mRandomQuotesFragment = new RandomQuotesFragment();
    final RandomQuotePicturesFragment mRandomQuotePicturesFragment = new RandomQuotePicturesFragment();
    final SearchQuotePicturesFragment mSearchQuotePicturesFragment = new SearchQuotePicturesFragment();
    final MyQuotesFragment mMyQuotesFragment = new MyQuotesFragment();
    final FavoritesFragment mFavoritesFragment = new FavoritesFragment();
    final SettingsFragment mSettingsFragment = new SettingsFragment();




    //================= Define METHODS ===========================================================================

    //Override onCreate(..) activity lifecycle callback method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName(); //Get package name

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Set orientation configuration to vertical

        setContentView(R.layout.activity_main); //Set layout for the MainActivity
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //Define reference variable for the DrawerLayout root element of the activity_main layout resource file


        //================= CONFIGURE TOOLBAR ====================================================================
        Toolbar toolbar = findViewById(R.id.toolbar); //Define reference variable for the Toolbar widget element of the activity_main layout resource file
        setSupportActionBar(toolbar); //Set the Action Bar to the toolbar layout reference variable
        ActionBar actionbar = getSupportActionBar(); //Get the Action Bar from the activity
        actionbar.setDisplayHomeAsUpEnabled(true); //Enable the app's "home" button on the toolbar
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu); //Change the "home" button to the navigation drawer drawable (i.e. ic_menu)


        //================= CONFIGURE FRAGMENTS ====================================================================
        mFragmentManager = getSupportFragmentManager(); //Create the FragmentManager for interacting with fragments associated with MainActivity
        mFragment = new QuoteOfTheDayFragment(); //Create (first) mFragment to be opened
        mFragmentManager.beginTransaction().add(R.id.content_frame, mFragment).commit(); //Add QuoteOfTheDayFragment mFragment to the activity's view


        //================= CONFIGURE NAVIGATION DRAWER ====================================================================
        NavigationView navigationView = findViewById(R.id.navigation_view); //Get the reference variable to the the NavigationView (i.e. the layout of the navigation drawer)

        //Set listener for the items of the navigation drawer
        navigationView.setNavigationItemSelectedListener(

                //Create listener the listener
                new NavigationView.OnNavigationItemSelectedListener() {

                    //Override the listener interface method to define what happens when a navigation drawer item is clicked on
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true); //Set the selected item to be highlighted

                        mDrawerLayout.closeDrawers(); //Close the navigation drawer when the item is tapped

                        //Scan through the navigation drawer's MenuItem IDs. Perform operations to open the fragments of the selected items
                        switch (menuItem.getItemId()){

                            case R.id.quote_of_the_day_quote_title:
                                openFragment(mQuoteOfTheDayFragment);
                                return true;

                            case R.id.random_quotes:
                                openFragment(mRandomQuotesFragment);
                                return true;

                            case R.id.random_quote_pictures:
                                openFragment(mRandomQuotePicturesFragment);
                                return true;

                            case R.id.search_quotes:
                                openFragment(mSearchQuotesFragment);
                                return true;


                            case R.id.search_quote_pictures:
                                openFragment(mSearchQuotePicturesFragment);
                                return true;

                            case R.id.my_quotes:
                                openFragment(mMyQuotesFragment);
                                return true;

                            case R.id.favorites:
                                openFragment(mFavoritesFragment);
                                return true;

                            case R.id.settings:
                                openFragment(mSettingsFragment);
                                return true;
                        }

                        return false;
                    }
                });
    }




    //Helper method - performs the closing of any current fragments of the R.id.content_frame RelativeLayout and opens the fragment selected in the navigation drawer
    private void openFragment(Fragment fragmentToOpen){

        //If the Fragment that is opened is selected in the navigation drawer, then DO NOTHING.
        //NOTE: If this conditional block is left out, then the Fragment would disappear if it is opened and selected again in the navigation drawer
        if (mFragment == fragmentToOpen){
            return;
        }

        //If a fragment already exists (i.e a fragment is in view)
        if (mFragment != null){
            mFragmentManager.beginTransaction().remove(mFragment).commit(); //Remove the current fragment from the R.id.content_frame RelativeLayout
        }

        mFragment = fragmentToOpen; //Re-assign the mFragment reference variable to the Fragment to open

        mFragmentManager.beginTransaction().add(R.id.content_frame, mFragment).commit(); //Call mFragmentManager to add the new fragment to the RelativeLayout
    }




    //When the Back button is pressed
    @Override
    public void onBackPressed() {

        //If the navigation drawer is opened
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START); //Close the navigation drawer
        }
        else{
            super.onBackPressed();
        }
    }




    //Create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Scan through the options menu's MenuItem IDs
        switch (item.getItemId()) {

            //The navigation drawer
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}