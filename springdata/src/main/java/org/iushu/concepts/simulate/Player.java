package org.iushu.concepts.simulate;

import java.util.Random;

/**
 * @author iuShu
 * @since 3/23/21
 */
public class Player {

    private String name;
    private int level;

    private int blood;
    private int magic;

    private int shield;
    private int resistance;

    private Object weapon;

    public void battle() {
        int randomDeduce = new Random().nextInt(10);
        setBlood(blood - randomDeduce);
    }

    public boolean isAlive() {
        return blood > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public Object getWeapon() {
        return weapon;
    }

    public void setWeapon(Object weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", blood=" + blood +
                ", magic=" + magic +
                ", shield=" + shield +
                ", resistance=" + resistance +
                ", weapon=" + weapon +
                '}';
    }
}
