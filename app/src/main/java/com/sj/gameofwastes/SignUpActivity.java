package com.sj.gameofwastes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SignUpActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView addressview;
    private Button btnSubmit;
    private EditText nameET;
    private String uniqueId;

    private static final String uuid = "UUID";
    private static final String name = "Name";
    private static final String address = "address";
    private static final String PHONE_KEY = "Phone";

    FirebaseFirestore db;
    String COLLECTION;
    Location mLocation;

    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        addListenerOnButton();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        nameET = (EditText) findViewById(R.id.namebox);

        final Button button = findViewById(R.id.addressbox);
        addressview = findViewById(R.id.addressdisplay);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {


                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    mLocation = location;
                                    geocoder = new Geocoder(SignUpActivity.this, Locale.getDefault());
                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        address += addresses.get(0).getLocality();
                                        addressview.setText(address);

                                    }
                                    catch (Exception e){}
                                }
                            }
                        });
            }
        });


        db = FirebaseFirestore.getInstance();


    }

    private void addNewUser(String typeOfUser) {
        Map<String, Object> newContact = new HashMap<>();
        Log.e("asd", "start all done");
        uniqueId = UUID.randomUUID().toString();
        newContact.put(uuid, uniqueId);
        newContact.put(name, nameET.getText().toString());
        newContact.put(address, addressview.getText().toString());
        newContact.put(PHONE_KEY, "080-0808-009");
        Log.e("asd", "mid done");
        newContact.put("Latitude", mLocation.getLatitude());
        newContact.put("Longitude", mLocation.getLongitude());

        String s = "";
        if (typeOfUser.equals("Customer")) {
            COLLECTION = "customers";
        }
        else {
            COLLECTION = "storekeepers";
        }
        Log.e("asd", COLLECTION + " all done "+newContact);
        db.collection(COLLECTION).document(uniqueId).set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "User Registered",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        btnSubmit = (Button) findViewById(R.id.submitbox);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                Log.e("asd", "here i am done");
                Toast.makeText(SignUpActivity.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();

                addNewUser(radioButton.getText().toString());

                Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
                startActivity(intent);

            }

        });





    }
}
