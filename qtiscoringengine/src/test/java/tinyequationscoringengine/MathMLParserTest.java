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
    }

}
