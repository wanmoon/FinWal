package com.wanmoon.finwal.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wanmoon.finwal.R;

import java.util.Timer;
/**
 * Created by pimpischaya on 5/27/2017 AD.
 */

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextTransaction;
    private EditText editTextHowmuch;
    private TextView textViewFinish;
    private TextView textViewCancel;
    //button income || expense
    private Button buttonPlus;
    private Button buttonMinus;
    //button income
    private Button buttonSalary;
    private Button buttonLoan;
    private Button buttonGift;
    private Button buttonFamilyIncome;
    private Button buttonExtra;

    //button expense
    private Button buttonBill;
    private Button buttonEducation;
    private Button buttonEntertainment;
    private Button buttonFood;
    private Button buttonShopping;
    private Button buttonTransport;
    private Button buttonTravel;
    private Button buttonFamilyExpense;
    private Button buttonHealthCare;
    private Button buttonSaving;

    private Dialog incomeCate;
    private Dialog expenseCate;

    private TextView textViewCategories;
    private TextView textViewTransaction;

    private String transaction;
    private String cate;
    private String getTransac;
    private int getHowMuch;
    private Timer timeCloseDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Get the view from addtransaction.xml
        setContentView(R.layout.addtransaction);

        textViewFinish = (TextView) findViewById(R.id.textViewFinish);
        textViewCancel = (TextView) findViewById(R.id.textViewCancel);
        textViewFinish.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);

        editTextTransaction = (EditText) findViewById(R.id.editTextTransaction);
        editTextHowmuch = (EditText) findViewById(R.id.editTextHowmuch);

        textViewTransaction = (TextView) findViewById(R.id.textViewTransaction);
        textViewCategories = (TextView) findViewById(R.id.textViewCategories);

        //transaction button : click then have popup
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeCate = new Dialog(AddTransaction.this);
                incomeCate.getWindow();
                incomeCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                incomeCate.setContentView(R.layout.income_cate);
                incomeCate.setCancelable(true);
                incomeCate.show();

                transaction = "Income";
                textViewTransaction.setText("Transaction : " + transaction);

                // income button
                buttonGift = (Button) incomeCate.findViewById(R.id.buttonGift);
                buttonGift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonGift.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonSalary = (Button) incomeCate.findViewById(R.id.buttonSalary);
                buttonSalary.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        cate = buttonSalary.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonLoan = (Button) incomeCate.findViewById(R.id.buttonLoan);
                buttonLoan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cate = buttonLoan.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonFamilyIncome = (Button) incomeCate.findViewById(R.id.buttonFamilyIncome);
                buttonFamilyIncome.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        cate = buttonFamilyIncome.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Income", Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonExtra = (Button) incomeCate.findViewById(R.id.buttonExtra);
                buttonExtra.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick (View v){
                        cate = buttonExtra.getText().toString();
                        Toast.makeText(AddTransaction.this,cate, Toast.LENGTH_LONG).show();
                        incomeCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

            }
        });

        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseCate = new Dialog(AddTransaction.this);
                expenseCate.getWindow();
                expenseCate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                expenseCate.setContentView(R.layout.expense_cate);
                expenseCate.setCancelable(true);
                expenseCate.show();

                transaction = "Expense";
                textViewTransaction.setText("Transaction : " + transaction);

                //expense's button
                buttonBill = (Button) expenseCate.findViewById(R.id.buttonBill);
                buttonBill.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonBill.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonEducation = (Button) expenseCate.findViewById(R.id.buttonEducation);
                buttonEducation.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        cate = buttonEducation.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonEntertainment = (Button) expenseCate.findViewById(R.id.buttonEntertainment);
                buttonEntertainment.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonEntertainment.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonFood = (Button) expenseCate.findViewById(R.id.buttonFood);
                buttonFood.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonFood.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonShopping = (Button) expenseCate.findViewById(R.id.buttonShopping);
                buttonShopping.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonShopping.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonTransport = (Button) expenseCate.findViewById(R.id.buttonTransport);
                buttonTransport.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonTransport.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonTravel = (Button) expenseCate.findViewById(R.id.buttonTravel);
                buttonTravel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonTravel.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonFamilyExpense = (Button) expenseCate.findViewById(R.id.buttonFamilyExpense);
                buttonFamilyExpense.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonFamilyExpense.getText().toString();
                        Toast.makeText(AddTransaction.this,cate, Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonHealthCare = (Button) expenseCate.findViewById(R.id.buttonHealthCare);
                buttonHealthCare.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonHealthCare.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });

                buttonSaving = (Button) expenseCate.findViewById(R.id.buttonSaving);
                buttonSaving.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        cate = buttonSaving.getText().toString();
                        Toast.makeText(AddTransaction.this,cate + " Expense", Toast.LENGTH_LONG).show();
                        expenseCate.cancel();
                        textViewCategories.setText("Categories : " + cate);
                    }
                });
            }
        });
    }

    public void addTransaction() {
        getTransac = editTextTransaction.getText().toString();
        getHowMuch = Integer.parseInt(editTextHowmuch.getText().toString());

        /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("My Transaction");
        alert.setMessage(getTransac+"\n"+getHowMuch+" Baht\n"+transaction+" : "+cate);
        alert.setCancelable(true);

        final AlertDialog closedialog = alert.create();

        closedialog.show();

        timeCloseDialog = new Timer();
        timeCloseDialog.schedule(new TimerTask() {
            public void run() {
                closedialog.dismiss();
                timeCloseDialog.cancel(); //this will cancel the timer of the system
            }
        }, 10000); // the timer will count 5 seconds....
*/


    }

    @Override
    public void onClick(View v) {

        if (v == textViewFinish) {
            addTransaction();

            // will open login activity here
            Intent i = new Intent(getApplicationContext(), DetailDaily.class);
            startActivity(i);

        }
        if (v == textViewCancel) {
            // will open login activity here
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        }
    }
}


