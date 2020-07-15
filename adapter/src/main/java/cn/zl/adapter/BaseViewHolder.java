package cn.zl.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author zhangling
 * date 2019-04-25 20:48
 * description: viewHolder基类
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private SparseArray<View> views;
    private Context context;

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public BaseViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        views = new SparseArray<>();
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = views.get(viewId);
        if (null != view) {
            return (T) view;
        }
        view = itemView.findViewById(viewId);
        views.put(viewId, view);
        return (T) view;
    }

    public BaseViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View mView = findViewById(viewId);
        if (mView != null) {
            mView.setVisibility(visibility);
        }
        return this;
    }

    public BaseViewHolder setTvText(@IdRes int viewId, @StringRes int resId) {
        TextView mTextView = findViewById(viewId);
        if (mTextView != null) {
            mTextView.setText(context.getResources().getString(resId));
        }
        return this;
    }

    public BaseViewHolder setTvText(@IdRes int viewId, String text) {
        TextView mTextView = findViewById(viewId);
        if (mTextView != null) {
            mTextView.setText(text);
        }
        return this;
    }

    public BaseViewHolder setBtnText(@IdRes int viewId, String text) {
        Button mButton = findViewById(viewId);
        if (mButton != null) {
            mButton.setText(text);
        }
        return this;
    }

    public BaseViewHolder setBtnText(@IdRes int viewId, @StringRes int resId) {
        Button mButton = findViewById(viewId);
        if (mButton != null) {
            mButton.setText(context.getResources().getString(resId));
        }
        return this;
    }

    public BaseViewHolder setIvImage(@IdRes int viewId, Bitmap bitmap) {
        ImageView mImageView = findViewById(viewId);
        if (mImageView != null) {
            mImageView.setImageBitmap(bitmap);
        }
        return this;
    }

    public BaseViewHolder setIvImage(@IdRes int viewId, @DrawableRes int resId) {
        ImageView mImageView = findViewById(viewId);
        if (mImageView != null) {
            mImageView.setImageResource(resId);
        }
        return this;
    }

//    public BaseViewHolder loadImage(@IdRes int viewId, String url) {
//        ImageView imageView = findViewById(viewId);
//        Glide.with(context).load(url).into(imageView);
//        return this;
//    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        findViewById(viewId).setOnClickListener(onClickListener);
        return this;
    }

    public BaseViewHolder setItemOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
        return this;
    }
}