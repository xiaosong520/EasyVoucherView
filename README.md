## EasyVoucherView
## 一个自定义View 优惠券控件

本项目的优势：

1.优惠券边缘锯齿形状有：圆形、椭圆、三角形、正方形；

2.锯齿的间距、大小、控件颜色等可自定义设置；

3.边缘锯齿是透明的。

之前看到的好几个项目都是单纯的绘制白色锯齿添加到Canvas上面去，如果把布局的背景改成其他的颜色，就会产生如下效果：

![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/others.png)

为了解决掉这个问题，我用另外的思路实现了这个功能，利用图像合成类PorterDuffXfermode并给画笔设置DST_OUT模式，让这个控件的边缘锯齿透明。

### 本项目实现的效果图

![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/circle.png)
![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/ellipse.png)
![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/square.png)
![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/triangle.png)


## How to use 


### 引入依赖 （Dependency Library）

**有三种方式**：

#### 1.引入Jcenter 库

```
compile 'com.xiaosong520:voucher:1.0.1'
```

#### 2.下载源代码，将Module引入到项目

#### 3.下载源代码，将项目中 VoucherView 类 以及 attrs.xml 文件里面的自定义属性拷贝到项目中去。


### 在需要使用的布局文件中，添加控件，代码如下

根部局添加如下属性：

xmlns:VoucherView="http://schemas.android.com/apk/res-auto"


添加控件(路径请替换成实际项目中 VoucherView 类所在的路径)：

```xml
 <com.voucher.VoucherView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="30dp"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true"
        VoucherView:drawType="circle"
        VoucherView:orientation="horizontal"
        VoucherView:mGap="5dp"
        VoucherView:mRadius="5dp"
        VoucherView:BgColor="#FFA90F">
```

#### 4.自定义属性（attrs）

|method(方法名称)|format(参数格式）|description（描述）|
|:---:|:---:|:---:|
|drawType|enum（枚举）|有圆形、椭圆、三角形、正方形这四种边缘锯齿形状
|orientation|enum（枚举）|包含 horizontal、vertical、around 这三种方向，分别表示水平、垂直、四周。
|mGap|dimension（尺寸）|该参数控制边缘锯齿之间的间隔宽度
|mRadius|dimension（尺寸）|该参数控制边缘锯齿的半径长度
|BgColor|color（颜色）|该参数控制自定义控件的背景颜色


