package com.example.qr_book;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class newBookFragment extends MyFragment {
    public newBookFragment() {
        // Required empty public constructor
    }
    Button subirLibroBtn;
    EditText nameBook, yearBook, autorBook;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameBook = view.findViewById(R.id.nameBookEditText);
        yearBook= view.findViewById(R.id.yearBookEditText);
        autorBook = view.findViewById(R.id.autorBookEditText);

        subirLibroBtn = view.findViewById(R.id.subirBookBtn);
        subirLibroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicar();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_book, container, false);
    }

    private void publicar() {
        String nameBookS = nameBook.getText().toString();
        String yearBookS = yearBook.getText().toString();
        String autorBookS = autorBook.getText().toString();

        if(TextUtils.isEmpty(nameBookS)){
            nameBook.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(yearBookS)){
            yearBook.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(autorBookS)){
            autorBook.setError("Required");
            return;
        }

        subirLibroBtn.setEnabled(false);

        guardarEnFirestore(nameBookS, yearBookS, autorBookS);
    }
    private void guardarEnFirestore(String name, String year, String autor) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Book book = new Book(user.getUid(), name, year, autor);

        FirebaseFirestore.getInstance().collection("books")
                .add(book)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        navController.popBackStack();
                    }
                });
    }
}
