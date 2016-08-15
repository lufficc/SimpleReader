package com.lufficc.simplereader.api;

import com.lufficc.simplereader.model.Article;
import com.lufficc.simplereader.model.Category;
import com.lufficc.simplereader.model.Markdown;
import com.lufficc.simplereader.model.PagedResult;
import com.lufficc.simplereader.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lufficc on 2016/8/9.
 */

public interface Api {
    @GET("article")
    Call<PagedResult<List<Article>>> getArticles();

    @GET("article/{article_id}/markdown")
    Call<Result<Markdown>> getMarkdown(@Path("article_id") long article_id);

    @GET("category")
    Call<Result<List<Category>>> getCategories();

    @GET("category/{id}/article?size=10&sort=createdAt,desc")
    Call<PagedResult<List<Article>>> getArticlesByCategory(
            @Path("id") long category_id,
            @Query("page") int page
    );


}
