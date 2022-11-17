package com.varsity.dgmdashboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowNotesBinding;
import com.varsity.dgmdashboard.model.GetDGMNoteListResponse;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.DataViewHolder> {
    private ArrayList<GetDGMNoteListResponse> list;

    public NotesListAdapter(ArrayList<GetDGMNoteListResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowNotesBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_notes, parent, false);
        return new NotesListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvNotes.setShowingChar(100);
        holder.mBinding.tvNotes.setShowingLine(2);

        holder.mBinding.tvSubject.setText("" + list.get(position).getSubject());
        holder.mBinding.tvNotes.setText("" + list.get(position).getNotes());
        holder.mBinding.tvDate.setText("" + list.get(position).getCreatedDate());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowNotesBinding mBinding;

        public DataViewHolder(RowNotesBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }
}
