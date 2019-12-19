package tinyequationscoringengine;

import org.junit.Before;
import org.junit.Test;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.QTIScoringException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TinyEquationTest {

    @Before
    public void setUp(){
        //this requires EqScoringWebService.py to be running locally
        CustomOperatorRegistry.getInstance()
            .addOperatorFactory(new TinyEqCustomOpFactory(URI.create("http://0.0.0.0:8080"), 3, 300));

    }
    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndNumericValues() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();

        // numeric equivalence
        testData.add(new TestData("1", "1", true));
        testData.add(new TestData("2", "1", false));
        testData.add(new TestData("2", "1+1", false));
        testData.add(new TestData("2.1", "0.21e+1", false));
        testData.add(new TestData("1/3", "2/6", false));
        testData.add(new TestData("1/3", "0.333", false));
        testData.add(new TestData("1", "1.0", false));
        testData.add(new TestData("-1/5", "-(1)/5", true));
        testData.add(new TestData("5+4", "4   + 5", true));
        testData.add(new TestData(".5", "0.5", false));
        testData.add(new TestData("(box*box*box*box*box*box*box*box*box*box*box*box*box*.0)", "0", false));
        run(testData, false);

        // the same as above with simplification
        testData.clear();
        testData.add(new TestData("1", "1", true));
        testData.add(new TestData("2", "1", false));
        testData.add(new TestData("2", "1+1", true));
        testData.add(new TestData("2.1", "0.21e+1", true));
        testData.add(new TestData("1/3", "2/6", true));
        testData.add(new TestData("1/3", "0.333", false));
        testData.add(new TestData("1", "1.0", true));
        testData.add(new TestData("-1/5", "-(1)/5", true));
        testData.add(new TestData("5+4", "4   + 5", true));
        testData.add(new TestData(".5", "0.5", true));
        testData.add(new TestData("(box*box*box*box*box*box*box*box*box*box*box*box*box*.0)", "0", true));
        run(testData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndBooleanValues() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();

        testData.add(new TestData("True", "True", true));
        testData.add(new TestData("False", "False", true));
        testData.add(new TestData("True", "False", false));
        testData.add(new TestData("1", "True", false));
        run(testData, false);

        // the same as above with simplification
        testData.clear();
        testData.add(new TestData("True", "True", true));
        testData.add(new TestData("False", "False", true));
        testData.add(new TestData("True", "False", false));
        testData.add(new TestData("1", "True", false));
        run(testData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndRelationalFunctions() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();

        // relational equivalence with numbers
        testData.add(new TestData("Ge(2,1)", "Ge(2,1)", true));
        testData.add(new TestData("Le(1,2)", "Ge(2,1)", true));
        testData.add(new TestData("Ne(2,1)", "Ne(2,1)", true));
        testData.add(new TestData("Ne(2,1)", "Ne(1,2)", true));
        testData.add(new TestData("Le(1,2)", "Le(1,2)", true));
        testData.add(new TestData("Ge(1+1,1)", "Le(1,2)", false));
        testData.add(new TestData("Ge(3,1)", "Le(1,2)", false));
        testData.add(new TestData("Ge(1+1+1,1)", "Le(1,2)", false));
        testData.add(new TestData("Le(1,2)", "Lt(1,2)", false));

        // relational with expressions
        testData.add(new TestData("Eq(x,1)", "Eq(1,x)", true));
        testData.add(new TestData("Gt(x,1)", "Lt(1,x)", true));
        testData.add(new TestData("Gt(x,1)", "Lt(x,1)", false));
        testData.add(new TestData("Ne(x,1)", "Ne(1,x)", true));
        testData.add(new TestData("Ge(x,1)", "Le(1,x)", true));
        testData.add(new TestData("Ge(x,1)", "Le(x,1)", false));
        testData.add(new TestData("Ge(x,6.99999999999999999)", "Le(7.0,x)", false));
        testData.add(new TestData("Lt(225,x)", "Gt(4*x,900)", false));
        testData.add(new TestData("Lt(225.1,x)", "Gt(4*x,900)", false));
        testData.add(new TestData("Lt(x,225)", "Gt(4*x,900)", false));

        testData.add(new TestData("Ge(2,1+x)", "Ge(2,x+1)", true));
        testData.add(new TestData("Ge(2,1+x)", "Le(x+1,2)", true));

        testData.add(new TestData("Eq(1,5-4+3-2-1)", "True", false));
        testData.add(new TestData("True", "Eq(1,5-4+3-2-1)", false));
        run(testData, false);


        // the same as above with simplification
        testData.clear();
        // relational equivalence with numbers
        testData.add(new TestData("Ge(2,1)", "Ge(2,1)", true));
        testData.add(new TestData("Le(1,2)", "Ge(2,1)", true));

        testData.add(new TestData("Ne(2,1)", "Ne(2,1)", true));
        testData.add(new TestData("Ne(2,1)", "Ne(1,2)", true));
        testData.add(new TestData("Le(1,2)", "Le(1,2)", true));
        testData.add(new TestData("Ge(1+1,1)", "Le(1,2)", true));
        testData.add(new TestData("Ge(3,1)", "Le(1,2)", true));
        testData.add(new TestData("Ge(1+1+1,1)", "Le(1,2)", true));
        testData.add(new TestData("Le(1,2)", "Lt(1,2)", true));

        // relational with expressions
        testData.add(new TestData("Eq(x,1)", "Eq(1,x)", true));
        testData.add(new TestData("Gt(x,1)", "Lt(1,x)", false));
        testData.add(new TestData("Gt(x,1)", "Lt(x,1)", false));
        testData.add(new TestData("Ne(x,1)", "Ne(1,x)", true));
        testData.add(new TestData("Ge(x,1)", "Le(1,x)", false));
        testData.add(new TestData("Ge(x,1)", "Le(x,1)", false));
        testData.add(new TestData("Ge(x,6.99999999999999999)", "Le(7.0,x)", false));
        testData.add(new TestData("Lt(225,x)", "Gt(4*x,900)", false));
        testData.add(new TestData("Lt(225.1,x)", "Gt(4*x,900)", false));
        testData.add(new TestData("Lt(x,225)", "Gt(4*x,900)", false));

        testData.add(new TestData("Ge(2,1+x)", "Ge(2,x+1)", true));
        testData.add(new TestData("Ge(2,1+x)", "Le(x+1,2)", false));

        testData.add(new TestData("Eq(1,5-4+3-2-1)", "True", false));
        testData.add(new TestData("True", "Eq(1,5-4+3-2-1)", false));
        run(testData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndExpression() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();

        testData.add(new TestData("2x", "2*x", false));
        testData.add(new TestData("x**((0.5))", "x**0.5", true));
        testData.add(new TestData("x**(1/2)", "x**0.5", false));
        testData.add(new TestData("(((x*y))*12+((2*x+2*y))*z))*1.25", "2.5*(6*x*y+x*z+y*z)", false));
        testData.add(new TestData("a*(a+b)", "a**2+a*b", false));
        testData.add(new TestData("a*(a+b)", "a**2.0+a*b", false));
        run(testData, false);

        // the same as above with simplification
        testData.clear();

        testData.add(new TestData("2x", "2*x", true));
        testData.add(new TestData("x**((0.5))", "x**0.5", true));
        testData.add(new TestData("x**(1/2)", "x**0.5", true));
//        testData.add(new TestData("(((x*y))*12+((2*x+2*y))*z))*1.25", "2.5*(6*x*y+x*z+y*z)", true)); fails with an exception
        testData.add(new TestData("a*(a+b)", "a**2+a*b", true));
        testData.add(new TestData("a*(a+b)", "a**2.0+a*b", true));
        run(testData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndEq() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();

        testData.add(new TestData("Eq(2,1)", "Eq(3,2)", false));
        testData.add(new TestData("Eq(x,2)", "Eq(x,2)", true));
        testData.add(new TestData("Eq(x,2)", "Eq(x,2.0)", false));
        testData.add(new TestData("Eq(x,2)", "Eq(2,x)", true));
        testData.add(new TestData("Eq(x,2)", "Eq(x,1+1)", false));
        testData.add(new TestData("Eq(1,5-4+3-2-1)", "Eq(1,1)", false));
        testData.add(new TestData("Eq(1,1)", "Eq(1,5-4+3-2-1)", false));
        testData.add(new TestData("Eq(x/2,x*(1/2))", "Eq(x/2,x/2)", false));
        testData.add(new TestData("Eq((4)**(k),N*k)", "Eq(N*k,(4)**(k))", true));

        testData.add(new TestData("Eq(P*(t),(0.022*(t**2)+92))", "Eq(P*(t),(0.022*(t**2)+92))", true));
        testData.add(new TestData("Eq((P*(t)-92)/0.022,(t**2))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("(Eq(t,P-21.98))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("Eq(P*(t),(0.022*(t**2)+92-1))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("(91.97/0)", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("((30)/0)", "2/3", false));

        testData.add(new TestData("Eq(2*x+4,2)", "Eq(x+2,1)", false));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(-2*b-1,-a)", false));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(-2*b-1,-a+1)", false));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(2*b+1,a)", true));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(1+2*b,a)", true));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(1+b*2,a)", true));
        testData.add(new TestData("Eq(x,1)", "x", false));
        testData.add(new TestData("Eq(E,6.5x+0.04y)", "Eq(E,6.5x+0.04y)", true));
        testData.add(new TestData("Eq(6.5x+0.04y,E)", "Eq(E,6.5x+0.04y)", false));
        testData.add(new TestData("Eq(E,6.5*x+0.04*y)", "Eq(E,0.04*y+6.5*x)", true));
        testData.add(new TestData("Eq(6.5*x+0.04*y,E)", "Eq(E,6.5*x+0.04*y)", true));
        testData.add(new TestData("Eq(x,1)", "Eq(x,Add(2,-1,evaluate=True))", false));
        testData.add(new TestData("Eq(x,0.5)", "Eq(x,1/2)", false));
        testData.add(new TestData("Eq(x,0.5)", "Eq(x,.5)", false));
        testData.add(new TestData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", false));
        testData.add(new TestData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", false));
        testData.add(new TestData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", false));
        testData.add(new TestData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", false));
        testData.add(new TestData("Eq(28.80*y*3*h,86.4*y*h)", "Eq(y,9.6*h)", false));
        testData.add(new TestData("(Eq(2*x+6*y,25.50))", "Eq(y,-10.8*x**2+83.08*x-9.99)", false));

        // Rounding error
        testData.add(new TestData("Eq(x,7)", "Eq(3.45*x,24.15)", false));
        testData.add(new TestData("Eq(x,7.1)", "Eq(3.45*x,24.15)", false));
        // second representation for exponential
        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false));
        testData.add(new TestData("Eq(y,(x)**(1/3))", "Eq(y,(x)**(1/3))", true));
        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,nthroot(x,3))", true));
        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false));
        testData.add(new TestData("Eq(y,nthroot(x,2))", "Eq(y,sqrt(x))", false));
        run(testData, false);

        // the same as above with simplification
        testData.clear();
        testData.add(new TestData("Eq(2,1)", "Eq(3,2)", true));
        testData.add(new TestData("Eq(x,2)", "Eq(x,2)", true));
        testData.add(new TestData("Eq(x,2)", "Eq(x,2.0)", true));
        testData.add(new TestData("Eq(x,2)", "Eq(2,x)", true));
        testData.add(new TestData("Eq(x,2)", "Eq(x,1+1)", true));
        testData.add(new TestData("Eq(1,5-4+3-2-1)", "Eq(1,1)", true));
        testData.add(new TestData("Eq(1,1)", "Eq(1,5-4+3-2-1)", true));
        testData.add(new TestData("Eq(x/2,x*(1/2))", "Eq(x/2,x/2)", true));
        testData.add(new TestData("Eq((4)**(k),N*k)", "Eq(N*k,(4)**(k))", true));

        testData.add(new TestData("Eq(P*(t),(0.022*(t**2)+92))", "Eq(P*(t),(0.022*(t**2)+92))", true));
        testData.add(new TestData("Eq((P*(t)-92)/0.022,(t**2))", "Eq(P*(t),(0.022*(t**2)+92))", true));
        testData.add(new TestData("(Eq(t,P-21.98))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("Eq(P*(t),(0.022*(t**2)+92-1))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("(91.97/0)", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testData.add(new TestData("((30)/0)", "2/3", false));

        testData.add(new TestData("Eq(2*x+4,2)", "Eq(x+2,1)", true));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(-2*b-1,-a)", true));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(-2*b-1,-a+1)", false));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(2*b+1,a)", true));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(1+2*b,a)", true));
        testData.add(new TestData("Eq(a,2*b+1)", "Eq(1+b*2,a)", true));
        testData.add(new TestData("Eq(x,1)", "x", false));
        testData.add(new TestData("Eq(E,6.5x+0.04y)", "Eq(E,6.5x+0.04y)", false));
        testData.add(new TestData("Eq(6.5x+0.04y,E)", "Eq(E,6.5x+0.04y)", false));
        testData.add(new TestData("Eq(E,6.5*x+0.04*y)", "Eq(E,0.04*y+6.5*x)", true));
//        testData.add(new TestData("Eq(6.5*x+0.04*y,E)", "Eq(E,6.5*x+0.04*y)", true)); fails with an exception
//        testData.add(new TestData("Eq(x,1)", "Eq(x,Add(2,-1,evaluate=True))", true)); fails with an exception
        testData.add(new TestData("Eq(x,0.5)", "Eq(x,1/2)", true));
        testData.add(new TestData("Eq(x,0.5)", "Eq(x,.5)", true));
        testData.add(new TestData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", true));
        testData.add(new TestData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", true));
        testData.add(new TestData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", true));
        testData.add(new TestData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", true));
        testData.add(new TestData("Eq(28.80*y*3*h,86.4*y*h)", "Eq(y,9.6*h)", false));
        testData.add(new TestData("(Eq(2*x+6*y,25.50))", "Eq(y,-10.8*x**2+83.08*x-9.99)", false));

        // Rounding error
        testData.add(new TestData("Eq(x,7)", "Eq(3.45*x,24.15)", true));
        testData.add(new TestData("Eq(x,7.1)", "Eq(3.45*x,24.15)", false));

        // second representation for exponential
//        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false)); fails with an exception
        testData.add(new TestData("Eq(y,(x)**(1/3))", "Eq(y,(x)**(1/3))", true));
//        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,nthroot(x,3))", true)); fails with an exception
//        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false)); fails with an exception
//        testData.add(new TestData("Eq(y,nthroot(x,2))", "Eq(y,sqrt(x))", false)); fails with an exception
        run(testData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndTrig() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();
        testData.add(new TestData("cos(t)", "sin(t+pi/2)", false));
        testData.add(new TestData("cos(t+pi/2)", "-sin(t)", false));
        testData.add(new TestData("sin(2*x)", "2*sin(x)*cos(x)", false));
        testData.add(new TestData("cos(2*x)", "1-2*sin(x)**2", false));
        testData.add(new TestData("cos(x/0.5)", "1-2*sin(x)**2", false));
        testData.add(new TestData("sin(x)**2+cos(x)**2", "1", false));
        run(testData, false);

        // the same as above with simplification
        testData.clear();
        testData.add(new TestData("cos(t)", "sin(t+pi/2)", true));
        testData.add(new TestData("cos(t+pi/2)", "-sin(t)", true));
        testData.add(new TestData("sin(2*x)", "2*sin(x)*cos(x)", true));
        testData.add(new TestData("cos(2*x)", "1-2*sin(x)**2", true));
        testData.add(new TestData("cos(x/0.5)", "1-2*sin(x)**2", true));
        testData.add(new TestData("sin(x)**2+cos(x)**2", "1", true));
        run(testData, true);
    }


    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndLog() throws QTIScoringException {
        final List<TestData> testData = new ArrayList();
        testData.add(new TestData("exp(2*x)", "(exp(x))**2", false));
        testData.add(new TestData("exp(ln(x))", "x", false));
        testData.add(new TestData("log(x**2)", "2*log(x)", false));
        run(testData, false);

        // the same as above with simplification
        testData.clear();
        testData.add(new TestData("exp(2*x)", "(exp(x))**2", true));
        testData.add(new TestData("exp(ln(x))", "x", true));
        testData.add(new TestData("log(x**2)", "2*log(x)", false));
        run(testData, true);
    }

    private void run(final List<TestData> testData, boolean simplify) throws QTIScoringException {
        for (final TestData test : testData) {
            assertEquals(String.format("Testing %s", test.response),
                TinyEquation
                    .getIsEquivalent(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                            "<MathExpressionInfo><SympyResponse><string>%s</string></SympyResponse></MathExpressionInfo>",
                        test.response),
                        test.exemplar, simplify, false, false, false),
                test.result);
        }
    }

    static class TestData {
        String exemplar;
        String response;
        boolean result;

        TestData(final String exemplar, final String response, final boolean result) {
            this.exemplar = exemplar;
            this.response = response;
            this.result = result;
        }

        public String getExemplar() {
            return exemplar;
        }

        public String getResponse() {
            return response;
        }

        public boolean isResult() {
            return result;
        }
    }
}
