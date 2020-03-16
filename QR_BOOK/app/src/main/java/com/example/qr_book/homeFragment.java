package com.example.qr_book;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends MyFragment {


    public homeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.gotoNewBookFragmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.newBookFragment);
            }
        });

        RecyclerView booksRecyclerView = view.findViewById(R.id.booksRecyclerView);

        Query query = FirebaseFirestore.getInstance().collection("books").limit(50);

        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .setLifecycleOwner(this)
                .build();

        booksRecyclerView.setAdapter(new BookAdapter(options));


    }

    class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.BookViewHolder> {
        public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {super(options);}

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_book, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull final Book book) {
            holder.nameTextView.setText(book.name);
            holder.yearTextView.setText(book.year);
            holder.autorTextView.setText(book.author);
        }

        class BookViewHolder extends RecyclerView.ViewHolder {
            TextView autorTextView, nameTextView, yearTextView;

            BookViewHolder(@NonNull View itemView) {
                super(itemView);

                autorTextView= itemView.findViewById(R.id.autorBookTextView);
                nameTextView = itemView.findViewById(R.id.nameBookTextView);
                yearTextView = itemView.findViewById(R.id.yearBookTextView);
            }
        }
    }
}
