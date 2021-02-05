package org.iushu.expression.component;

import org.springframework.expression.ParserContext;

/**
 * @author iuShu
 * @since 1/22/21
 */
public class ELParserContext implements ParserContext {

    @Override
    public boolean isTemplate() {
        return true;
    }

    @Override
    public String getExpressionPrefix() {
        return "${";
    }

    @Override
    public String getExpressionSuffix() {
        return "}";
    }
}
