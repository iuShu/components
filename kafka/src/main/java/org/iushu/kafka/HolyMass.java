package org.iushu.kafka;

import java.util.*;

import static org.iushu.kafka.BFactors.getAllFactors;

/**
 * Created by iuShu on 19-2-14
 */
public class HolyMass {

    public static void main(String[] args) {
        for (int i=1; i<12; i++) {
            int[] arr = adjustPageSize(i, 5, 3);
            System.out.println("  pageNo: " + arr[0]);
            System.out.println("pageSize: " + arr[1]);
            System.out.println("   begin: " + arr[2]);
            System.out.println("     end: " + arr[3]);
            System.out.println("-------------------<" + i + ">--------------------");
        }
    }

    public static int[] adjustPageSize(int adjustPageNo, int originalPageSize, int alreadyShowSize) {
        int beginShowIndex = adjustPageNo * originalPageSize - 1;
        int endShowIndex = beginShowIndex + originalPageSize - 1;
        if (adjustPageNo == 1)
            return new int[]{1, originalPageSize + alreadyShowSize, beginShowIndex, endShowIndex};

        // find a most appropriated factors
        int amplifiedIndex = endShowIndex;
        int maxAmplifiedIndex = originalPageSize * 2;
        BFactors factors;
        while (true) {
            factors = getAllFactors(amplifiedIndex, maxAmplifiedIndex);
            factors.removeNotInRange(beginShowIndex);
            if (!factors.isEmpty())
                break;

            amplifiedIndex++;
        }

        int[] mostAppropriated = factors.getMostAppropriated(originalPageSize);
        return new int[]{mostAppropriated[0], mostAppropriated[1], beginShowIndex, endShowIndex};
    }

}

class BFactors {

    private Map<Integer, Integer> factors = new HashMap<>();

    public void addFactor(int x, int y, int max) {
//        if (x > max || y > max)
//            return;
//        if (factors.containsKey(y) && factors.containsValue(x))
//            return;
        factors.put(x, y);
    }

    public void removeNotInRange(int min) {
        Map<Integer, Integer> temp = new HashMap<>(factors);
        for (Map.Entry<Integer, Integer> entry : temp.entrySet())
            if (entry.getKey() * entry.getValue() - entry.getValue() + 1 > min)
                factors.remove(entry.getKey());
    }

    public int[] getMostAppropriated(int exchangeFactor) {
        Map.Entry<Integer, Integer> mostAppropriated = null;
        for (Map.Entry<Integer, Integer> entry : factors.entrySet()) {
            if (mostAppropriated == null || max(mostAppropriated) > max(entry))
                mostAppropriated = entry; // we need small number
        }

//        if (mostAppropriated.getKey() > exchangeFactor && mostAppropriated.getKey() < mostAppropriated.getValue())
//            return new int[]{mostAppropriated.getValue(), mostAppropriated.getKey()};   // exchange
        return new int[]{mostAppropriated.getKey(), mostAppropriated.getValue()};
    }

    public int max(Map.Entry<Integer, Integer> entry) {
        int x = entry.getKey();
        int y = entry.getValue();
        return x > y ? x : y;
    }

    public static BFactors getAllFactors(int number, int max) {
        BFactors factors = new BFactors();
        for (int i=2; i<number; i++)
            if (number % i == 0)
                factors.addFactor(i, number / i, max);
        return factors;
    }

    public boolean isEmpty() {
        return factors.isEmpty();
    }

    public Map<Integer, Integer> getBFactors() {
        return Collections.unmodifiableMap(factors);
    }

}
