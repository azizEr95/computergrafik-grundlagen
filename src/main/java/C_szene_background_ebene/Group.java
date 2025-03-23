package C_szene_background_ebene;

import java.util.ArrayList;

/*
 * Die Group-Klasse repräsentiert eine Sammlung von Formen (Shapes), die als Gruppe behandelt werden.
 * Die Gruppe kann mehrere Objekte enthalten, und bei einem Strahl-Schnitt (Ray Intersection) wird
 * das nächstgelegene Schnittpunkt-Objekt zurückgegeben.
 */
public class Group implements Shape {
    
    private ArrayList<Shape> forms; // Liste der enthaltenen Shapes (Formen)

    /*
     * Konstruktor für eine leere Gruppe von Formen.
     * Die Liste der Formen wird initialisiert, um später Elemente aufnehmen zu können.
     */
    public Group() {
        this.forms = new ArrayList<>();
    }

    /*
     * Fügt eine neue Form (Shape) zur Gruppe hinzu.
     * - shape: Die Form, die der Gruppe hinzugefügt werden soll.
     */
    public void add(Shape shape) {
        forms.add(shape);
    }

    /*
     * Berechnet den Schnittpunkt des gegebenen Strahls (Ray) mit der Gruppe.
     * Durchläuft alle in der Gruppe enthaltenen Formen und gibt den nächstgelegenen Schnittpunkt zurück.
     * Falls der Strahl keine Form in der Gruppe trifft, wird null zurückgegeben.
     * 
     * - ray: Der Strahl, der auf die Gruppe trifft.
     * Rückgabewert: Das Hit-Objekt des nächstgelegenen Treffers oder null, falls kein Treffer vorliegt.
     */
    @Override
    public Hit intersect(Ray ray) {
        Hit closestHit = null; // Speichert den nächsten Schnittpunkt
    
        // Iteriert durch alle Shapes in der Gruppe und prüft auf Schnittpunkte
        for (Shape s : forms) {
            Hit hit = s.intersect(ray);

            // Falls es eine Kollision gibt und der Schnittpunkt näher ist, aktualisieren
            if (hit != null && (closestHit == null || hit.t() < closestHit.t())) {
                closestHit = hit;
            }
        }

        return closestHit; // Rückgabe des nächstgelegenen Schnittpunkts oder null
    }
}
