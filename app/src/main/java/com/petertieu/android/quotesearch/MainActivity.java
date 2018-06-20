package com.petertieu.android.quotesearch;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    int menuItemNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_one_fragment);








        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);





        NavigationView navigationView = findViewById(R.id.navigation_view);
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




                        //Return the FragmentManager for interacting with fragments associated with this activity
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        //Find a fragment that was identified by the given ID
                        Fragment fragment = fragmentManager.findFragmentById(R.id.content_frame);







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











                            case R.id.search_category:
                                Toast.makeText(getApplicationContext(), "Search Category", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new SearchCategoryFragment();
                                //Start a series of edit operations on the Fragments associated with this FragmentManager
                                fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();


                                return true;






                            case R.id.search_author:
                                Toast.makeText(getApplicationContext(), "Search Author", Toast.LENGTH_LONG).show();
                                menuItemNumber = 2;


                                if (fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }

                                //Create a fragment
                                fragment = new SearchAuthorFragment();
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



                            case R.id.random_quote:
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }






}