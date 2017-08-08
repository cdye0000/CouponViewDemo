# CouponViewDemo
优惠券 布局继承自Linlayout，边缘裁剪形状支持圆形和三角形

# 使用
	Step 1 
	Add it in your root build.gradle at the end of repositories:
		allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	Step 2
	Add the dependency
	dependencies {
	        compile 'com.github.cdye0000:CouponViewDemo:1.0'
	}
	
	
# 参数设置
		CouponView 继承自Linlayout,额外参数配置：
		裁剪形状，不设置默认无裁剪形状
		vertical_style_left       垂直方向左边裁剪形状
		vertical_style_right      垂直方向右边裁剪形状
		horizontal_style_top	  水平方向上边裁剪形状
		horizontal_style_bottom   水平方向下边裁剪形状
		
		color_parent_bg           设置裁剪部分背景颜色，可以保持与父布局背景颜色统一
		interval                  裁剪间隔
        radius                    裁剪半径