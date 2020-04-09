package com.example.androidviewmodel.ui.Main;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidviewmodel.R;
import com.example.androidviewmodel.data.beans.Hero;
import com.general.base_act_frg.BaseAppCompatActivity;
import com.general.ui.adapters.GenericRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseAppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private GenericRecyclerViewAdapter mAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void configureUI() {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GenericRecyclerViewAdapter(this, new GenericRecyclerViewAdapter.AdapterDrawData() {
            @Override
            public RecyclerView.ViewHolder getView(ViewGroup parent,int viewType) {

                return new HeroVH(MainActivity.this,
                        HeroVH.getView(MainActivity.this, parent));
            }

            @Override
            public void bindView(GenericRecyclerViewAdapter genericRecyclerViewAdapter,
                                 RecyclerView.ViewHolder holder, Object item, int position) {
                ((HeroVH) holder).bindData(
                        genericRecyclerViewAdapter.getItem(position), position);
            }
        });
        recyclerview.setAdapter(mAdapter);



        HeroesViewModel model = new ViewModelProvider(this).get(HeroesViewModel.class);

        model.getHeroes(this,true).observe(this, new Observer<List<Hero>>() {
            @Override
            public void onChanged(@Nullable List<Hero> heroList) {
                if(heroList!=null)
                {
                    mAdapter.setAll(heroList);
                }
            }
        });
    }
}
