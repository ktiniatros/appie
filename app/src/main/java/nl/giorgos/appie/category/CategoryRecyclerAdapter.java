package nl.giorgos.appie.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.giorgos.appie.R;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final List<String> flags;

    private final List<String> countryCodes;

    private final CategoryContract.Presenter presenter;

    public CategoryRecyclerAdapter(List<String> flags, List<String> countryCodes, CategoryContract.Presenter presenter) {
        this.flags = flags;
        this.countryCodes = countryCodes;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.setCountry(flags.get(position), countryCodes.get(position));
    }

    @Override
    public int getItemCount() {
        return flags.size();
    }

}
