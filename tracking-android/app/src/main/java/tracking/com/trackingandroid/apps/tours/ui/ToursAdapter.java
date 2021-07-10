package tracking.com.trackingandroid.apps.tours.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tracking.com.trackingandroid.R;
import tracking.com.trackingandroid.data.model.Tour;

import static tracking.com.trackingandroid.util.CommonUtils.COMPLETE_DATE_FORMAT;
import static tracking.com.trackingandroid.util.CommonUtils.SIMPLE_DATE_FORMAT;
import static tracking.com.trackingandroid.util.CommonUtils.TOURS_MOVIE_DETAIL;

public class ToursAdapter extends RecyclerView.Adapter<ToursAdapter.CustomViewHolder> {
    private List<Tour> tourList;
    private Context context;
    private DateFormat completeDateFormat = new SimpleDateFormat(COMPLETE_DATE_FORMAT);
    private DateFormat simpleDateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
    private static final String TAG = ToursAdapter.class.getSimpleName();

    public ToursAdapter(List<Tour> tourList, Context context) {
        this.tourList = tourList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_tour, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
        Tour tour = tourList.get(position);
        DateTime dateStart;
        DateTime dateFinish;
        try {
            dateStart = new DateTime(completeDateFormat.parse(tour.getTimeStart()));
            dateFinish = new DateTime(completeDateFormat.parse(tour.getTimeFinish()));

            int hh = Hours.hoursBetween(dateStart, dateFinish).getHours() % 24;
            int mm = Minutes.minutesBetween(dateStart, dateFinish).getMinutes() % 60;
            int ss = Seconds.secondsBetween(dateStart, dateFinish).getSeconds() % 60;

            customViewHolder.textId.setText(tour.getTourId().toString());
            customViewHolder.textTimeTravel.setText(String.format("%s:%s:%s", hh, mm, ss));
            customViewHolder.textDate.setText(simpleDateFormat.format(completeDateFormat.parse(tour.getTimeStart())));

            customViewHolder.cardTour.setOnClickListener(v -> {
                Intent intent =new Intent(context, TourDetailActivity.class);
                intent.putExtra(TOURS_MOVIE_DETAIL, tour);
                v.getContext().startActivity(intent);
            });
        } catch (ParseException e) {
            Log.i(TAG, "PARSE_ERROR: " + e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public void updateToursAdapter(List<Tour> tourList) {
        this.tourList = tourList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_id) TextView textId;
        @BindView(R.id.text_time_travel) TextView textTimeTravel;
        @BindView(R.id.text_date) TextView textDate;
        @BindView(R.id.card_tour) CardView cardTour;

        Unbinder unbinder;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
