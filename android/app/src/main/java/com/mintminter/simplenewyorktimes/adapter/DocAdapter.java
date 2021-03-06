package com.mintminter.simplenewyorktimes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mintminter.simplenewyorktimes.R;
import com.mintminter.simplenewyorktimes.activity.DetailActivity;
import com.mintminter.simplenewyorktimes.interfaces.ContinueCallBack;
import com.mintminter.simplenewyorktimes.models.NYTDoc;
import com.mintminter.simplenewyorktimes.util.Common;

import java.util.ArrayList;

/**
 * Created by Irene on 9/22/17.
 */

public class DocAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEWTYPE_DOC = 0;
    private static final int VIEWTYPE_HIGHSCOREDOC = 1;

    private Context mContext;
    private ContinueCallBack mCallback;

    private ArrayList<NYTDoc> mDocList = new ArrayList<>();
    public DocAdapter(Context context, ContinueCallBack callback){
        mCallback = callback;
        mContext = context;
    }

    public void bind(ArrayList<NYTDoc> docs, boolean bAppend){
        if(bAppend) {
            mDocList.addAll(docs);
        }else{
            mDocList = docs;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        NYTDoc doc = mDocList.get(position);
        if(doc.isHighScoreDoc() && !TextUtils.isEmpty(doc.getWideUrl())){
            return VIEWTYPE_HIGHSCOREDOC;
        }else{
            return VIEWTYPE_DOC;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEWTYPE_HIGHSCOREDOC) {
            return new HighScoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highscoredoc, parent, false));
        }else{
            return new DocViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doc, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == VIEWTYPE_HIGHSCOREDOC){
            ((HighScoreViewHolder) holder).bind(position);
        }else {
            ((DocViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDocList.size();
    }

    class DocViewHolder extends RecyclerView.ViewHolder{
        private ImageView mPoster;
        private TextView mTitle;
        private TextView mContent;
        private View mItemView;

        public DocViewHolder(View itemView) {
            super(itemView);
            mPoster = (ImageView) itemView.findViewById(R.id.item_doc_poster);
            mTitle = (TextView) itemView.findViewById(R.id.item_doc_title);
            mContent = (TextView) itemView.findViewById(R.id.item_doc_content);
            mItemView = itemView;
        }

        public void bind(int position){
            final NYTDoc doc = mDocList.get(position);
            String thumbnailUrl = doc.getThumbnailUrl();
            if(!TextUtils.isEmpty(thumbnailUrl) &&
                    Common.isUrl(thumbnailUrl)) {
                mPoster.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(thumbnailUrl)
                        .into(mPoster);
            }else{
                mPoster.setVisibility(View.GONE);
            }
            mTitle.setText(doc.getTitle());
            mContent.setText(doc.snippet);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(mContext, DetailActivity.class);
                    detailIntent.putExtra(Common.EXTRA_URL, doc.web_url);
                    detailIntent.putExtra(Common.EXTRA_TITLE, doc.getTitle());
                    mContext.startActivity(detailIntent);
                }
            });
            if(position >= (int)(getItemCount() * Common.LEVER_CONTINUELOADING)){
                mCallback.continueLoading();
            }
        }
    }

    class HighScoreViewHolder extends RecyclerView.ViewHolder{

        private ImageView mPoster;
        private TextView mTitle;
        private TextView mContent;
        private View mItemView;

        public HighScoreViewHolder(View itemView) {
            super(itemView);
            mPoster = (ImageView) itemView.findViewById(R.id.item_highscore_poster);
            mTitle = (TextView) itemView.findViewById(R.id.item_highscore_title);
            mContent = (TextView) itemView.findViewById(R.id.item_highscore_content);
            mItemView = itemView;
        }

        public void bind(int position){
            final NYTDoc doc = mDocList.get(position);
            String wideUrl = doc.getWideUrl();
            Glide.with(mContext)
                    .load(wideUrl)
                    .into(mPoster);
            mTitle.setText(doc.getTitle());
            mContent.setText(doc.snippet);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(mContext, DetailActivity.class);
                    detailIntent.putExtra(Common.EXTRA_URL, doc.web_url);
                    detailIntent.putExtra(Common.EXTRA_TITLE, doc.getTitle());
                    mContext.startActivity(detailIntent);
                }
            });
            if(position >= (int)(getItemCount() * Common.LEVER_CONTINUELOADING)){
                mCallback.continueLoading();
            }
        }
    }
}
