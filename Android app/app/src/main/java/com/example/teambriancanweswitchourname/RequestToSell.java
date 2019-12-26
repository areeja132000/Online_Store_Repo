package com.example.teambriancanweswitchourname;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.ShoppingCart;
import com.b07.security.PasswordHelpers;
import com.b07.users.Customer;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

public class RequestToSell extends AppCompatActivity {

    ShoppingCart currentCart;
    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RequestToSell.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_to_sell);
        Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customerId", 0);
        final int accountId = intent.getIntExtra("accountId", 0);

        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");
        try {
            currentCart = new ShoppingCart(new Customer(0,"", 0, ""));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        Button requestSellBtn = (Button) findViewById(R.id.requestSell);
        requestSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ItemName = (EditText)findViewById(R.id.ItemName);
                String ItemNameCleaned = ItemName.getText().toString();

                EditText ItemPrice = (EditText)findViewById(R.id.ItemPrice);
                String ItemPriceCleaned = ItemPrice.getText().toString();

                EditText ItemStock = (EditText)findViewById(R.id.ItemStock);
                int ItemStockCleaned = Math.toIntExact(Integer.parseInt(ItemStock.getText().toString()));

                EditText EmployeeId = (EditText)findViewById(R.id.EmployeeId);
                int EmployeeIdCleaned = Math.toIntExact(Integer.parseInt(EmployeeId.getText().toString()));

                EditText EmployeePassword = (EditText)findViewById(R.id.EmployeePassword);
                String EmployeePasswordCleaned = EmployeePassword.getText().toString();

                String actualPass = mydb.getPassword(EmployeeIdCleaned);
                String hashedEntered = PasswordHelpers.passwordHash(EmployeePasswordCleaned);

                if (actualPass.equals(hashedEntered)) {
                    long ItemId = mydb.insertItem(ItemNameCleaned, new BigDecimal(""+ItemPriceCleaned));
                    mydb.insertInventory(Math.toIntExact(ItemId), ItemStockCleaned);
                    new MaterialAlertDialogBuilder(RequestToSell.this).setMessage("Authentication succeeded.\nYour item is now part of the stock.\nThank you.\n").setPositiveButton("Ok", null).show();
                } else {
                    new MaterialAlertDialogBuilder(RequestToSell.this).setMessage("Authentication failed. \nYou are not an employee or you passed in the wrong password or Id. \nPlease try again.\n").setPositiveButton("Ok", null).show();
                }
            }
        });


        Button buttonBack = (Button) findViewById(R.id.backBtn);
        final ShoppingCart finalCurrentCart = currentCart;
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestToSell.this, CustomerMenu.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", finalCurrentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }
}
