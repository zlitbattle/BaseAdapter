package cn.zl.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author zhangling
 * date 2019-04-25 22:29
 * description: 多样式列比奥适配器
 */
public class MultipleBaseAdapter<T> extends BaseAdapter<T> {
    private SparseArray<BaseItemView<T>> itemViews;
    /**
     * key:position
     * value:对应的BaseItemView
     */
    private SparseArray<BaseItemView<T>> positionForItemViews;

    public MultipleBaseAdapter(Context context, List<T> list) {
        super(context, list, 0);
        itemViews = new SparseArray<>();
        positionForItemViews = new SparseArray<>();
    }

    public void addBaseItemView(BaseItemView<T> baseItemView) {
        itemViews.put(baseItemView.hashCode(), baseItemView);
    }

    @Override
    public int getItemViewType(int position) {
        int fatherResult = super.getItemViewType(position);
        if (fatherResult != OTHER_VIEW_TYPE) {
            return fatherResult;
        }
        for (int i = 0; i < itemViews.size(); i++) {
            BaseItemView<T> tBaseItemView = itemViews.valueAt(i);
            if (tBaseItemView.isForViewHolder(list.get(position), position)) {
                positionForItemViews.put(position, tBaseItemView);

                return itemViews.keyAt(i);
            }
        }
        throw new IllegalArgumentException("not found BaseItemView on position = " + position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return itemViews.get(i).createViewHolder(viewGroup);
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, T t, int i) {
        positionForItemViews.get(i).bindViewHolder(baseViewHolder, t, i);
    }
}