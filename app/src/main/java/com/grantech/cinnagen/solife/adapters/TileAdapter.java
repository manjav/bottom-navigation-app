package com.grantech.cinnagen.solife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.grantech.cinnagen.solife.R;

import java.util.List;
import java.util.Objects;

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.ViewHolder> {
    private final Context context;
    private final List<Boolean> data;
    private final List<Integer> captions;

    public TileAdapter(Context context, Fragment fragment, List<Boolean> data, List<Integer> captions) {
//        this.itemSize = (int) (context.getResources().getDisplayMetrics().density * 40);
        this.context = context;
        this.data = data;
        this.captions = captions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ImageView hideView;
        private final ImageView imageView;
        private final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            View layout = itemView.findViewById(R.id.tile_layout);
            imageView = layout.findViewById(R.id.tile_icon);
            textView = layout.findViewById(R.id.tile_text);
            hideView = layout.findViewById(R.id.hide_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if( position > 5 )
                return;
            data.set(position, !data.get(position));
            update(position);
        }

        void update(int position) {
            if( position > 5 )
                return;
            int id = context.getResources().getIdentifier("settings_" + position, "mipmap", Objects.requireNonNull(context).getPackageName());
            imageView.setImageResource(id);
            imageView.setAlpha(data.get(position) ? 1.0f : 0.6f);
            hideView.setVisibility(data.get(position) ? View.INVISIBLE : View.VISIBLE);
            textView.setText(captions.get(position));
        }
    }

    @NonNull
    @Override
    public TileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tile_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TileAdapter.ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}