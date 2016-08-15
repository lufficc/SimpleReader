package com.lufficc.simplereader.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lufficc.simplereader.R;
import com.lufficc.simplereader.model.Article;

/**
 * Created by lufficc on 2016/8/15.
 */

public class ArticleListAdapter extends BaseQuickAdapter<Article>{
    public ArticleListAdapter() {
        super(R.layout.item_article, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Article article) {
        baseViewHolder.setText(R.id.title,article.getTitle());
        baseViewHolder.setText(R.id.description,article.getDescription());
    }
}
