package com.sty.xxt.viewpagerfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyFragment extends LazyFragment {
    private static final String TAG = MyFragment.class.getSimpleName();
    public static final String POSITION = "Position";

    ImageView imageView;
    TextView textView;
    int tabIndex;
    private CountDownTimer countDownTimer;
    private Handler handler = new Handler();

    public static MyFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        MyFragment fragment = new MyFragment();
        fragment.setFragmentDelegator(new FragmentDelegator(fragment));
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, tabIndex + " fragment onCreate: ");
    }

    @Override
    protected void initView(View rootView) {
        imageView = rootView.findViewById(R.id.iv_content);
        textView = rootView.findViewById(R.id.tv_loading);
        tabIndex = getArguments().getInt(POSITION);

        Log.d(TAG, tabIndex + " fragment initView: ");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_lazy_loading;
    }

    //终端更新
    @Override
    public void onFragmentLoadStop() {
        super.onFragmentLoadStop();
        tabIndex = getArguments().getInt(POSITION);
        handler.removeMessages(10);
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        Log.d(TAG, tabIndex + " fragment 暂停一切操作 pause: ");
        //对UI操作的代表
        textView.setText("av");
    }

    @Override
    public void onFragmentLoad() {
        super.onFragmentLoad();
        getData();
        Log.d(TAG, tabIndex + " fragment 真正更新界面 ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getData() {
        countDownTimer = new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                handler.sendEmpthMessage(0);
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, tabIndex + " fragment onResume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, tabIndex + " fragment onPause ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        tabIndex = getArguments().getInt(POSITION);
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, tabIndex + " fragment setUserVisibleHint ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, tabIndex + " fragment onAttach ");
    }
}
