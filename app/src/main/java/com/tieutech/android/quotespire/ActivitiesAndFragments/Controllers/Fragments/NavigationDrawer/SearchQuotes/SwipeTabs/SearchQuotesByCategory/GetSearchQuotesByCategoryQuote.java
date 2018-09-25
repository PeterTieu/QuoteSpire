package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByCategory;

import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.Quote;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Others.APIKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//Class called by the GetSearchQuotesByCategoryAsyncTask AsyncTask inner class of the SearchQuotesByCategoryFragment class.
//Objective: Searches the Quote based on the category or phrase search query passed to the SearchView of SearchQuotesByCategoryFragment.
public class GetSearchQuotesByCategoryQuote {

    private static final String TAG = "GetRandomQuote"; //Tag for Logcat




    //Get the Quote (based on the search query passed to the SearchView of SearchQuotesByCategoryFragment)
    public Quote getSearchQuotesByCategoryQuote(String searchQuery){

        Quote quote = new Quote(); //Create Quote object

        String URLString = "http://quotes.rest/quote/search.json?minlength=0&maxlength=1000&category=" + searchQuery + "&private=false&api_key=" + APIKey.API_KEY; //Get URL string containing the search query


        //Try risky task:
        // getJSONString(..) may throw IOException if no JSON String could be found. parseJSONObjectToQuote(..) could throw JSONException if JSON String could not be parsed to JSON object
        try {
            String JSONString = getJSONString(URLString); //Get JSON String from the URL String

            Log.i(TAG, "Received JSON: " + JSONString); //Log to Logcat

            JSONObject JSONObject = new JSONObject(JSONString); //Create JSONObject object from JSON String

            parseJSONObjectToQuote(quote, JSONObject); //Stash the JSON Object values to the member variables of the Quote

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", je);
        }

        //Log values to Logcat
        Log.i(TAG, "Search Quotes by Category Quote - Quote: " + quote.getQuote());
        Log.i(TAG, "Search Quotes by Category Quote - Author: " + quote.getAuthor());

        //Return Quote (to the doInBackground(..) method of the AsyncTask)
        return quote;
    }





    //Get JSON String from the URL String
    @SuppressWarnings({"UnnecessaryLocalVariable", "UnusedAssignment"})
    private String getJSONString(String URLString) throws IOException {

        URL url = new URL(URLString); //Create URL object from URLString

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //Establish connection to the URL


        //Try risky task - httpURLConnection.getResponseCode() throws IOException if it doesn't equal HttpURLConnection.HTTP_OK
        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //Create ByeArrayOutputStream object for storing bytes that are to be shown to the screen

            InputStream inputStream = httpURLConnection.getInputStream(); //Get stream of bytes from the URL connection

            //If no connection to the URL exists
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() + " with " + URLString);
            }


            int bytesRead = 0; //Declare variable to store bytes read

            byte[] buffer = new byte[1024]; //Create bytes array for storing bytes data read from the InputStream


            //What happens in EACH loop in the while-loop:
            //inputStream.read(..) READS bytes FROM 'inputStream' and WRITES them INTO 'buffer'.
            //..Then.. it returns the number of bytes (int) read. That is, the number of bytes that was read INTO 'buffer'.
            //This number (int) is stored in 'bytesRead'.
            //NOTE: A maximum of 1024 bytes are read FROM 'inputStream' TO 'buffer' at a time (as this is the size of 'buffer').
            //NOTE: Each byte represents an ASCII character in the HTTP web address (NOTE: In 1 byte, there are 2^8 = 256 different combinations).
            //Example while-loop cycles:
            //Cycle 1: bytesRead = 1024
            //Cycle 2: bytesRead = 1024
            //Cycle 3: bytesREad = 1024
            //.....
            //Cycle 15: bytesREad = 1024
            //Cycle 16: bytesRead = 52 (i.e. the last/remaining 52 bytes left to read)
            //Cycle 17 bytesRead = -1 (i.e. no more bytes were left to read - in which case, the while-loop stops!)
            //NOTE: It doesn't matter what the size of 'buffer' is.
            // The TOTAL number of bytes to read is UNCHANGED!!
            // A larger 'buffer' size just means that less while-loops will be run (below) - that's all!
            //This process will continue until all bytes in the inputStream array are read,
            //until inputStream.read(buffer) returns -1 (stopping the while-loop).
            while ((bytesRead = inputStream.read(buffer)) > 0) {

                byteArrayOutputStream.write(buffer, 0, bytesRead); //byteArrayOutputStream.write(buffer, 0, bytesRead) READS bytes FROM 'buffer' and WRITES it to byteArrayOutputStream.

            }


            //NOTE: At this point, byteArrayOutputStream contains the bytes that correspond to the JSON text

            byteArrayOutputStream.close(); //Close the byteArrayOutputStream when done


            String JSONString = new String(byteArrayOutputStream.toByteArray()); //Create JSON String object from bytes array representing the JSON text

            return JSONString; //Return the JSONString

        }
        finally {
            httpURLConnection.disconnect(); //Disconnect the connection to the URL
        }


    }





    //Stash the JSON Object values to the member variables of the Quote
    @SuppressWarnings({"RedundantThrows", "SpellCheckingInspection"})
    private void parseJSONObjectToQuote(Quote quote, JSONObject jsonObject) throws IOException, JSONException {


        //Example JSON String...

        /*{
    "success": {
        "total": 1
    },
    "contents": {
        "quote": "Your success will not be determined by your gender or your ethnicity, but only on the scope of your dreams and your hard work to achieve them.",
        "author": "Zaha Hadid",
        "id": "bjY_8j8kCupLt4VShbn9_QeF",
        "permalink": "https://theysaidso.com/quote/zaha-hadid-your-success-will-not-be-determined-by-your-gender-or-your-ethnicity",
        "requested_category": "inspire",
        "categories": [
            "dream",
            "hard-work",
            "inspire"
        ]
    }*/


        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents"); //Get JSON Object titled "contents"


        quote.setQuote(contentsJSONObject.getString("quote")); //Stash JSON String titled "quote" to the mQuote member variable of the Quote object
        quote.setAuthor(contentsJSONObject.getString("author")); //Stash JSON String titled "author" to the mAuthor member variable of the Quote object
        quote.setId(contentsJSONObject.getString("id")); //Stash JSON String titled "id" to the mId member variable of the Quote object


        JSONArray categoriesJSONArray = contentsJSONObject.getJSONArray("categories"); //Get the JSON Array titled "categories"
        List<String> categoriesArrayList = new ArrayList<>(); //Create ArrayList of String objects to store the categories of the Quote

        //Run through each of the String under the "category" title of the JSON Object and save it to the categoriesArrayList
        for (int i=0; i<categoriesJSONArray.length(); i++){
            String category = categoriesJSONArray.getString(i);
            categoriesArrayList.add(category);
        }

        quote.setCategories(categoriesArrayList); //Set the categoriesArrayList to the mCategories member variable of the Quote


        //Log to Logcat
        Log.i(TAG, "Search Quotes by Category Quote - method - Quote String: " + quote.getQuote());
        Log.i(TAG, "Search Quotes by Category Quote - method  - Author: " + quote.getAuthor());
        Log.i(TAG, "Search Quotes by Category Quote - method - ID: " + quote.getId());
        Log.i(TAG, "\"Random Quote - method - Categories: \" " + quote.getCategories());
    }
}
