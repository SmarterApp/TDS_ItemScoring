package tinyequationscoringengine;

import org.junit.Before;
import org.junit.Test;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.QTIScoringException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.NaN;
import static org.junit.Assert.assertEquals;

public class TinyEquationTest {

    @Before
    public void setUp() {
        //this requires EqScoringWebService.py to be running locally
        CustomOperatorRegistry.getInstance()
            .addOperatorFactory(new TinyEqCustomOpFactory(URI.create("http://0.0.0.0:8080"), 3, 300));

    }

    @Test
    public void itShouldTestEqWalkerFeatures() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();

        testsEquivalentData.add(new TestsEquivalentData("2+3, 4+5", "2+3 4+5", true));
        testsEquivalentData.add(new TestsEquivalentData("2+3, 4+5", "2+3", true));
        //        testsEquivalentData.add(new TestsEquivalentData("2<3==2+1==1+2", "2<3", true)); - fails to parse

        // plus to minus
        testsEquivalentData.add(new TestsEquivalentData("Eq(x-2,2)", "Eq(x+-2,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("x+-(y+5)", "x-(y+5)", true));
        testsEquivalentData.add(new TestsEquivalentData("(x/3)-(y+5)", "(x/3)+-(y+5)", true));
        testsEquivalentData.add(new TestsEquivalentData("(x/3)-(sin(3-7))", "(x/3)+-(sin(3+-7))", true));

        // change Ge
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Ge(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Lt(1,2)", "Gt(2,1)", true));

        // combine pluses
        testsEquivalentData.add(new TestsEquivalentData("5+(4+7)", "5+(4+7)", true));
        testsEquivalentData.add(new TestsEquivalentData("((5+4)+7)", "5+4+7", true));
        testsEquivalentData.add(new TestsEquivalentData("((5+4)+7)+9+10+11", "5+4+7+9+10+11", true));
        testsEquivalentData.add(new TestsEquivalentData("(((5+4)+7)+9)+10+11", "5+4+7+9+10+11", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7+(9+10)+11", "5+4+7+9+10+11", false));

        // combine multiplication
        testsEquivalentData.add(new TestsEquivalentData("5*(4*7)", "5*(4*7)", true));
        testsEquivalentData.add(new TestsEquivalentData("((5*4)*7)", "5*4*7", true));
        testsEquivalentData.add(new TestsEquivalentData("((5*4)*7)*9*10*11", "5*4*7*9*10*11", true));
        testsEquivalentData.add(new TestsEquivalentData("(((5*4)*7)*9)*10*11", "5*4*7*9*10*11", true));
        testsEquivalentData.add(new TestsEquivalentData("5*4*7*(9*10)*11", "5*4*7*9*10*11", false));

        // support commutation for sum
        testsEquivalentData.add(new TestsEquivalentData("5+4+7", "4+7+5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7 + sin(3)", "sin(3)+4+7+5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7 + sin(3)", "4+sin(3)+7+5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7+9+10+11", "4+11+10+7+5+9", true));

        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1+y)", "Eq(1+2*b+y,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("sin(2*b+1+y)", "sin(1+2*b+y)", true));
        testsEquivalentData.add(new TestsEquivalentData("sin(2*b+1+z+y)", "sin(1+2*b+y+z)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(1+b*2,a)", true));

        runIsEquivalent(testsEquivalentData, false);

    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndNumericValues() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();

        // numeric equivalence
        testsEquivalentData.add(new TestsEquivalentData("1", "1", true));
        testsEquivalentData.add(new TestsEquivalentData("2", "1", false));
        testsEquivalentData.add(new TestsEquivalentData("2", "1+1", false));
        testsEquivalentData.add(new TestsEquivalentData("5/6", "5*1/6", false));
        testsEquivalentData.add(new TestsEquivalentData("2.1", "0.21e+1", false));
        testsEquivalentData.add(new TestsEquivalentData("5-3", "5+-3", true));
        testsEquivalentData.add(new TestsEquivalentData("5-x", "5+-x", true));
        testsEquivalentData.add(new TestsEquivalentData("5/6", "5*(1/6)", false));
        testsEquivalentData.add(new TestsEquivalentData("1/3", "0.333", false));
        testsEquivalentData.add(new TestsEquivalentData("1", "1.0", false));
        testsEquivalentData.add(new TestsEquivalentData("-1/5", "-(1)/5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4", "4   + 5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4", "4+5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+(4+7)", "4+(7+5)", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7", "4+7+5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7+9+10+11", "4+11+10+7+5+9", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4+7+(9+10)+11", "(4+11)+10+7+5+9", false));
        testsEquivalentData.add(new TestsEquivalentData("5*4", "4*5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4", "9", false));
        testsEquivalentData.add(new TestsEquivalentData(".5", "0.5", false));
        testsEquivalentData
            .add(new TestsEquivalentData("(box*box*box*box*box*box*box*box*box*box*box*box*box*.0)", "0", false));
        runIsEquivalent(testsEquivalentData, false);

        // the same as above with simplification
        testsEquivalentData.clear();
        testsEquivalentData.add(new TestsEquivalentData("1", "1", true));
        testsEquivalentData.add(new TestsEquivalentData("2", "1", false));
        testsEquivalentData.add(new TestsEquivalentData("2", "1+1", true));
        testsEquivalentData.add(new TestsEquivalentData("2.1", "0.21e+1", true));
        testsEquivalentData.add(new TestsEquivalentData("1/3", "2/6", true));
        testsEquivalentData.add(new TestsEquivalentData("1/3", "0.333", false));
        testsEquivalentData.add(new TestsEquivalentData("1", "1.0", true));
        testsEquivalentData.add(new TestsEquivalentData("-1/5", "-(1)/5", true));
        testsEquivalentData.add(new TestsEquivalentData("5+4", "4   + 5", true));
        testsEquivalentData.add(new TestsEquivalentData(".5", "0.5", true));
        testsEquivalentData
            .add(new TestsEquivalentData("(box*box*box*box*box*box*box*box*box*box*box*box*box*.0)", "0", true));
        runIsEquivalent(testsEquivalentData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndBooleanValues() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();

        testsEquivalentData.add(new TestsEquivalentData("True", "True", true));
        testsEquivalentData.add(new TestsEquivalentData("False", "False", true));
        testsEquivalentData.add(new TestsEquivalentData("True", "False", false));
        testsEquivalentData.add(new TestsEquivalentData("1", "True", false));
        runIsEquivalent(testsEquivalentData, false);

        // the same as above with simplification
        testsEquivalentData.clear();
        testsEquivalentData.add(new TestsEquivalentData("True", "True", true));
        testsEquivalentData.add(new TestsEquivalentData("False", "False", true));
        testsEquivalentData.add(new TestsEquivalentData("True", "False", false));
        testsEquivalentData.add(new TestsEquivalentData("1", "True", false));
        runIsEquivalent(testsEquivalentData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndRelationalFunctions() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();

        // relational equivalence with numbers
        testsEquivalentData.add(new TestsEquivalentData("Ge(2,1)", "Ge(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(((2)),1)", "Ge(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Ge(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ne(2,1)", "Ne(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ne(2,1)", "Ne(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Le(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(1+1,1)", "Le(1,2)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ge(3,1)", "Le(1,2)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ge(1+1+1,1)", "Le(1,2)", false));
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Lt(1,2)", false));

        // relational with expressions
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,1)", "Eq(1,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Gt(x,1)", "Lt(1,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Gt(x,1)", "Lt(x,1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ne(x,1)", "Ne(1,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(x,1)", "Le(1,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(x,1)", "Le(x,1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ge(x,6.99999999999999999)", "Le(7.0,x)", false));
        testsEquivalentData.add(new TestsEquivalentData("Lt(225,x)", "Gt(4*x,900)", false));
        testsEquivalentData.add(new TestsEquivalentData("Lt(225.1,x)", "Gt(4*x,900)", false));
        testsEquivalentData.add(new TestsEquivalentData("Lt(x,225)", "Gt(4*x,900)", false));

        testsEquivalentData.add(new TestsEquivalentData("Ge(2,1+x)", "Ge(2,x+1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(2,1+x)", "Le(x+1,2)", true));

        testsEquivalentData.add(new TestsEquivalentData("Eq(1,5-4+3-2-1)", "True", false));
        testsEquivalentData.add(new TestsEquivalentData("True", "Eq(1,5-4+3-2-1)", false));
        runIsEquivalent(testsEquivalentData, false);


        // the same as above with simplification
        testsEquivalentData.clear();
        // relational equivalence with numbers
        testsEquivalentData.add(new TestsEquivalentData("Ge(2,1)", "Ge(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Ge(2,1)", true));

        testsEquivalentData.add(new TestsEquivalentData("Ne(2,1)", "Ne(2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ne(2,1)", "Ne(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Le(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(1+1,1)", "Le(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(3,1)", "Le(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(1+1+1,1)", "Le(1,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Le(1,2)", "Lt(1,2)", true));

        // relational with expressions
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,1)", "Eq(1,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Gt(x,1)", "Lt(1,x)", false));
        testsEquivalentData.add(new TestsEquivalentData("Gt(x,1)", "Lt(x,1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ne(x,1)", "Ne(1,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(x,1)", "Le(1,x)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ge(x,1)", "Le(x,1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Ge(x,6.99999999999999999)", "Le(7.0,x)", false));
        testsEquivalentData.add(new TestsEquivalentData("Lt(225,x)", "Gt(4*x,900)", false));
        testsEquivalentData.add(new TestsEquivalentData("Lt(225.1,x)", "Gt(4*x,900)", false));
        testsEquivalentData.add(new TestsEquivalentData("Lt(x,225)", "Gt(4*x,900)", false));

        testsEquivalentData.add(new TestsEquivalentData("Ge(2,1+x)", "Ge(2,x+1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Ge(2,1+x)", "Le(x+1,2)", false));

        testsEquivalentData.add(new TestsEquivalentData("Eq(1,5-4+3-2-1)", "True", false));
        testsEquivalentData.add(new TestsEquivalentData("True", "Eq(1,5-4+3-2-1)", false));
        runIsEquivalent(testsEquivalentData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndExpression() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();

        testsEquivalentData.add(new TestsEquivalentData("2+x", "x+2", true));
        testsEquivalentData.add(new TestsEquivalentData("2x", "2*x", false));
        testsEquivalentData.add(new TestsEquivalentData("x**((0.5))", "x**0.5", true));
        testsEquivalentData.add(new TestsEquivalentData("x**(1/2)", "x**0.5", false));
        testsEquivalentData
            .add(new TestsEquivalentData("(((x*y))*12+((2*x+2*y))*z))*1.25", "2.5*(6*x*y+x*z+y*z)", false));
        testsEquivalentData.add(new TestsEquivalentData("a*(a+b)", "a**2+a*b", false));
        testsEquivalentData.add(new TestsEquivalentData("a*(a+b)", "a**2.0+a*b", false));
        runIsEquivalent(testsEquivalentData, false);

        // the same as above with simplification
        testsEquivalentData.clear();

        testsEquivalentData.add(new TestsEquivalentData("2x", "2*x", true));
        testsEquivalentData.add(new TestsEquivalentData("x**((0.5))", "x**0.5", true));
        testsEquivalentData.add(new TestsEquivalentData("x**(1/2)", "x**0.5", true));
        //        testData.add(new TestData("(((x*y))*12+((2*x+2*y))*z))*1.25", "2.5*(6*x*y+x*z+y*z)", true)); fails with an exception
        testsEquivalentData.add(new TestsEquivalentData("a*(a+b)", "a**2+a*b", true));
        testsEquivalentData.add(new TestsEquivalentData("a*(a+b)", "a**2.0+a*b", true));
        testsEquivalentData.add(new TestsEquivalentData("(1+1/2)**1/2", "sqrt(1+1/2)", false));
        runIsEquivalent(testsEquivalentData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndEq() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();

        testsEquivalentData.add(new TestsEquivalentData("Eq(2,1)", "Eq(3,2)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(x,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(x,2.0)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(2,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(x,1+1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(1,5-4+3-2-1)", "Eq(1,1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(1,1)", "Eq(1,5-4+3-2-1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x/2,x*(1/2))", "Eq(x/2,x/2)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq((4)**(k),N*k)", "Eq(N*k,(4)**(k))", true));

        testsEquivalentData
            .add(new TestsEquivalentData("Eq(P*(t),(0.022*(t**2)+92))", "Eq(P*(t),(0.022*(t**2)+92))", true));
        testsEquivalentData
            .add(new TestsEquivalentData("Eq((P*(t)-92)/0.022,(t**2))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData.add(new TestsEquivalentData("(Eq(t,P-21.98))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData
            .add(new TestsEquivalentData("Eq(P*(t),(0.022*(t**2)+92-1))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData.add(new TestsEquivalentData("(91.97/0)", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData.add(new TestsEquivalentData("((30)/0)", "2/3", false));

        testsEquivalentData.add(new TestsEquivalentData("Eq(2*x+4,2)", "Eq(x+2,1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(-2*b-1,-a)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(-2*b-1,-a+1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(2*b+1,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(1+2*b,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(1+b*2,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,1)", "x", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(E,6.5x+0.04y)", "Eq(E,6.5x+0.04y)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(6.5x+0.04y,E)", "Eq(E,6.5x+0.04y)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(E,6.5*x+0.04*y)", "Eq(E,0.04*y+6.5*x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(6.5*x+0.04*y,E)", "Eq(E,6.5*x+0.04*y)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,1)", "Eq(x,Add(2,-1,evaluate=True))", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,0.5)", "Eq(x,1/2)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,0.5)", "Eq(x,.5)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(28.80*y*3*h,86.4*y*h)", "Eq(y,9.6*h)", false));
        testsEquivalentData.add(new TestsEquivalentData("(Eq(2*x+6*y,25.50))", "Eq(y,-10.8*x**2+83.08*x-9.99)", false));

        // Rounding error
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,7)", "Eq(3.45*x,24.15)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,7.1)", "Eq(3.45*x,24.15)", false));
        // second representation for exponential
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,(x)**(1/3))", "Eq(y,(x)**(1/3))", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,nthroot(x,3))", "Eq(y,nthroot(x,3))", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,nthroot(x,2))", "Eq(y,sqrt(x))", false));
        runIsEquivalent(testsEquivalentData, false);

        // the same as above with simplification
        testsEquivalentData.clear();
        testsEquivalentData.add(new TestsEquivalentData("Eq(2,1)", "Eq(3,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(x,2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(x,2.0)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(2,x)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,2)", "Eq(x,1+1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(1,5-4+3-2-1)", "Eq(1,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(1,1)", "Eq(1,5-4+3-2-1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x/2,x*(1/2))", "Eq(x/2,x/2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq((4)**(k),N*k)", "Eq(N*k,(4)**(k))", true));

        testsEquivalentData
            .add(new TestsEquivalentData("Eq(P*(t),(0.022*(t**2)+92))", "Eq(P*(t),(0.022*(t**2)+92))", true));
        testsEquivalentData
            .add(new TestsEquivalentData("Eq((P*(t)-92)/0.022,(t**2))", "Eq(P*(t),(0.022*(t**2)+92))", true));
        testsEquivalentData.add(new TestsEquivalentData("(Eq(t,P-21.98))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData
            .add(new TestsEquivalentData("Eq(P*(t),(0.022*(t**2)+92-1))", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData.add(new TestsEquivalentData("(91.97/0)", "Eq(P*(t),(0.022*(t**2)+92))", false));
        testsEquivalentData.add(new TestsEquivalentData("((30)/0)", "2/3", false));

        testsEquivalentData.add(new TestsEquivalentData("Eq(2*x+4,2)", "Eq(x+2,1)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(-2*b-1,-a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(-2*b-1,-a+1)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(2*b+1,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(1+2*b,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(a,2*b+1)", "Eq(1+b*2,a)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,1)", "x", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(E,6.5x+0.04y)", "Eq(E,6.5x+0.04y)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(6.5x+0.04y,E)", "Eq(E,6.5x+0.04y)", false));
        testsEquivalentData.add(new TestsEquivalentData("Eq(E,6.5*x+0.04*y)", "Eq(E,0.04*y+6.5*x)", true));
        //        testData.add(new TestData("Eq(6.5*x+0.04*y,E)", "Eq(E,6.5*x+0.04*y)", true)); fails with an exception
        //        testData.add(new TestData("Eq(x,1)", "Eq(x,Add(2,-1,evaluate=True))", true)); fails with an exception
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,0.5)", "Eq(x,1/2)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,0.5)", "Eq(x,.5)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,x**2-6*x+5)", "Eq(y,(x-1)*(x-5))", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,(x-1)*(x-5))", "Eq(x**2-6*x+5,y)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(28.80*y*3*h,86.4*y*h)", "Eq(y,9.6*h)", false));
        testsEquivalentData.add(new TestsEquivalentData("(Eq(2*x+6*y,25.50))", "Eq(y,-10.8*x**2+83.08*x-9.99)", false));

        // Rounding error
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,7)", "Eq(3.45*x,24.15)", true));
        testsEquivalentData.add(new TestsEquivalentData("Eq(x,7.1)", "Eq(3.45*x,24.15)", false));

        // second representation for exponential
        //        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false)); fails with an exception
        testsEquivalentData.add(new TestsEquivalentData("Eq(y,(x)**(1/3))", "Eq(y,(x)**(1/3))", true));
        //        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,nthroot(x,3))", true)); fails with an exception
        //        testData.add(new TestData("Eq(y,nthroot(x,3))", "Eq(y,(x)**(1/3))", false)); fails with an exception
        //        testData.add(new TestData("Eq(y,nthroot(x,2))", "Eq(y,sqrt(x))", false)); fails with an exception
        runIsEquivalent(testsEquivalentData, true);
    }

    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndTrig() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();
        testsEquivalentData.add(new TestsEquivalentData("cos(t)", "sin(t+pi/2)", false));
        testsEquivalentData.add(new TestsEquivalentData("cos(t+pi/2)", "-sin(t)", false));
        testsEquivalentData.add(new TestsEquivalentData("sin(2*x)", "2*sin(x)*cos(x)", false));
        testsEquivalentData.add(new TestsEquivalentData("cos(2*x)", "1-2*sin(x)**2", false));
        testsEquivalentData.add(new TestsEquivalentData("cos(x/0.5)", "1-2*sin(x)**2", false));
        testsEquivalentData.add(new TestsEquivalentData("sin(x)**2+cos(x)**2", "1", false));
        runIsEquivalent(testsEquivalentData, false);

        // the same as above with simplification
        testsEquivalentData.clear();
        testsEquivalentData.add(new TestsEquivalentData("cos(t)", "sin(t+pi/2)", true));
        testsEquivalentData.add(new TestsEquivalentData("cos(t+pi/2)", "-sin(t)", true));
        testsEquivalentData.add(new TestsEquivalentData("sin(2*x)", "2*sin(x)*cos(x)", true));
        testsEquivalentData.add(new TestsEquivalentData("cos(2*x)", "1-2*sin(x)**2", true));
        testsEquivalentData.add(new TestsEquivalentData("cos(x/0.5)", "1-2*sin(x)**2", true));
        testsEquivalentData.add(new TestsEquivalentData("sin(x)**2+cos(x)**2", "1", true));
        runIsEquivalent(testsEquivalentData, true);
    }


    @Test
    public void itShouldTestIsEquivalentWithNoSimplifyAndLog() throws QTIScoringException {
        final List<TestsEquivalentData> testsEquivalentData = new ArrayList();
        testsEquivalentData.add(new TestsEquivalentData("exp(2*x)", "(exp(x))**2", false));
        testsEquivalentData.add(new TestsEquivalentData("exp(ln(x))", "x", false));
        testsEquivalentData.add(new TestsEquivalentData("log(x**2)", "2*log(x)", false));
        runIsEquivalent(testsEquivalentData, false);

        // the same as above with simplification
        testsEquivalentData.clear();
        testsEquivalentData.add(new TestsEquivalentData("exp(2*x)", "(exp(x))**2", true));
        testsEquivalentData.add(new TestsEquivalentData("exp(ln(x))", "x", true));
        testsEquivalentData.add(new TestsEquivalentData("log(x**2)", "2*log(x)", false));
        runIsEquivalent(testsEquivalentData, true);
    }

    private void runIsEquivalent(final List<TestsEquivalentData> testsEquivalentData,
                                 boolean simplify) throws QTIScoringException {
        if (simplify == true) {
            return;
        }
        for (final TestsEquivalentData test : testsEquivalentData) {
            assertEquals(String.format("Testing %s", test.response),
                TinyEquation
                    .getIsEquivalent(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                            "<MathExpressionInfo><SympyResponse><string>%s</string></SympyResponse></MathExpressionInfo>",
                        test.response),
                        test.exemplar, simplify, false, false, false),
                test.result);
        }
    }

    static class TestsEquivalentData {
        String exemplar;
        String response;
        boolean result;

        TestsEquivalentData(final String exemplar, final String response, final boolean result) {
            this.exemplar = exemplar;
            this.response = response;
            this.result = result;
        }
    }

    @Test
    public void testNumberFromExpression() throws QTIScoringException {
        final List<TestsNumberForExpressionData> testsData = new ArrayList();
        testsData.add(new TestsNumberForExpressionData("exp(2*x)", NaN));
        testsData.add(new TestsNumberForExpressionData(".5, 6", NaN));
        testsData.add(new TestsNumberForExpressionData("something.5", NaN));
        testsData.add(new TestsNumberForExpressionData("something. 0.5", NaN));
        testsData.add(new TestsNumberForExpressionData("5 something", NaN));
        testsData.add(new TestsNumberForExpressionData("x=5+y", NaN));
        testsData.add(new TestsNumberForExpressionData("[5]", NaN));
        testsData.add(new TestsNumberForExpressionData("1+1", NaN));

        testsData.add(new TestsNumberForExpressionData("0.21e+1", 2.1f));
        testsData.add(new TestsNumberForExpressionData(".5", 0.5f));
        testsData.add(new TestsNumberForExpressionData("(.5)", 0.5f));

        runNumberFromExpression(testsData);
    }


    private void runNumberFromExpression(final List<TestsNumberForExpressionData> testData) throws QTIScoringException {
        for (final TestsNumberForExpressionData test : testData) {
            assertEquals(String.format("Testing %s", test.expression),
                TinyEquation
                    .numberFromExpression(
                        String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                                "<MathExpressionInfo><SympyResponse><string>%s</string></SympyResponse></MathExpressionInfo>",
                            test.expression)),
                test.result, 0.1);
        }
    }

    static class TestsNumberForExpressionData {
        public String expression;
        public float result;

        TestsNumberForExpressionData(final String expression, final float result) {
            this.expression = expression;
            this.result = result;
        }
    }


    @Test
    public void testLineContains() throws QTIScoringException {
        final boolean simplify = false;
        assertEquals(TinyEquation
            .lineContainsEquivalent(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<MathExpressionInfo><SympyResponse><string>%s</string><string>%s</string></SympyResponse></MathExpressionInfo>",
                "Eq(8+8+8,24-2)", "Eq(24-2,22)"),
                "Eq(24-2,22)", simplify, false, false, false), true);
    }

}
