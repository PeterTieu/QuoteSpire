package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;


import android.util.Log;

import com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Activities.MainActivity;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.FavoriteQuotesManager;
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


public class GetQuoteOfTheDay {


    private static final String TAG = "GetQuoteOfTheDay";


    public Quote getQuoteOfTheDay() {

        Quote quoteOfTheDay = new Quote();


//        String urlString = "http://quotes.rest/qod.json";
        String urlString = "http://quotes.rest/qod.json?api_key=" + APIKey.API_KEY;
//        String urlString = "http://quotes.rest/quote/search.json?category=strong&api_key=1FGGcyK9BwzYfAi8IyYZ8geF";

        try {
            String jsonString = getJsonString(urlString);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);


            parseJsonObjectToQuoteOfTheDay(quoteOfTheDay, jsonObject);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", je);
        }




        Log.i(TAG, "Quote of the day - Quote: " + quoteOfTheDay.getQuote());
        Log.i(TAG, "Quote of the day - Category: " + quoteOfTheDay.getCategory());
        Log.i(TAG, "Quote of the day - Author: " + quoteOfTheDay.getAuthor());

        return quoteOfTheDay;
    }







    public String getJsonString(String urlString) throws IOException {

        URL url = new URL(urlString);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


        try {


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


    public void parseJsonObjectToQuoteOfTheDay(Quote quote, JSONObject jsonObject) throws IOException, JSONException {
        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents");

        JSONArray quotesJsonArray = contentsJSONObject.getJSONArray("quotes");

        for (int i=0; i<quotesJsonArray.length(); i++){
            JSONObject quoteJsonObject = quotesJsonArray.getJSONObject(i);


            quote.setQuote(quoteJsonObject.getString("quote"));
            quote.setCategory(quoteJsonObject.getString("category"));





            quote.setAuthor(quoteJsonObject.getString("author"));
            quote.setId(quoteJsonObject.getString("id"));


            Log.i(TAG, "Quote of the day - method - Quote String: " + quote.getQuote());
            Log.i(TAG, "Quote of the day - method  - Category: " + quote.getCategory());
            Log.i(TAG, "Quote of the day - method  - Author: " + quote.getAuthor());
            Log.i(TAG, "Qutoe of the day - method - ID: " + quote.getId());





            String category = quoteJsonObject.getString("category");
            List<String> categories = new ArrayList<String>();
            categories.add(category);
            quote.setCategories(categories);
            Log.i(TAG, "\"Qutoe of the day Author Quote - method - Categories: \" " + quote.getCategories());

        }
    }


}
