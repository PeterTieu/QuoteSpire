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

public class DeleteMyQuoteConfirmationDialogFragment extends DialogFragment {


    private static final String TAG = "DeleteMyQuoteConfDF";

    private TextView mAuthor;
    private TextView mQuote;


    private static final String ARGUMENT_BUNDLE_MY_QUOTE_AUTHOR = "argumentBundleMyQuoteAuthor";
    private static final String ARGUMENT_BUNDLE_MY_QUOTE_QUOTE = "argumentBundleMyQuoteQuote";
    private static final String ARGUMENT_BUNDLE_MY_QUOTE_POSITION = "argumentBundleMyQuotePosition";

    public static final String EXTRA_SHOULD_DELETE_MY_QUOTE_ID = "shouldDeleteMyQuoteID";
    public static final String EXTRA_POSITION_ID = "positionID";


    public static DeleteMyQuoteConfirmationDialogFragment newInstance(String myQuoteAuthor, String myQuoteQuote, int position){

        Bundle argumentBundle = new Bundle();

        argumentBundle.putString(ARGUMENT_BUNDLE_MY_QUOTE_AUTHOR, myQuoteAuthor);
        argumentBundle.putString(ARGUMENT_BUNDLE_MY_QUOTE_QUOTE, myQuoteQuote);
        argumentBundle.putInt(ARGUMENT_BUNDLE_MY_QUOTE_POSITION, position);

        DeleteMyQuoteConfirmationDialogFragment deleteMyQuoteConfirmationDialogFragment = new DeleteMyQuoteConfirmationDialogFragment();

        deleteMyQuoteConfirmationDialogFragment.setArguments(argumentBundle);

        return deleteMyQuoteConfirmationDialogFragment;
    }



    //Override onCreateDialog(..) callback method - to set up dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        String myQuoteAuthor = (String) getArguments().getString(ARGUMENT_BUNDLE_MY_QUOTE_AUTHOR);
        String myQuoteQuote = (String) getArguments().getString(ARGUMENT_BUNDLE_MY_QUOTE_QUOTE);
        final int position = (int) getArguments().getInt(ARGUMENT_BUNDLE_MY_QUOTE_POSITION);


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_delete_my_quote_confirmation, null);


        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nDelete Quote\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.orange)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.FavoriteQuotesAndMyQuotesListItems)); //Set curentDescriptionEditTextString background color




        //If MyQuote Author title does NOT exist, is empty, or contains only spaces
        if (myQuoteAuthor == null || myQuoteAuthor.isEmpty() || myQuoteAuthor.trim().length()==0){
            myQuoteAuthor = "<i>" + "*No Author*"  + "</i>";
        }

        //If MyQuote Quote does NOT exist, is empty, or contains only spaces
        if (myQuoteQuote == null || myQuoteQuote.isEmpty() || myQuoteQuote.trim().length()==0){
            myQuoteQuote = "<i>" + "*No Quote*"  + "</i>";
        }









        return new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(Html.fromHtml( //Set message of dialog //Set message of dialog
                        "<br>" + "</br>" + //New line
                                "<br>" + "</br>" + //New line
                                "<b>" + "Author:" + "</b>" + " " + myQuoteAuthor + //Title line (where "Title:" is bolded)
                                "<br>" + "</br>" + //New line
                                "<br>" + "</br>" + //New line
                                "<b>" + "Quote:" + "</b>" + " " + myQuoteQuote + //Description line (where "Description:" is bolded)
                                "<br>" + "</br>" + //New line
                                "<br>" + "</br>" + //New line
                                "<br>" + "</br>" + //New line
                                "<br>" + "</br>" + //New line
                                "<b>" + "Are you sure you want to delete this Quote?" + "</b>")) //Prompt line (bolded)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.delete_quote,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                boolean shouldDeleteMyQuote = true;


                                sendResults(Activity.RESULT_OK, shouldDeleteMyQuote, position);
                            }


                        })
                .create();
    }





    private void sendResults(int resultCode, boolean shouldDeleteMyQuote, int position){

        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();

        intent.putExtra(EXTRA_SHOULD_DELETE_MY_QUOTE_ID, shouldDeleteMyQuote);
        intent.putExtra(EXTRA_POSITION_ID, position);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }



}
