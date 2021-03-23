package org.iushu.concepts.simulate;

import org.springframework.util.Assert;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author iuShu
 * @since 3/23/21
 */
public class GameChapter {

    private int chapter;
    private TreeMap<Integer, SafeHouse> safeHouses = new TreeMap<>();
    private int progress = 0;

    public void addSafeHouse(SafeHouse safeHouse) {
        Assert.notNull(safeHouse);
        safeHouses.put(safeHouse.getProgress(), safeHouse);
    }

    public boolean reachSafeHouse() {
        return progress != 0 && safeHouses.get(progress) != null;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public TreeMap<Integer, SafeHouse> getSafeHouses() {
        return safeHouses;
    }

    public void setSafeHouses(TreeMap<Integer, SafeHouse> safeHouses) {
        this.safeHouses = safeHouses;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    static class SafeHouse implements Savepoint {

        private int savepointId;
        private int progress;
        private String savepointName;
        private GameChapter owner;

        public void setSavepointId(int savepointId) {
            this.savepointId = savepointId;
        }

        @Override
        public int getSavepointId() {
            return savepointId;
        }

        public void setSavepointName(String savepointName) {
            this.savepointName = savepointName;
        }

        @Override
        public String getSavepointName() {
            return savepointName;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public GameChapter getOwner() {
            return owner;
        }

        public void setOwner(GameChapter owner) {
            this.owner = owner;
        }

        @Override
        public String toString() {
            return "SafeHouse{" +
                    "id=" + savepointId +
                    ", progress=" + progress +
                    ", name='" + savepointName + '\'' +
                    ", owner=" + owner +
                    '}';
        }

    }

}
