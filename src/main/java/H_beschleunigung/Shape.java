package H_beschleunigung;

/*
 * Das Shape-Interface definiert die grundlegenden Operationen, die jedes geometrische Objekt (z.B. eine Kugel, Scheibe, etc.)
 * in der Szene implementieren muss, um mit dem Raytracing-System zu interagieren.
 * Es enthält Methoden, um mit Strahlen zu interagieren und die Begrenzung des Objekts zu bestimmen.
 */
public interface Shape {

    // Diese Methode prüft, ob der übergebene Strahl das Objekt schneidet.
    // Falls ein Schnittpunkt gefunden wird, gibt sie das Hit-Objekt zurück.
    // Ansonsten wird null zurückgegeben.
    public Hit intersect(Ray ray);
    
    // Diese Methode gibt die Begrenzungsbox (Bounding Box) des Objekts zurück.
    // Eine Bounding Box wird verwendet, um das Objekt in einem schnellen Bounding-Box-Intersektionstest einzugrenzen.
    public BoundingBox bounds();
}

