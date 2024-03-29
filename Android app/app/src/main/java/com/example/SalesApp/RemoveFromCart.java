package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mockAmazonApp.exceptions.NotEnoughResourcesException;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.inventory.ShoppingCart;
import com.mockAmazonApp.store.ItemImpl;
import com.example.SalesApp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

public class RemoveFromCart extends AppCompatActivity implements Serializable {

    ShoppingCart currentCart;
    int customerId;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_from_cart);
        Intent intent = getIntent();
        customerId = intent.getIntExtra("customerId", 0);
        accountId = intent.getIntExtra("accountId", 0);
        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");

        Button removeButton = (Button) findViewById(R.id.removeBtn);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RemoveFromCart.this);

                EditText removeNameInput = (EditText) findViewById(R.id.removeItemName);
                String removeName = removeNameInput.getText().toString();

                EditText removeQuantityInput = (EditText) findViewById(R.id.removeQuantity);
                int removeQuantity = Integer.parseInt(removeQuantityInput.getText().toString());

                //Cursor to Items table
                Cursor allItems = mydb.getAllItems();
                allItems.moveToFirst();

                Item temp = null;
                while (!allItems.isAfterLast()) {
                    if (removeName.equals(allItems.getString(1))) {
                        temp = new ItemImpl(allItems.getInt(0), allItems.getString(1), new BigDecimal (""+allItems.getString(2)));
                    }
                    allItems.moveToNext();
                }
                try {
                    currentCart.removeItem(temp, removeQuantity);
                    new MaterialAlertDialogBuilder(RemoveFromCart.this).setMessage("Your cart now:\n"+ currentCart.displayCart()).setPositiveButton("Ok", null).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NotEnoughResourcesException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button buttonBack = (Button) findViewById(R.id.backBtn);
        final ShoppingCart finalCurrentCart = currentCart;
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", currentCart);
                intent.putExtra("accountId", accountId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
