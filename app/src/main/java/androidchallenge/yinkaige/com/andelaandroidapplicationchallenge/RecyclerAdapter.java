package androidchallenge.yinkaige.com.andelaandroidapplicationchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yinka Ige on 31/10/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    ArrayList<Currency> currencyArrayList;
    TypedArray imageArray;
    Context context;

    RecyclerAdapter(ArrayList<Currency> arrayList, TypedArray images) {
        currencyArrayList = arrayList;
        imageArray = images;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView namesTV;
        public TextView abvTv;
        public ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            namesTV = (TextView)itemView.findViewById(R.id.namesTv);
            abvTv = (TextView)itemView.findViewById(R.id.abvTv);
            imageView = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    @Override
    public RecyclerAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.Holder holder, final int position) {
        final String name = currencyArrayList.get(position).getName();
        final String abv = currencyArrayList.get(position).getAbv();
        holder.imageView.setImageResource(imageArray.getResourceId(position, -1));
        holder.namesTV.setText(name);
        holder.abvTv.setText(abv);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CurrencyPage.class);
                i.putExtra("abv", abv);
                i.putExtra("name", name);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyArrayList.size();
    }
}
