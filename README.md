# Computergrafik Grundlagen

Diese Projekte haben wir in unserem Hochschul-Modul 'Computergrafik-Grundlagen' erstellt. Ziel des Projekts war es, sich mit den 
grundlegenden Konzepten und Techniken der Computergrafik auseinanderzusetzen. Die Klassen bauen jeweils aufeinander auf und erweitern sich im Laufe der Aufgaben. 
Die Themen sind jeweils in Unterordner unterteilt:

1. **Bilderzeugung**
    In dieser Aufgabe haben wir uns mit der Speicherung von Bildern und der Verhinderung von Alias-Effekten beschäftigt. Die ersten Bilder, die wir erstellt haben, bestand aus einfachen farbigen 2D Discs.

    ![Discs](images/A_bilderzeugung-discs-stratified-gamma.png)

2. **Beginn unseres Raytracing-Implementierung**
    Ziel war es eine minimale Implementierung des Raytracing-Algorithmus. Zunächst werden Strahlen erzeugt, die dann mit einer Kugel geschnitten werden.

    ![Spheres](images/B_raytracing_start-spheres.png)

3. **Szene mit Shapes, Hintergrund, Ebene**
    Hier führten wir einige Code-Refactoring-Maßnahmen durch, um den Raytracer „skalierbar“ zu machen für komplexere hierarchische Szenen, die aus verschiedenen Objekttypen bestehen. Dabei werden auch der Hintergrund und Ebenen im Raum als solche Objekttypen hinzugefügt.

    ![Szene](images/C_szene_background_ebene-picture-01.png)

4. **Direkte Beleuchtung und Schatten**
    In dieser Aufgabe führten wir die Interfaces "Material" und "DirectLight" ein und erweitern den Raytracer um direkte Beleuchtung mittels des Phong-Modells, Lichtquellen und Schattenberechnung.

    ![Szene mit Beleuchtung und Schatten](images//D_beleuchtung_schatten-spheres.png)

5. **Transformationen**
    Die Aufgabe bestand darin, die Transformationsmatritzen in die Kamera-Klasse und in die "Gruppenknoten" zu intergrieren. Dann übten wir den Umgang mit Transformationen an praktischen Aufgaben. Am Ende war das Ziel ein gelenkiges Wesen zu erstellen.

    ![Menschformen](images/E_transformation-human.png)

6. **Texturen**
    Hier integrierten wir prozedurale Texturen und Bildtexturen samt Texturtransformationen in einfache Shapes.

    ![Szene mit Texturen](images/F_texturen-scene.png)

7. **Rekursiver Path Tracer**
    Wir erweiterten den Raytracer um ein rekursives Monte Carlo Path Tracing, um globale Beleuchtungseffekte darstellen zu können.

    ![Szene mit globale Beleuchtung](images/G_rekursiv_pathtracing-scene.png)

8. **Beschleunigung**
    In der letzten Aufgabe führten wir eine Hüllkörper-Hierarchie ein, um die Berechnungszeit für das Rendern von Szenen mit 1000 Shapes auf c.a. 17 Sekunden zu reduzieren.

    ![Szene mit 10.000 Kugeln](images/H_beschleunigung-scene-10000.png)

## Technologien
    - Java Version 21
    - Maven zur Verwaltung von Abhängigkeiten