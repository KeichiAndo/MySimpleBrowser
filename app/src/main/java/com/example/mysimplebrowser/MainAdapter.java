package com.example.mysimplebrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mysimplebrowser.database.LinkEntry;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<LinkEntry> mLinkEntries;

    final private ItemClickListener mItemClickListener;
    private Context mContext;

    public MainAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view_main_layout, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        LinkEntry linkEntry = mLinkEntries.get(position);
        String linkName = linkEntry.getLinkName();

        holder.linkButton.setText(linkName);
    }

    @Override
    public int getItemCount() {
        if (mLinkEntries == null) {
            return 0;
        }
        return mLinkEntries.size();
    }

    public List<LinkEntry> getLink() { return mLinkEntries; }

    public void setLinks (List<LinkEntry> linkEntries) {
        mLinkEntries = linkEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button linkButton;

        public MainViewHolder(View itemView) {
            super(itemView);

            linkButton = itemView.findViewById(R.id.btn_link);
            linkButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mLinkEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
