package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities;

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

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.FavoritesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes.RandomQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchPicturesOfQuotes.SearchPicturesOfQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SearchQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.SettingsFragment;
import com.petertieu.android.quotesearch.R;



//Main activity - contains the CORE fragments of the app (i.e.
public class MainActivity extends AppCompatActivity {


    //================= Declare INSTANCE VARIABLES ==============================================================

    //Layout reference variables
    private DrawerLayout mDrawerLayout;         //Layout of the MainActivity

    //Fragment management reference variables
    private FragmentManager mFragmentManager;    //Manager for mFragment removal and addition
    private Fragment mFragment;                  //Fragment to be active on screen (only one is active at a time)

    //Navigation drawer fragment reference variables
    QuoteOfTheDayFragment mQuoteOfTheDayFragment = new QuoteOfTheDayFragment();
    SearchQuotesFragment mSearchQuotesFragment = new SearchQuotesFragment();
    RandomQuotesFragment mRandomQuotesFragment = new RandomQuotesFragment();
    SearchPicturesOfQuotesFragment mSearchPicturesOfQuotesFragment = new SearchPicturesOfQuotesFragment();
    FavoritesFragment mFavoritesFragment = new FavoritesFragment();
    SettingsFragment mSettingsFragment = new SettingsFragment();




    //================= Define METHODS ===========================================================================

    //Override onCreate(..) activity lifecycle callback method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Set layout for the MainActivity
        setContentView(R.layout.activity_main);

        //Define reference variable for the DrawerLayout root element of the activity_main layout resource file
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        //================= CONFIGURE TOOLBAR ====================================================================

        //Define reference ariable for the Toolbarm widget element of the activity_main layout resource file
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Set the Action Bar to the toolbar layout reference variable
        setSupportActionBar(toolbar);

        //Get the Action Bar from the activity
        ActionBar actionbar = getSupportActionBar();

        //Enable the app's "home" button on the toolbar
        actionbar.setDisplayHomeAsUpEnabled(true);

        //Change the "home" button to the navigation drawer drawable (i.e. ic_menu)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



        //================= CONFIGURE FRAGMENTS ====================================================================
        //Create the FragmentManager for interacting with fragments associated with MainActivity
        mFragmentManager = getSupportFragmentManager();

        //Create (first) mFragment to be opened
        mFragment = new QuoteOfTheDayFragment();

        //Add QuoteOfTheDayFragment mFragment to the activity's view
        mFragmentManager.beginTransaction().add(R.id.content_frame, mFragment).commit();



        //================= CONFIGURE NAVIGATION DRAWER ====================================================================
        //Get the reference variable to the the NavigationView (i.e. the layout of the navigation drawer)
        NavigationView navigationView = findViewById(R.id.navigation_view);

        //Set listener for the items of the navigation drawer
        navigationView.setNavigationItemSelectedListener(

                //Create listener the listener
                new NavigationView.OnNavigationItemSelectedListener() {

                    //Override the listener interface method to define what happens when a navigation drawer item is clicked on
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //Set the selected item to be highlighted
                        menuItem.setChecked(true);

                        //Close the navigation drawer when the item is tapped
                        mDrawerLayout.closeDrawers();

                        //Scan through the navigation drawer's MenuItem IDs. Perform operations to open the fragments of the selected items
                        switch (menuItem.getItemId()){

                            case R.id.quote_of_the_day:
                                openFragment(mQuoteOfTheDayFragment);
                                return true;

                            case R.id.search_quote:
                                openFragment(mSearchQuotesFragment);
                                return true;

                            case R.id.random_quotes:
                                openFragment(mRandomQuotesFragment);
                                return true;

                            case R.id.search_pictures_of_quotes:
                                openFragment(mSearchPicturesOfQuotesFragment);
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
            //Remove the current fragment from the R.id.content_frame RelativeLayout
            mFragmentManager.beginTransaction().remove(mFragment).commit();
        }

        //Re-assign the mFragment reference variable to the Fragment to open
        mFragment = fragmentToOpen;

        //Call mFragmentManager to add the new fragment to the RelativeLayout
        mFragmentManager.beginTransaction().add(R.id.content_frame, mFragment).commit();
    }




    //When the Back button is pressed
    @Override
    public void onBackPressed() {

        //If the navigation drawer is opened
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            //Close the navigation drawer
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }




    //Create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the options menu
        getMenuInflater().inflate(R.menu.activity_main, menu);
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

            //The Settings toolbar button
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}