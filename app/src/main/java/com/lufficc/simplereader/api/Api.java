package com.lufficc.simplereader.api;

import com.lufficc.simplereader.model.Article;
import com.lufficc.simplereader.model.Category;
import com.lufficc.simplereader.model.PagedResult;
import com.lufficc.simplereader.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lcc_luffy on 2016/8/9.
 */

public interface Api {
    @GET("article")
    Call<PagedResult<List<Article>>> getArticles();

    @GET("category")
    Call<Result<List<Category>>> getCategories();
}
