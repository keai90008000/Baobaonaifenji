package lianxin.com.baobaonaifenji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 1.类的用途：自定义GridVIew 计算了GridView的高度
 * 2.渠刚
 * 3.@date
 * 4.用途:
 */
public class MyGridView extends GridView{
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
