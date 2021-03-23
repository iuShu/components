package org.iushu.concepts.simulate;

import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.util.Assert;

import java.util.TreeMap;

/**
 * The object managing by the PlayerTransactionManager.
 * The object is DataSourceTransactionObject in DataSourceTransactionManager.
 *
 * @see org.springframework.jdbc.datasource.JdbcTransactionObjectSupport
 * @see org.springframework.jdbc.datasource.DataSourceTransactionManager.DataSourceTransactionObject
 * @see org.iushu.concepts.simulate.PlayerTransactionManager
 *
 * @author iuShu
 * @since 3/23/21
 */
public class PlayerTransactionObject implements SavepointManager, SmartTransactionObject {

    private Player player;
    private GameChapter chapter;

    public PlayerTransactionObject(Player player, GameChapter chapter) {
        Assert.notNull(player);
        this.player = player;
        this.chapter = chapter;
    }

    @Override
    public Object createSavepoint() throws TransactionException {
        TreeMap<Integer, GameChapter.SafeHouse> safeHouses = chapter.getSafeHouses();
        int currentProgress = chapter.getProgress();
        int previousSafeHouseId = safeHouses.floorKey(currentProgress);
        return safeHouses.get(previousSafeHouseId);
    }

    @Override
    public void rollbackToSavepoint(Object savepoint) throws TransactionException {
        GameChapter.SafeHouse safeHouse = (GameChapter.SafeHouse) savepoint;
        chapter.setProgress(safeHouse.getProgress());   // transport the player back to safe house
    }

    @Override
    public void releaseSavepoint(Object savepoint) throws TransactionException {
        if (savepoint == null)
            return;

        GameChapter.SafeHouse safeHouse = (GameChapter.SafeHouse) savepoint;
        System.out.println(">> releaseSavepoint: " + safeHouse.getSavepointName());
    }

    @Override
    public boolean isRollbackOnly() {
        return false;
    }

    @Override
    public void flush() {
        player.setLevel(player.getLevel() + 1);
    }

    public Player getPlayer() {
        return player;
    }

    public GameChapter getChapter() {
        return chapter;
    }

}
