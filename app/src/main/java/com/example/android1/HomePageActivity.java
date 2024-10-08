package com.example.android1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android1.R;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    private CheckBox parrots, lovebirds, macaws;
    ArrayList<String> arr = new ArrayList<>();

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private TextView quantityTextView, priceTextView, birdshome, ratingText,seekbar;
    private Button increment, decrement, placeOrder;
    private int quantity = 0;
    private int price = 0;

    private AlertDialog.Builder builder;
    private RatingBar ratingBar;
    private SeekBar seekBar;
    private TextView progressText;
    private Switch switchButton;
    private TextView switchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //For CheckBox
        parrots = findViewById(R.id.parrots);
        lovebirds = findViewById(R.id.lovebirds);
        macaws = findViewById(R.id.macaws);
        birdshome = findViewById(R.id.birdshome);

        //For RadioGroup
        radioGroup = findViewById(R.id.radioGroup);

        //For Increment Decrement
        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);

        placeOrder = findViewById(R.id.order_btn);
        builder = new AlertDialog.Builder(this);

        //For RatingBar
        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.rating);



        parrots.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });
        lovebirds.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });
        macaws.setOnCheckedChangeListener((buttonView, isChecked) -> {
            check(buttonView, isChecked);
        });

        increment.setOnClickListener(v -> {
            quantity++;
            price = quantity * 500;
            quantityTextView.setText("" + quantity);
            priceTextView.setText("$" + price);
        });
        decrement.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                price = quantity * 250;
                quantityTextView.setText("" + quantity);
                priceTextView.setText("$" + price);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText("Rating: " + rating);
            }
        });
//for seekbar
        seekBar = findViewById(R.id.seekbar);
        progressText = findViewById(R.id.progressText);

        progressText.setText("Progress: " + seekBar.getProgress() + " / " + seekBar.getMax());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressText.setText("Progress: " + progress + " / " + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//for switch
        switchButton = findViewById(R.id.switchButton);
        switchStatus = findViewById(R.id.switchStatus);

        switchStatus.setText("Switch is OFF");

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    switchStatus.setText("Switch is ON");
                } else {

                    switchStatus.setText("Switch is OFF");
                }
            }
        });


        placeOrder.setOnClickListener(v -> {
            try {
                if (arr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Birds!!", Toast.LENGTH_SHORT).show();
                }
                String radioValue = radioButton.getText().toString();
                if (quantity == 0) {
                    Toast.makeText(getApplicationContext(), "Please add quantity!!", Toast.LENGTH_SHORT).show();
                } else {
                    builder.setTitle("Order Placed!!")
                            .setMessage("Order Summary:\n" + "Birds: " + arr + "\nBirds Size: " + radioValue + "\nQuantity: " + quantity + "\nTotal Price: BDT " + price + "\nRating: " + ratingBar.getRating() +"\nThank you!!")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Order Placed!!", Toast.LENGTH_SHORT).show();
                                    quantity = 0;
                                    price = 0;
                                    quantityTextView.setText("0");
                                    priceTextView.setText("BDT 0");
                                   birdshome.setText("");
                                   parrots.setChecked(false);
                                   lovebirds.setChecked(false);
                                   macaws.setChecked(false);

                                    radioGroup.clearCheck();
                                    ratingBar.setRating(0);
                                }
                            }).show();
                }
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Please Select Bird Size!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void check(CompoundButton buttonView, Boolean isChecked){
        if (isChecked) {
            arr.add(buttonView.getText().toString());
            Log.d("array", String.valueOf(arr));
        } else{
            arr.remove(buttonView.getText().toString());
        }
       birdshome.setText(String.valueOf(arr));
    }
}