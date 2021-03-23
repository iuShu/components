package org.iushu.concepts.simulate;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 * @author iuShu
 * @since 3/23/21
 */
public class GameWorld {

    private static final String KEY_CHAPTER_AMOUNT = "game.world.chapter.amount";
    private static final String KEY_CHAPTER_ID_PREFIX = "game.chapter.id.";
    private static final String KEY_CHAPTER_SAFE_HOUSE_AMOUNT = "game.chapter.safe.house.amount";
    private static final String KEY_CHAPTER_SAFE_HOUSE_PREFIX = "game.chapter.safe.house.";
    private static final String SAFE_HOUSE_INFO_SPLIT = "/";

    public static final int DEFAULT_PLAYER_BLOOD = 350;

    private List<GameChapter> chapters;
    private Map<Player, GameChapter> world = new HashMap<>();

    private static GameWorld INSTANCE = new GameWorld();

    private GameWorld() {
        loadChapters();
    }

    public static GameWorld instance() {
        return INSTANCE;
    }

    public PlayerTransactionObject startGame() {
        Player player = newPlayer();
        GameChapter initial = initialGameChapter();
        world.put(player, initialGameChapter());
        play(player, initial);
        return new PlayerTransactionObject(player, initial);
    }

    public void play(Player player, GameChapter chapter) {

    }

    public GameChapter currentChapter(Player player) {
        Assert.notNull(player);
        GameChapter progress = world.get(player);
        if (progress == null) {
            progress = initialGameChapter();
            world.put(player, progress);
        }
        return progress;
    }

    public GameChapter initialGameChapter() {
        GameChapter config = chapters.get(0);
        GameChapter initial = new GameChapter();
        initial.setChapter(config.getChapter());
        initial.setSafeHouses(config.getSafeHouses());
        return initial;
    }

    private Player newPlayer() {
        Player player = new Player();
        player.setName("Rod Johnson");
        player.setLevel(1);
        player.setBlood(DEFAULT_PLAYER_BLOOD);
        player.setMagic(270);
        player.setShield(30);
        player.setResistance(15);
        player.setWeapon(null);
        return player;
    }

    private void loadChapters() {
        Resource resource = new DefaultResourceLoader().getResource("classpath:org/iushu/concepts/simulate/game-chapter.properties");
        try {
            PropertySource propertySource = new ResourcePropertySource(resource);
            int chapterAmount = Integer.valueOf(propertySource.getProperty(KEY_CHAPTER_AMOUNT).toString());
            List<GameChapter> chapters = new ArrayList<>(chapterAmount);
            for (int i=1; i<=chapterAmount; i++) {
                int chapterId = Integer.valueOf(propertySource.getProperty(KEY_CHAPTER_ID_PREFIX + i).toString());
                int safeHouseAmount = Integer.valueOf(propertySource.getProperty(KEY_CHAPTER_SAFE_HOUSE_AMOUNT).toString());
                GameChapter chapter = new GameChapter();
                chapter.setChapter(chapterId);
                for (int j=1; j<=safeHouseAmount; j++) {
                    String text = propertySource.getProperty(KEY_CHAPTER_SAFE_HOUSE_PREFIX + chapterId + "." + j).toString();
                    String[] info = text.split(SAFE_HOUSE_INFO_SPLIT);
                    GameChapter.SafeHouse safeHouse = new GameChapter.SafeHouse();
                    safeHouse.setSavepointId(Integer.valueOf(info[0]));
                    safeHouse.setProgress(Integer.valueOf(info[1]));
                    safeHouse.setSavepointName(info[2]);
                    safeHouse.setOwner(chapter);
                    chapter.addSafeHouse(safeHouse);
                }
                chapters.add(chapter);
            }
            this.chapters = Collections.unmodifiableList(chapters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
