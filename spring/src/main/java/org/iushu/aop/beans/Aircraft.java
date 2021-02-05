package org.iushu.aop.beans;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author iuShu
 * @since 1/7/21
 */
public class Aircraft implements Maintainable {

    private int fuel;
    private int airfoil;
    private int wheel;
    private int landingGear;

    public void fly() {
        taxing();
        pullUp();
        putAwayLandingGear();
    }

    public void taxing() {
        pushCarStandby();
        move();
    }

    public void move() {
        checkHealth();
    }

    public void checkHealth() {
        tank();
        airfoil();
        wheel();
        landingGear();
    }

    public void pushCarStandby() { }
    public void pullUp() { }
    public void putAwayLandingGear() {
        throw new IllegalArgumentException();
    }

    @Override
    public void maintain() {
        setFuel(100);
        setAirfoil(100);
        setLandingGear(100);
        setWheel(100);
        System.out.println("aircraft: maintain finished");
//        throw new RuntimeException("Hikari");     // for ThrowsAdvice
    }

    public boolean tank() {
        return fuel == 100;
    }

    public boolean airfoil() {
        return airfoil >= 70;
    }

    public boolean wheel() {
        return wheel >= 60;
    }

    public boolean landingGear() {
        return landingGear >= 80;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getAirfoil() {
        return airfoil;
    }

    public void setAirfoil(int airfoil) {
        this.airfoil = airfoil;
    }

    public int getWheel() {
        return wheel;
    }

    public void setWheel(int wheel) {
        this.wheel = wheel;
    }

    public int getLandingGear() {
        return landingGear;
    }

    public void setLandingGear(int landingGear) {
        this.landingGear = landingGear;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "fuel=" + fuel +
                ", airfoil=" + airfoil +
                ", wheel=" + wheel +
                ", landingGear=" + landingGear +
                '}';
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        os.writeObject(this);
    }

}
