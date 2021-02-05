package org.iushu.formatter.beans;

/**
 * @author iuShu
 * @since 1/28/21
 */
public class EnglishText implements TranslatibleText{

    private String source;
    private String english;

    @Override
    public String text() {
        return getEnglish();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return "EnglishText{" +
                "source='" + source + '\'' +
                ", english='" + english + '\'' +
                '}';
    }
}
