package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }

    int quantity = 0;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameText = (EditText) findViewById(R.id.name_field);
        String nameValue = nameText.getText().toString();

        displayMessage(createOrderSummary(hasWhippedCream, hasChocolate, nameValue));
    }

    private String createOrderSummary(boolean whippedCream, boolean chocolate, String name) {
        String whipChoice;
        String chocChoice;

        if (whippedCream == false) {
            whipChoice = "No";
        }
        else{
            whipChoice = "Yes";
        }

        if (chocolate == false) {
            chocChoice = "No";
        }
        else{
            chocChoice = "Yes";
        }

        String message = "Name: " + name +
                "\nQuantity: " + quantity +
                "\nWhipped Cream: " + whipChoice +
                "\nChocolate: " + chocChoice +
                "\nTotal: $" + calculatePrice(whippedCream, chocolate) +
                "\nThank you!";

        return message;
    }

    public void sendEmail(View View) {
        EditText nameText = (EditText) findViewById(R.id.name_field);
        String nameValue = nameText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.email_field);
        String emailValue = emailText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailValue});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(hasWhippedCream, hasChocolate, nameValue));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String name) {

    }

    /**
     * Calculates the price of the order based on the current quantity.
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {

        if (whippedCream && chocolate){
            return quantity * (5 + 1 + 2);
        }
        else if (whippedCream){
            return quantity * (5 + 1);
        }
        else if (chocolate) {
            return quantity * (5 + 2);
        }
        else {
            return quantity * 5;
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //exit method early
            return;
        }

        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exit method early
            return;
        }

        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}