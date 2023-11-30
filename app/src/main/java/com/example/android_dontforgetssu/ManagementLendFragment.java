package com.example.android_dontforgetssu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_dontforgetssu.databinding.FragmentManagementLendBinding;
import com.example.android_dontforgetssu.databinding.TransactionListItemBinding;
import java.util.ArrayList;

public class ManagementLendFragment extends Fragment {
    private FragmentManagementLendBinding binding;

    private ImageButton darrow1;
    private ImageButton rarrow1;
    private ImageButton darrow2;
    private ImageButton rarrow2;
    private ImageButton darrow3;
    private ImageButton rarrow3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManagementLendBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        darrow1 = view.findViewById(R.id.darrow);
        rarrow1 = view.findViewById(R.id.rarrow);
        darrow2 = view.findViewById(R.id.darrow2);
        rarrow2 = view.findViewById(R.id.rarrow2);
        darrow3 = view.findViewById(R.id.darrow3);
        rarrow3 = view.findViewById(R.id.rarrow3);

        // 처음에는 모든 rarrow 버튼을 숨김(GONE으로 설정)
        rarrow1.setVisibility(View.GONE);
        rarrow2.setVisibility(View.GONE);
        rarrow3.setVisibility(View.GONE);

        setButtonListeners(darrow1, rarrow1);
        setButtonListeners(darrow2, rarrow2);
        setButtonListeners(darrow3, rarrow3);

        ArrayList<TransactionListItem> items = new ArrayList<>();
        items.add(new TransactionListItem(R.drawable.apppicture, "임재민", "10000", "가나다라마바사아자", "상환까지 D-3"));
        items.add(new TransactionListItem(R.drawable.icon_boy, "최고은", "200000", "차카타파하", "상환까지 ~~~"));
        items.add(new TransactionListItem(R.drawable.icon_girl, "정상진", "3000000", "응애응애응애응애", "상환까지 ~~~~~"));

        binding.transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.transactionRecyclerView.setAdapter(new TransactionAdapter(items));

        return view;
    }

    private void setButtonListeners(final ImageButton darrow, final ImageButton rarrow) {
        darrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // darrow를 숨기기 (GONE으로 설정)
                darrow.setVisibility(View.GONE);

                // rarrow를 보이게 하기 (VISIBLE로 설정)
                rarrow.setVisibility(View.VISIBLE);
            }
        });

        // rarrow를 클릭했을 때 darrow를 다시 보이게 하기
        rarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rarrow를 숨기기 (GONE으로 설정)
                rarrow.setVisibility(View.GONE);

                // darrow를 보이게 하기 (VISIBLE로 설정)
                darrow.setVisibility(View.VISIBLE);
            }
        });
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

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
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
                    // Handle item click
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