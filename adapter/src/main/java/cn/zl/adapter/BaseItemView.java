package cn.zl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhangling
 * date 2019-04-25 22:29
 * description: 多布局适配器布局基类
 */
public abstract class BaseItemView<T> {
    private Context context;
    private int resLayoutId;

    public BaseItemView(Context context, int resLayoutId) {
        this.context = context;
        this.resLayoutId = resLayoutId;
    }

    public BaseViewHolder createViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        return new BaseViewHolder(context, inflate);
    }

    public abstract boolean isForViewHolder(T itemDate, int position);

    protected abstract void bindViewHolder(BaseViewHolder viewHolder, T itemDate, int position);

    @Override
    public int hashCode() {
        return resLayoutId;
    }
}