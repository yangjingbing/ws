package com.ws.geodetic;

/**
 * @author isHello
 */
public class Point {
    private double xAxis;
    private double yAxis;
    private double zAxis;

    public Point() {
        this.xAxis = 0.0D;
        this.yAxis = 0.0D;
        this.zAxis = 0.0D;
    }

    public Point(double x, double y, double z) {
        this.xAxis = x;
        this.yAxis = y;
        this.zAxis = z;
    }

    public double get(int index) {
        if (index == 0) {
            return this.xAxis;
        } else {
            return index == 1 ? this.yAxis : this.zAxis;
        }
    }

    public void set(int index, double value) {
        if (index == 0) {
            this.xAxis = value;
        } else if (index == 1) {
            this.yAxis = value;
        } else {
            this.zAxis = value;
        }

    }
}
