/** @author henrik.tramberend@bht-berlin.de */
package lib_cgtools;

public record Color(double r, double g, double b) {

  @Override
  public String toString() {
    return String.format("(Col: %.2f %.2f %.2f)", r, g, b);
  }
}
