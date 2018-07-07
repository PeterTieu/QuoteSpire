package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.QuoteOfTheDay;


import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetQuoteOfTheDay {


    private static final String TAG = "GetQuoteOfTheDay";


    public Quote getQuoteOfTheDay() {

        Quote quoteOfTheDay = new Quote();


//        String urlString = "http://quotes.rest/qod.json";
        String urlString = "http://quotes.rest/qod.json?api_key=eQMFnO84Di1ojF0riK_HfgeF";
//        String urlString = "http://quotes.rest/quote/search.json?category=empowering&api_key=eQMFnO84Di1ojF0riK_HfgeF";

        try {
            String jsonString = getJsonString(urlString);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);


            parseJsonBodyToQuoteOfTheDay(quoteOfTheDay, jsonBody);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON string", ioe);
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
                throw new IOException(httpURLConnection.getResponseMessage() + " with" + urlString);
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


    public void parseJsonBodyToQuoteOfTheDay(Quote quote, JSONObject jsonObject) throws IOException, JSONException {
        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents");

        JSONArray quotesJsonArray = contentsJSONObject.getJSONArray("quotes");

        for (int i=0; i<quotesJsonArray.length(); i++){
            JSONObject quoteJsonObject = quotesJsonArray.getJSONObject(i);


            quote.setQuote(quoteJsonObject.getString("quote"));

            quote.setCategory(quoteJsonObject.getString("category"));

            quote.setAuthor(quoteJsonObject.getString("author"));



            Log.i(TAG, "Quote of the day - method - Quote: " + quote.getQuote());
            Log.i(TAG, "Quote of the day - method  - Category: " + quote.getCategory());
            Log.i(TAG, "Quote of the day - method  - Author: " + quote.getAuthor());
        }
    }


}
