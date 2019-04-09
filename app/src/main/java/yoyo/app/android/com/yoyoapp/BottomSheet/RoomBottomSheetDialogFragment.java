package yoyo.app.android.com.yoyoapp.BottomSheet;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.dagang.library.GradientButton;
import yoyo.app.android.com.yoyoapp.Addapters.RoomsRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.DataModels.Room;
import yoyo.app.android.com.yoyoapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RoomBottomSheetDialogFragment extends BottomSheetDialogFragment {


    private GradientButton gradientButton;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_sheet_room,container,false);


        init();
        setupRecyclerview();



        return view;
    }

    private void setupRecyclerview() {
        RecyclerView recyclerView = view.findViewById(R.id.rv_room_hotel);
        RoomsRecyclerviewAddaptor roomsRecyclerviewAddaptor = new RoomsRecyclerviewAddaptor(getContext(), getFakeRooms(), new RoomsRecyclerviewAddaptor.OnPlusClickedListner() {
            @Override
            public void onPlusClicked(boolean plusClicked) {
                gradientButton.setVisibility(View.VISIBLE);
                if (plusClicked)
                {
                    Toast.makeText(getContext(), "apear", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "disapear", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(roomsRecyclerviewAddaptor);
    }


    private void init() {
        gradientButton = view.findViewById(R.id.button_room_book);
    }

    public static RoomBottomSheetDialogFragment newInstance()
    {
        return new RoomBottomSheetDialogFragment();
    }

    public ArrayList<Room> getFakeRooms()
    {
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Room room = new Room();
            room.setType("Twin Room  (non smoking) High Floor - Main Tower");
            rooms.add(room);
        }
        return rooms;
    }




}
