package com.pratik.gyroexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import android.view.Display;

import android.view.View;
import android.view.WindowManager;

/**
 * Created by pratik on 6/21/17.
 */

public class BubbleView extends View {

    private ShapeDrawable bubble;
    private Context mContext;

    private int diameter = 0;
    private int xPos = 0, yPos = 0, dxPos = 0, dyPos = 0;
    private int maxWidth = 0, maxHeight = 0;
    private int midWidth = 0, midHeight = 0;
    private int sbWidth = 0, sbHeight = 0;
    private int nBarHeight = 0;
    private int sBarHeight = 0;
    private int aBarHeight = 0;

    public int getSbWidth() {
        return sbWidth;
    }

    public void setSbWidth(int sbWidth) {
        this.sbWidth = sbWidth;
    }

    public int getSbHeight() {
        return sbHeight;
    }

    public void setSbHeight(int sbHeight) {
        this.sbHeight = sbHeight;
    }

    public int getaBarHeight() {
        return aBarHeight;
    }

    public void setaBarHeight(int aBarHeight) {
        this.aBarHeight = aBarHeight;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getDxPos() {
        return dxPos;
    }

    public void setDxPos(int dxPos) {
        this.dxPos = dxPos;
    }

    public int getDyPos() {
        return dyPos;
    }

    public void setDyPos(int dyPos) {
        this.dyPos = dyPos;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMidWidth() {
        return midWidth;
    }

    public void setMidWidth(int midWidth) {
        this.midWidth = midWidth;
    }

    public int getMidHeight() {
        return midHeight;
    }

    public void setMidHeight(int midHeight) {
        this.midHeight = midHeight;
    }

    public int getnBarHeight() {
        return nBarHeight;
    }

    public void setnBarHeight(int nBarHeight) {
        this.nBarHeight = nBarHeight;
    }

    public int getsBarHeight() {
        return sBarHeight;
    }

    public void setsBarHeight(int sBarHeight) {
        this.sBarHeight = sBarHeight;
    }

    public BubbleView(Context context) {
        super(context);
        mContext = context;
        diameter = (int) mContext.getResources().getDimension(R.dimen.v24dip);
        nBarHeight = (int) mContext.getResources().getDimension(R.dimen.v48dip);
        sBarHeight = (int) mContext.getResources().getDimension(R.dimen.v24dip);
        aBarHeight = (int) mContext.getResources().getDimension(R.dimen.v48dip);
        createBubble();
    }

    protected void move(int x, int y) {
        xPos = x;
        yPos = y;
        bubble.setBounds(xPos, yPos, xPos + diameter, yPos + diameter);
    }

    protected void createBubble() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();

        // get app size
        display.getSize(size);

        maxWidth = size.x;
        maxHeight = size.y;

        sbWidth = size.x;
        sbHeight = size.y;

        midWidth = (size.x) / 2;
        midHeight = (size.y) / 2;

        xPos = midWidth;
        yPos = midHeight;

        dxPos = midWidth;
        dyPos = midHeight;

        bubble = new ShapeDrawable(new OvalShape());
        bubble.setBounds(xPos, yPos, xPos + diameter, yPos + diameter);
        bubble.getPaint().setColor(0xff74AC23);
        bubble.getPaint().setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bubble.draw(canvas);
    }

}
