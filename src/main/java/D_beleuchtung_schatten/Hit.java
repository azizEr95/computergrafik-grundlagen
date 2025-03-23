package D_beleuchtung_schatten;

import lib_cgtools.*;

/*
 * Die Hit-Klasse repräsentiert ein Trefferereignis, das auftritt, wenn ein Strahl (Ray) mit 
 * einem Objekt in der 3D-Szene schneidet. Diese Klasse enthält Informationen über den 
 * Treffpunkt, den Normalenvektor an diesem Punkt und die Farbe des Objekts an der 
 * Trefferstelle.
 */
public record Hit(double t, Point hit, Direction normalV, Material material) {
    // t: Strahlparameter
    //hit: der Position des Trefferpunkts 
    //normalV: Normalvektor für die Kugel
    // material: Farbe der Oberfläche
}
 