package com.sj.gameofwastes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class scan_info extends AppCompatActivity {

TextView cust_name;
EditText weight;

Button send;
    String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_info);
        send = (Button) findViewById(R.id.add);

        final String uuid= getIntent().getStringExtra("CONTENT");
        displayName(uuid);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                sendtofirebase(uuid);
            }
        });
    }
    public void displayName(String uuid){
//        DocumentReference user = db.collection("customers").document(uuid);
//        user.get().addOnCompleteListener(new OnCompleteListener <DocumentSnapshot> () {
//            @Override
//            public void onComplete(@NonNull Task < DocumentSnapshot > task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot doc = task.getResult();
//                    StringBuilder fields = new StringBuilder("");
//                    String cust_name=doc.get("Name"));
//
//
        String cust_name="Shreya";
                   TextView cust_name_tv = (TextView)findViewById(R.id.Cust_name);
                    cust_name_tv.setText(cust_name);
                }
           // }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                    }
//                });

        void sendtofirebase(String uuid){

            String wei = weight.getText().toString();

            double weig=Double.parseDouble(wei);
            weig=weig*10;
//            db.collection("customers")
//                    .document("uuid")
//                    .update({
//                            "tokens" double weig1 = weig;:weig
//                        });
        }

}