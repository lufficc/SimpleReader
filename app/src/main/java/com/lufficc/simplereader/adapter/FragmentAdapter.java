package com.lufficc.simplereader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lufficc.simplereader.fragment.ArticleFragment;
import com.lufficc.simplereader.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lufficc on 2016/8/14.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        this.categories.addAll(categories);
        for (Category category : categories) {
            fragments.add(ArticleFragment.newInstance(category));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }
}
