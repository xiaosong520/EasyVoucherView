# EasyVoucherView
## 一个自定义View 优惠券控件

这个项目的优势：
1.优惠券边缘锯齿形状有：圆形、椭圆、三角形、正方形；
2.锯齿的间距、大小、控件颜色等可自定义设置；
3.之前搜到的好几个项目都是单纯的绘制白色锯齿添加到Canvas上面去，如果把布局的背景改成其他的颜色，就一点都不美观了；所以这个项目和其他类似的优惠券不同，我用另外的思路实现了这个功能，这个控件的边缘锯齿是透明的！

![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/circle.png)
![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/ellipse.png)
![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/square.png)
![EasyVoucherView](https://github.com/xiaosong520/EasyVoucherView/blob/master/preview/triangle.png)
## How to use （如何使用）

#### 1.下载源代码，然后将项目中 VoucherView 类 以及 attrs.xml 文件里面的自定义属性拷贝到项目中去

#### 2.在需要使用的布局文件中，添加控件，代码如下(路径请替换成实际项目中 VoucherView 类所在的路径)：

根部局添加如下属性：

xmlns:VoucherView="http://schemas.android.com/apk/res-auto"

添加控件：

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
#### 3.自定义属性（attrs）

|method(方法名称)|format(参数格式）|description（描述）|
|---|---|---|
|drawType|enum（枚举）|有圆形、椭圆、三角形、正方形这四种边缘锯齿形状
|orientation|enum（枚举）|包含 horizontal、vertical、around 这三种样式，分别表示锯齿方向水平、垂直、四周。
|mGap|dimension（尺寸）|该参数控制边缘锯齿之间的间隔宽度
|mRadius|dimension（尺寸）|该参数控制边缘锯齿的半径长度
|BgColor|color（颜色）|该参数控制自定义控件的背景颜色


