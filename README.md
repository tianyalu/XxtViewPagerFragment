



# `ViewPager Fragment`缓存之懒加载性能优化 

[TOC]

## 一、基本原理

### 1.1 `ViewPager`缓存

我们知道使用`ViewPager`时默认是有缓存的，我们可以用如下代码来设置`ViewPager`的缓存页面数：  

```java
viewPager.setOffscreenPageLimit(1);
```

> 该方法设置当前页面左右各缓存多少个`page`，即总共会创建 `2*limit+1` 个 `Fragment`，超出这里`limit`范围的`Fragment`则会被销毁。  
> 如果`limit`小于1，该值默认设置为1（此时最多缓存3个`page`）；  
> 如果`limit`设置为2，则加上当前页面最多缓存5个`page`。  

基于`ViewPager`的缓存机制，当其承载的`Fragment`比较复杂，请求的数据量很大时，由于初始时会一次加载至少3个页面的数据，会造成卡顿和性能的浪费。所以需要对此进行优化。  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_cache.png)   

#### 1.1.1 缓存与懒加载

缓存：无法避免

懒加载：避免预加载，需要看到的时候才去加载页面的数据并显示

> 不可见 --> 可见的一瞬间 ： 加载  
> 可见 --> 不可见的一瞬间：停止加载

### 1.2 `ViewPager`适配器

`ViewPager` 适配器：  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_adapter.png)    

### 1.3 `ViewPager Fragment` 生命周期

`Fragment`：提交事务，触发器执行。

#### 1.3.1 `ViewPager Fragment` 生命周期  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_lifecycle.png)  

#### 1.3.2 生命周期相关  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_cache.png)  

#### 1.3.3 特殊情况

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_special_condition.png)  

ArrayList<ItemInfo> mItems: 缓存的东西不同，空白的，替换

## 二、具体实现

本文实现的效果是在任意一个`Fragment`显示的时候才进行网络请求加载，此时相应的隐藏的`Fragment`停止加载；同时任何其他的`Fragment`不做动作。

核心代码如下：  

```java
public abstract class LazyFragment extends Fragment {
    private View rootView;
    private boolean isViewCreated = false;  //View是否创建了
    private boolean isCurrentVisibleState = false;  //当前是否可见

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
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
            onFragmentLoadStop();
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
        if(getUserVisibleHint() && !isCurrentVisibleState) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getUserVisibleHint() && isCurrentVisibleState) {
            dispatchUserVisibleHint(false);
        }
    }
  
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isCurrentVisibleState = false;
    }
}
```

