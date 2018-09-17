package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.petertieu.android.quotesearch.R;

import java.util.UUID;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class AboutAppDialogFragment extends DialogFragment{

    private static final String TAG = "AddNewQuoteDF";


    public static AboutAppDialogFragment newInstance(){
        return new AboutAppDialogFragment();
    }



    //Override onCreateDialog(..) callback method
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //Inflate the layout of the AlertDialog
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_about_app, null);

        //Create and customise the dialog title
        TextView dialogTitle = new TextView(getActivity());
        dialogTitle.setText("About Quote Studio");
        dialogTitle.setTextSize(24);
        dialogTitle.setHeight(120);
        dialogTitle.setGravity(Gravity.CENTER);
        dialogTitle.setTypeface(null, Typeface.BOLD);
        dialogTitle.setTextColor(getResources().getColor(R.color.colorAccent));
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.orange));



        //Create the AlertDialog
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(Html.fromHtml(
                        "<br>" + "</br>" + //New Line
                                "Quote Studio is your personal and wholy comprehensive app for Quotes!" + " " +
                                "<br>" + "</br>" + //New Line
                                "<br>" + "</br>" + //New Line
                                "Be motivated everyday by quotes and beautiful pictures of quotes from the world's best quote REST API, TheySaidSo." +
                                "<br>" + "</br>" + //New Line
                                "<br>" + "</br>" + //New Line
                                "<b>" + "Tap into " + "</b>" +
                                "an endless library of quotes from numerous philosophers, writers, thinkers and great minds whose words have shed wisdom and inspiration in the world!" +
                                "<br>" + "</br>" + // New Line
                                "<br>" + "</br>" + //New Line
                                "<b>" + "Features " + "</b>" +
                                "new inspirational quotes everyday; countless assortments of random quotes and pictures of quotes; search functions for quotes and pictures " +
                                "of quotes by any author, category and/or keyword." +
                                "<br>" + "</br>" + //New Line
                                "<br>" + "</br>" + //New Line
                                "Found a quote or picture you like? Add it to your own collection of favorite quotes!" +
                                "<br>" + "</br>" + //New Line
                                "<br>" + "</br>" + //New Line
                                "You could even create your own quotes!" +
                                "<br>" + "</br>" + //New Line
                                "<br>" + "</br>" + //New Line
                                "<br>" + "</br>" + //New Line
                                "<div style=\"text-align:center\">" + "Brought to you by TieuTech" + "</div>"))
                .setPositiveButton(android.R.string.ok, null)
                .show();


        //If the screen orientation is PORTRAIT
        if (getActivity().getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            alertDialog.getWindow().setLayout(900,1400); //Set the size of the AlertDialog as per PORTRAIT orientation
        }
        //If the screen orientation is LANDSCAPE
        else{
            alertDialog.getWindow().setLayout(1450, 900); //Set the size of the AlertDialog as per LANDSCAPE orientation
        }


        //Return the AlertDialog
        return alertDialog;
    }


}
