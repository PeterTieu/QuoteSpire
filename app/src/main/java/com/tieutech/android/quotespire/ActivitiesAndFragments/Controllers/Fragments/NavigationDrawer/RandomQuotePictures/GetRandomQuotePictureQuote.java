package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.util.Log;
import com.tieutech.android.quotespire.ActivitiesAndFragments.Models.QuotePicture;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//Fragment that fetches the following fields for a Random QuotePicture (by networking with the TheySaidSo API):
    //1: ID
    //2: Picture Download URI
//This class is called by the AsyncTask class of RandomQuotePicturesFragment's doInBackground(..) method.
//Therefore, it runs asynchronously to the main thread
public class GetRandomQuotePictureQuote {

    private static final String TAG = "GRQPictureQuote"; //Log to Logcat



    //Fetch the fields mentioned above
    //NOTE: Multiple AsyncTasks would be run simultaneously in RandomQuotePicturesFragment. Thus, this method would be run multiple times simultaneously.
    public QuotePicture getRandomQuotePictureQuote(){

        QuotePicture randomQuotePicture = new QuotePicture(); //Instantiate a QuotePicture

        String urlString = "http://quotes.rest/quote/image/search.json?api_key=1FGGcyK9BwzYfAi8IyYZ8geF"; //Define URI endpoint for networking
                                                                                                            //Fetches JSON text of a Random Quote Picture containing:
                                                                                                            //1: ID
                                                                                                            //2: Picture download URI

        //Try risky task - getJSONString(..) and parseJSONObjectToRandomQuotePictureQuote(..) may throw IOException and JSONException, respectively
        try {
            String JSONString = getJSONString(urlString); //Obtain the JSON String from the URI endpoint

            Log.i(TAG, "Received JSON: " + JSONString); //Log the JSON String

            JSONObject JSONObject = new JSONObject(JSONString); //Instantiate a JSONObject, taking the JSON String of the URI endpoint

            parseJSONObjectToRandomQuotePictureQuote(randomQuotePicture, JSONObject); //Parse the JSON Object to the QuotePicture
        }
        catch (IOException IOException) {
            Log.e(TAG, "Failed to obtain JSON String", IOException);
        }
        catch (JSONException JSONException) {
            Log.e(TAG, "Failed to parse JSON String to JSON Objects", JSONException);
        }

        return randomQuotePicture; //Return the QuotePicture
    }




    //Helper method - obtains the JSON String from the URI endpoint
    @SuppressWarnings({"UnnecessaryLocalVariable", "UnusedAssignment"})
    private String getJSONString(String urlString) throws IOException {

        URL url = new URL(urlString); //Instantiate a URL from the URI endpoint

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //Instantiate a HttpURLConnection to establish with the URI endpoint

        try {
            InputStream inputStream = httpURLConnection.getInputStream(); //Create InputStream and link it to the HttpURLConnection
            int bytesRead = 0; //Counter for bytes read into the buffer byte
            byte[] bufferBytesArray = new byte[1024]; //Buffer bytes array to read bytes from the InputStream before writing to the ByteArrayOutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //Instantiate a ByteArrayOutputStream for reading the JSON data obtained from the URI endpoint


            //Check if response code from the HttpURLConnection is successful (before proceeding with transferring bytes from the InputStream to the ByteArrayOutputStream
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() + " with " + urlString);
            }


            //While the bytes read from the InputStream to the buffer Bytes Array is greater than zero.
            // NOTE: -1 is returned when no more bytes are read, in which case, the while loop is broken
            while ((bytesRead = inputStream.read(bufferBytesArray)) > 0) { //Read from the InputStream and write into the bufferBytesArray
                byteArrayOutputStream.write(bufferBytesArray, 0, bytesRead); //Read fro the bufferBytesArray and write to the byteArrayOutputStream
            }

            byteArrayOutputStream.close(); //Close the ByteArrayOutputStream, since all the bytes from the InputStream have been transferred over

            String JSONString = new String(byteArrayOutputStream.toByteArray()); //Stash the bytes from the ByteArrayOutputStream to the String object referred to by the JSONString variable

            return JSONString; //Return the JSON String (JSON of type String)
        }
        finally {
            httpURLConnection.disconnect(); //Disconnect the HttpURLConnection object
        }

    }




    //Helper method - Parses the JSON Object to the QuotePicture
    @SuppressWarnings({"RedundantThrows", "SpellCheckingInspection"})
    private void parseJSONObjectToRandomQuotePictureQuote(QuotePicture randomQuotePicture, JSONObject jsonObject) throws IOException, JSONException {

        JSONObject contentsJSONObject = jsonObject.getJSONObject("contents"); //Obtain the "contents" JSONObject from the (greater) JSONObject

        JSONObject qImageObject = contentsJSONObject.getJSONObject("qimage"); //Obtain the "qimage" JSONObject from the "contents" JSONObject

        randomQuotePicture.setId(qImageObject.getString("quote_id")); //Obtain the quote ID and stash it to the Random QuotePicture
        randomQuotePicture.setQuotePictureDownloadURI(qImageObject.getString("download_uri")); //Obtain the quote Picture Download URI and stash it ot the Random QuotePicture


        Log.i(TAG, "Random Quote Picture Quote - method - Quote ID: " + randomQuotePicture.getId()); //Log ID
        Log.i(TAG, "Random Quote Picture Quote - method  - Picture Download URI: " + randomQuotePicture.getQuotePictureDownloadURI()); //Log Picture Download URI
    }

}
