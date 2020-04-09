package com.example.androidviewmodel.ui.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidviewmodel.R;
import com.example.androidviewmodel.data.beans.Hero;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroVH extends RecyclerView.ViewHolder {


    View itemView;
     Context context;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;



    public HeroVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Object item, final int position) {

        final Hero hero = (Hero) item;
        Glide.with(context)
                .load(hero.getImageurl())
                .into(imageView);

        textView.setText(hero.getName());

    }

    public static View getView(Context context, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.recyclerview_layout, viewGroup, false);
    }


}


