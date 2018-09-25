package com.tieutech.android.quotespire.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tieutech.android.quotespire.R;

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


//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_remove_all_my_quotes_confirmation, null);


//        //Set-up custom title to display in the dialog
//        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
//        dialogTitle.setText("\nDelete Quote\n"); //Set curentDescriptionEditTextString on TextView
//        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
//        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
//        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
//        dialogTitle.setTextColor(getResources().getColor(R.color.orange)); //Set curentDescriptionEditTextString color
//        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey)); //Set curentDescriptionEditTextString background color


        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nRemove All\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color






//        String dialogMessage = null;
//        if (myQuotesListSize == 1){
//            dialogMessage = "Are you sure you want to remove this Quote from My Quotes?";
//        }
//        else if (myQuotesListSize == 2){
//            dialogMessage = "Are you sure you want to remove these 2 Quotes from My Quotes?";
//        }
//        else if (myQuotesListSize > 1){
//            dialogMessage = "Are you sure you want to remove all " + myQuotesListSize + " Quotes from My Quotes?";
//        }







        View dialogFragmentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_remove_all_my_quotes_confirmation, null);


        TextView removeAllMyQuotesConfirmationDialogFragmenMessage = (TextView) dialogFragmentView.findViewById(R.id.dialog_fragment_remove_all_my_quotes_confirmation_message);


        if (myQuotesListSize == 1){
            removeAllMyQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove this Quote from My Quotes?");
        }
        else if (myQuotesListSize == 2){
            removeAllMyQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove these 2 Quotes from My Quotes?");
        }
        else if (myQuotesListSize > 1){
            removeAllMyQuotesConfirmationDialogFragmenMessage.setText("Are you sure you want to remove all " + myQuotesListSize + " Quotes from My Quotes?");
        }



        final AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity())
                .setView(dialogFragmentView)
                .setCustomTitle(dialogTitle)
//                .setMessage(dialogMessage)
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






        //Set colors of negative and positive buttons
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.dialogFragmentButton));
                alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.dialogFragmentButton));
            }
        });



        return alertDialog;

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
