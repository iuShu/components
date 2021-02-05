package org.iushu.formatter.beans;

/**
 * @author iuShu
 * @since 1/28/21
 */
public class ChineseText implements TranslatibleText{

    private String source;
    private String chinese;

    @Override
    public String text() {
        return getChinese();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "ChineseText{" +
                "source='" + source + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}
