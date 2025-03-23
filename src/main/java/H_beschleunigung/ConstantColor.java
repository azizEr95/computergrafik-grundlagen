package H_beschleunigung;

import lib_cgtools.*;

/*
 * Die Klasse ConstantColor implementiert das Sampler-Interface und gibt für jedes Pixel im Bild die gleiche Farbe zurück.
 * Sie wird verwendet, um ein Bild mit einer einheitlichen Farbe zu füllen.
 */
public class ConstantColor implements Sampler {
    private Color color;

    public ConstantColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(double x, double y) {
        return color;
    }
    
}
