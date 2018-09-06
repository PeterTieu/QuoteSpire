package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.petertieu.android.quotesearch.R;

import java.util.UUID;

public class AddNewQuoteDialogFragment extends DialogFragment{

    private static final String TAG = "AddNewMyQuoteDF";

    private EditText mAuthorEditText;
    private EditText mQuoteEditText;

    public static final String EXTRA_ID = "extraID";
    public static final String EXTRA_AUTHOR = "extraAuthor";
    public static final String EXTRA_QUOTE = "extraQuote";



    public static AddNewQuoteDialogFragment newInstance(){
        AddNewQuoteDialogFragment addNewQuoteDialogFragment = new AddNewQuoteDialogFragment();

        return addNewQuoteDialogFragment;
    }



    //Override onCreateDialog(..) callback method - to set up dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nAdd New Quote\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.orange)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey)); //Set curentDescriptionEditTextString background color




        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_add_new_quote, null);

        mAuthorEditText = (EditText) view.findViewById(R.id.add_new_quote_author_edit_text);
        mQuoteEditText = (EditText) view.findViewById(R.id.add_new_quote_quote_edit_text);






        return new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(null)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String ID = UUID.randomUUID().toString(); //Create unique UUID for the Quote so that it could be distinguished in the SQLiete databases
                                String author = mAuthorEditText.getText().toString();
                                String quote = mQuoteEditText.getText().toString();


                                Log.i(TAG, "Quote ID: " + ID);
                                Log.i(TAG, "Quote Author: " + author);
                                Log.i(TAG, "Quote Quote: " + quote);



                                sendResults(Activity.RESULT_OK, ID, author, quote);
                            }


                        })
                .create();
    }



    private void sendResults(int resultCode, String ID, String author, String quote){

        if (getTargetFragment() == null){
            return;
        }


        Intent intent = new Intent();

        intent.putExtra(EXTRA_ID, ID);
        intent.putExtra(EXTRA_AUTHOR, author);
        intent.putExtra(EXTRA_QUOTE, quote);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }



}
