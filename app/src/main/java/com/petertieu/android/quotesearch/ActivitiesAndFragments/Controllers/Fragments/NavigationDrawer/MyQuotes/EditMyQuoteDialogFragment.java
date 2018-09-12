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

public class EditMyQuoteDialogFragment extends DialogFragment{


    private static final String TAG = "EditMyQuoteDF";


    private EditText mAuthorEditText;
    private EditText mQuoteEditText;




    private static final String ARGUMENT_BUNDLE_MY_QUOTE_ID = "argumentBundleMyQuoteID";
    private static final String ARGUMENT_BUNDLE_MY_QUOTE_AUTHOR = "argumentBundleMyQuoteAuthor";
    private static final String ARGUMENT_BUNDLE_MY_QUOTE_QUOTE = "argumentBundleMyQuoteQuote";


    public static final String EXTRA_AUTHOR = "extraAuthor";
    public static final String EXTRA_QUOTE = "extraQuote";
    public static final String  EXTRA_ID = "extraID";
    public static final String EXTRA_POSITION = "extraPosition";





    public static EditMyQuoteDialogFragment newInstance(String author, String quote, String ID){

        Bundle argumentBundle = new Bundle();

        argumentBundle.putString(ARGUMENT_BUNDLE_MY_QUOTE_ID, ID);
        argumentBundle.putString(ARGUMENT_BUNDLE_MY_QUOTE_AUTHOR, author);
        argumentBundle.putString(ARGUMENT_BUNDLE_MY_QUOTE_QUOTE, quote);

        EditMyQuoteDialogFragment editMyQuoteDialogFragment = new EditMyQuoteDialogFragment();

        editMyQuoteDialogFragment.setArguments(argumentBundle);

        return editMyQuoteDialogFragment;
    }






    //Override onCreateDialog(..) callback method - to set up dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        final String ID = getArguments().getString(ARGUMENT_BUNDLE_MY_QUOTE_ID);
        String author = getArguments().getString(ARGUMENT_BUNDLE_MY_QUOTE_AUTHOR);
        String quote = getArguments().getString(ARGUMENT_BUNDLE_MY_QUOTE_QUOTE);




        //Set-up custom title to display in the dialog
        TextView dialogTitle = new TextView(getActivity()); //Create TextView object
        dialogTitle.setText("\nEdit Quote\n"); //Set curentDescriptionEditTextString on TextView
        dialogTitle.setTextSize(22); //Set size of curentDescriptionEditTextString
        dialogTitle.setGravity(Gravity.CENTER); //Set  position of curentDescriptionEditTextString in the title box of the dialog
        dialogTitle.setTypeface(null, Typeface.BOLD); //Set the curentDescriptionEditTextString to be bold
        dialogTitle.setTextColor(getResources().getColor(R.color.orange)); //Set curentDescriptionEditTextString color
        dialogTitle.setBackgroundColor(getResources().getColor(R.color.grey)); //Set curentDescriptionEditTextString background color






        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_edit_my_quote, null);

        mAuthorEditText = (EditText) view.findViewById(R.id.edit_my_quote_author_edit_text);
        mQuoteEditText = (EditText) view.findViewById(R.id.edit_my_quote_quote_edit_text);

        mAuthorEditText.setText(author);
        mQuoteEditText.setText(quote);






        //Force the soft-keyboard to show
        // Without this implementation: When the DialogFragment is opened, mAuthorEditText focusses automatically, but the soft-keyboard doesn't show up automatically)
        final InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);





        return new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setCustomTitle(dialogTitle)
                .setMessage(null)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.save_edit,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                String editedAuthor = mAuthorEditText.getText().toString();
                                String editedQuote = mQuoteEditText.getText().toString();


                                Log.i(TAG, "Quote Author: " + editedAuthor);
                                Log.i(TAG, "Quote Quote: " + editedQuote);


                                //Accidental code: Somehow, adding this line fixes the error whereby when the user manually closes the soft-keyboard before pressing
                                // the 'positive' button ("Add Quote"), the list-item in MyQuotesFragment wouldn't be displayed until the Fragment view is reloaded
                                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);



                                //If NOTHING is typed into the Author.. AND... Quote EditTexts
                                if (editedAuthor.length() == 0 && editedQuote.length() == 0){

                                    Toast toast = Toast.makeText(getContext(), "No Quote Added", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                                    toast.show();

                                    return;
                                }


                                sendResults(Activity.RESULT_OK, editedAuthor, editedQuote, ID);
                            }


                        })
                .create();
    }



    private void sendResults(int resultCode, String editedAuthor, String editedQuote, String ID){

        if (getTargetFragment() == null){
            return;
        }


        Intent intent = new Intent();

        intent.putExtra(EXTRA_AUTHOR, editedAuthor);
        intent.putExtra(EXTRA_QUOTE, editedQuote);
        intent.putExtra(EXTRA_ID, ID);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }



}
