/** @author henrik.tramberend@bht-berlin.de */
package lib_cgtools;

// A Sampler is something that can return a color value for a two dimensional
// position.
public interface Sampler {
  public Color getColor(double x, double y);
}
