package com.example.restaurantsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentMenu extends Fragment {
    MenuAdapter menuAdapter;

    List<String> menuTitleList;
    List<String> menuImagesList;

    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu,container,false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        listView =  view.findViewById(R.id.fragmentmenu_listView);
        menuTitleList =new ArrayList<>();
        menuImagesList =new ArrayList<>();

        //retrieve menu information from firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Menu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuTitleList.clear();
                menuImagesList.clear();

                for(DataSnapshot menuDataSnap : snapshot.getChildren()){
                    MyMenuModel myMenuModel = menuDataSnap.getValue(MyMenuModel.class);

                    String title= myMenuModel.getTitle();
                    menuTitleList.add(title);

                    String image= myMenuModel.getImage();
                    menuImagesList.add(image);

                }
                menuAdapter= new MenuAdapter(getActivity(),menuTitleList,menuImagesList);
                listView.setAdapter(menuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}