package com.cn.common.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hoo on 2018/1/23.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    public static final String TAG = "BaseDialogFragment";
    private static final float DEFAULT_DIMAMOUNT = 0.2F;


    protected abstract int getLayoutRes();

    protected abstract View getDialogView();

    protected abstract void bindView(View view);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (getLayoutRes() > 0) {
            view = inflater.inflate(getLayoutRes(), container, false);
        }
        if (getDialogView() != null) {
            view = getDialogView();
        }
        bindView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //去除Dialog默认头部
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(isCancelableOutside());
        setCancelable(isCancelable());
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置背景色
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            if (getDialogHeight() > 0) {
                params.height = getDialogHeight();
            } else {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (getDialogWidth() > 0) {
                params.width = getDialogWidth();
            } else {
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            //透明度
            params.dimAmount = getDimAmount();
            //位置
            params.gravity = getGravity();
            window.setAttributes(params);

        }
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getFragmentTag());
    }

    protected boolean isCancelableOutside() {
        return true;
    }

    //默认宽高为包裹内容
    public int getDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public int getDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    //默认弹窗位置为中心
    public int getGravity() {
        return Gravity.CENTER;
    }

    //默认透明度为0.2
    public float getDimAmount() {
        return DEFAULT_DIMAMOUNT;
    }

    /**
     * 获得屏幕宽/高
     */
    public static int getWindowHeight(Activity acitvity) {
        return acitvity.getWindowManager().getDefaultDisplay().getHeight();
    }

    protected static int getWindowWidth(Activity acitvity) {
        return acitvity.getWindowManager().getDefaultDisplay().getWidth();
    }
}
