package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.petertieu.android.quotesearch.R;

public class RemoveAllMyQuotesConfirmationDialogFragment extends DialogFragment{


    private static final String TAG = "RemoveAllMQConfDF";



    private static final String ARGUMENT_BUNDLE_MY_QUOTES_LIST_SIZE = "argumentMyQuotesListSize";

    public static final String EXTRA_SHOULD_REMOVE_ALL_MY_QUOTES = "shouldRemoveAllMyQuotes";



    public static RemoveAllMyQuotesConfirmationDialogFragment newInstance(int myQuotesListSize){

        Bundle argumentBundle = new Bundle();

        argumentBundle.putInt(ARGUMENT_BUNDLE_MY_QUOTES_LIST_SIZE, myQuotesListSize);

        RemoveAllMyQuotesConfirmationDialogFragment removeAllMyQuotesConfirmationDialogFragment = new RemoveAllMyQuotesConfirmationDialogFragment();

        removeAllMyQuotesConfirmationDialogFragment.setArguments(argumentBundle);

        return removeAllMyQuotesConfirmationDialogFragment;
    }



    //Override onCreateDialog(..) callback method - to set up dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        int myQuotesListSize = (int) getArguments().getInt(ARGUMENT_BUNDLE_MY_QUOTES_LIST_SIZE);


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_delete_my_quote_confirmation, null);


        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nDelete Quote\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.orange)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey)); //Set curentDescriptionEditTextString background color




        String dialogMessage = null;
        if (myQuotesListSize == 1){
            dialogMessage = "Are you sure you want to remove this Quote from My Quotes?";
        }
        else if (myQuotesListSize == 2){
            dialogMessage = "Are you sure you want to remove these 2 Quotes from My Quotes?";
        }
        else if (myQuotesListSize > 1){
            dialogMessage = "Are you sure you want to remove all " + myQuotesListSize + " Quotes from My Quotes?";
        }









        return new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                boolean shouldRemoveAllMyQuotes = true;


                                sendResults(Activity.RESULT_OK, shouldRemoveAllMyQuotes);
                            }


                        })
                .create();
    }





    private void sendResults(int resultCode, boolean shouldDeleteMyQuote){

        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();

        intent.putExtra(EXTRA_SHOULD_REMOVE_ALL_MY_QUOTES, shouldDeleteMyQuote);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }



}
