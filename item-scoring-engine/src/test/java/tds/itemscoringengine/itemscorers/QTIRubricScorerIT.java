package tds.itemscoringengine.itemscorers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import qtiscoringengine.CustomOperatorRegistry;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ResponseInfo;
import tinyctrlscoringengine.CtrlCustomOpFactory;
import tinygrscoringengine.TinyGRCustomOpFactory;
import tinytablescoringengine.TinyTableCustomOpFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.junit.Assert.assertEquals;
import static tds.itemscoringengine.RubricContentType.ContentString;
import static tds.itemscoringengine.ScoringStatus.Scored;

public class QTIRubricScorerIT {

    private QTIRubricScorer scorer;


    @Before
    public void setUp() {
        scorer = new QTIRubricScorer("");
//        CustomOperatorRegistry.getInstance ().addOperatorFactory (new ISECustomOperator(scorer));
        CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyGRCustomOpFactory());
        CustomOperatorRegistry.getInstance ().addOperatorFactory (new CtrlCustomOpFactory());
        CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyTableCustomOpFactory());

    }

    @Test
    public void isShouldScoreMaxPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/sample_rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            "<itemResponse><response id='1'><value><![CDATA[2,1,5,4,3,0]]></value></response></itemResponse>";
        final ItemScore score = scorer.scoreItem(new ResponseInfo("itemFormat", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 1);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreZeroPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/sample_rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            "<itemResponse><response id='1'><value><![CDATA[5,1,2,4,3,0]]></value></response></itemResponse>";
        final ItemScore score = scorer.scoreItem(new ResponseInfo("itemFormat", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 0);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreGiItem() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-respose_correct.xml")), StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 1);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreGiItemWithZeroPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-respose.xml")), StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 0);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreEbsrItem() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-response-correct.xml")), StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 1);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreEbsrItemWithZeroPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-response.xml")), StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 0);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Ignore("this requires python calls, need more research")
    @Test
    public void isShouldScoreEqtemWithTwoPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/eq-rubric.xml")), StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/eq-response-correct.xml")), StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("eq", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 2);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }
}
