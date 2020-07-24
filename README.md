



# `ViewPager Fragment`懒加载

[TOC]

ViewPager中的适配器原理

`ViewPager Fragment` 适配器：  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_adapter.png)  

`ViewPager Fragment` 生命周期：  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/viewpager_fragment_lifecycle.png)  



生命周期相关：  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_cache.png)  

事务：提交事务，触发器执行

预加载：可以避免

缓存：无法避免



懒加载，避免预加载

需要看到的时候才去加载页面的数据并显示

懒加载：

不可见 --> 可见的一瞬间 ： 加载

可见 --> 不可见的一瞬间：停止加载



特殊情况:  

![image](https://github.com/tianyalu/XxtViewPagerFragment/raw/master/show/viewpager_fragment_special_condition.png)  

onPause()



HashMap 内存优化

1.7 数组+链表

1.8 数组 + 链表 + 红黑树

阈值



broadCast->注册方式  --》 广播通信原理是怎样的？  Binder --> 设计方案怎样？优势好处

ArrayList 内存优化

ArrayList<ItemInfo> mItems: 缓存的东西不同，空白的，替换