package G_rekursiv_pathtracing;

/*
 * Das Shape-Interface definiert die grundlegende Struktur für geometrische Formen,
 * die in der Raytracing-Szene verwendet werden. Jede Form, die dieses Interface
 * implementiert, muss eine Methode bereitstellen, um zu überprüfen, ob sie von einem 
 * gegebenen Ray getroffen wird.
 */
public interface Shape {

    /*
     * Bestimmt, ob ein gegebener Ray (Strahl) mit der Form kollidiert.
     * Falls eine Kollision auftritt, gibt die Methode ein Hit-Objekt mit Informationen 
     * über den Treffpunkt, die Normalenrichtung und die Farbe zurück.
     * 
     * - ray: Der zu testende Strahl
     * 
     * Rückgabewert:
     * - Ein Hit-Objekt mit Trefferdetails, falls der Ray die Form trifft
     * - Null, falls keine Kollision stattfindet
     */
    public Hit intersect(Ray ray);
}
