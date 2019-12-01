package tinygrscoringengine;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlReader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GeoObjectTest {

    @Test
    public void testIntersect() throws IOException, JDOMException, TinyGRException {

        XmlReader reader = new XmlReader(new StringReader("<root>" +
            " <Object>\n" +
            "   <PointVector>{(144,338)(312,338)(144,194)(312,194)}</PointVector>\n" +
            "   <EdgeVector>{ {(144,338),(312,338)} {(144,338),(144,194)} {(312,338),(312,194)} {(312,194),(144,194)}}\n" +
            "   </EdgeVector>\n" +
            "   <LabelList>{}</LabelList>\n" +
            "   <ValueList>{}</ValueList>\n" +
            " </Object>" +
            "</root>"));
        Document doc = reader.getDocument();
        List<Element> elements = new XmlElement(doc.getRootElement()).selectNodes("//root/Object");

        GRObject geoObject = GeoObject.fromXml(elements.get(0));
        // within
        assertEquals(geoObject.intersectsRegion(new Point(118, 362), new Point(363, 120), 5.0), true);
        // equals
        assertEquals(geoObject.intersectsRegion(new Point(144, 338), new Point(312, 194), 5.0), true);
        // touches inside
        assertEquals(geoObject.intersectsRegion(new Point(144, 362), new Point(312, 194), 5.0), true);
        // touches outside
        assertEquals(geoObject.intersectsRegion(new Point(100, 338), new Point(144, 194), 5.0), true);
        //crosses
        assertEquals(geoObject.intersectsRegion(new Point(144, 338), new Point(300, 120), 5.0), true);
        //contains
        assertEquals(geoObject.intersectsRegion(new Point(149, 333), new Point(307, 199), 0.0), false);
        //contains within 5 points
        assertEquals(geoObject.intersectsRegion(new Point(149, 333), new Point(307, 199), 5.0), true);
    }

    @Test
    public void testGetVector() throws TinyGRException {

        /**
         a = Vector(head=Point(x=20, y=10), tail=Point(x=20, y=50))
         b = Vector(head=Point(x=20, y=50), tail=Point(x=30, y=80))
         c = Vector(head=Point(x=30, y=80), tail=Point(x=45, y=80))
         d = Vector(head=Point(x=45, y=80), tail=Point(x=60, y=80))
         e = Vector(head=Point(x=30, y=80), tail=Point(x=45, y=10))
         f = Vector(head=Point(x=45, y=80), tail=Point(x=45, y=10))
         g = Vector(head=Point(x=60, y=80), tail=Point(x=45, y=10))
         h = Vector(head=Point(x=45, y=10), tail=Point(x=30, y=10))
         i = Vector(head=Point(x=30, y=10), tail=Point(x=20, y=10))
         j = Vector(head=Point(x=30, y=10), tail=Point(x=20, y=50))
         k = Vector(head=Point(x=60, y=80), tail=Point(x=90, y=80))
         l = Vector(head=Point(x=90, y=80), tail=Point(x=45, y=10))

                            c        d        k
         80-|             +-------+-------+------+
            |          b/   \     |      /     /
            |          /     \    |     /    /
         50-|        +       e\  f|    /g  /
            |        | \       \  |   /  / l
            |       a|  \ j     \ |  / /
            |        |   \       \| //
         10-|        +____|_______/
            |          i     h
            |__________________________________________________
                      |     |      |      |       |
                     20   30     45      60      90
         **/

        final String jai = "<Object><PointVector></PointVector>" +
            "<EdgeVector>{ {(30,10),(20,50)} {(20,10),(20,50)} {(30,10),(20,10)}}</EdgeVector></Object>";
        // response is counter clock wise
        assertEquals(TinyGR.getVector(jai, 0), "<Vector>{(20,50),(20,10)}</Vector>"); // reversed a
        assertEquals(TinyGR.getVector(jai, 1), "<Vector>{(20,10),(30,10)}</Vector>"); // reversed i
        assertEquals(TinyGR.getVector(jai, 2), "<Vector>{(20,50),(30,10)}</Vector>"); // reversed j

        final String dgf = "<Object><PointVector></PointVector>" +
            "<EdgeVector>{ {(45,80),(60,80)} {(60,80),(45,10)} {(45,80),(45,10)}}</EdgeVector></Object>";
        // response is counter clock wise
        assertEquals(TinyGR.getVector(dgf, 0), "<Vector>{(45,80),(45,10)}</Vector>"); // f
        assertEquals(TinyGR.getVector(dgf, 1), "<Vector>{(45,10),(60,80)}</Vector>"); // reversed g
        assertEquals(TinyGR.getVector(dgf, 2), "<Vector>{(45,80),(60,80)}</Vector>"); // d

        final String klg = "<Object><PointVector></PointVector>" +
            "<EdgeVector>{ {(60,80),(90,80)} {(90,80),(45,10)} {(60,80),(45,10)}}</EdgeVector></Object>";
        // response is counter clock wise
        assertEquals(TinyGR.getVector(klg, 0), "<Vector>{(45,10),(60,80)}</Vector>"); // reversed g
        assertEquals(TinyGR.getVector(klg, 1), "<Vector>{(45,10),(90,80)}</Vector>"); // reversed l
        assertEquals(TinyGR.getVector(klg, 2), "<Vector>{(60,80),(90,80)}</Vector>"); // k

        final String cef = "<Object><PointVector></PointVector>" +
            "<EdgeVector>{ {(30,80),(45,80)} {(30,80),(45,10)} {(45,80),(45,10)}}</EdgeVector></Object>";
        // response is clock wise
        assertEquals(TinyGR.getVector(cef, 0), "<Vector>{(30,80),(45,80)}</Vector>"); //c
        assertEquals(TinyGR.getVector(cef, 1), "<Vector>{(45,80),(45,10)}</Vector>"); //f
        assertEquals(TinyGR.getVector(cef, 2), "<Vector>{(30,80),(45,10)}</Vector>"); //e

        final String jhbcf = "<Object><PointVector></PointVector>" +
            "<EdgeVector>{ {(30,10),(20,50)} {(45,10),(30,10)} {(20,50),(30,80)} {(30,80),(45,80)} {(45,80),(45,10)}}</EdgeVector></Object>";
        // response is counter clock wise
        assertEquals(TinyGR.getVector(jhbcf, 0), "<Vector>{(20,50),(30,80)}</Vector>"); // b
        assertEquals(TinyGR.getVector(jhbcf, 1), "<Vector>{(20,50),(30,10)}</Vector>"); // reversed j
        assertEquals(TinyGR.getVector(jhbcf, 2), "<Vector>{(30,10),(45,10)}</Vector>"); // reversed h
        assertEquals(TinyGR.getVector(jhbcf, 3), "<Vector>{(45,80),(45,10)}</Vector>"); // f
        assertEquals(TinyGR.getVector(jhbcf, 4), "<Vector>{(30,80),(45,80)}</Vector>"); // c
    }
}
