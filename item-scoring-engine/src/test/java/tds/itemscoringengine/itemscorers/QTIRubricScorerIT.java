package tds.itemscoringengine.itemscorers;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.ISECustomOperator;
import qtiscoringengine.QTIScoringException;
import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ItemScorerManagerImpl;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.web.server.AppStatsRecorder;
import tinyctrlscoringengine.CtrlCustomOpFactory;
import tinyequationscoringengine.MathScoringService;
import tinyequationscoringengine.TinyEqCustomOpFactory;
import tinyequationscoringengine.WebProxy;
import tinygrscoringengine.TinyGRCustomOpFactory;
import tinytablescoringengine.TinyTableCustomOpFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.io.ByteStreams.toByteArray;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tds.itemscoringengine.RubricContentType.ContentString;
import static tds.itemscoringengine.ScoringStatus.Scored;

public class QTIRubricScorerIT {
    private static final Logger logger = LoggerFactory.getLogger(QTIRubricScorerIT.class);

    private static final IItemScorer mciScorer = new MCItemScorer();
    private static final IItemScorer qtiScorer = new QTIItemScorer();
    private QTIRubricScorer scorer;
    private Map<String, IItemScorer> scorers = new HashMap<>();
    private ScoringMaster scoringMaster;

    private static final boolean runWithLocalPythonService = false;

    @Before
    public void setUp() throws QTIScoringException {
        scorer = new QTIRubricScorer("");
        scorers.put("MC", mciScorer);
        scorers.put("MS", mciScorer);
        scorers.put("MI", qtiScorer);
        scorers.put("QTI", qtiScorer);
        scorers.put("EBSR", qtiScorer);
        scorers.put("HTQ", qtiScorer);
        scorers.put("GI", qtiScorer);
        scorers.put("EQ", qtiScorer);
        scorers.put("TI", qtiScorer);
        scoringMaster = new ScoringMaster(scorers);
    }

    @Test
    public void isShouldScoreMaxPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/sample_rubric.xml")),
                StandardCharsets.UTF_8));

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
            new String(toByteArray(this.getClass().getResourceAsStream("/items/sample_rubric.xml")),
                StandardCharsets.UTF_8));

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
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-rubric.xml")),
                StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-respose_correct.xml")),
                StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 1);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreGiItemWithZeroPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-rubric.xml")),
                StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/gi-respose.xml")),
                StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 0);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreEbsrItem() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-rubric.xml")),
                StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-response-correct.xml")),
                StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 1);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    @Test
    public void isShouldScoreEbsrItemWithZeroPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-rubric.xml")),
                StandardCharsets.UTF_8));

        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/ebsr-response.xml")),
                StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("gi", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 0);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    //TODO: while this test runs, it is not scoring exactly right; this needs to be looked at further
    @Test
    public void isShouldScoreEqtemWithTwoPoints() throws IOException {
        scorer.load(ContentString,
            new String(toByteArray(this.getClass().getResourceAsStream("/items/eq-rubric.xml")),
                StandardCharsets.UTF_8));

        //NOTE: this does not seem to be scored right...it ignores the first part of the response
        final String response =
            new String(toByteArray(this.getClass().getResourceAsStream("/items/eq-response-correct.xml")),
                StandardCharsets.UTF_8);
        final ItemScore score = scorer.scoreItem(new ResponseInfo("eq", "123", response, null, ContentString,
            "contextToken", false));
        assertEquals(score.getScoreInfo().getStatus(), Scored);
        assertEquals(score.getScoreInfo().getPoints(), 2);
        assertEquals(score.getScoreInfo().getMaxScore(), -1);
    }

    static class ScoringMaster extends ItemScorerManagerImpl {
        WebProxy webProxy;

        ScoringMaster(
            final Map<String, IItemScorer> engines) throws QTIScoringException {
            super(20, 500, 400, new AppStatsRecorder());

            for (Map.Entry<String, IItemScorer> entry_i : engines.entrySet()) {
                RegisterItemScorer(entry_i.getKey(), entry_i.getValue());
            }
            CustomOperatorRegistry.getInstance().addOperatorFactory(new ISECustomOperator(this));
            CustomOperatorRegistry.getInstance().addOperatorFactory(new TinyGRCustomOpFactory());
            CustomOperatorRegistry.getInstance().addOperatorFactory(new CtrlCustomOpFactory());
            CustomOperatorRegistry.getInstance().addOperatorFactory(new TinyTableCustomOpFactory());

            if (runWithLocalPythonService) {
                //this requires EqScoringWebService.py to be running locally
                CustomOperatorRegistry.getInstance()
                    .addOperatorFactory(new TinyEqCustomOpFactory(URI.create("http://0.0.0.0:8080"), 3, 300));
            } else {
                webProxy = mock(WebProxy.class);
                when(webProxy.parsable(any())).thenAnswer(invocation -> {
                    logger.info("Invoked webProxy.parsable  with params [" + invocation.getArguments()[0] + "]");
                    return true;
                });

                MathScoringService.getInstance().initialize(webProxy);
                CustomOperatorRegistry.getInstance()
                    .addOperatorFactory(new TinyEqCustomOpFactory(URI.create("ignored"), 3, 300));
            }
        }
    }
}
