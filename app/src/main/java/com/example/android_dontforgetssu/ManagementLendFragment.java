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

import com.example.android_dontforgetssu.databinding.FragmentManagementLendBinding;
import com.example.android_dontforgetssu.databinding.TransactionListItemBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ManagementLendFragment extends Fragment {
    private FragmentManagementLendBinding binding;
    private LendInfoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManagementLendBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new LendInfoAdapter();
        fetchLendInfoFromFirebase();

        binding.transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.transactionRecyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchLendInfoFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            CollectionReference lendInfoCollection = db.collection("Member").document(uid).collection("빌려준 정보");

            lendInfoCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<LendInfo> lendInfoList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Firebase에서 가져온 데이터를 LendInfo 객체로 변환하여 리스트에 추가
                            LendInfo lendInfo = document.toObject(LendInfo.class);
                            lendInfoList.add(lendInfo);
                            Log.d("FirestoreData", "Name: " + lendInfo.getBorrowerName());
                        }

                        Collections.sort(lendInfoList, new Comparator<LendInfo>() {
                            @Override
                            public int compare(LendInfo lendInfo1, LendInfo lendInfo2) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date1 = dateFormat.parse(lendInfo1.getLendAcceptDate());
                                    Date date2 = dateFormat.parse(lendInfo2.getLendAcceptDate());
                                    return date1.compareTo(date2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });

                        adapter.setLendInfoList(lendInfoList);
                    } else {
                        Log.w("ManagementLendFragment", "Error getting documents.", task.getException());
                    }
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private class LendInfoViewHolder extends RecyclerView.ViewHolder {
        private TransactionListItemBinding binding;

        private LendInfoViewHolder(TransactionListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(LendInfo lendInfo) {

            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(new Date());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date lend_accept_date = null;
            try {
                lend_accept_date = dateFormat.parse((lendInfo.getLendAcceptDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }

            long daysDiff = (lend_accept_date.getTime() - currentDate.getTimeInMillis())/(24 * 60 * 60 * 1000);

            binding.transactionImage.setImageResource(R.drawable.icon_boy);
            binding.transactionName.setText(lendInfo.getBorrowerName());
            binding.transactionMoney.setText("빌린 금액 : " + lendInfo.getCalculatedMoney() + "원");
            binding.transactionMemo.setText("메모 : " + lendInfo.getMemo());
            if (daysDiff > 0) {
                binding.transactionDate.setText("상환까지 D-"+daysDiff);
            } else if (daysDiff == 0) {
                binding.transactionDate.setText("상환일 D-"+daysDiff);
            } else {
                binding.transactionDate.setText("연체중 D+"+(-daysDiff));
            }
        }
    }

    private class LendInfoAdapter extends RecyclerView.Adapter<LendInfoViewHolder>{

        private ArrayList<LendInfo> lendInfoList = new ArrayList<>();

        public void setLendInfoList(ArrayList<LendInfo> lendInfoList) {
            this.lendInfoList = lendInfoList;
            notifyDataSetChanged();

            ViewGroup.LayoutParams layoutParams = binding.transactionRecyclerView.getLayoutParams();
            layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            binding.transactionRecyclerView.setLayoutParams(layoutParams);
        }
        @NonNull
        @Override
        public LendInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = binding.transactionRecyclerView.getChildAdapterPosition(v);
                    LendInfo lendInfo = lendInfoList.get(position);
                    Log.d("transaction", "item clicked: " + lendInfo.getBorrowerName());

                    // 클릭된 아이템의 정보를 가지고 DetailInformationActivity로 이동하는 코드
                    Intent intent = new Intent(v.getContext(), LendManagementDetailInfo.class);
                    intent.putExtra("lendInfo", lendInfo);
                    v.getContext().startActivity(intent);
                }
            });

            return new LendInfoViewHolder(TransactionListItemBinding.bind(view));
        }

        @Override
        public void onBindViewHolder(@NonNull LendInfoViewHolder holder, int position) {
            LendInfo lendInfo = lendInfoList.get(position);
            holder.bind(lendInfo);
        }

        @Override
        public int getItemCount() {
            return lendInfoList.size();
        }
    }
}