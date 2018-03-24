我司的开发过程中有一套标签系统，会对每一个项目工程添加很多不同的标签，设计师在设计的时候，各种标签的样式都不一样，这对我开发过程造成了很大的问题，问题是：
> 标签的border颜色、标签四个角的弧度、标签的填充背景色、标签字体的颜色 、有的标签还有图片

之前的做法儿，每种标签来了之后，都需要定制化的一套shape，这样带来很大的成本。几乎每来一套新的标签都需要有这么一个操作，这样的操作很频繁，也很累。

于是，想把这个标签封装成一个通用的View。

通过配置属性变可以完成一系列的标签标签属性配置：

如下所示：

```
<zhanggeng.www.tagview.TagTextView
    android:layout_width="match_parent"
    android:layout_height="40dp"
    android:gravity="center"
    app:tagStrokeWidth="1px"
    app:tagStrokeColor="@color/colorPrimary"
    app:tagRadius="5dp"
    app:tagNormalSolidColor="#ffddcc"
    android:textColor="@color/colorPrimary"
    android:text="我们" />
    ```
    
其中的：
	•	tagStrokeWidth: 用来配置标签 边框的宽度；
	•	tagStrokeColor: 用来配置标签 边框的颜色；
	•	tagRadius： 用来配置标签四个角的半径；
	•	tagNormalSolidColor: 用来配置标签通常情况下的填充颜色；
 
没错，就是想要这样的效果。一键配置省去为各个标签定制shape的麻烦；
 
下面说说，可能需要的属性：
 
	1.	标签的边框颜色；
	2.	标签的边框粗细；
	3.	标签的字体颜色；
	4.	标签的背景颜色；
	5.	标签的四个角的半径；
	6.	带图片的标签；
	7.	标签的交互：正常情况下、选中状态；以及不同状态下的颜色、背景
	8.	标签的填充色
	
大致能想到的就这几个方面。

后续继续完善。


