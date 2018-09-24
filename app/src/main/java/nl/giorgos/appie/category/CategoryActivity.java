package nl.giorgos.appie.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindView;
import nl.giorgos.appie.R;
import nl.giorgos.appie.base.BaseActivity;
import nl.giorgos.appie.country_news_list.CountryNewsListActivity;

public class CategoryActivity extends BaseActivity implements CategoryContract.View {

    @BindView(R.id.category_recycler_view)
    RecyclerView categoryRecyclerView;

    @BindArray(R.array.flags)
    String[] flags;

    @BindArray(R.array.countries)
    String[] countryCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category);
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        CategoryContract.Presenter presenter = new CategoryPresenter(this);
        categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(Arrays.asList(flags), Arrays.asList(countryCodes), presenter));
    }

    @Override
    public void openNewsActivity(@NonNull String countryCode) {
        final Intent intent = new Intent(this, CountryNewsListActivity.class);
        intent.putExtra(CountryNewsListActivity.COUNTRY_CODE_EXTRA, countryCode);
        startActivity(intent);
    }
}
