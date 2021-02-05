package org.iushu.expression;

import org.iushu.expression.beans.*;
import org.iushu.expression.component.ELParserContext;
import org.iushu.expression.component.FocusWrapperBeanResolver;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.expression.*;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author iuShu
 * @since 1/15/21
 */
public class Application {

    /**
     * NOTE: String requires surrounded by semicolon in SpEL expression
     *
     * @see org.springframework.expression.spel.ast.Literal
     */
    public static void literalExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello SpEL'");
        System.out.println(expression.getValue());

        expression = parser.parseExpression("6.0221415E+23");
        Double doubleVal = (Double) expression.getValue();
        System.out.println(doubleVal);

        expression = parser.parseExpression("0x7FFFFFFF");  // Integer.MAX_VALUE
        Integer integer = expression.getValue(Integer.class);
        System.out.println(integer);

        expression = parser.parseExpression("true");
        Boolean bool = (Boolean) expression.getValue();
        System.out.println(bool);

        expression = parser.parseExpression("null");
        Object object = expression.getValue();
        System.out.println(object);
    }

    /**
     * @see org.springframework.expression.spel.ast.CompoundExpression
     * @see org.springframework.expression.spel.ast.MethodReference
     * @see org.springframework.expression.MethodExecutor
     * @see org.springframework.expression.MethodResolver
     * @see org.springframework.expression.spel.ast.PropertyOrFieldReference
     */
    public static void methodInvocation() {
        // java.lang method invoke
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello '.concat('SpEL')");
        System.out.println(expression.getValue());

        expression = parser.parseExpression("'Hello SpEL'.bytes");
        byte[] bytes = (byte[]) expression.getValue();
        System.out.println(bytes.length);

        expression = parser.parseExpression("new String('Constructor').toUpperCase()");
        System.out.println(expression.getValue(String.class));  // with given value type

        // customized class method invoke
        Human human = new Human();
        expression = parser.parseExpression("setName('Pupin')");
        expression.getValue(human);  // human.setName("Pupin"); can also receive the result if any.
        System.out.println(human);

        // customized class as method parameter
        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        Woman princess = new Woman();
        princess.setName("Lily");
        context.setVariable("pupin", human);
        expression = parser.parseExpression("partner = #pupin");
        expression.getValue(context, princess);
        System.out.println(princess);

        // invoke customized class method (only static method)
        Method isEqualsMethod = ReflectionUtils.findMethod(ReflectionUtils.class, "isEqualsMethod", Method.class);
        Method addCategory = ReflectionUtils.findMethod(Village.class, "addCategory", String.class, Human.class);
        context.setVariable("isEqualsMethod", isEqualsMethod);
        context.setVariable("addCategory", addCategory);
        expression = parser.parseExpression("#isEqualsMethod(#addCategory)");
        Boolean bool = expression.getValue(context, Boolean.class);
        System.out.println(bool);
    }

    /**
     * Common usage
     *
     * @see org.springframework.expression.spel.ast.Operator
     */
    public static void classExpression() {
        Human man = new Man();
        man.setName("Nicola Tesla");
        man.setAge(13);

        // Parsing expression by a root object

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("age");      // a property of class Human
        System.out.println(expression.getValue(man));

        expression = parser.parseExpression("age == 13");           // even apply the relation operator in property
        System.out.println(expression.getValue(man, Boolean.class));

        expression = parser.parseExpression("name.toUpperCase()");  // also method invocation is available
        System.out.println(expression.getValue(man, String.class));
    }

    /**
     * @see org.springframework.expression.spel.ast.Indexer Array, List
     * @see org.springframework.expression.spel.ast.ConstructorReference
     * @see org.springframework.expression.spel.ast.InlineList
     * @see org.springframework.expression.spel.ast.InlineMap
     */
    public static void fullTypePropSupport() {
        Woman anna = new Woman();
        anna.setName("Anna");
        anna.setAge(26);

        // property operation
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("age - 3");
        Integer modifiedAge = expression.getValue(anna, Integer.class);
        System.out.println("operator: " + modifiedAge);

        // read hierarchy property
        Woman eva = new Woman();
        eva.setName("Eva");
        eva.setAge(25);
        eva.setPartner(anna);
        expression = parser.parseExpression("partner.name");
        String partnerName = expression.getValue(eva, String.class);
        System.out.println("hierarchy: " + partnerName);

        // arrays property
        Village village = new Village();
        village.setManagers(new Human[]{anna, eva});
        expression = parser.parseExpression("managers[1]");
        Human manager1 = expression.getValue(village, Human.class);
        System.out.println("array: " + manager1);

        // list property
        village.getCreatures().add(eva);
        village.getCreatures().add(anna);
        expression = parser.parseExpression("creatures[1]");
        Creature creature = expression.getValue(village, Creature.class);
        System.out.println("list: " + creature);

        // map property
        village.addCategory("executive", eva);
        village.addCategory("leader", anna);
        expression = parser.parseExpression("category['leader']");
        Human leader = expression.getValue(village, Human.class);
        System.out.println("map: " + leader);

        // inline array (force type convert)
        expression = parser.parseExpression("new int[3]");
        int[] array = (int[]) expression.getValue();
        System.out.println("inline array: " + Arrays.toString(array));

        expression = parser.parseExpression("new int[]{1,2,3}");
        array = (int[]) expression.getValue();
        System.out.println("inline array: " + Arrays.toString(array));

        // inline list
        expression = parser.parseExpression("{1,2,3,4}");
        List list = expression.getValue(List.class);
        System.out.println("inline list: " + list + "\t" + list.get(0).getClass().getName());

        expression = parser.parseExpression("{{'a','b'},{'c','d'}}");
        list = expression.getValue(List.class);
        System.out.println("inline list: " + list + "\t" + list.get(0).getClass().getName());

        // inline map
        expression = parser.parseExpression("{name:'Joe',age:65}");
        Map map = expression.getValue(Map.class);
        System.out.println("inline map: " + map + "\t" + map.getClass().getName());

        expression = parser.parseExpression("{killer:{name:'Leon',age:39},partner:{name:'Pity',age:23}}");
        map = expression.getValue(Map.class);
        System.out.println("inline map: " + map + "\t" + map.get("killer").getClass().getName());
    }

    /**
     * It is enable to add customize type converters
     *
     * @see org.springframework.expression.EvaluationContext
     */
    public static void typeConversion() {
        Human woman = new Woman();
        Village village = new Village();
        village.getCreatures().add(null);   // placeholder
        System.out.println(village);

        EvaluationContext evaluationContext = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("creatures[0]");
        expression.setValue(evaluationContext, village, woman);  // add 'woman' in the 0 index place
        System.out.println(village);
    }

    /**
     * Config before context prepared to parse the expression
     *
     * @see org.springframework.expression.spel.SpelParserConfiguration
     */
    public static void parseConfiguration() {
        Human woman = new Woman();
        Village village = new Village();
        System.out.println(village);

        SpelParserConfiguration configuration = new SpelParserConfiguration(false, true);
        ExpressionParser parser = new SpelExpressionParser(configuration);
        Expression expression = parser.parseExpression("creatures[1]");  // auto grow collections
        expression.setValue(village, woman);
        System.out.println(village);
        System.out.println(village.getCreatures().size());  // the element amount is expanded to 2
    }

    /**
     * Get beans from the BeanFactory
     *
     * @see org.springframework.expression.BeanResolver
     * @see org.springframework.context.expression.BeanFactoryResolver
     * @see org.springframework.expression.spel.ast.BeanReference#FACTORY_BEAN_PREFIX
     */
    public static void beanReference() {
        Resource resource = new ClassPathResource("org/iushu/expression/spring-exp.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);

        BeanResolver beanResolver = new BeanFactoryResolver(beanFactory);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(beanResolver);

        // common Bean from BeanFactory
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("@village");
        Village village = expression.getValue(context, Village.class);
        System.out.println(village);

        // FactoryBean from BeanFactory
        expression = parser.parseExpression("&factoryBean");
        Object val = expression.getValue(context);
        System.out.println(val);

        // common Bean created by FactoryBean
        expression = parser.parseExpression("@factoryBean");
        val = expression.getValue(context);
        System.out.println(val);

        // simple simulation
        context.setBeanResolver(new FocusWrapperBeanResolver(beanFactory));
        expression = parser.parseExpression("@village");
        val = expression.getValue(context);
        System.out.println(val);
    }

    /**
     * Interpreter: provide dynamic flexibility but not optimum performance (Default mode)
     * Compiler: Optimum performance but sacrificed flexibility in runtime (dynamic types)
     *
     * @see org.springframework.expression.spel.SpelCompilerMode
     * @see org.springframework.expression.spel.SpelParserConfiguration
     */
    public static void interpreterAndCompiler() {
        Human woman = new Woman();
        woman.setName("Steve Jobs");

        // turn on Compiler in programmatic
        SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, Application.class.getClassLoader());
        ExpressionParser parser = new SpelExpressionParser(configuration);
        Expression expression = parser.parseExpression("name");
        System.out.println(expression.getValue(woman));

        // turn on Compiler in system property
        System.setProperty("spring.expression.compiler.mode", SpelCompilerMode.IMMEDIATE.name());
    }

    /**
     * Expression in bean definitions
     * NOTE: Integrating SpEL expression in a BeanFactory, needs import context module.
     * NOTE: There is a small difference in resolving the expression between BeanFactory and ApplicationContext.
     *
     * (XML-based)
     * @see org.springframework.beans.factory.config.ConfigurableBeanFactory#setBeanExpressionResolver(BeanExpressionResolver)
     * @see org.springframework.beans.factory.support.AbstractBeanFactory#beanExpressionResolver
     * @see org.springframework.context.expression.StandardBeanExpressionResolver resolve SpEL expression (core)
     * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues)
     *
     * (Annotation-based)
     * @see org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor responsible for processing @Autowired & @Value
     * @see org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver resolves SpEL expression in @Value
     */
    public static void beanDefinition() {
        System.setProperty("tesla.height", "170.5");
        Resource resource = new ClassPathResource("org/iushu/expression/spring-exp.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);

        // for resolve the SystemProperties expression
        StandardEnvironment environment = new StandardEnvironment();
        beanFactory.registerSingleton("systemProperties", environment.getSystemProperties());

        // core expression resolver, holding an instance of org.springframework.expression.ExpressionParser
        BeanExpressionResolver expressionResolver = new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader());
        beanFactory.setBeanExpressionResolver(expressionResolver);

        Man tesla = (Man) beanFactory.getBean("tesla");
        Woman eva = (Woman) beanFactory.getBean("eva");
        System.out.println(tesla);
        System.out.println(eva);

        // Annotation-base

        System.setProperty("Anna.marriage", "1");    // for @Value(..) in field
        BeanDefinition definition = new RootBeanDefinition(Woman.class);
        definition.getPropertyValues().add("name", "anna");
        beanFactory.registerBeanDefinition("anna", definition);

        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // resolve expression combined with StandardBeanExpressionResolver
        AutowireCandidateResolver resolver = new ContextAnnotationAutowireCandidateResolver();
        beanFactory.setAutowireCandidateResolver(resolver);

        Woman anna = (Woman) beanFactory.getBean("anna");
        System.out.println(anna);
    }

    /**
     * @see org.springframework.expression.ParserContext
     * @see org.springframework.expression.common.TemplateParserContext
     */
    public static void customized() {
        ExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new ELParserContext();    // ${..}
        Expression expression = parser.parseExpression("${#name}", parserContext);
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", "Huff");
        String value = expression.getValue(context, String.class);
        System.out.println(value);
    }

    public static void main(String[] args) {
//        literalExpression();
//        methodInvocation();
//        classExpression();
//        fullTypePropSupport();
//        typeConversion();
//        parseConfiguration();
//        beanReference();
//        interpreterAndCompiler();
//        beanDefinition();
        customized();
    }

}
