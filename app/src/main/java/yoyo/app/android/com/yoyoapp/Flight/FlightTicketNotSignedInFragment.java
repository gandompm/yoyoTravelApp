package yoyo.app.android.com.yoyoapp.Flight;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Ticket;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class FlightTicketNotSignedInFragment extends BaseFragment {

    private ApiServiceFlight apiServiceFlight;
    private UserSharedManagerFlight userSharedManager;
    private EditText emailEditText, voucherNumberEditText;
    private Button sendButton, downloadTicketButton;
    private View ticketView;
    private View view;
    private TextView signInTextview;
    private ProgressBar progressBar;
    private CardView cardView;
    private TextView adultnumTextview,childnumTextview,infantnumTextview,priceTextview
                      ,voucherNumberTextview,departureTextview,destinationTextview,dateTextview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_not_signed_in,container,false);

        init();
        setupSendButton();
        setupSignIn();

        return view;
    }

    private void setupSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!voucherNumberEditText.getText().toString().equals("") &&
                        !emailEditText.getText().toString().equals(""))
                getTicket();
                else
                    Toasty.error(getContext(),getResources().getString(R.string.please_complete_all_fields)).show();
            }
        });
    }

    private void setupSignIn() {
        signInTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUpSignInActivity.class));
            }
        });
    }

    private void getTicket() {
        progressBar.setVisibility(View.VISIBLE);
        String urlParameter ="";
        urlParameter += "?email=" + emailEditText.getText() + "&voucher_number=" + voucherNumberEditText.getText();
        apiServiceFlight.getTickets(urlParameter, new ApiServiceFlight.OnTicketsRecieved() {
            @Override
            public void onRecieved(ArrayList<Ticket> tickets) {
                progressBar.setVisibility(View.GONE);

                if (tickets != null && tickets.size()!= 0)
                {
                    cardView.setVisibility(View.GONE);
                    ticketView.setVisibility(View.VISIBLE);
                    Ticket ticket = tickets.get(0);
                    adultnumTextview.setText(String.valueOf(ticket.getAdultNum()));
                    childnumTextview.setText(String.valueOf(ticket.getChildNum()));
                    infantnumTextview.setText(String.valueOf(ticket.getInfantNum()));
                    priceTextview.setText(ticket.getTotalPrice());
                    voucherNumberTextview.setText(ticket.getVoucherNumber());
                    destinationTextview.setText(ticket.getDestination());
                    departureTextview.setText(ticket.getDeparture());
                    dateTextview.setText(ticket.getDate());

                    downloadTicketButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ticket.getDownloadUrl() != null)
                            {
                                downloadTicket(ticket.getDownloadUrl());
                            }
                        }
                    });
                }
                else
                    Toasty.error(getContext(),"There is not available ticket for you!").show();
            }
        });
    }

    private void downloadTicket(String urlString) {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(urlString);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }

    private void init() {
        userSharedManager = new UserSharedManagerFlight(getContext());
        apiServiceFlight = new ApiServiceFlight(getContext());
        signInTextview = view.findViewById(R.id.tv_ticket_sign_in);
        progressBar = view.findViewById(R.id.progressbar);
        emailEditText = view.findViewById(R.id.et_ticket_email);
        voucherNumberEditText = view.findViewById(R.id.et_ticket_voucher);
        sendButton = view.findViewById(R.id.button_ticket_send);
        ticketView = view.findViewById(R.id.ticket_ticket_not_signed_in);
        adultnumTextview = view.findViewById(R.id.tv_itemticket_adultnum);
        childnumTextview = view.findViewById(R.id.tv_itemticket_childnum);
        infantnumTextview = view.findViewById(R.id.tv_itemticket_infantnum);
        priceTextview = view.findViewById(R.id.tv_itemticket_price);
        voucherNumberTextview = view.findViewById(R.id.tv_itemticket_voucher);
        departureTextview = view.findViewById(R.id.tv_itemticket_departure);
        destinationTextview = view.findViewById(R.id.tv_itemticket_destination);
        dateTextview = view.findViewById(R.id.tv_itemticket_date);
        downloadTicketButton = view.findViewById(R.id.button_itemticket_download);
        cardView = view.findViewById(R.id.cv_ticket);
    }
}

