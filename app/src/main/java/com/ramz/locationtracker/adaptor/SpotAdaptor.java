package com.ramz.locationtracker.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramz.locationtracker.R;

import java.util.ArrayList;


/**
 * Created by munnaz on 31/3/17.
 */

public class SpotAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

     ArrayList<String>mData;
    Context mContext;
    public SpotAdaptor(ArrayList<String>mData, Context mContext) {
        this.mContext=mContext;
      this.mData=mData;

    }




    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView companyNameTv;


        public HomeViewHolder(View view) {
            super(view);
         companyNameTv=(TextView)view.findViewById(R.id.spot_txt);


        }
    }


//    public MoviesAdapter(List<Movie> moviesList) {
//        this.moviesList = moviesList;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spot_row, parent, false);
            return new HomeViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mainholder, final int position) {

        if (mainholder instanceof HomeViewHolder) {

            final HomeViewHolder holder = (HomeViewHolder) mainholder;
            holder.companyNameTv.setText("Route No "+mData.get(position));

            }
//            holder.companyNameTv.setText();

        }




    @Override
    public int getItemCount() {
        return mData.size();
    }



}