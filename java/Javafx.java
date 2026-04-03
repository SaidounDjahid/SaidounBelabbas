

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Javafx {
    static void createJavafx(Node n, Element elt, Element rac, Document doc){
        for (int o=0;o<n.getChildNodes().getLength();o++)
            if((!n.getChildNodes().item(o).getNodeName().matches("#text|#comment"))){
                Node e = n.getChildNodes().item(o);
                for(int x=0;x<e.getAttributes().getLength();x++){
                    Attr attr = (Attr) e.getAttributes().item(x);
                    elt = doc.createElement("texte");
                    elt.setAttribute(attr.getName(), "x");
                    elt.setTextContent(attr.getValue());
                    rac.appendChild(elt);
                }
                createJavafx(e, elt, rac, doc);
            }
    }

    static void out(File path) throws ParserConfigurationException, SAXException, IOException, TransformerException{

        DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parseur.parse(path);
        DOMImplementation domImp = parseur.getDOMImplementation();
        Document outDoc = domImp.createDocument(null,"Racine", null);
        outDoc.setXmlStandalone(true);

        Element outRacine = outDoc.getDocumentElement();
        Node racine = document.getDocumentElement();
        // get the attribute.
        Attr attrR = (Attr) racine.getAttributes().getNamedItem("xmlns:fx");
        String strVal = attrR.getValue();
        // On a terminé la racine
        outRacine.setAttribute(attrR.getName(), strVal.substring(0, strVal.length()-2));

        Element elt = null;
        for(int x=0;x<racine.getAttributes().getLength();x++){
            Attr attr = (Attr) racine.getAttributes().item(x);
            elt = outDoc.createElement("texte");
            elt.setAttribute(attr.getName(), "x");
            elt.setTextContent(attr.getValue());
            outRacine.appendChild(elt);
        }

        createJavafx(racine,elt,outRacine, outDoc);

        DOMSource source = new DOMSource(outDoc);
        StreamResult resultat = new StreamResult(new File("Sorties\\javafx.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
        transformer.transform(source, resultat);

        Path file = Paths.get("Sorties\\javafx.xml");
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(file), charset);
        content = content.replaceAll("  ", "\t");
        Files.write(file, content.getBytes(charset));
    }
	/*public static void main(String[] args) {
		try {
			out(new File("projet\\projet_bis\\poeme\\fiches\\renault\\javafx\\boitedialog.fxml"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}*/

}
