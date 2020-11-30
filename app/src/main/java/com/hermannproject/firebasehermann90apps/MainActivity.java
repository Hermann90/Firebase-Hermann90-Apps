package com.hermannproject.firebasehermann90apps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText editTextDescription;
    ProgressDialog myProgressDialog;
//    private ProgressBar progressBar;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_test_title);
        editTextDescription = findViewById(R.id.edit_test_descript);

//        progressBar = findViewById(R.id.progress_bar);


        Button saveButton = findViewById(R.id.saveNode);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "tets");
                saveNode(view);
            }
        });
    }

    public void saveNode(View v){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
//        progressBar.setVisibility(View.VISIBLE);
        myProgressDialog = ProgressDialog.show(context,
                "", "Loading ...", true);
        Map<String,Object>note = new HashMap<>();
        note.put(KEY_TITLE,title);
        note.put(KEY_DESCRIPTION,description);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.e(TAG, "Here ===> "+db.getApp().getOptions().getProjectId());

//        try {
            db.collection("notebook").document("my_first_document").set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "onSuccess");
                            //progressBar.setVisibility(View.GONE);
                            myProgressDialog.dismiss();
                            Toast.makeText(context,"Save Node",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure");
//                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                            Log.e(TAG,e.toString());
                        }
                    });
//        }catch (Exception e){
//            Log.e("error catch", e.getMessage());
//        }

    }
}