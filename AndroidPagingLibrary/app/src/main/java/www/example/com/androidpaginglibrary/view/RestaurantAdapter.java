package www.example.com.androidpaginglibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import www.example.com.androidpaginglibrary.R;
import www.example.com.androidpaginglibrary.data.retrofit.Restaurant;

public class RestaurantAdapter extends PagedListAdapter<Restaurant, RestaurantAdapter.ViewHolder> {

    private Context context;
    private static DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Restaurant>() {
                @Override
                public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
                    return oldItem.getRestaurantDetails().getName().equals(newItem.getRestaurantDetails().getName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
                    return oldItem.getRestaurantDetails().getId().equals(newItem.getRestaurantDetails().getId());
                }
            };

    public RestaurantAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(getItem(position)!=null && getItem(position).getRestaurantDetails()!=null) {
            holder.name.setText(getItem(position).getRestaurantDetails().getName());
            Glide.with(context).load(getItem(position).getRestaurantDetails().getThumbnail()).placeholder(R.drawable.ic_broken_image).into(holder.thumbnail);
            holder.rating.setText(getItem(position).getRestaurantDetails().getUserRating().getRating());
            holder.rating.setTextColor(Color.parseColor("#"+getItem(position).getRestaurantDetails().getUserRating().getRatingColorRGBA()));
            holder.address.setText(getItem(position).getRestaurantDetails().getRestaurantLocation().getAddress());
            holder.id.setText("(id: "+getItem(position).getRestaurantDetails().getId()+")");
            holder.numberOfVoters.setText("("+getItem(position).getRestaurantDetails().getUserRating().getVotes()+")");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, rating, address, id, numberOfVoters;
        private ImageView thumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restaurant_name);
            rating = itemView.findViewById(R.id.restaurant_rating);
            address = itemView.findViewById(R.id.restaurant_address);
            thumbnail = itemView.findViewById(R.id.restaurant_thumbnail);
            id = itemView.findViewById(R.id.restaurant_id);
            numberOfVoters = itemView.findViewById(R.id.restaurant_rating_user_number);
        }
    }
}
