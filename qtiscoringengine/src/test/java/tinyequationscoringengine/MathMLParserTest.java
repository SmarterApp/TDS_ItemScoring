package tinyequationscoringengine;

import org.junit.Test;
import qtiscoringengine.QTIScoringException;

import static org.junit.Assert.assertEquals;

public class MathMLParserTest {

    @Test
    public void itShouldParseMathMl() throws QTIScoringException {
        MathExpressionSet expressions = MathMLParser.processMathMLData("<response>\n" +
            "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\" title=\"8+8+8=24-2=22\">\n" +
            "        <mstyle>\n" +
            "            <mn>8</mn>\n" +
            "            <mo>+</mo>\n" +
            "            <mn>8</mn>\n" +
            "            <mo>+</mo>\n" +
            "            <mn>8</mn>\n" +
            "            <mo>=</mo>\n" +
            "            <mn>24</mn>\n" +
            "            <mo>-</mo>\n" +
            "            <mn>2</mn>\n" +
            "            <mo>=</mo>\n" +
            "            <mn>22</mn>\n" +
            "        </mstyle>\n" +
            "    </math>\n" +
            "    <math xmlns=\"http://www.w3.org/1998/Math/MathML\" title=\"s=36\">\n" +
            "        <mstyle>\n" +
            "            <mi>s</mi>\n" +
            "            <mo>=</mo>\n" +
            "            <mn>36</mn>\n" +
            "        </mstyle>\n" +
            "    </math>\n" +
            "</response>\n");


        assertEquals(expressions.size(), 2);
        assertEquals(expressions.toString(), "[?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<MathExpressionInfo><_appliedCorrection>false</_appliedCorrection>" +
            "<SympyResponse><string>Eq(8+8+8,24-2)</string>" +
            "<string>Eq(24-2,22)</string></SympyResponse>" +
            "<_sympyResponseNotSimplified>" +
            "<string>Eq(((8+_a9)*_m9)+((8+_a8)*_m8)+((8+_a7)*_m7),((24+_a11)*_m11)-((2+_a10)*_m10))</string>" +
            "<string>Eq(((24+_a11)*_m11)-((2+_a10)*_m10),((22+_a12)*_m12))</string>" +
            "</_sympyResponseNotSimplified><_triedToApplyCorrection>true</_triedToApplyCorrection>" +
            "</MathExpressionInf, ?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<MathExpressionInfo><_appliedCorrection>false</_appliedCorrection>" +
            "<SympyResponse>" +
            "<string>Eq(s,36)</string>" +
            "</SympyResponse>" +
            "<_sympyResponseNotSimplified><string>Eq(s,((36+_a2)*_m2))</string></_sympyResponseNotSimplified><" +
            "_triedToApplyCorrection>true</_triedToApplyCorrection></MathExpressionInf]");


        expressions = MathMLParser.processMathMLData("<response>" +
            "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n" +
            "  <mstyle displaystyle=\"true\">\n" +
            "    <mrow class=\"MJX-TeXAtom-ORD\">\n" +
            "      <mi>x</mi>\n" +
            "      <mo>=</mo>\n" +
            "      <msup>\n" +
            "        <mi>x</mi>\n" +
            "        <mfenced open=\"(\" close=\")\">\n" +
            "          <mrow>\n" +
            "            <mn>2</mn>\n" +
            "          </mrow>\n" +
            "        </mfenced>\n" +
            "      </msup>\n" +
            "      <mo>&#x2212;<!-- − --></mo>\n" +
            "      <mn>6</mn>\n" +
            "      <mi>x</mi>\n" +
            "    </mrow>\n" +
            "  </mstyle>\n" +
            "</math>" +
            "</response>\n");

        assertEquals(expressions.size(), 1);
        assertEquals(expressions.toString(), "[?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<MathExpressionInfo><_appliedCorrection>false</_appliedCorrection>" +
            "<SympyResponse><string>Eq(x,(x)**((2))-6*x)</string></SympyResponse>" +
            "<_sympyResponseNotSimplified><string>Eq(x,(x)**((2))-((6+_a3)*_m3)*x)</string></_sympyResponseNotSimplified>" +
            "<_triedToApplyCorrection>true</_triedToApplyCorrection></MathExpressionInf]");


        expressions = MathMLParser.processMathMLData("<response>" +
            "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">" +
            "<mi>x</mi><mo>+</mo><mn>3</mn><mo>=</mo><mi>y</mi><mo>-</mo><mn>7</mn><mo>=</mo><mn>8</mn><mo>+</mo><mi>y</mi><mo>/</mo><mi>x</mi>" +
            "</math>" +
            "</response>");
        assertEquals(expressions.size(), 1);
        assertEquals(expressions.toString(), "[?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<MathExpressionInfo><_appliedCorrection>false</_appliedCorrection>" +
            "<SympyResponse><string>Eq(x+3,y-7)</string><string>Eq(y-7,8+y/x)</string></SympyResponse>" +
            "<_sympyResponseNotSimplified><string>Eq(x+((3+_a4)*_m4),y-((7+_a5)*_m5))</string><string>Eq(y-((7+_a5)*_m5),((8+_a6)*_m6)+y/x)</string></_sympyResponseNotSimplified>" +
            "<_triedToApplyCorrection>true</_triedToApplyCorrection></MathExpressionInf]");
    }

}
