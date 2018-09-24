package com.petertieu.android.quotesearch.ActivitiesAndFragments.Controllers.Fragments.NavigationDrawer.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.petertieu.android.quotesearch.R;

public class TimePickerDialogFragment extends DialogFragment{

    TimePicker mTimePicker;

    private static final String ARGUMENT_QOD_UPDATE_HOUR = "QODUpdateHour"; //Declare 'key'
    private static final String ARGUMENT_QOD_UPDATE_MINUTE = "QODUpdateMinute"; //Declare 'key'

    public static final String EXTRA_PICKED_HOUR = "extraPickedHour"; //Define identifier for dialog fragment extra
    public static final String EXTRA_PICKED_MINUTE = "extraPickedMinute"; //Define identifier for dialog fragment extra


    int mPickedHour = 10;
    int mPickedMinute = 5;



    //Build encapsulating 'constructor'
    public static TimePickerDialogFragment newInstance(int quoteOfTheDayUpdateHour, int quoteOfTheDayUpdateMinute){

        Bundle argumentBundle = new Bundle(); //Create Bundle object (i.e. argument-bundle)

        argumentBundle.putInt(ARGUMENT_QOD_UPDATE_HOUR, quoteOfTheDayUpdateHour); //Set key-value pairs for argument-bundle
        argumentBundle.putInt(ARGUMENT_QOD_UPDATE_MINUTE, quoteOfTheDayUpdateMinute);

        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment(); //Create DateDialogFragment

        timePickerDialogFragment.setArguments(argumentBundle); //Set argument-bundle for the PixDetailFragment

        return timePickerDialogFragment; //Return DateDialogFragment object
    }




    //Override lifecycle callback method from DialogFragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //Get 'value' from argument-bundle
        final int hour = (int) getArguments().getInt(ARGUMENT_QOD_UPDATE_HOUR);
        final int minute = (int) getArguments().getInt(ARGUMENT_QOD_UPDATE_MINUTE);


        //Inflate DatePicker dialog layout
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_time_picker, null);

        //Assign DatePicker reference variable to associated resource ID
        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_picker);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        }else{
            //below Marshmallow
        }




//        TextView dialogTitle = new TextView(getActivity());
//        dialogTitle.setText(R.string.check_time_preference_title);
//        dialogTitle.setTextColor(getResources().getColor(R.color.orange));
//        dialogTitle.setTextSize(25);
//        dialogTitle.setTypeface(null, Typeface.BOLD);
//        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
//        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color



        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\n" + getResources().getString(R.string.check_time_preference_title) + "\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.dialogFragmentTitleText)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.dialogFragmentTitleBackground)); //Set curentDescriptionEditTextString background color



        //Return AlertDialog (subclass of Dialog), which sets the dialog properties
        final AlertDialog alertDialog = new AlertDialog
                .Builder(getActivity()) //Create Builder
                .setView(view) //Set View of dialog
                .setCustomTitle(dialogTitle) //Set TITLE of dialog
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, //Set POSITIVE BUTTON of the dialog, and a listener for it
                        new DialogInterface.OnClickListener() {

                            //Override listener of DialogInterface.OnClickListener.OnClickListener interface
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                                    mPickedHour = mTimePicker.getHour();
                                    mPickedMinute = mTimePicker.getMinute();



                                }else{
                                    //below Marshmallow
                                }


                                //Send new Date data back to hosting activity (PixViewPagerActivity)
                                sendResult(Activity.RESULT_OK, mPickedHour, mPickedMinute);
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





    //Send result to the hosting activity
    private void sendResult(int resultCode, int hour, int minute){

        //If hosting fragment (PixDetailFragment) DOES NOT exist
        if (getTargetFragment() == null){
            return;
        }

        //Create Intent
        Intent intent = new Intent();

        //Add Date data as 'extra'
        intent.putExtra(EXTRA_PICKED_HOUR, hour);
        intent.putExtra(EXTRA_PICKED_MINUTE, minute);


        //Send resultCode and Intent to hosting fragment (PixDetailFragment)
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


}
