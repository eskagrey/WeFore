package com.ka.saiful.wefore.Weather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ka.saiful.wefore.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Weather> weathers;

    public Adapter(List<Weather> weathers) {
        this.weathers = weathers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = weathers.get(position);
        holder.tvSpeed.setText(weather.getMain() + "- " + weather.getDescription());
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeed;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSpeed = itemView.findViewById(R.id.tv_cuaca);
        }
    }

    public void addAll(List<Weather> speeds) {
        this.weathers = speeds;
        notifyDataSetChanged();
    }
}
