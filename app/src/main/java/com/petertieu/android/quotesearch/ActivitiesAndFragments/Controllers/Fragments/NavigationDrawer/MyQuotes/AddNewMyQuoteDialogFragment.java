package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.MyQuotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.petertieu.android.quotesearch.R;

public class AddNewMyQuoteDialogFragment extends DialogFragment{


    public static AddNewMyQuoteDialogFragment newInstance(){
        AddNewMyQuoteDialogFragment addNewMyQuoteDialogFragment = new AddNewMyQuoteDialogFragment();

        return addNewMyQuoteDialogFragment;
    }



    //Override onCreateDialog(..) callback method - to set up dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nAdd a New Quote\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.orange)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey)); //Set curentDescriptionEditTextString background color




        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_add_new_quote, null);

        return new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(null)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.yes, null)
                .create();
    }



}
