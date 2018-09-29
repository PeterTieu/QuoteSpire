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
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//Class for obtaining the Quote Of The Day AUTHOR - via networking
public class GetQuoteOfTheDayAuthorQuote {

    private static final String TAG = "GetQODAuthorQuote";


    public Quote getQuoteOfTheDayAuthorQuote(String quoteOfTheDayAuthor){


        Quote quoteOfTheDayAuthorQuote = new Quote();


        String urlString = "http://quotes.rest/quote/search.json?author=" + quoteOfTheDayAuthor + "&api_key=" + APIKey.API_KEY;
//        String urlString = "http://quotes.rest/quote/search.json?author=" + "mohamad+ali" + "&api_key=eQMFnO84Di1ojF0riK_HfgeF";
//        String urlString = "http://quotes.rest/quote/search.json?author=" + "brad+pitt" + "&api_key=eQMFnO84Di1ojF0riK_HfgeF";

        try{
            String jsonString = getJsonString(urlString);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            parseJsonObjectToQuoteOfTheDayAuthorQuote(quoteOfTheDayAuthorQuote, jsonObject);

        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        }
        catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON Stringto JSON Object", je);
        }



        Log.e(TAG, "Quote of the day Author Quote - Quote String: " + quoteOfTheDayAuthorQuote.getQuote());
        Log.e(TAG, "Quote of the day Author Quote - Category: " + quoteOfTheDayAuthorQuote.getCategory());
        Log.e(TAG, "Quote of the day Author Quote - Author: " + quoteOfTheDayAuthorQuote.getAuthor());


        return quoteOfTheDayAuthorQuote;
    }




    public String getJsonString(String urlString) throws IOException{
        URL url = new URL(urlString);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            InputStream inputStream = httpURLConnection.getInputStream();

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() + " with " + urlString);
            }

            int bytesRead = 0;

            byte[] buffer = new byte[1024];

            while ((bytesRead = inputStream.read(buffer)) > 0) {


                byteArrayOutputStream.write(buffer, 0, bytesRead);

            }

            byteArrayOutputStream.close();


            String JsonString = new String(byteArrayOutputStream.toByteArray());

            return JsonString;

        }
        finally {
            httpURLConnection.disconnect();
        }

    }



    public void parseJsonObjectToQuoteOfTheDayAuthorQuote(Quote quote, JSONObject jsonObject) throws IOException, JSONException {
        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents");


        quote.setQuote(contentsJSONObject.getString("quote"));
        quote.setCategory(contentsJSONObject.getString("categories"));
        quote.setAuthor(contentsJSONObject.getString("author"));
        quote.setId(contentsJSONObject.getString("id"));




        Log.i(TAG, "Quote of the day Author Quote - method - Quote String: " + quote.getQuote());
        Log.i(TAG, "Quote of the day Author Quote - method  - Category: " + quote.getCategory());
        Log.i(TAG, "Quote of the day Author Quote - method  - Author: " + quote.getAuthor());
        Log.i(TAG, "Quote of the day Author Quote - method - ID: " + quote.getId());









        JSONArray categoriesJSONArray = contentsJSONObject.getJSONArray("categories");

        List<String> categoriesArrayList = new ArrayList<String>();

        for (int i=0; i<categoriesJSONArray.length(); i++){
            String category = categoriesJSONArray.getString(i);
            categoriesArrayList.add(category);
        }

        quote.setCategories(categoriesArrayList);
        Log.i(TAG, "\"Qutoe of the day Author Quote - method - Categories: \" " + quote.getCategories());




    }







}
