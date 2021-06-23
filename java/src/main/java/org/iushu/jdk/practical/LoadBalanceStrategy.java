package org.iushu.jdk.practical;

import org.iushu.jdk.Utils;
import org.iushu.jdk.practical.ConsistentHashing.LoadBalanceNode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author iuShu
 * @since 6/22/21
 */
public class LoadBalanceStrategy {

    private static final List<String> SERVERS;
    private static final List<String> CLIENTS;

    static {
        Random random = new Random();
        List<String> clients = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            StringBuffer ip = new StringBuffer();
            ip.append(random.nextInt(127) + ".");
            ip.append(random.nextInt(255) + ".");
            ip.append(random.nextInt(255) + ".");
            ip.append(random.nextInt(255));
            clients.add(ip.toString());
        }
        clients.add(clients.get(0));
        clients.add(clients.get(1));
        clients.add(clients.get(2));
        CLIENTS = Collections.unmodifiableList(clients);

        List<String> servers = new ArrayList<>();
        servers.add("192.168.0.1:1111");
        servers.add("192.168.0.2:2222");
        servers.add("192.168.0.3:3333");
        servers.add("192.168.0.4:4444");
        servers.add("192.168.0.5:5555");
        servers.add("192.168.0.6:6666");
        servers.add("192.168.0.7:7777");
        SERVERS = Collections.unmodifiableList(servers);
    }

    static void roundRobin() {
        int r = 0;
        for (int i = 0; i < CLIENTS.size(); i++) {
            int selected = r++;
            r = r >= SERVERS.size() ? 0 : r;    // range check
            System.out.println(String.format("[RoundRobin %2d] %15s    %15s", i, CLIENTS.get(i), SERVERS.get(selected)));
        }
    }

    static void random() {
        Random random = new Random();
        for (int i = 0; i < CLIENTS.size(); i++) {
            int selected = random.nextInt(SERVERS.size() - 1);
            System.out.println(String.format("[Random %2d] %15s    %15s", i, CLIENTS.get(i), SERVERS.get(selected)));
        }
    }

    static void weight() {
        int[] weights = new int[SERVERS.size()];
        Random random = new Random();
        for (int i = 0; i < weights.length; i++)
            weights[i] = random.nextInt(5);     // weight configurations

        for (int i = 0; i < CLIENTS.size(); i++) {
            int selected ;
        }
    }

    static void hashMode() {
        for (int i = 0; i < CLIENTS.size(); i++) {
            int selected = (int) Utils.longIP(CLIENTS.get(i)) % SERVERS.size();
            System.out.println(String.format("[Hash %2d] %15s    %15s", i, CLIENTS.get(i), SERVERS.get(selected)));
        }
    }

    static void consistentHashing() {
        ConsistentHashing hashing = new ConsistentHashing();
        for (String server : SERVERS)
            hashing.addNode(server);

        for (int i = 0; i < CLIENTS.size(); i++) {
            String client = CLIENTS.get(i);
            LoadBalanceNode node = hashing.selectNode(client);
            node.access(client);
            System.out.println(String.format("[Consistent %2d] %15s    %15s", i, client, node.getKey()));
        }
    }

    static void dubboConsistentHashCode() {
        int defaultVirtualScale = 160;
        String address = "192.168.24.42";
        for (int i = 0; i < defaultVirtualScale / 4; i++) {
            byte[] digest = Utils.digest((address + i).getBytes());  // 16 bits
            for (int j = 0; j < 4; j++) {   // calculate every 4 bits from low to high (4 x j = 16)
                long hash = dubboHash(digest, j);
                System.out.println(String.format("[DUBBO] %s-%d\t%s", address + i, j, hash));
            }
        }
    }

    // make sure every bits are included in hashing                 // for example
    static long dubboHash(byte[] digest, int number) {              // digest[0 1 2 3] = -39 -76 45 -26
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)      // 1110 0110 0000 0000 0000 0000 0000 0000
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)    // 0000 0000 0010 1101 0000 0000 0000 0000
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)     // 0000 0000 0000 0000 1011 0100 0000 0000
                | (digest[number * 4] & 0xFF))                      // 0000 0000 0000 0000 0000 0000 1101 1001
                & 0xFFFFFFFFL;  // 2^32-1                           // 1110 0110 0010 1101 1011 0100 1101 1001
    }

    public static void main(String[] args) {
        roundRobin();
        random();
        weight();
        hashMode();
        consistentHashing();
        dubboConsistentHashCode();
    }

}
