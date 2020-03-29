package com.jonathan.cocktailapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathan.cocktailapi.GlideApp;
import com.jonathan.cocktailapi.R;
import com.jonathan.cocktailapi.entity.Drink;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 1772004 - Jonathan Bernad
 */
public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private ArrayList<Drink> drinks;
    private Context context;
    private ItemClickListener clickListener;


    public DrinkAdapter(Context c, ItemClickListener clickListener) {
        this.clickListener = clickListener;
        drinks = new ArrayList<>();
        context = c;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_item, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinks.get(position);
        holder.txtDrinkName.setText(drink.getName());

        GlideApp.with(context)
                .load(drink.getThumbnail())
                .into(holder.ivDrinkThumbnail);


        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(drink);
        });
    }

    public void changeData(ArrayList<Drink> source) {
        drinks.clear();
        drinks.addAll(source);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_drink_name)
        TextView txtDrinkName;

        @BindView(R.id.iv_drink_thumbnail)
        ImageView ivDrinkThumbnail;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClickListener {

        void itemClicked(Drink drink);
    }
}
