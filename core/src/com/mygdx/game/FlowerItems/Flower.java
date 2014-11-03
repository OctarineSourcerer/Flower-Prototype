package com.mygdx.game.FlowerItems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;
import com.sun.javafx.geom.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Not an interface - the flower should be procedural
 */
public class Flower { //These are their own classes as they may need unique functionality later
    public List<PetalFlyweight> petals;
    public Head head;
    public Stem stem;

    public Flower(Petal mainPetal, Head flowerHead, Stem flowerStem, int petalCount, PetalStyle petalArrangement) {
        head = flowerHead;
        stem = flowerStem;
        petals = new ArrayList<PetalFlyweight>();
        petals.add(new PetalFlyweight(mainPetal));
        ArrangePetals(petalArrangement, petalCount, 0);
    }
    public void Draw(SpriteBatch batch) {
        head.sprite.draw(batch);
        for (Flower.PetalFlyweight petalType : petals) {
            petalType.DrawCentered(batch);
        }
    }
    void ArrangePetals(PetalStyle arrangement, int count, int petalIndex) {
        float sepAngle = 360f / (float) count;
        switch (arrangement) {
            case Touching:
                PetalFlyweight thisFlyweight = petals.get(petalIndex);
                float petalWidth = FlowerMaths.GetPetalWidth(sepAngle, 0.5f, head.radius);
                Petal relevantPetal = thisFlyweight.petal;
                relevantPetal.sprite.setOrigin(relevantPetal.sprite.getWidth() / 2, 0); //origin at bottom thingy
                //relevantPetal.Scale(petalWidth / relevantPetal.sprite.getWidth()); //scales petal
                for (float i = 0; i < sepAngle * count; i += sepAngle) {
                    Point2D location = FlowerMaths.AddPoints(head.GetCenter()
                            , FlowerMaths.GetPetalPos(head.radius, i));
                    //location.y += relevantPetal.sprite.getHeight();
                    //location.x += relevantPetal.sprite.getWidth();
                    thisFlyweight.AddPetal(location, i);
                }
                break;
        }
    }

    public enum PetalStyle {
        Overlapping,
        Touching,
    }

    public class PetalFlyweight {
        Petal petal;
        public List<Point2D> locations;
        public List<Float> rotations;

        PetalFlyweight(Petal petal) {
            this.petal = petal;
            this.petal.sprite.setOrigin(petal.sprite.getWidth() / 2, 0);
            locations = new ArrayList<Point2D>();
            rotations = new ArrayList<Float>();
        }

        PetalFlyweight(Petal petal, Point2D location) {
            this.petal = petal;
            this.petal.sprite.setOrigin(petal.sprite.getWidth() / 2, 0);
            locations = new ArrayList<Point2D>();
            rotations = new ArrayList<Float>();
            AddPetal(location, 0);
        }

        /**
         * @param location
         * @param rotation
         */
        void AddPetal(Point2D location, float rotation) {
            locations.add(location);
            rotations.add(rotation);
        }

        public void DrawCentered(SpriteBatch batch) {
            for (int i = 0; i < locations.size(); i++) {
                Point2D loc = locations.get(i);
                petal.SetPosWithRotationalOrigin(loc);
                petal.sprite.setRotation(-(rotations.get(i)));
                petal.sprite.draw(batch);
            }
        }
    }
}