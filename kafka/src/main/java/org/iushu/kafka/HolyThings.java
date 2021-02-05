package org.iushu.kafka;

import java.util.*;

import static org.iushu.kafka.Factors.getAllFactors;
import static org.iushu.kafka.Factors.getMostAppropriated;

/**
 * Created by iuShu on 19-2-14
 */
public class HolyThings {

    public static void main(String[] args) {
        for (int i = 1; i < 21; i++) {
            int[] arr = adjustPageSize(i, 5, 3);
            System.out.println("  pageNo: " + arr[0]);
            System.out.println("pageSize: " + arr[1]);
            System.out.println("   begin: " + arr[2]);
            System.out.println("     end: " + arr[3]);
            System.out.println("------------------<" + i + ">---------------------");
        }
    }

    public static int[] adjustPageSize(int adjustPageNo, int originalPageSize, int alreadyShowSize) {
        int beginShowIndex = adjustPageNo * originalPageSize - 1;
        int endShowIndex = beginShowIndex + originalPageSize - 1;
        if (adjustPageNo == 1)
            return new int[]{1, originalPageSize + alreadyShowSize, beginShowIndex, endShowIndex};

        // find a most appropriated factors
        int amplifiedIndex = endShowIndex;
        List<Factors> found = new ArrayList<>();
        while (true) {
            Factors factors = getAllFactors(amplifiedIndex);
            factors.removeNotInRange(beginShowIndex);
            if (!factors.isEmpty())
                found.add(factors);

            amplifiedIndex++;
            if (found.size() >= 2) // find two factors for choose the optimums.
                break;
        }

        int[] mostAppropriated = getMostAppropriated(found.get(0), found.get(1));
        return new int[]{mostAppropriated[0], mostAppropriated[1], beginShowIndex, endShowIndex};
    }

}

class Factors {

    private Map<Integer, Integer> factors = new HashMap<>();

    public void addFactor(int x, int y) {
        factors.put(x, y);
    }

    public void removeNotInRange(int min) {
        Map<Integer, Integer> temp = new HashMap<>(factors);
        for (Map.Entry<Integer, Integer> entry : temp.entrySet())
            if (entry.getKey() * entry.getValue() - entry.getValue() + 1 > min)
                factors.remove(entry.getKey());
    }

    public static int[] getMostAppropriated(Factors f1, Factors f2) {
        int[] rs1 = f1.getMostAppropriated();
        int[] rs2 = f2.getMostAppropriated();
        return rs1[1] > rs2[1] ? rs2 : rs1;
    }

    private int[] getMostAppropriated() {
        Map.Entry<Integer, Integer> mostAppropriated = null;
        for (Map.Entry<Integer, Integer> entry : factors.entrySet()) {
            if (mostAppropriated == null || max(mostAppropriated) > max(entry) || mostAppropriated.getValue() < entry.getValue())
                mostAppropriated = entry;
        }

        return new int[]{mostAppropriated.getKey(), mostAppropriated.getValue()};
    }

    public int max(Map.Entry<Integer, Integer> entry) {
        int x = entry.getKey();
        int y = entry.getValue();
        return x > y ? x : y;
    }

    public static Factors getAllFactors(int number) {
        Factors factors = new Factors();
        for (int i = 2; i < number; i++)
            if (number % i == 0)
                factors.addFactor(i, number / i);
        return factors;
    }

    public boolean isEmpty() {
        return factors.isEmpty();
    }

    public Map<Integer, Integer> getFactors() {
        return Collections.unmodifiableMap(factors);
    }

}
