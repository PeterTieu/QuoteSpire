package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotes;

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


//Class for obtaining a Random Quote - via networking
public class GetRandomQuote {

    private static final String TAG = "GetRandomQuote"; //Log for Logcat


    //Get the Random Quote - called by AsyncTask in RandomQuotesFragment
    public Quote getRandomQuote(){

        Quote randomQuote = new Quote(); //Create Quote object

        String urlString = "http://quotes.rest/quote/random.json?api_key=" + APIKey.API_KEY; //URL for obtain JSON of Random Quote


        //Try risky task - JSONObject(..) may throw IOException, and parseJsonObjectToRandomQuote(..) may throw JSONException
        try {
            String JSONString = getJSONString(urlString); //Obtain JSON String from URL
            Log.i(TAG, "Received JSON: " + JSONString); //Log JSON String to Logcat

            JSONObject jsonObject = new JSONObject(JSONString); //Obtain JSONObject from JSON String
            parseJSONObjectToRandomQuote(randomQuote, jsonObject); //Stash data obtained from JSONObject to the Quote object
        }
        catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        }
        catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", je);
        }


        //Log data to Logcat
        Log.i(TAG, "Random quote - Quote: " + randomQuote.getQuote());
        Log.i(TAG, "Random quote - Category: " + randomQuote.getCategory());
        Log.i(TAG, "Random quote - Author: " + randomQuote.getAuthor());

        return randomQuote; //Return random Quote object
    }




    //Helper method - obtain the JSON String from the URL String
    public String getJSONString(String urlString) throws IOException {

        URL url = new URL(urlString); //Convert URL String to URL object

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //Establish network connection to the URL

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //Create stream to get from

            InputStream inputStream = httpURLConnection.getInputStream(); //Create InputStream to receive data from the HttpURLConnection

            //Check if network connection is available
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() + " with " + urlString);
            }

            int bytesRead = 0; //Flag for whileloop

            byte[] buffer = new byte[1024]; //Buffer to store the bytes transferred from the InputStream to the ByteArrayOutputStream

            //Transfer data from the InputStream to the ByteArrayOutputStream via the buffer
            //i.e. Read data from inputStream to buffer, then return...
            // # greater than zero: Number of bytes (data) read from inputStream to buffer
            // -1: No (more) bytes (data) read from inputStream to buffer
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, bytesRead); //Write data FROM the buffer TO the byteArrayOutputStream
            }

            byteArrayOutputStream.close(); //Close the byteArrayOutputStream

            String JsonString = new String(byteArrayOutputStream.toByteArray()); //Obtain JSONString from the byteArrayOutputStream

            return JsonString; //Return the JSONString
        }
        finally {
            httpURLConnection.disconnect(); //Disconnect the HttpURLConnection
        }

    }



    //Helper method - convert the obtained JSON Object to the Random Quote object
    public void parseJSONObjectToRandomQuote(Quote quote, JSONObject jsonObject) throws IOException, JSONException {

        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents"); //Identify the "contents" JSON object

        quote.setQuote(contentsJSONObject.getString("quote")); //Get "quote" JSON Object and stash it to the random Quote object
        quote.setCategory(contentsJSONObject.getString("categories")); //Get "category" JSON Object and stash it to the random Quote object
        quote.setAuthor(contentsJSONObject.getString("author")); //Get "author" JSON Object and stash it to the random Quote object
        quote.setId(contentsJSONObject.getString("id")); //Get "id" JSON Object and stash it to the random Quote object

        //Log data to Logcat
        Log.i(TAG, "Random Quote - method - Quote String: " + quote.getQuote());
        Log.i(TAG, "Random Quote - method  - Category: " + quote.getCategory());
        Log.i(TAG, "Random Quote - method  - Author: " + quote.getAuthor());
        Log.i(TAG, "Random Quote - method - ID: " + quote.getId());


        //Set the categories for the Quote
        JSONArray categoriesJSONArray = contentsJSONObject.getJSONArray("categories"); //Get "categories" JSON Object

        List<String> categoriesArrayList = new ArrayList<String>(); //Declare categories String ArrayList reference variable

        //Obtain the JSON Objects nested under the "quotes" JSON Array
        for (int i=0; i<categoriesJSONArray.length(); i++){
            String category = categoriesJSONArray.getString(i); //Get "category" JSON Object
            categoriesArrayList.add(category); //Add category to the the categories ArrayList
        }

        quote.setCategories(categoriesArrayList); //Stash the categories ArrayList to the Quote
        Log.i(TAG, "\"Random Quote - method - Categories: \" " + quote.getCategories()); //Log to Logcat
    }

}