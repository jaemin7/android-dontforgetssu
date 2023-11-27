package com.example.android_dontforgetssu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.android_dontforgetssu.databinding.FragmentManagementLendBinding;
import com.example.android_dontforgetssu.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class ManagementLendFragment extends Fragment {
    private FragmentManagementLendBinding binding;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManagementLendBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayList<TransactionListItem> items = new ArrayList<>();
        items.add(new TransactionListItem(R.drawable.apppicture, "임재민", "10000", "가나다라마바사아자", "상환까지 D-3"));
        items.add(new TransactionListItem(R.drawable.icon_boy, "최고은", "200000", "차카타파하", "상환까지 ~~~"));
        items.add(new TransactionListItem(R.drawable.icon_girl, "정상진", "3000000", "응애응애응애응애", "상환까지 ~~~~~"));

        binding.transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.transactionRecyclerView.setAdapter(new TransactionAdapter(items));

        return view;
    }

    private class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TransactionListItemBinding binding;

        private TransactionViewHolder(TransactionListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(TransactionListItem item) {
            binding.transactionImage.setImageResource(item.getImage());
            binding.transactionName.setText(item.getName());
            binding.transactionMoney.setText(item.getMoney());
            binding.transactionMemo.setText(item.getMemo());
            binding.transactionDate.setText(item.getDate());
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder>{

        private ArrayList<TransactionListItem> items;

        public TransactionAdapter(ArrayList<TransactionListItem> items) {
            this.items = items;
        }
        @NonNull
        @Override
        public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = binding.transactionRecyclerView.getChildAdapterPosition(v);
                    TransactionListItem item = items.get(position);
                    Log.d("transaction", "item clicked: " + item.getName());
                }
            });

            return new TransactionViewHolder(TransactionListItemBinding.bind(view));
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
            TransactionListItem item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}