package F_texturen;

import lib_cgtools.*;
import static lib_cgtools.Vector.*;

/*
 * Die RectTexture-Klasse implementiert das Sampler-Interface und erzeugt eine Textur,
 * die abhängig von den Parametern u und v eine Farbe berechnet. Diese Textur wird 
 * verwendet, um verschiedene Farbtöne basierend auf den Texturkoordinaten zu erzeugen.
 */
public class RectTexture implements Sampler {

    /*
     * Konstruktor: In diesem Fall wird der Konstruktor verwendet, um die Instanz zu initialisieren.
     * Er enthält jedoch keine spezifische Logik.
     */
    public RectTexture() {
    }

    /*
     * Die getColor-Methode berechnet die Farbe für die gegebenen u- und v-Koordinaten
     * und gibt eine Farbe zurück, die aus einer Kombination von Rot- und Grünanteilen besteht.
     * Der Farbwert basiert auf den Parametern u und v, die als Texturkoordinaten fungieren.
     */
    @Override
    public Color getColor(double u, double v) {
        // Berechnet den Rotanteil der Farbe basierend auf dem u-Wert
        Color uColor = multiply(red, u);

        // Berechnet den Grünanteil der Farbe basierend auf dem v-Wert
        Color vColor = multiply(green, v);

        // Kombiniert die berechneten Rot- und Grünanteile zu einer finalen Farbe
        return add(uColor, vColor);
    }
}
