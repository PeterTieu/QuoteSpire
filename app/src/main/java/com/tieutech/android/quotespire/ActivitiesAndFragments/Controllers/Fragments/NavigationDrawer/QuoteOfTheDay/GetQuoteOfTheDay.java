package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;


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


//Class for obtaining the Quote Of The Day - via networking
public class GetQuoteOfTheDay {


    private static final String TAG = "GetQuoteOfTheDay"; //Log for Logcat


    //Get the Quote of the Day - called by AsyncTask in QuoteOfTheDayFragment
    public Quote getQuoteOfTheDay() {

        Quote quoteOfTheDay = new Quote(); //Create Quote object

        String urlString = "http://quotes.rest/qod.json?api_key=" + APIKey.API_KEY; //URL for obtain JSON of Quote Of The Day


        //Try risky task - JSONObject(..) may throw IOException, and parseJSONObjectToQuoteOfTheDay(..) may throw JSONException
        try {
            String jsonString = getJSONString(urlString); //Obtain JSON String from URL
            Log.i(TAG, "Received JSON: " + jsonString); //Log JSON String to Logcat

            JSONObject jsonObject = new JSONObject(jsonString); //Obtain JSONObject from JSON String
            parseJSONObjectToQuoteOfTheDay(quoteOfTheDay, jsonObject); //Stash data obtained from JSONObject to the Quote object for Quote Of The Day
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", je);
        }


        //Log data to Logcat
        Log.i(TAG, "Quote of the day - Quote: " + quoteOfTheDay.getQuote());
        Log.i(TAG, "Quote of the day - Category: " + quoteOfTheDay.getCategory());
        Log.i(TAG, "Quote of the day - Author: " + quoteOfTheDay.getAuthor());

        return quoteOfTheDay;
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


            String JSONString = new String(byteArrayOutputStream.toByteArray()); //Obtain JSONString from the byteArrayOutputStream

            return JSONString; //Return the JSONString
        }
        finally {
            httpURLConnection.disconnect(); //Disconnect the HttpURLConnection
        }
    }




    //Helper method - convert the obtained JSON Object to the QOD Quote object
    public void parseJSONObjectToQuoteOfTheDay(Quote quote, JSONObject jsonObject) throws IOException, JSONException {

        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents"); //Identify the "contents" JSON object

        JSONArray quotesJsonArray = contentsJSONObject.getJSONArray("quotes"); //Identify the "quotes" JSON Array nested under the "contents" JSON Object


        //Obtain the JSON Objects nested under the "quotes" JSON Array
        for (int i=0; i<quotesJsonArray.length(); i++){

            JSONObject quoteJsonObject = quotesJsonArray.getJSONObject(i);

            quote.setQuote(quoteJsonObject.getString("quote")); //Get "quote" JSON Object and stash it to the Quote object
            quote.setCategory(quoteJsonObject.getString("category")); //Get "category" JSON Object and stash it to the Quote object
            quote.setAuthor(quoteJsonObject.getString("author")); //Get "author" JSON Object and stash it to the Quote object
            quote.setId(quoteJsonObject.getString("id")); //Get "id" JSON Object and stash it to the Quote object

            //Log to Logcat
            Log.i(TAG, "Quote of the day - method - Quote String: " + quote.getQuote());
            Log.i(TAG, "Quote of the day - method  - Category: " + quote.getCategory());
            Log.i(TAG, "Quote of the day - method  - Author: " + quote.getAuthor());
            Log.i(TAG, "Qutoe of the day - method - ID: " + quote.getId());


            //Set the categories for the Quote
            String category = quoteJsonObject.getString("category"); //Get "category" JSON Object
            List<String> categories = new ArrayList<String>(); //Create categories ArrayList
            categories.add(category); //Add category to the the categories ArrayList
            quote.setCategories(categories); //Stash the categories ArrayList to the Quote
            Log.i(TAG, "\"Qutoe of the day Author Quote - method - Categories: \" " + quote.getCategories()); //Log to Logcat
        }
    }

}
