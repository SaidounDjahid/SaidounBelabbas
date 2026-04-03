
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;

public class Neruda {

    public static void out(File chemin) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        DOMImplementation domImp = parseur.getDOMImplementation();
        DocumentType dtd = domImp.createDocumentType("neruda.xml", null, "neruda.dtd");
        Document outDoc = domImp.createDocument(null, "poema", dtd);
        Element outRacine = outDoc.getDocumentElement();
        outDoc.setXmlStandalone(true);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(chemin.getAbsolutePath())), "UTF-8"));

        Element createElement = outDoc.createElement("titulo");
        createElement.appendChild(outDoc.createTextNode(bufferedReader.readLine()));
        outRacine.appendChild(createElement);

        String line;
        int i = 5;
        while (i > 0) {
            while ((line = bufferedReader.readLine()).isEmpty());

            Element createElement2 = outDoc.createElement("estrofa");
            do {
                Element verso = outDoc.createElement("verso");
                verso.appendChild(outDoc.createTextNode(line));
                createElement2.appendChild(verso);
            } while (!(line = bufferedReader.readLine()).isEmpty());

            outRacine.appendChild(createElement2);
            i--;
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "neruda.dtd");

        DOMSource source = new DOMSource(outDoc);
        StreamResult resultat = new StreamResult(new File("neruda.xml"));

        transformer.transform(source, resultat);
        bufferedReader.close();
    }


}
