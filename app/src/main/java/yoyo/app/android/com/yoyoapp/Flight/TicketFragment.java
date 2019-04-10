package yoyo.app.android.com.yoyoapp.Flight;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.TicketRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Ticket;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class TicketFragment extends Fragment {

    private RecyclerView recyclerView;
    private TicketRecyclerviewAddapter addapter;
    private ArrayList<Ticket> tickets;
    private ApiServiceFlight apiServiceFlight;
    private UserSharedManagerFlight userSharedManager;
    private View view;
    private TextView informTextview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket,container,false);
        init();
        setupRecyclerview();
        getTickets();

        return view;
    }

    // get all user's tickets from server
    private void getTickets() {
        apiServiceFlight.getTickets(null, new ApiServiceFlight.OnTicketsRecieved() {
            @Override
            public void onRecieved(ArrayList<Ticket> ticketArrayList) {

                if (ticketArrayList != null) {
                    if (ticketArrayList.size() != 0)
                    informTextview.setVisibility(View.GONE);
                    addapter.notifyDataSetChanged();
                    tickets.addAll(ticketArrayList);
                }
            }
        });
    }

    private void init() {
        userSharedManager = new UserSharedManagerFlight(getContext());
        apiServiceFlight = new ApiServiceFlight(getContext());
        tickets = new ArrayList<>();
        informTextview = view.findViewById(R.id.tv_tickets_inform);
    }

    // setup ticket's recycler view
    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_reservation);
        addapter = new TicketRecyclerviewAddapter(getContext(),tickets);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(addapter);
    }

    // generate fake data for ticket list
    private ArrayList<Ticket> getFakeData() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Ticket ticket = new Ticket();
            if (i==0)
            {
                ticket.setAdultNum(3);
                ticket.setChildNum(1);
                ticket.setInfantNum(0);
                ticket.setDeparture("TEHRAN");
                ticket.setDestination("SHIRAZ");
                ticket.setDate("2019/02/17");
                ticket.setTotalPrice("1030$");
                ticket.setVoucherNumber("#3334322");
            }
            else if (i==1)
            {
                ticket.setAdultNum(2);
                ticket.setChildNum(0);
                ticket.setInfantNum(0);
                ticket.setDeparture("SHIRAZ");
                ticket.setDestination("ISFAHAN");
                ticket.setDate("2019/01/07");
                ticket.setTotalPrice("510$");
                ticket.setVoucherNumber("#2234322");
            }
            else
            {
                ticket.setAdultNum(1);
                ticket.setChildNum(1);
                ticket.setInfantNum(0);
                ticket.setDeparture("DUBAI");
                ticket.setDestination("TEHRAN");
                ticket.setDate("2019/01/01");
                ticket.setTotalPrice("710$");
                ticket.setVoucherNumber("#1331111");
            }


            tickets.add(ticket);
        }
        return tickets;
    }







}

