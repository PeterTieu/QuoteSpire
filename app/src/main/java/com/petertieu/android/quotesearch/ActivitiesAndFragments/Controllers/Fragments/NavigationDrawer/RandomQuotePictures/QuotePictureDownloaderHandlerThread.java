package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.RandomQuotePictures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.petertieu.android.quotesearch.ActivitiesAndFragments.Models.Quote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class QuotePictureDownloaderHandlerThread<T> extends HandlerThread {

    private final static String TAG = "QPDHandlerThread";

    private Handler mRequestHandler;

    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();

    private QuotePictureDownloadListener<T> mQuoteQuotePictureDownloadListener;

    private boolean mHasHandlerThreadQuit;

    private Handler mResponseHander;


    //The "what" instance variable of the Message (of the ThumbnailDownloader bakcground thread).
    //This is the Message instance variable (field) that is used to identify the message
    private static final int MESSAGE_DOWNLOAD = 0;



    public QuotePictureDownloaderHandlerThread(Handler responseHandler){
        super(TAG);
        mResponseHander = responseHandler;
    }


    public void setQuoteQuotePictureDownloadListener(QuotePictureDownloadListener quotePictureDownloadListener){

        mQuoteQuotePictureDownloadListener = quotePictureDownloadListener;
    }


    public interface QuotePictureDownloadListener<T>{

        void onQuotePictureDownloaded(T quotePictureViewHolder, Bitmap quotePicture);
    }






    //Override the quit() method inherited from the HandlerThread class.
    //quit() is called in the onDestroy() fragment lifecycle callback method in RandomQuotePicturesFragment.
    //The quit() method terminates the Looper object (i.e. causes it to stop processing any messages in the message queue)
    // We override quit() so that we could make mHasQuit = true;
    @Override
    public boolean quit() {
        mHasHandlerThreadQuit = true;
        return super.quit();
    }


    //clearQueue() clears the (request) Handler and ConcurrentHashMap.
    //It is called when the RandomQuotesPicturesFragment's (main thread's) view is destroyed (in onDestoryView())
    public void clearQueue(){

        //removeMessages(int) is from Handler class. It doesn't return anything.
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }








    //NOTE: Messages have THREE main fields (instance variables);
    //1) what: The identifier of the message (i.e. MESSAGE_DOWNLOAD)
    //2) obj: The instance object sent with the Message (i.e. Quote)
    //3) target: The Handler to process the Message



    //queueThumbnail(T, String) is callbed by Quote().
    // This means it is run EVERYTIME a list item is created,
    // and everytime a list item is created, a Message is created by the Handler (mRequestHandler) (via the help of the Looper),
    // and is enqueued in the MessageQueue by the Looper...
    // waiting for to be processed by the Handler
    //IOW, queueThumbnail(T, String) method logs the URL of the image that is downloaded
    // AND uses a Handler object to create a Message (for the ThumbnailDownloader background thread)
    //queueThumbnail(T, String) is called by Quote
    // Argument 1: 'T' is the class identifier for the image to download. In this case, it is Quote.
    // It is also the "obj" instance variable of the Message, which is sent with the Message
    // Argument 2: The URL of the image to download (i.e. galleryItem.getUrl(), where galleryItem is an instance of GetRandomQuotePictureQuote)
    public void enqueueQuotePictureDownloadURIIToMessageQueue(T quotePictureViewHolder, String quotePictureDownloadURI){



        //If the "url_s" parameter of the JSON text (i.e. JSON object called "url_s") doesn't exist
        if (quotePictureDownloadURI == null){
            //Remove the target (Quote) from the ConcurrentHashMap collection (mRequestMap),
            //NOTE: This also removes the 'url' counterpart of the element 'obj' is in,
            // as 'obj' and 'url' are in the same element in the ConcurrentHashMap
            mRequestMap.remove(quotePictureViewHolder);
        }

        //If the "url_s" parameter of the JSON text (i.e. JSON object called "url_s" exists
        else {
            //Add to the ConcurrentHashMap:
            // 1: The obj (Quote), and
            // 2: The url (i.e. GalleryItem.getUrl())
            mRequestMap.put(quotePictureViewHolder, quotePictureDownloadURI);


            //Have the Handler create a Message object,
            // and 'link' the Message to the Handler (via the sendToTarget() method of the Message class)
            //NOTE: At this point, mRequestHandler would have been created (from the onLooperPrepared(..)) method of the Handler class
            // as no Handler object exists yet
            // This is yet to happen in the onLooperPrepared() method (defined below)
            //obtainMessage(int, Object) is a method of the Handler object (mRequestHandler).
            // It returns a new Message...
            // and sets the "what" and "obj" instance variables of the Handler
            //Argument 1 (int): "what" - The int instance variable of the Message that is used to IDENTIFY the Message (i.e. MESSAGE_DOWNLOAD = 0)
            //Argument 2 (Object): "obj" - The instance variable of the Message that is an Object class to also identify the Message (i.e. Quote)
            //sendToTarget() is a method of the Message object (which is created by the Handler). It uses the Looper to send this Message to the Handler (i.e. the "target").
            // REMEMBER: The Looper loops the MessageQueue and GRABS Messages and ESCROTS/SENDS them to the Handler,
            //  as the Handler does not deal with the logistics of Messages in the background thread.
            //  Handlers only process Messages when they are brought to them by the Looper,
            //  and create Messages, which are moved to the MessageQueue by the Looper!
            // This means that mRequestHandler is in charge of processing the Message
            // (when the Message is pulled off the MESSAGE QUEUE by the Looper)
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, quotePictureViewHolder).sendToTarget();
        }


    }










    //Override the onLooperPrepared() method inherited from the HandlerThread class.
    //onLooperPrepared() is a call back method that can be explicity overriden to SET THINGS UP
    // BEFORE the Looper object loops (NOTE: The Looper is created by the HandlerThread).
    //IOW, onLooperPrepared() is called before the Looper checks the queue for the FIRST TIME.
    // This makes it a good place to create the Handler
    //NOTE: onLooperPrepared() only gets called !!ONCE!!,
    // and that is, it is called before the Looper executes. (This is why we create the (one and only) Handler object here) (pg. 507).
    // HOWEVER, the handleMessage(Message) method (overidden) is called !!EVERY TIME!!
    // a Message is pulled off the queue by the Looper and passed to the Handler!!
    @Override
    protected void onLooperPrepared(){

        //Instantiate a Handler object and assign it to the mRequestHanlder (Handler) reference variable
        //The Handler's job is:
        // 1: Help create the Message via the Looper (see diagrams on pg. 507-509)
        // 2: Processes Message(s) from the MESSAGE QUEUE
        //NOTE: Only ONE Handler is created for this background thread.
        // This means all Messages are processed by this single Handler! (pg. 507 diagram)
        mRequestHandler = new Handler(){

            //Override the handleMessage(Message) method inherited from the Handler class.
            //handleMessage(Message) defines what the Handler does
            // !!EVERYTIME!! Messages are pulled off the queue by the Looper and passed to the Handler.
            //NOTE: handleMessage(Message) is called EVERYTIME a download Message is pulled off the MESSAGE QUEUE
            // and ready to be processed
            //NOTE: Argument 1 (Message) is actually the Message that was created from: mRequestHandler.obtainMessage(..) (above)
            //IOW, we are HANDLING the message that was created (above) by obtainMessage(..).
            @Override
            public void handleMessage(Message message){

                //If the "what" instance variable of the Message class (that is used to IDENTIFY the Message)
                // is equal to MESSAGE_DOWNLOAD (which is 0)..
                if (message.what == MESSAGE_DOWNLOAD){

                    //Instantiate an object of type T (in this situation, Quote) referenced by obj,
                    // and let it equal the "obj" instance variable of the Message (msg).
                    // REMEMBER: The "obj" instance variable of the Message (msg) is the identifier for the request.
                    // In this case, "obj" is of type Quote (since T is of type Quote)
                    T quotePictureViewHolder = (T) message.obj;

                    //Log that a Message is pulled from the MESSAGE QUEUE, which requests for a URL
                    //NOTE: mRequestMap.get(obj) returns the VALUE element of the 'obj' KEY, which is: 'url' (aka the "url_s" parameter of the JSON string),
                    // as both 'obj' and 'url' are in the same element in the ConcurrentHashMap
                    Log.i(TAG, "Got a request for url_s: " + mRequestMap.get(quotePictureViewHolder));

                    //Call the handleRequest(T) helper method,
                    // which is configured to DOWNLOAD the bitmap
                    handleRequest(quotePictureViewHolder);
                }

            }

        };
    }











    //The handleRequest(T) method is a helper method of handleMessage(Message) (inside the overriden onLooperPrepared()).
    // It uses the url to download the bitmap into bytes,
    // then parses it to a bitmap image
    private void handleRequest(final T quotePicture){

        //Try a 'risky' task,
        // as getUrlBytes(url) could throw a IOException exception
        try{
            //Instantiate a String object referenced by url,
            // and assign it to the String 'url' that is associated with the 'obj' variable
            // in the mRequestMap (ConcurrentHashMap)
            //NOTE: 'url' is actually the String version of the "url_s" parameter of the JSON String. It represents the url to the thumbnail!
            //NOTE: In this case, 'obj' is Quote
            //NOTE: get(obj) returns the VALUE, 'url' (aka the "url_s" parameter of the JSON String).
            final String quotePictureDownloadURI = mRequestMap.get(quotePicture);

            //If the url doesn't exist, then leave this method
            if (quotePictureDownloadURI == null){
                return;
            }

            //At this point, a url_s (i.e. the mUrl instance variable of GalleryItem) exists...
            //Instantiate a byte array referenced by bitmapBytes,
            // and assign it to the getUrlBytes(String) method,
            // which parses the String version of a url to bytes, and returns an array of bytes
            //NOTE: getUrlBytes(String) could throw a IOException exception
            // if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            byte[] bitmapBytes = getUrlBytes(quotePictureDownloadURI);

            //Decode the the byte array (bitmapBytes) into a Bitmap.
            //decodeByteArray(byte[], int, int) is a static method from BitmapFactory.
            //Argument 1: The bitmap byte array
            //Argument 2: The index in the byte array where the decoder should begin parsing
            //Argument 3: The number of bytes in the array (starting from the location specified in Argument 2)
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);


            //Log that the Bitmap has been created
            Log.i(TAG, "Bitmap created");





            //At this point..
            // The REQUEST HANDLER, mRequestHandler, would have processed the Message sent from the main thread
            // (i.e. decoded the "url_s" parameter from the JSON text of the main thread into a bitmap),
            // all we have left to do is send the bitmp to the main thread (so that it could be displayed in the UI)...
            // This is done by running then RESPONSE HANDLER, mResponseHandler, and getting it to bind to the bitmap to the Quote.
            // (NOTE: The RESPONSE HANDLER is the Handler belonging to the main thread (vs. the REQUEST HANDLER, which belongs to the background thread.
            // This is because it is creatd by the Looper in the main thread, and therefore maintains its loyalty to the main thread)
            // The REPONSE HANDLER, mResponseHandler, is run via the Runnable code below.
            // NOTE that the code in the run() method is run in the MAIN THREAD instead of in this background thread,
            // because this Handler belongs to the main thread... where the 'response' processing occurs (i.e. displaying the thumbnail to the UI).
            // This means that no matter what the call stack on the main thread might be at any one point in time,
            // once run() is called, it goes directly to the top of the call stack of the main thread,
            // in order to be processed right away!!
            //NOTE: We would have now gotten 4 THREADS to run:
            //1: Main thread (ONGOING while the app is running)
            //2: GetRandomQuotePictureAsyncTask (AsyncTask) - background thread (would have COMPLETED running by the time we have created mItems in RandomQuotePicturesFragment)
            //3: Message looper for RequestHandler - background thread (ONGOING while there are still images to download)
            //4: Message looper for ResponseHandler - background thread (ONNGOING while there are still images to decode and display to the UI)

            //The post(Runnable) method of the Handler class adds a Runnable to the MessageQueue.
            // The Runnable will be run on the thread (i.e. Main UI) in which the Handler (i.e. ResponseHandler) is attached to.
            // NOTE: Because the mResponseHandler Handler was created in the MAIN THREAD (RandomQuotePicturesFragment),
            // it will be loyally linked to the Looper of the main thread,
            // and so, all the code under the run() method will be executed in the MAIN THREAD!!
            mResponseHander.post(new Runnable () {

                //Override the run() method of the Runnable interface,
                // so that we could define the JOB for the (main) thread
                @Override
                public void run(){

                    //Double check the mRequestMap.
                    //If the VALUE component of the 'obj' KEY (i.e. the 'url' variable) in the ConcurrentHashMap object (mRequestMap) isn't actually the 'url' variable,
                    // OR... mHasQuit = true (a 'flag' - to make sure that this background thread hasn't reached the quit() cycle of its life yet)
                    // then leave the method (return null)
                    if (mRequestMap.get(quotePicture) != quotePictureDownloadURI || mHasHandlerThreadQuit){
                        return;
                    }



                    //Remove the Quote-URI mapping from the mRequestMap
                    mRequestMap.remove(quotePicture);

                    //RECALL: Callback interface..
                    //Call the onThumbnailDownloadListener interface object (mThumbnailDownloadListener)
                    // that is declared in this class, but defined in the RandomQuotePicturesFragment's (main thread's) class
                    // in order to BIND the bitmap image to the Quote object!
                    //Set the bitmap on the Quote object
                    // i.e. the 'obj' instance variable of the Message
                    mQuoteQuotePictureDownloadListener.onQuotePictureDownloaded(quotePicture, bitmap);
                }
            });



        }


        //Catchg the IOException exception that is thrown by getUrlBytes(String)
        // if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
        catch (IOException ioe){

            //Log that the image could not be downloaded
            Log.e(TAG, "Error downloading image", ioe);
        }
    }

















    //The getUrlBytes(String) method is a helper method of getUrlString.
    // It fetches raw data from a URL (as argument) and returns it as an array of bytes.
    //NOTE: All (raw) data that is extracted from a Web URL is in the type of BYTES in an InputStream!
    // They must be converted to meaningful data (e.g JSON String) by writing them into a ByteArrayOutputStream, then parsed to String!
    public byte[] getUrlBytes(String urlSpec) throws IOException {

        //Create URL object from the urlSpec String parameter (e.g. https://www.bignerdranch.com)
        // IOW, parse the REST URL of type String to type URL
        //NOTE: URL stands for (Uniform Resource Locator), a pointer to a "resource" on the World Wide Web.
        // A URL is a REFERENCE..
        // A URL could be:
        //  reference to file directory, reference to database, reference to search engine, etc.
        URL url = new URL(urlSpec);

        //openConnection() is a method of the URL class.
        //It creates a connection object pointed at the URL (i.e. an ADAPTER from the URL object to the object/endpoint it refers to)
        // url.openConnection() actually returns a URLConnection object,
        // but since we are connecting to a http URL, we can narrow cast the "connection" to HttpURLConnection.
        //NOTE: We could narrow cast URLConnection to HttpURLConnection,
        // as HttpURLConnection subclasses URLConnection (i.e. HttpURLConnection IS-A URLConnection).
        //This gives HTTP-specific interfaces for working with request methods, response codes, streaming methods, and more.
        //NOTE: HttpURLConnection is a connection adapter.
        // However, it will not connect to the object/endpoint until getInputStream()/getOutputStream() is called.
        // Until then, we cannot get a valid response code
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        //Try a 'risky' task (where IOException is thrown
        // if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) (below)
        try {

            //ByteArrayOutputStream STORES the bytes that are TO BE shown to the screen (e.g. to the Android Monitor or the user)
            //ByteArrayOutputStream implements an output stream in which the data is written into a byte array.
            //This object acts as a buffer to store memory.
            // The buffer automatically grows as data is written to it.
            // The data can be retrieved using toByteArray() and toString().
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            //InputStream is a class that respresents an input stream of BYTES, obtained from the 'url'.
            //connect.getInputStream() returns the data of bytes from the URL "resource".
            //For example, if the URL "resource" is https://bignerdranch.com, then
            //connection.getInputStream() returns all the bytes that represent the HTML file of this page
            //(where each individual byte (i.e. 8 bits, e.g. 00101110) represents an ASCII character of the HTML file. NOTE: 2^8 = 256 combinations)
            //...ultimately, all of this is stored in inputStream (of type InputStream)
            InputStream inputStream = connection.getInputStream();


            //Test if the connection to the URL has been formed..
            //connection.getResponseCode() returns an int..
            //IF a connection IS formed, then..
            //connection.getResponseCode() returns: 200
            //IF a connection is NOT formed, then..
            //connection.getResponseCode() returns: 401
            //HttpURLConnection.HTTP_OK is a STATUS CODE that equals int: 200...
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //i.e. connection.getResponseCode() returns 401

                //At this point, a connection to the URL is NOT formed (i.e. connection.getResponseCode() returns 401)
                //Therefore, connection.getResponseMessage returns "NOT FOUND"
                //..so we get:
                //NOT FOUND: with *https://......*
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            //If code reaches this point (past the try block), then a connection to the "resource" (via the URL) will have formed
            //i.e. connection.getResponseCode = HttpURLConnect.HTTP_OK = 200


            //Declare a local primitive variable to store the NUMBER OF BYTES read from the buffer array
            //bytesRead will be an integer equal to the size of inputStream
            int bytesRead = 0;

            //Create a buffer array of type byte (size = 1024)
            //This is a buffer array to temporarily store the bytes data read from the inputStream
            //so that this data could then be written into the byteArrayOutputStream (in the while loop).
            //NOTE: EACH byte (i.e. 8-bit number) in this buffer array represeents
            // a single ASCII character of the URL "resource"
            //NOTE: If the URL is a 'non-REST' HTTP web address,
            // then each byte represents a single ASCII character of the HTML of the HTTP web address
            byte[] buffer = new byte[1024];




            //What happens in EACH loop in the whileloop:
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
            //Cycle 17 bytesRead = -1 (i.e. no more bytes were left to read - in which case, the whileloop stops!)
            //NOTE: It doesn't matter what the size of 'buffer' is.
            // The TOTAL number of bytes to read is UNCHANGED!!
            // A larger 'buffer' size just means that less whileloops will be run (below) - that's all!

            //This process will continue until all bytes in the inputStream array are read,
            //until inputStream.read(buffer) returns -1 (stopping the whileloop).

            while ( (bytesRead = inputStream.read(buffer) ) > 0) {

                //byteArrayOutputStream.write(buffer, 0, bytesRead) READS bytes FROM 'buffer' and WRITES it to byteArrayOutputStream.
                //Argument 1 (byte[]): The data ('buffer') to READ from (in order to WRITE to 'byteArrayOuputStream')
                //Argument 2 (int): The offset in the data
                //Argument 3 (int): The number of bytes to write (into 'byteArrayOutputStream')
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            //Close the byteArrayOutputStream when done
            byteArrayOutputStream.close();


            //Convert the ByteSrrayOutpuStream's to an array of bytes
            //NOTE: This array of bytes is to later
            // be processed by the getUrlString(String) method,
            // where the bytes will be turned to String
            return byteArrayOutputStream.toByteArray();
        }

        //Then disconnect the connection
        finally {
            connection.disconnect();
        }
    }




}
