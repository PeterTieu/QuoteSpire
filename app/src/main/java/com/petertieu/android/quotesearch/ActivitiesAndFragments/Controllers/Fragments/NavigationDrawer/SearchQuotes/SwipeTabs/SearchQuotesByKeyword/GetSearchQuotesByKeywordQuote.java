package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.SearchQuotes.SwipeTabs.SearchQuotesByKeyword;

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
import java.util.ArrayList;
import java.util.List;

public class GetSearchQuotesByKeywordQuote {

    private static final String TAG = "GetRandomQuote";


    public Quote getSearchQuotesByKeywordQuote(String searchQuery){

        Quote randomQuote = new Quote();

//        String urlString = "http://quotes.rest/quote/search.json?minlength=0&maxlength=1000&query=apple&private=false&api_key=1FGGcyK9BwzYfAi8IyYZ8geF";
//        String urlString = "http://quotes.rest/quote/search.json?author=albert+einstein&api_key=1FGGcyK9BwzYfAi8IyYZ8geF";

        String urlString = "http://quotes.rest/quote/search.json?minlength=0&maxlength=1000&query=" + searchQuery + "&private=false&api_key=1FGGcyK9BwzYfAi8IyYZ8geF";


        try {
            String jsonString = getJsonString(urlString);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);


            parseJsonObjectToRandomQuote(randomQuote, jsonObject);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", je);
        }




        Log.i(TAG, "Search Quotes by Keyword Quote - Quote: " + randomQuote.getQuote());
        Log.i(TAG, "Search Quotes by Keyword Quote - Category: " + randomQuote.getCategory());
        Log.i(TAG, "Search Quotes by Keyword Quote - Author: " + randomQuote.getAuthor());

        return randomQuote;
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


    public void parseJsonObjectToRandomQuote(Quote quote, JSONObject jsonObject) throws IOException, JSONException {
        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents");


        quote.setQuote(contentsJSONObject.getString("quote"));
        quote.setCategory(contentsJSONObject.getString("categories"));
        quote.setAuthor(contentsJSONObject.getString("author"));
        quote.setId(contentsJSONObject.getString("id"));



        Log.i(TAG, "Search Quotes by Keyword Quote - method - Quote String: " + quote.getQuote());
        Log.i(TAG, "Search Quotes by Keyword Quote - method  - Category: " + quote.getCategory());
        Log.i(TAG, "Search Quotes by Keyword Quote - method  - Author: " + quote.getAuthor());
        Log.i(TAG, "Search Quotes by Keyword Quote - method - ID: " + quote.getId());




        JSONArray categoriesJSONArray = contentsJSONObject.getJSONArray("categories");

        List<String> categoriesArrayList = new ArrayList<String>();

        for (int i=0; i<categoriesJSONArray.length(); i++){
            String category = categoriesJSONArray.getString(i);
            categoriesArrayList.add(category);
        }

        quote.setCategories(categoriesArrayList);
        Log.i(TAG, "\"Random Quote - method - Categories: \" " + quote.getCategories());



    }


}
