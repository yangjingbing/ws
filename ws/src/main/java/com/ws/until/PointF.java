package com.ws.until;

public class PointF {
    private Double halfPixelGlobeSize;
    private Double v;
    private float x;
    private float y;
    public PointF(double halfPixelGlobeSize, double halfPixelGlobeSize1, double v) {
    }

    public Double getHalfPixelGlobeSize() {
        return halfPixelGlobeSize;
    }

    public void setHalfPixelGlobeSize(Double halfPixelGlobeSize) {
        this.halfPixelGlobeSize = halfPixelGlobeSize;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
