package com.example.twig;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Profile2Fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<FriendsModel> list;
    Toolbar toolbar;

    public Profile2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile2, container, false);

        ((TextView) view.findViewById(R.id.textView2)).setText((User.getDisplayName() + " __ " + User.getCurrentUser().getEmail()));
        recyclerView = view.findViewById(R.id.friendsRV);
        list = new ArrayList<>();
        list.add(new FriendsModel(R.drawable.profile));

        list.add(new FriendsModel(R.drawable.nature_dordogne));
        list.add(new FriendsModel(R.drawable.nature));
        list.add(new FriendsModel(R.drawable.cover));
        list.add(new FriendsModel(R.drawable.deaf));
        list.add(new FriendsModel(R.drawable.original));
        list.add(new FriendsModel(R.drawable.profile1));
        list.add(new FriendsModel(R.drawable.nature1));

        FriendsAdapter adapter = new FriendsAdapter(list,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.logout).setOnClickListener(v -> {
            User.logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

//        toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.notification).setVisible(true);
        menu.findItem(R.id. profile).setVisible(true);
        menu.findItem(R.id. setting).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }*/
}