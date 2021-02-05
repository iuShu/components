package org.iushu.kafka;

import java.util.*;

import static org.iushu.kafka.TFactors.getAllFactors;
import static org.iushu.kafka.TFactors.getMostAppropriated;

/**
 * Created by iuShu on 19-2-14
 */
public class HolyTangle {

    public final static double amplifiedFactor = 2;
    public final static double maxPageSizeFactor = 4;

    public static void main(String[] args) {
        int totalPage = 100;
        int originalPageSize = 20;
        int alreadyShowSize = 1;
//        test(time, originalPageSize, alreadyShowSize);

        for (int a=5; a<=40; a++) {
            for (int i = 1; i < a; i++) {
                test(totalPage, a, i);
                System.out.println("-------------< " + a + "-" + i + " >--------------");
            }
        }
    }

    public static void test(int times, int originalPageSize, int alreadyShowSize) {
        int sum = 0;
        int max = 0;
        List<Integer> tooSlow = new ArrayList<>();
        for (int i=1; i<=times; i++) {
            int[] arr = adjustPageSize(i, originalPageSize, alreadyShowSize);
            int pageNo = arr[0];
            int pageSize = arr[1];
            int displayBeginIndex = arr[2];
            int displayEndIndex = arr[3];

            int fetchBeginIndex = pageNo * pageSize - pageSize + 1 ;
            int fetchEndIndex = pageNo * pageSize;
            int cutBeginIndex = displayBeginIndex - fetchBeginIndex;
            int cutEndIndex = pageSize - (fetchEndIndex - displayEndIndex);

            fetch(arr, cutBeginIndex, cutEndIndex);

//            System.out.println("  pageNo: " + pageNo);
//            System.out.println("pageSize: " + pageSize);
//            System.out.println("   begin: " + displayBeginIndex);
//            System.out.println("     end: " + displayEndIndex);
//            System.out.println("------------------< " + i + " >-------------------");

            sum += arr[1];
            max = arr[1] > max ? arr[1] : max;

            if (pageSize > originalPageSize * 2)
                tooSlow.add(pageSize);
        }

        System.out.println("average: " + sum / times);
        System.out.println("    max: " + max);
        System.out.println("   slow: " + tooSlow);
    }

    public static int[] adjustPageSize(int adjustPageNo, int originalPageSize, int alreadyShowSize) {
        if (originalPageSize <= alreadyShowSize)
            throw new IllegalArgumentException("originalPageSize <= alreadyShowSize is not allowed.");
        int beginShowIndex, endShowIndex;
        if (adjustPageNo == 1) {
            beginShowIndex = alreadyShowSize + 1;
            endShowIndex = beginShowIndex + originalPageSize - 1;
            return new int[]{1, originalPageSize + alreadyShowSize, beginShowIndex, endShowIndex};
        }

        endShowIndex = adjustPageNo * originalPageSize + alreadyShowSize;
        beginShowIndex = endShowIndex - originalPageSize + 1;

        // find a most appropriated factors
        int amplifiedIndex = endShowIndex;
        int maxAmplifiedIndex = (int) (amplifiedIndex * amplifiedFactor);

        int maxPageSize = (int) (originalPageSize * maxPageSizeFactor);
        List<TFactors> factorsList = findFactors(amplifiedIndex, beginShowIndex, maxAmplifiedIndex);
        int searchingIndex = 0;
        while (tooSlowPerformance(factorsList, maxPageSize)) {
            searchingIndex = factorsList.isEmpty() ? searchingIndex + 1 : factorsList.get(factorsList.size() - 1).getTarget() + 1;
            factorsList = findFactors(searchingIndex, beginShowIndex, maxAmplifiedIndex);
        }

        int[] mostAppropriated = getMostAppropriated(factorsList);
        return new int[]{mostAppropriated[0], mostAppropriated[1], beginShowIndex, endShowIndex};
    }

    public static List<TFactors> findFactors(int amplifiedIndex, int beginShowIndex, int maxAmplifiedIndex) {
        List<TFactors> factorsList = new ArrayList<>();
        while (true) {
            TFactors factors = getAllFactors(amplifiedIndex);
            factors.removeNotInRange(beginShowIndex);
            if (!factors.isEmpty())
                factorsList.add(factors);

            amplifiedIndex++;
            if (factorsList.size() >= 5 || amplifiedIndex >= maxAmplifiedIndex)
                break;
        }
        return factorsList;
    }

    public static List<Integer> dataList(int begin, int size) {
        Integer[] array = new Integer[size];
        for (int i=size-1; i>=0; i--)
            array[i] = begin--;
        return Arrays.asList(array);
    }

    public static boolean tooSlowPerformance(List<TFactors> factorsList, int maxPageSize) {
        int size = 0;
        int unMatchSize = 0;
        for (TFactors f : factorsList) {
            for (Map.Entry<Integer, Integer> entry : f.getTFactors().entrySet()) {
                size++;
                if (entry.getValue() > maxPageSize)
                    unMatchSize++;
            }
        }
        return unMatchSize == size;
    }

    public static void fetch(int[] array, int cutBeginIndex, int cutEndIndex) {
        List<Integer> list = new ArrayList<>(dataList(array[0] * array[1], array[1]));
//        System.out.println("   fetch: " + list);
//        System.out.println("beginCut: " + cutBeginIndex);
//        System.out.println("  endCut: " + cutEndIndex);
//        System.out.println("     cut: " + list.subList(cutBeginIndex, cutEndIndex));
    }

}

class TFactors {

    private int target;
    private Map<Integer, Integer> factors = new HashMap<>();

    public TFactors(int target) {
        this.target = target;
    }

    public void addFactor(int x, int y) {
        factors.put(x, y);
    }

    public void removeNotInRange(int min) {
        Map<Integer, Integer> temp = new HashMap<>(factors);
        for (Map.Entry<Integer, Integer> entry : temp.entrySet())
            if (entry.getKey() * entry.getValue() - entry.getValue() + 1 > min)
                factors.remove(entry.getKey());
    }

    public static int[] getMostAppropriated(List<TFactors> factorsList) {
        int[] mostAppropriated = null;
        for (TFactors f : factorsList) {
            int[] array = f.getMostAppropriated();
            if (mostAppropriated == null || mostAppropriated[1] > array[1])
                mostAppropriated = array;
        }
        return mostAppropriated;
    }

    public int[] getMostAppropriated() {
        Map.Entry<Integer, Integer> mostAppropriated = null;
        for (Map.Entry<Integer, Integer> entry : factors.entrySet()) {
            if (mostAppropriated == null || mostAppropriated.getValue() > entry.getValue())
                mostAppropriated = entry; // we need small number
        }

        return new int[]{mostAppropriated.getKey(), mostAppropriated.getValue()};
    }

    public static TFactors getAllFactors(int number) {
        TFactors factors = new TFactors(number);
        for (int i=2; i<number; i++)
            if (number % i == 0)
                factors.addFactor(i, number / i);
        return factors;
    }

    public boolean isEmpty() {
        return factors.isEmpty();
    }

    public Map<Integer, Integer> getTFactors() {
        return Collections.unmodifiableMap(factors);
    }

    public int getTarget() {
        return target;
    }
}
