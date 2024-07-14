package com.example.kucingkuapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kucingkuapp.Activity.Database.Reservation;
import com.example.kucingkuapp.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<Reservation> reservationList;

    public HistoryAdapter(Context context, List<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvReservationId, tvUserName, tvPlaceName, tvReservationDate, tvStartTime, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReservationId = itemView.findViewById(R.id.tvReservationId);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvReservationDate = itemView.findViewById(R.id.tvReservationDate);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }

        public void bind(Reservation reservation) {
            tvReservationId.setText(reservation.getReservationId());
            tvUserName.setText(reservation.getUserName());
            tvPlaceName.setText(reservation.getPlaceName());
            tvReservationDate.setText(reservation.getReservationDate());
            tvStartTime.setText(reservation.getStartTime());
            tvStatus.setText(reservation.getStatus());
        }
    }
}
