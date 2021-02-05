package org.iushu.expression.beans;

import java.util.*;

/**
 * @author iuShu
 * @since 1/18/21
 */
public class Village {

    private int id;
    private String name;
    private List<Creature> creatures = new ArrayList<>(8);
    private Human[] managers;
    private Map<String, Human> category = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    public Human[] getManagers() {
        return managers;
    }

    public void setManagers(Human[] managers) {
        this.managers = managers;
    }

    public Map<String, Human> getCategory() {
        return category;
    }

    public Human addCategory(String title, Human human) {
        return category.put(title, human);
    }

    @Override
    public String toString() {
        return "Village{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creatures=" + creatures +
                ", managers=" + Arrays.toString(managers) +
                ", category=" + category +
                '}';
    }
}
