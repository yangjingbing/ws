package com.ws.until;

public class ConPixToConn
    {
        private double PixelTileSize = 256d;
        private double DegreesToRadiansRatio = 180d / Math.PI;
        private double RadiansToDegreesRatio = Math.PI / 180d;
        private PointF PixelGlobeCenter;
        private double XPixelsToDegreesRatio;
        private double YPixelsToRadiansRatio;

        public ConPixToConn(double zoomLevel)
        {
            double pixelGlobeSize = this.PixelTileSize * Math.pow(2d, zoomLevel);
            this.XPixelsToDegreesRatio = pixelGlobeSize / 360d;
            this.YPixelsToRadiansRatio = pixelGlobeSize / (2d * Math.PI);
            double halfPixelGlobeSize = pixelGlobeSize / 2d;
            this.PixelGlobeCenter = new PointF(halfPixelGlobeSize, halfPixelGlobeSize,0d);
        }

        public ConPixToConn() {

        }

        public PointF FromCoordinatesToPixel(PointF coordinates)
        {
            double x = Math.round(this.PixelGlobeCenter.getX()
                    + (coordinates.getX() * this.XPixelsToDegreesRatio));
            double f = Math.min(
                    Math.max(
                            Math.sin(coordinates.getY() * RadiansToDegreesRatio),
                            -0.9999d),
                    0.9999d);
            double y = Math.round(this.PixelGlobeCenter.getY() + .5d *
                    Math.log((1d + f) / (1d - f)) * -this.YPixelsToRadiansRatio);
            return new PointF(x, y, 0d);
        }

        public PointF FromPixelToCoordinates(PointF pixel)
        {
            double longitude = (pixel.getX() - this.PixelGlobeCenter.getY()) /
                    this.XPixelsToDegreesRatio;
            double latitude = (2 * Math.atan(Math.exp(
                    (pixel.getY() - this.PixelGlobeCenter.getY()) / -this.YPixelsToRadiansRatio))
                    - Math.PI / 2) * DegreesToRadiansRatio;
            return new PointF(latitude, longitude, 0d);
        }

        public static void main(String[] args) {
            ConPixToConn gmap = new ConPixToConn(1);
            PointF out = gmap.FromPixelToCoordinates(new PointF(70.50459*0.23,72.38887*0.23, 0f));
            System.out.println(out.getX());
            System.out.println(out.getY());
        }
}
