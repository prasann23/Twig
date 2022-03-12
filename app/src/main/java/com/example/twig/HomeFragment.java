package com.example.twig;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView storyRV, dashboardRV;

    ArrayList<DashboardModel> dashboardList;
    ImageView addStory;

    private final loadPostsCallback postsCallback = new loadPostsCallback() {
        @Override
        public void loadPosts(ArrayList<Post> posts) {
            dashboardList = new ArrayList<>();

            posts.forEach(post -> {
                StorageUtil.getInstance().child(StorageUtil.IMAGES_DB + "/" + post.imageUuid).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String imageUrl = task.getResult().toString();
                            dashboardList.add(new DashboardModel(imageUrl, post.creatorName, Integer.toString(post.likesCount)));

                            DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList,getContext());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                            dashboardRV.setLayoutManager(linearLayoutManager);
                            dashboardRV.addItemDecoration(new DividerItemDecoration(dashboardRV.getContext(), DividerItemDecoration.VERTICAL));
                            dashboardRV.setNestedScrollingEnabled(false);
                            dashboardRV.setAdapter(dashboardAdapter);
                        }
                    }
                });
            });
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //Dashboard Recycler View
        dashboardRV = view.findViewById(R.id.dashboardRV);
        DatabaseUtil.getAllPost(getActivity().getApplicationContext(), postsCallback);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface loadPostsCallback {
        void loadPosts(ArrayList<Post> posts);
    }
}
