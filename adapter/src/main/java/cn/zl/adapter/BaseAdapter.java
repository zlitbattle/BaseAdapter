package cn.zl.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangling
 * date 2019-04-25 21:10
 * description: recyclerView适配器基类
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int PAGE_SIZE = 10;
    private Context context;
    protected List<T> list;
    private int layoutResId;
    private SparseArray<View> headerViews, footerViews;
    protected final int OTHER_VIEW_TYPE = 0;

    public BaseAdapter(Context context, List<T> list, @LayoutRes int layoutResId) {
        this.context = context;
        this.list = list == null ? new ArrayList<T>() : list;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (headerViews != null && ((view = headerViews.get(i)) != null)) {
            return new BaseViewHolder(context, view);
        }
        if (footerViews != null && ((view = footerViews.get(i)) != null)) {
            return new BaseViewHolder(context, view);
        }
        view = LayoutInflater.from(context).inflate(layoutResId, viewGroup, false);
        return new BaseViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (isHeader(i) || isFooter(i)) {
            return;
        }
        int headerSize = headerViews == null ? 0 : headerViews.size();
        bindData(baseViewHolder, list.get(i - headerSize), i - headerSize);
    }

    protected abstract void bindData(BaseViewHolder baseViewHolder, T t, int i);

    @Override
    public int getItemCount() {
        int headerSize = headerViews == null ? 0 : headerViews.size();
        int footerSize = footerViews == null ? 0 : footerViews.size();
        int listSize = list == null ? 0 : list.size();
        return headerSize + footerSize + listSize;
    }

    /**
     * 追加数据
     *
     * @param list 需要追加的数据
     */
    public void appendDataList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = this.list.size();
        this.list.addAll(list);
        notifyItemInserted(size + 1 + (headerViews == null ? 0 : headerViews.size()));
    }

    /**
     * 刷新（重置）数据
     *
     * @param list 新数据
     */
    public void refreshData(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 添加表头
     *
     * @param view 表头
     */
    public void addHeader(View view) {
        if (headerViews == null) {
            headerViews = new SparseArray<>();
        }
        headerViews.put(headerViews.size() + 1, view);
    }

    /**
     * 添加表尾
     *
     * @param view 表尾
     */
    public void addFooter(View view) {
        if (footerViews == null) {
            footerViews = new SparseArray<>();
        }
        footerViews.put(-(footerViews.size() + 1), view);
    }

    public boolean isHeader(int position) {
        if (headerViews == null) {
            return false;
        }
        return position < headerViews.size();
    }

    public boolean isFooter(int position) {
        if (footerViews == null) {
            return false;
        }
        return position >= list.size() + (headerViews == null ? 0 : headerViews.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return position + 1;
        }
        int headSize = headerViews == null ? 0 : headerViews.size();
        if (isFooter(position)) {
            return -(position - headSize - list.size() + 1);
        }
        return OTHER_VIEW_TYPE;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    if (isHeader(i) || isFooter(i)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return spanSizeLookup != null ? spanSizeLookup.getSpanSize(i) : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        if (isHeader(layoutPosition) || isFooter(layoutPosition)) {
            ViewGroup.LayoutParams layoutParams = holder.getItemView().getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    /**
     * 删除一行
     *
     * @param position 要删除的行
     */
    public void remove(int position) {
        int p = position - (headerViews == null ? 0 : headerViews.size());
        list.remove(p);
        notifyItemRemoved(p);
    }

    public int getPage() {
        return list.size() / PAGE_SIZE;
    }

    public Context getContext() {
        return context;
    }

    public T getItemDataForListByPosition(int position) {
        return list.get(position);
    }

    public List<T> getList() {
        return list;
    }
}