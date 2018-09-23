package nl.giorgos.appie.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.giorgos.appie.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.country_flag_text_view)
    TextView flagTextView;

    String countryCode;

    private CategoryContract.Presenter presenter;

    public CategoryViewHolder(View view, CategoryContract.Presenter presenter) {
        super(view);
        this.presenter = presenter;

        ButterKnife.bind(this, view);
    }

    public void setCountry(String flag, String countryCode) {
        flagTextView.setText(flag);
        this.countryCode = countryCode;
    }

    @OnClick(R.id.country_flag_text_view)
    public void onClick(View view) {
        presenter.onCountrySelected(countryCode);
    }
}
