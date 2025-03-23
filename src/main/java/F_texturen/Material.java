package F_texturen;

import lib_cgtools.*;

/*
 * Das Material-Interface definiert Methoden zur Berechnung der Lichtreflexion 
 * an Oberflächen von Objekten in der Szene. Dies umfasst sowohl die Reflexion 
 * des Umgebungslichts als auch die Reflexion von direktem Licht, das von 
 * Lichtquellen auf ein Objekt trifft.
 */
public interface Material {
    
    /*
     * Berechnet die Reflexion des Umgebungslichts (ambient light) auf einer Oberfläche.
     * Diese Reflexion ist unabhängig von der Betrachtungsrichtung und gilt für alle
     * ausgehenden Lichtstrahlen gleichermaßen.
     *
     * - ambient_light: Die Farbe des Umgebungslichts in der Szene.
     * - hit: Trefferpunkt
     * Rückgabewert: Die berechnete Reflexionsfarbe der Oberfläche unter Umgebungslicht.
     */
    public Color calculateAmbientReflection(Color ambient_light, Hit hit);

    /*
     * Berechnet die direkte Lichtreflexion einer Lichtquelle auf einem Objekt.
     * Diese Reflexion hängt vom Blickwinkel des Betrachters, der Oberflächennormalen
     * und der Richtung der Lichtquelle ab.
     *
     * - to_viewer: Die Richtung vom Schnittpunkt (Hit) zur Kamera.
     * - hit: Das Schnittpunkt-Objekt, das Informationen über den getroffenen Punkt enthält.
     * - light: Die Lichtquelle, deren Reflexion berechnet werden soll.
     * Rückgabewert: Die berechnete Farbe der Reflexion für diesen speziellen Lichteinfall.
     */
    public Color calculateDirectLightReflection(Direction to_viewer, Hit hit, DirectLight light);
}

