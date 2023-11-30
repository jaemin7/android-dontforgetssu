package com.example.android_dontforgetssu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_dontforgetssu.databinding.FragmentLawyerDetailBinding;
import com.example.android_dontforgetssu.databinding.LawyerListItemBinding;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class LawyerDetailFragment extends Fragment {

    private FragmentLawyerDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLawyerDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ArrayList<LawyerListItem> items = new ArrayList<>();
        items.add(new LawyerListItem(R.drawable.apppicture, "뽀로로", "법무법인 세상", "129"));
        items.add(new LawyerListItem(R.drawable.icon_boy, "크롱", "눈동산", "1"));
        items.add(new LawyerListItem(R.drawable.icon_girl, "루피", "법무법인 숲속", "13"));
        items.add(new LawyerListItem(R.drawable.icon_girl, "루피", "법무법인 숲속", "13"));


        binding.lawyerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LawyerAdapter adapter = new LawyerAdapter(items);
        binding.lawyerRecyclerView.setAdapter(adapter);

        return view;
    }

    private static class LawyerViewHolder extends RecyclerView.ViewHolder {
        private final LawyerListItemBinding binding;

        private LawyerViewHolder(LawyerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(LawyerListItem item) {
            binding.lawyerImage.setImageResource(item.getImage());
            binding.lawyerName.setText(item.getName());
            binding.lawyerMemo.setText(item.getMemo());
            binding.lawyerNum.setText(item.getNum());
        }
    }

    private class LawyerAdapter extends RecyclerView.Adapter<LawyerViewHolder> {
        private final ArrayList<LawyerListItem> items;

        public LawyerAdapter(ArrayList<LawyerListItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public LawyerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LawyerListItemBinding itemBinding = LawyerListItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );

            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = binding.lawyerRecyclerView.getChildAdapterPosition(v);
                    LawyerListItem item = items.get(position);
                    Log.d("lawyer", "item clicked:" + item.getName());
                }
            });

            return new LawyerViewHolder(itemBinding);
        }

        public void onBindViewHolder(@NonNull LawyerViewHolder holder, int position) {
            LawyerListItem item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
