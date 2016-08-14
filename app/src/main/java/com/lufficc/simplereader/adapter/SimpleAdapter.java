package com.lufficc.simplereader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lufficc.simplereader.R;
import com.lufficc.simplereader.activity.MarkdownActivity;
import com.lufficc.simplereader.model.Article;
import com.lufficc.simplereader.widget.MarkdownView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lcc_luffy on 2016/8/9.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.Holder> {
    List<Article> articles = new ArrayList<>();

    public void setData(List<Article> articleList) {
        articles.clear();
        articles.addAll(articleList);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Article article) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MarkdownActivity.showMarkdown(view.getContext(), article.getMarkdown());
                }
            });
            title.setText(article.getTitle());
            description.setText(article.getDescription());
        }
    }
}
