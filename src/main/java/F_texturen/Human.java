package F_texturen;

import static lib_cgtools.Matrix.*;
import static lib_cgtools.Vector.*;

import lib_cgtools.Matrix;

/*
 * Stellt ein Menschen in der Szene dar.
 */
public class Human extends Group{
    double headRadius = 6.0;
    double chest = 8.0;
    double stomach = 4.0;
    double hip = 3.0;
    double shoulderRadius = 4.0;
    double elbowRadius = 3.0;
    double armWidth = 2.0;
    double armLength = 4.0;

    Material material;
    double shoulderAngleLeft;
    double elbowAngleLeft;
    double shoulderAngleRight; 
    double elbowAngleRight;
    double hipAngleLeft;
    double hipAngleRight;
    double kneeAngleLeft;
    double kneeAngleRight;

    public Human(
        Material material, 
        double shoulderAngleLeft, 
        double elbowAngleLeft, 
        double shoulderAngleRight, 
        double elbowAngleRight,
        double hipAngleLeft,
        double hipAngleRight,
        double kneeAngleLeft,
        double kneeAngleRight) {

        super(identity);
        this.material = material;
        this.shoulderAngleLeft = shoulderAngleLeft;
        this.elbowAngleLeft  = elbowAngleLeft;
        this.shoulderAngleRight = shoulderAngleRight;
        this.elbowAngleRight = elbowAngleRight;
        this.hipAngleLeft = hipAngleLeft;
        this.hipAngleRight = hipAngleRight;
        this.kneeAngleLeft = kneeAngleLeft;
        this.kneeAngleRight = kneeAngleRight;

        create(); //Koerper erstellen
        //Kopf
        //Brust
        //Bauch
        //Huefte
        //Arme hinzufuegen
        //Beine den Hueften hinzufuegen


    }
    private void create() {
        createHead();
        createUpperBody();
        createArms();
        createLegs();
    }
    private void createHead() {
        Matrix neckTransform = Matrix.multiply(
            translation(direction(0,-headRadius/2,0)),
            scaling(1, 2.2, 1));
        
        Group headGroup = new Group(identity);
        Group neckGroup = new Group(neckTransform);

        headGroup.add(new Sphere(zero, headRadius, material));
        neckGroup.add(new Cube(headRadius/1.5, this.material));

        this.add(headGroup);
        headGroup.add(neckGroup);
    }

    private void createUpperBody() {
        Matrix chestTransform = Matrix.multiply(
            translation(direction(0,(-headRadius*2),0)),
            scaling(3, 2, 1));
        
        Matrix stomachTransform = Matrix.multiply(
            translation(direction(0, (-chest), 0)),
            scaling(1, 2, 2));
        
        Matrix hipsTransform = Matrix.multiply(
            translation(direction(0, -stomach/1.5, 0)),
            scaling(2, 1, 1));

        Group chestGroup = new Group(chestTransform);
        Group stomachGroup = new Group(stomachTransform);
        Group hipGroup = new Group(hipsTransform);

        chestGroup.add(new Cube(chest, this.material));
        stomachGroup.add(new Cube(stomach, this.material));
        hipGroup.add(new Cube(hip, this.material));
        this.add(chestGroup);
        chestGroup.add(stomachGroup);
        stomachGroup.add(hipGroup);
    }

    private void createArms() {
        
        Limb armLeft = new Limb(
            this.material, 
            shoulderAngleLeft,
            elbowAngleLeft,
            shoulderRadius,
            elbowRadius,
            armWidth,
            armLength,
            armWidth,
            armLength);
        
        Limb armRight = new Limb(
            this.material, 
            shoulderAngleRight,
            elbowAngleLeft,
            shoulderRadius,
            elbowRadius,
            armWidth,
            armLength,
            armWidth,
            armLength);
        
        Group leftArmGroup = new Group(multiply(
            translation(direction(-(chest*2), (-headRadius*2.2), 1)),
            scaling(1,1.2,1)
            ));
        leftArmGroup.add(armLeft);
        this.add(leftArmGroup);

        Group rightArmGroup = new Group(multiply(
            translation(direction((chest*2), (-headRadius*2.2), 1)),
            scaling(1,1.2,1)
            ));
        rightArmGroup.add(armRight);
        this.add(rightArmGroup);
    }

    private void createLegs() {
        
        Limb legLeft = new Limb(
            this.material, 
            hipAngleLeft,
            kneeAngleLeft,
            shoulderRadius,
            elbowRadius,
            armWidth,
            armLength,
            armWidth,
            armLength);
        
        Limb legRight = new Limb(
            this.material, 
            hipAngleRight,
            kneeAngleRight,
            shoulderRadius,
            elbowRadius,
            armWidth,
            armLength,
            armWidth,
            armLength);
        
        Group leftLegGroup = new Group(multiply(
            translation(direction(-(hip*2.2), ((-(1.5*headRadius)-chest-stomach-hip)*2), 1)),
            scaling(1.5,1.5,1)));
        leftLegGroup.add(legLeft);
        this.add(leftLegGroup);
        
        Group rightLegGroup = new Group(multiply(
            translation(direction((hip*2.2), ((-(1.5*headRadius)-chest-stomach-hip)*2), 1)),
            scaling(1.5,1.5,1)));
        rightLegGroup.add(legRight);
        this.add(rightLegGroup);
    }
}
