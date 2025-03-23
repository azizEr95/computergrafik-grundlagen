/** @author henrik.tramberend@bht-berlin.de */
package lib_cgtools;


import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A simple image writer that takes an array of pixel components and the image
 * size and writes the corresponding image in 16-bit PNG format with a linear
 * color space to the provided location.
 */
public class ImageWriter {

  // write an image to disk without gamma correction
  public static void write(String filename, double[] data, int width, int height) {
    write(filename, data, width, height, 1.0);
  }

  // write an image to disk with gamma correction
  public static void write(String filename, double[] data, int width, int height, double gamma) {
    try {
      write(filename, data, width, height, true, gamma);
    } catch (IOException error) {
      System.out.println(String.format("Something went wrong writing: %s: %s", filename, error));
      System.exit(1);
    }
  }

  private ImageWriter() {
  }

  private static void write(String filename, double[] data, int width, int height, boolean linear, double gamma) throws IOException {
    // setup an sRGB image with 16-bit components of the right size.
    int cs = ColorSpace.CS_sRGB;
    if (linear)
      cs = ColorSpace.CS_LINEAR_RGB;
    ComponentColorModel ccm = new ComponentColorModel(ColorSpace.getInstance(cs), false, false,
        ComponentColorModel.OPAQUE, DataBuffer.TYPE_USHORT);

    WritableRaster raster = Raster.createBandedRaster(DataBuffer.TYPE_USHORT, width, height, 3, null);
    BufferedImage image = new BufferedImage(ccm, raster, false, null);

    final double inv_gamma = 1.0 / gamma; 
    for (int j = 0; j != height; j++) {
      for (int i = 0; i != width; i++) {
        int k = (width * j + i) * 3;
        int[] rgb = {
          (int) (clamp(Math.pow(data[k + 0], inv_gamma)) * 65535.0),
          (int) (clamp(Math.pow(data[k + 1], inv_gamma)) * 65535.0),
          (int) (clamp(Math.pow(data[k + 2], inv_gamma)) * 65535.0)
        };
        raster.setPixel(i, j, rgb);
      }
    }
    // This fails on Windows across drive boundaries.
    // File outputfile = File.createTempFile("image-", ".png");
    // ImageIO.write(image, "png", outputfile);
    // Files.move(Paths.get(outputfile.getAbsolutePath()), Paths.get(filename),
    // REPLACE_EXISTING);
    File file = new File(filename);
    file.getParentFile().mkdirs();
    ImageIO.write(image, "png", file);
  }

  private static double clamp(double v) {
    return Math.min(Math.max(0, v), 1);
  }
}
