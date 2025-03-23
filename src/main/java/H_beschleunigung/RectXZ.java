package H_beschleunigung;

import static lib_cgtools.Util.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.*; 

/* A Shape representing a 2D rectangle in a X-Z-plane in 3D, facing upwards (Y direction) */
public class RectXZ implements Shape {

    private Point anchor; 
    private double x_size_half, z_size_half;
    private Material material; 

    private Point p_min;
    private Point p_max;
    private BoundingBox boundingBox;

    /* 
       create rectangle centered around anchor 
       dimensions: from -x_size/2 ... x_size/2 in X, from -z_size/2 ... z_size/2 in Z 
       normal: facing in Y direction
    */
    public RectXZ(Point anchor, double x_size, double z_size, Material material) {
        this.anchor = anchor;
        this.x_size_half = x_size/2.0; 
        this.z_size_half = z_size/2.0; 
        this.material = material; 

        //erstelle die Axis Aligned Bounding Box(AABB)
        p_min = new Point((anchor.x() - x_size_half),anchor.y(),(anchor.z() - z_size_half));
        p_max = new Point((anchor.x() + x_size_half),anchor.y(),(anchor.z() + z_size_half));
        boundingBox = new BoundingBox(p_min, p_max);
    }

    @Override
    public BoundingBox bounds() {
        return boundingBox;
    }

    @Override
    public Hit intersect(Ray ray) {
        // Ray parallel to X-Z-plane? Then there is no hit. 
        if(isZero(ray.direction().y())) {
            return null; 
        }
        // calculate hitpoint in plane
        double t = (anchor.y() - ray.origin().y()) / ray.direction().y(); 
        if(!ray.isValid(t)) {
            return null; 
        }
        // inside size of rectangle?
        Point p = ray.pointAt(t); 
        double x_l = Math.abs(p.x() - anchor.x()); 
        double z_l = Math.abs(p.z() - anchor.z()); 
        if(x_l > x_size_half || z_l > z_size_half) {
            return null; 
        }
        //Berechnung fuer Textur
        Direction rightDirection = subtract(p, anchor);
        double u = (rightDirection.x() + x_size_half) / (x_size_half * 2.0);
        double v = 1.0 - ((rightDirection.z() + z_size_half) / (z_size_half * 2.0));

        return new Hit(t, p, direction(0,1,0), material, u, v); 
    }
    
}
