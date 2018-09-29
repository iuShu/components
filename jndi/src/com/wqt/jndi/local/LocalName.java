package com.wqt.jndi.local;

import com.mysql.cj.util.StringUtils;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * pattern:
 *  com.wqt.jobcn.datasource.account
 *  com.wqt.jobcn.datasource.resume
 *
 * Created by iuShu on 18-9-20
 */
public class LocalName implements Name {

    public static final String DOT = ".";

    private transient String unparsed;
    private transient List<Lds> ldsList;

    public LocalName(String unparsed) {
        this(unparsed, null, 0, 0);
    }

    public LocalName(Name name) {
        LocalName n = (LocalName) name;
        ldsList = new ArrayList<>(n.ldsList);
    }

    public LocalName(String unparsed, List<Lds> ldsList, int begin, int end) {
        if (StringUtils.isNullOrEmpty(unparsed))
            throw new IllegalArgumentException("Param[unparsed] could not be NULL");

        this.unparsed = unparsed;
        this.ldsList = ldsList == null ? parse() : new ArrayList<>(ldsList.subList(begin, end));
    }

    @Override
    public Object clone() {
        return new LocalName(unparsed, ldsList, 0, ldsList.size());
    }

    @Override
    public int compareTo(Object obj) {
        // omit
        return 0;
    }

    @Override
    public int size() {
        return ldsList.size();
    }

    @Override
    public boolean isEmpty() {
        return ldsList.isEmpty();
    }

    @Override
    public Enumeration<String> getAll() {
        Iterator<Lds> it = ldsList.iterator();
        return new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return it.hasNext();
            }
            @Override
            public String nextElement() {
                return it.next().getName();
            }
        };
    }

    @Override
    public String get(int posn) {
        return ldsList.get(posn).getName();
    }

    @Override
    public Name getPrefix(int posn) {
        return new LocalName(unparsed, ldsList, 0, posn);
    }

    @Override
    public Name getSuffix(int posn) {
        return new LocalName(unparsed, ldsList, ldsList.size() - posn, ldsList.size());
    }

    @Override
    public boolean startsWith(Name n) {
        if (n == null)
            return false;
        if (!(n instanceof LocalName))
            return false;
        int len1 = size();
        int len2 = n.size();
        if (len1 < len2)
            return false;
        for (int i = 0; i < len2; i++) {
            if (!get(i).equals(n.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public boolean endsWith(Name n) {
        if (n == null)
            return false;
        if (!(n instanceof LocalName))
            return false;
        int len1 = size();
        int len2 = n.size();
        if (len1 < len2)
            return false;
        for (int i = len2; i > 0; i--) {
            if (!get(i).equals(n.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public Name addAll(Name suffix) throws InvalidNameException {
        if (!(suffix instanceof LocalName))
            throw new InvalidNameException("suffix class should be LocalName instance");

        LocalName ln = (LocalName) suffix;
        ldsList.addAll(ln.ldsList);
        return this;
    }

    @Override
    public Name addAll(int posn, Name n) throws InvalidNameException {
        if (posn > ldsList.size())
            throw new InvalidNameException("posn has greater than size");
        if (!(n instanceof LocalName))
            throw new InvalidNameException("n class should be LocalName instance");

        LocalName ln = (LocalName) n;
        ldsList.addAll(posn, ln.ldsList);
        return this;
    }

    @Override
    public Name add(String comp) throws InvalidNameException {
        if (StringUtils.isNullOrEmpty(comp))
            throw new InvalidNameException("comp could not NULL");

        ldsList.add(new Lds(comp));
        return this;
    }

    @Override
    public Name add(int posn, String comp) throws InvalidNameException {
        if (posn > ldsList.size())
            throw new InvalidNameException("posn has greater than size");
        if (StringUtils.isNullOrEmpty(comp))
            throw new InvalidNameException("comp could not NULL");

        ldsList.add(posn, new Lds(comp));
        return this;
    }

    @Override
    public Object remove(int posn) throws InvalidNameException {
        if (posn >= size() | posn < 0)
            throw new InvalidNameException("Illegal argument: posn");

        return ldsList.remove(posn).getName();
    }

    private List<Lds> parse() {
        unparsed = unparsed.trim();
        if (unparsed.contains(" "))
            throw new IllegalArgumentException("name could not contains space");
        String[] parts = unparsed.split("\\" + DOT);
        return Arrays.stream(parts).map(Lds::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof LocalName))
            return false;
        LocalName other = (LocalName) obj;
        if (other.size() != this.size())
            return false;
        for (int i=1; i<=size(); i++)
            if (!get(i).equals(other.get(i)))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        Enumeration<String> enm = getAll();
        StringBuilder stb = new StringBuilder("LocalName: ");
        while (enm.hasMoreElements())
            stb.append(enm.nextElement() + File.separator);
        return stb.toString().substring(0, stb.length() - 1);
    }

    class Lds {

        private String name;

        public Lds(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static void main(String[] args) throws InvalidNameException {
        LocalName ln = new LocalName("com.wqt.jndi.datasource");
        System.out.println(ln.getPrefix(2));
        System.out.println(ln.getPrefix(1));
        System.out.println(ln.getSuffix(3));
        System.out.println(ln.add("username"));
        System.out.println(ln.add(2, "httpService"));
        System.out.println(ln.remove(3));
        System.out.println(ln);
        System.out.println(ln.getSuffix(2));
        System.out.println(ln.remove(ln.size()-1));
        System.out.println(ln.remove(ln.size()-1));
        System.out.println(ln);

        LocalName nln = new LocalName("server/username");
        System.out.println(ln.addAll(nln));

        LocalName nnln = new LocalName("jobcn/enterprise");
        System.out.println(ln.addAll(2, nnln));
    }
}