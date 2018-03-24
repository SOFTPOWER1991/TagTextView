package zhanggeng.www.tagview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 标签view
 *
 * @author zg
 * @date 2018/03/21
 */
@SuppressLint("AppCompatCustomView")
public class TagTextView extends TextView {

    private GradientDrawable normalGD;
    private GradientDrawable pressedGD;
    private StateListDrawable selector;

    private int strokeWidth;
    private int radius;


    public TagTextView(Context context) {
        super(context);

    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(context, attrs);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(context, attrs);
    }


    private void setAttributeSet(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        int strokeColor = a.getColor(R.styleable.TagTextView_tagStrokeColor, Color.TRANSPARENT);
        radius = a.getDimensionPixelSize(R.styleable.TagTextView_tagRadius, 0);
        int leftTopRadius = a.getDimensionPixelSize(R.styleable.TagTextView_tagLeftTopRadius, 0);
        int leftBottomRadius = a.getDimensionPixelSize(R.styleable.TagTextView_tagLeftBottomRadius, 0);
        int rightTopRadius = a.getDimensionPixelSize(R.styleable.TagTextView_tagRightTopRadius, 0);
        int rightBottomRadius = a.getDimensionPixelSize(R.styleable.TagTextView_tagRightBottomRadius, 0);
        strokeWidth = a.getDimensionPixelSize(R.styleable.TagTextView_tagStrokeWidth, 0);
        int normalTextColor = a.getColor(R.styleable.TagTextView_tagNormaltagColor, Color.TRANSPARENT);
        int selectedTextColor = a.getColor(R.styleable.TagTextView_tagSelectedtagColor, Color.TRANSPARENT);
        int normalSolidColor = a.getColor(R.styleable.TagTextView_tagNormalSolidColor, Color.TRANSPARENT);
        int pressedSolidColor = a.getColor(R.styleable.TagTextView_tagPressedSolidColor, Color.TRANSPARENT);
        boolean isSelected = a.getBoolean(R.styleable.TagTextView_tagIsSelected,false);
        Drawable textDrawable = a.getDrawable(R.styleable.TagTextView_tagDrawable);

        a.recycle();

        selector = new StateListDrawable();
        normalGD = new GradientDrawable();
        pressedGD = new GradientDrawable();

        setPressedState(leftTopRadius,leftBottomRadius,rightBottomRadius,rightTopRadius,strokeColor,
                pressedSolidColor,isSelected);

        setNormalState(leftTopRadius,leftBottomRadius,rightBottomRadius,rightTopRadius,strokeColor,
                normalSolidColor);

        setBackgroundDrawable(selector);

        if (textDrawable != null) {
            BitmapDrawable bd = (BitmapDrawable) textDrawable;
            ImageSpan imageSpan = new ImageSpan(getContext(), bd.getBitmap());

            String text = "[icon]";
            SpannableString ss = new SpannableString("[icon]");

            ss.setSpan(imageSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(ss);
        }


        if (normalTextColor != 0 && selectedTextColor != 0) {
            setClickable(true);
            ColorStateList textColorSelect = null;

            if (isSelected) { //是否可以选中
                int[][] states = new int[2][1];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};
                textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, normalTextColor});
            }else{
                int[][] states = new int[3][1];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{android.R.attr.state_pressed};
                states[2] = new int[]{};
                textColorSelect = new ColorStateList(states, new int[]{selectedTextColor,selectedTextColor,normalTextColor});
            }

            setTextColor(textColorSelect);
        }else{
            setClickable(false);
        }
    }


    /**
     *
     * 设置正常状态下drawable
     *
     * @param leftTopRadius            左上角半径
     * @param leftBottomRadius         左下角半径
     * @param rightBottomRadius        右下角半径
     * @param rightTopRadius           右上角半径
     * @param strokeColor              描边颜色
     * @param normalSolid              正常状态下填充颜色
     */
    private void setNormalState(int leftTopRadius,int leftBottomRadius, int rightBottomRadius,int rightTopRadius,
                                int strokeColor,int normalSolid){

        //设置正常状态下填充色
        normalGD.setColor(normalSolid);
        //设置描边
        normalGD.setStroke(strokeWidth, strokeColor);
        //设置圆角
        setRadius(normalGD,leftTopRadius,leftBottomRadius,rightBottomRadius,rightTopRadius);
        //normal drawable
        LayerDrawable normalLayerDrawable = new LayerDrawable(new Drawable[]{normalGD});
        //设置正常状态下描边边距
        //设置正常状态下的drawable
        selector.addState(new int[]{}, normalLayerDrawable);
    }

    /**
     * 设置按下状态drawable
     *
     * @param leftTopRadius            左上角半径
     * @param leftBottomRadius         左下角半径
     * @param rightBottomRadius        右下角半径
     * @param rightTopRadius           右上角半径
     * @param strokeColor              描边颜色
     * @param pressedSolid             按下状态填充色
     * @param isSelected               是否可以选择状态
     */
    private void setPressedState(int leftTopRadius,int leftBottomRadius,int rightBottomRadius,int rightTopRadius,
                                 int strokeColor,int pressedSolid,boolean isSelected){

        if (pressedSolid != Color.TRANSPARENT) {
            //设置按下填充色
            pressedGD.setColor(pressedSolid);
            //设置选中状态下描边边距 以及颜色
            pressedGD.setStroke(strokeWidth, strokeColor);
            //设置圆角
            setRadius(pressedGD,leftTopRadius,leftBottomRadius,rightBottomRadius,rightTopRadius);

            LayerDrawable pressedLayerDrawable = new LayerDrawable(new Drawable[]{pressedGD});
            //设置按下状态
            if (isSelected) {
                selector.addState(new int[]{android.R.attr.state_selected}, pressedLayerDrawable);
            } else {
                selector.addState(new int[]{android.R.attr.state_pressed}, pressedLayerDrawable);
            }
        }
    }

    /**
     *
     * 设置半径
     *
     * 如果只指定了一个radius 那么设置所有的半径为一个值；
     * 否则，按照具体指定的半径进行设置
     *
     * @param drawable                 drawable
     * @param leftTopRadius            左上角半径
     * @param leftBottomRadius         左下角半径
     * @param rightBottomRadius        右下角半径
     * @param rightTopRadius           右上角半径
     *
     */
    private void setRadius(GradientDrawable drawable, int leftTopRadius, int leftBottomRadius,
                           int rightBottomRadius, int rightTopRadius){
        if (radius != 0) {
            drawable.setCornerRadius(radius);
        } else if (leftTopRadius != 0 || leftBottomRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
            drawable.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius,
                    rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
        }
    }



    /**
     * 设置填充图片
     *
     * @param drawableId  normalGD id
     */
    public void setTextDrawable(int drawableId) {
        if (drawableId != 0) {
            Drawable textdrwable = getResources().getDrawable(drawableId);
            BitmapDrawable bd = (BitmapDrawable) textdrwable;
            ImageSpan imageSpan = new ImageSpan(getContext(), bd.getBitmap());

            String text = "[icon]";
            SpannableString ss = new SpannableString("[icon]");

            ss.setSpan(imageSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(ss);
        }
    }

    /**
     *
     * 设置填充颜色
     *
     * @param colorId  颜色
     */
    public void setSolidColor(int colorId) {
        normalGD.setColor(colorId);
        setBackgroundDrawable(normalGD);
    }

    /**
     * 设置圆角半径
     *
     * @param leftTopRadius         左上角半径
     * @param leftBottomRadius      左下角半径
     * @param rightTopRadius        右上角半径
     * @param rightBottomRadius     右下角半径
     */
    public void setRadius(int leftTopRadius, int leftBottomRadius, int rightTopRadius, int rightBottomRadius) {
        normalGD.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
        setBackgroundDrawable(normalGD);
    }

    /**
     * 设置tagview边框颜色及宽度
     *
     * @param strokeWidth      边框宽度
     * @param colorId          边框颜色
     */
    public void setStrokeColorAndWidth(int strokeWidth,int colorId){
        normalGD.setStroke(strokeWidth, getResources().getColor(colorId));
    }



    /**
     * 设置tagViw 状态变化时的颜色变化
     *
     * @param normalTextColor     未选中时的颜色变化
     * @param selectedTextColor   选中时的颜色变化
     */
    public void setSelectedTextColor(int normalTextColor,int selectedTextColor) {

        normalTextColor = getResources().getColor(normalTextColor);
        selectedTextColor = getResources().getColor(selectedTextColor);

        if (normalTextColor != 0 && selectedTextColor != 0) {
            setClickable(true);
            int[][] states = new int[3][1];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{android.R.attr.state_pressed};
            states[2] = new int[]{};
            ColorStateList textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
            setTextColor(textColorSelect);
        }else{
            setClickable(false);
        }

    }

}
