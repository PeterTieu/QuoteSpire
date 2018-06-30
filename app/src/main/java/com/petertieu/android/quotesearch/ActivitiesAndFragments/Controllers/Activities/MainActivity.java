package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Activities;

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
import android.widget.Toast;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Favorites.FavoritesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay.QuoteOfTheDayFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes.RandomQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchPicturesOfQuotes.SearchPicturesOfQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SearchQuotesFragment;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings.SettingsFragment;
import com.petertieu.android.quotesearch.R;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    int menuItemNumber;

    private FragmentManager fragmentManager;
    private Fragment fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);








        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

//        getActionBar().setDisplayShowHomeEnabled(true);  // hides action bar icon
//        getActionBar().setDisplayShowTitleEnabled(true); // hides action bar title


//        //Arg #4 and #5: String resources to describe the "open" and "close" drawer actions for accessibiltiy
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.action_settings, R.string.action_settings);
//        //Set listener to notify of drawer events
//        mDrawerLayout.setDrawerListener(toggle);
//        //Synchronise the state of the drawer with the linked DrawerLayout
//        toggle.syncState();



        //Return the FragmentManager for interacting with fragments associated with this activity
        fragmentManager = getSupportFragmentManager();


        fragment = new QuoteOfTheDayFragment();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();




        NavigationView navigationView = findViewById(R.id.navigation_view);

        //TODO: Make method out of these listener calls
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here













                        switch (menuItem.getItemId()){
                            case R.id.quote_of_the_day:
                                Toast.makeText(getApplicationContext(), "Quote of the Day", Toast.LENGTH_LONG).show();
                                menuItemNumber = 1;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                fragment = new QuoteOfTheDayFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;





                            case R.id.search_quote:
                                Toast.makeText(getApplicationContext(), "Search Quotes", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new SearchQuotesFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;





                            case R.id.random_quotes:
                                Toast.makeText(getApplicationContext(), "Random Quote", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new RandomQuotesFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;




                            case R.id.search_pictures_of_quotes:
                                Toast.makeText(getApplicationContext(), "Search Pictures", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new SearchPicturesOfQuotesFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;












                            case R.id.favorites:
                                Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new FavoritesFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;












                            case R.id.settings:
                                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new SettingsFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;


                        }



                        return false;
                    }



                });






    }




    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }






}