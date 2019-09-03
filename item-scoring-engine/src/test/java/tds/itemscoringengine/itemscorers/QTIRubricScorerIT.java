package tds.itemscoringengine.itemscorers;

import org.junit.Before;
import org.junit.Test;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ResponseInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.junit.Assert.assertEquals;
import static tds.itemscoringengine.RubricContentType.ContentString;
import static tds.itemscoringengine.ScoringStatus.Scored;

public class QTIRubricScorerIT {

    private QTIRubricScorer scorer;

    @Before
    public void setUp() throws IOException {
        scorer = new QTIRubricScorer("");
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/sample_rubric.xml")), StandardCharsets.UTF_8));

    }

    @Test
    public void isShouldScoreMaxPoints() {
        final String response =
            "<itemResponse><response id='1'><value><![CDATA[2,1,5,4,3,0]]></value></response></itemResponse>";
        final ItemScore score = scorer.scoreItem(new ResponseInfo("itemFormat", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 1);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreZeroPoints() {
        final String response =
            "<itemResponse><response id='1'><value><![CDATA[5,1,2,4,3,0]]></value></response></itemResponse>";
        final ItemScore score = scorer.scoreItem(new ResponseInfo("itemFormat", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 0);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }
}
