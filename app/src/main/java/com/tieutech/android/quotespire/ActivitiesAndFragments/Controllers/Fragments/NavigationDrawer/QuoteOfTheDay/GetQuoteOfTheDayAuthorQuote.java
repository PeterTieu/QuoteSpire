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


//Class for obtaining the Quote Of The Day AUTHOR - via networking
public class GetQuoteOfTheDayAuthorQuote {

    private static final String TAG = "GetQODAuthorQuote"; //Log for Logcat

    //Get the Quote of the Day AUTHOR - called by AsyncTask in QuoteOfTheDayFragment
    public Quote getQuoteOfTheDayAuthorQuote(String quoteOfTheDayAuthor){

        Quote quoteOfTheDayAuthorQuote = new Quote(); //Create Quote object

        String urlString = "http://quotes.rest/quote/search.json?author=" + quoteOfTheDayAuthor + "&api_key=" + APIKey.API_KEY; //URL for obtain JSON of Quote Of The Day AUTHOR

        //Try risky task - JSONObject(..) may throw IOException, and parseJSONObjectToQuoteOfTheDay(..) may throw JSONException
        try{
            String jsonString = getJSONString(urlString); //Obtain JSON String from URL
            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonObject = new JSONObject(jsonString); //Obtain JSONObject from JSON String
            parseJSONObjectToQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthorQuote, jsonObject); //Stash data obtained from JSONObject to the Quote object for Quote Of The Day AUTHOR
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON Stringto JSON Object", je);
        }


        //Log data to Logcat
        Log.e(TAG, "Quote of the day Author Quote - Quote String: " + quoteOfTheDayAuthorQuote.getQuote());
        Log.e(TAG, "Quote of the day Author Quote - Category: " + quoteOfTheDayAuthorQuote.getCategory());
        Log.e(TAG, "Quote of the day Author Quote - Author: " + quoteOfTheDayAuthorQuote.getAuthor());

        return quoteOfTheDayAuthorQuote;
    }




    //Helper method - obtain the JSON String from the URL String
    public String getJSONString(String urlString) throws IOException{

        URL url = new URL(urlString); //Convert URL String to URL object

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //Establish network connection to the URL

        try{
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
                byteArrayOutputStream.write(buffer, 0, bytesRead);  //Write data FROM the buffer TO the byteArrayOutputStream
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
    public void parseJSONObjectToQuoteOfTheDayAuthorQuote(Quote quote, JSONObject JSONObject) throws IOException, JSONException {

        JSONObject contentsJSONObject = JSONObject.getJSONObject("contents"); //Identify the "contents" JSON object


        quote.setQuote(contentsJSONObject.getString("quote")); //Get "quote" JSON Object and stash it to the Quote object
        quote.setCategory(contentsJSONObject.getString("categories")); //Get "category" JSON Object and stash it to the Quote object
        quote.setAuthor(contentsJSONObject.getString("author")); //Get "author" JSON Object and stash it to the Quote object
        quote.setId(contentsJSONObject.getString("id")); //Get "id" JSON Object and stash it to the Quote object

        //Log to Logcat
        Log.i(TAG, "Quote of the day Author Quote - method - Quote String: " + quote.getQuote());
        Log.i(TAG, "Quote of the day Author Quote - method  - Category: " + quote.getCategory());
        Log.i(TAG, "Quote of the day Author Quote - method  - Author: " + quote.getAuthor());
        Log.i(TAG, "Quote of the day Author Quote - method - ID: " + quote.getId());


        JSONArray categoriesJSONArray = contentsJSONObject.getJSONArray("categories"); //Get "categories" JSON Object

        List<String> categoriesArrayList = new ArrayList<String>(); //Create categories ArrayList

        //Obtain the JSON Objects nested under the "quotes" JSON Array
        for (int i=0; i<categoriesJSONArray.length(); i++){
            String category = categoriesJSONArray.getString(i); //Get "category" JSON Object
            categoriesArrayList.add(category); //Add category to the the categories ArrayList
        }

        quote.setCategories(categoriesArrayList); //Stash the categories ArrayList to the Quote
        Log.i(TAG, "\"Qutoe of the day Author Quote - method - Categories: \" " + quote.getCategories()); //Log to Logcat
    }

}