package com.sty.xxt.viewpagerfragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyFragment extends Fragment {
    private static final String TAG = LazyFragment.class.getSimpleName();
    FragmentDelegator fragmentDelegator;
    private View rootView;
    private boolean isViewCreated = false;
    private boolean isCurrentVisibleState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if(rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        initView(rootView);
        isViewCreated = true;

        if(getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
        return rootView;
    }

    protected abstract void initView(View rootView);


    protected abstract int getLayoutRes();

    //判断Fragment是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isViewCreated) {
            if (!isCurrentVisibleState && isVisibleToUser) {
                dispatchUserVisibleHint(true);
            } else if(isCurrentVisibleState && !isVisibleToUser){
                dispatchUserVisibleHint(false);
            }
        }
    }

    private void dispatchUserVisibleHint(boolean visibleState) {
        isCurrentVisibleState = visibleState;
        if(visibleState) {
            onFragmentLoad();
        }else {
            onFragmentLoad();
        }
    }


    //停止网络数据请求
    public void onFragmentLoadStop() {
        Log.d(TAG, "onFragmentLoadStop: ");
    }

    //加载网络数据请求
    public void onFragmentLoad() {
        Log.d(TAG, "onFragmentLoad: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if(getUserVisibleHint() && !isCurrentVisibleState) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        if(getUserVisibleHint() && isCurrentVisibleState) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
