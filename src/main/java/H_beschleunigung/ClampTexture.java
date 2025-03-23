package H_beschleunigung;

import lib_cgtools.*;

/**
 * Die ClampTexture-Klasse implementiert das Sampler-Interface und repräsentiert eine Textur,
 * die die Farbwerte aus einer gegebenen Quelle (content) abruft und sicherstellt, dass
 * die Texturkoordinaten (u, v) innerhalb des Bereichs [0, 1] liegen. 
 * Liegen die Koordinaten außerhalb dieses Bereichs, wird eine Ersatzfarbe (color) zurückgegeben.
 */
public record ClampTexture(Sampler content, Color color) implements Sampler {
    
    @Override
    public Color getColor(double u, double v) {
        if (u < 0 || u > 1 || v < 0 || v > 1) {
            return color;
        }
        return content.getColor(u, v);
    }
}
