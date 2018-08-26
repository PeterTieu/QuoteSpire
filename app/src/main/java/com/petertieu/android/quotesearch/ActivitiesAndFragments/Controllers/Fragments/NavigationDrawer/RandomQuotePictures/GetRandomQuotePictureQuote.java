package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;
import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.QuotePicture;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetRandomQuotePictureQuote {

    private static final String TAG = "GRQPictureQuote";


    public QuotePicture getRandomQuotePictureQuote(){

        QuotePicture randomQuotePictureQuote = new QuotePicture();

        String urlString = "http://quotes.rest/quote/image/search.json?api_key=1FGGcyK9BwzYfAi8IyYZ8geF";

        try {
            String JSONString = getJSONString(urlString);

            Log.i(TAG, "Received JSON: " + JSONString);

            JSONObject JSONObject = new JSONObject(JSONString);


            parseJSONObjectToRandomQuotePictureQuote(randomQuotePictureQuote, JSONObject);


        } catch (IOException ioe) {
            Log.e(TAG, "Failed to obtain JSON String", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", je);
        }




//        Log.i(TAG, "Random Quote Picture Quote - Quote: " + randomQuotePictureQuote.getQuote());
//        Log.i(TAG, "Random Quote Picture Quote - Category: " + randomQuotePictureQuote.getCategory());
//        Log.i(TAG, "Random Quote Picture Quote - Author: " + randomQuotePictureQuote.getAuthor());

        return randomQuotePictureQuote;
    }







    public String getJSONString(String urlString) throws IOException {

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


            String JSONString = new String(byteArrayOutputStream.toByteArray());

            return JSONString;

        }
        finally {
            httpURLConnection.disconnect();
        }


    }


    public void parseJSONObjectToRandomQuotePictureQuote(QuotePicture quotePicture, JSONObject jsonObject) throws IOException, JSONException {
        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents");

        JSONObject qImageObject = contentsJSONObject.getJSONObject("qimage");

        quotePicture.setId(qImageObject.getString("quote_id"));
        quotePicture.setQuotePictureDownloadURI(qImageObject.getString("download_uri"));





        Log.i(TAG, "Random Quote Picture Quote - method - Quote ID: " + quotePicture.getId());
        Log.i(TAG, "Random Quote Picture Quote - method  - Picture Download URI: " + quotePicture.getQuotePictureDownloadURI());


    }


























}
