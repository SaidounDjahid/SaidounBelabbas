import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Sortie1_sortie2 {
    private static void createSortie(Node noeud, Element elet, Document doc) {

        if (noeud.getNodeName().equals("lb") || noeud.getNodeName().equals("pb")) {
            if (noeud.getNextSibling() != null) {
                Node noeudInter = noeud.getNextSibling();
                if (noeudInter.getNodeValue().trim().length() != 0) {
                    Element langueChild = doc.createElement("texte");
                    langueChild.appendChild((doc.createTextNode(noeudInter.getNodeValue().trim())));
                    elet.appendChild(langueChild);
                }
            }
        }
        if (noeud.getNodeName().equals("p")) {
            if (noeud.getFirstChild() != null) {
                Element langueChild = doc.createElement("texte");
                langueChild.appendChild((doc.createTextNode(noeud.getFirstChild().getNodeValue())));
                elet.appendChild(langueChild);
            }
        }

        if (noeud.hasChildNodes()) {
            NodeList nodes = noeud.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                createSortie(nodes.item(i), elet, doc);
            }
        }
    }

    static void out( File chemin, String nom) throws ParserConfigurationException, SAXException, TransformerException, IOException {
        String sortie = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		
        DocumentBuilder parseur = factory.newDocumentBuilder();
        Document document = parseur.parse(chemin);
        Element racine = document.getDocumentElement();
        
        

        if (nom.equals("M674")) {
            sortie = "sortie1.xml";
        }
        else if (nom.equals("M457")) {
            sortie = "sortie2.xml";
        }
        DOMImplementation domImp = parseur.getDOMImplementation();
        DocumentType dtd = domImp.createDocumentType(sortie, null, "dom.dtd");
        Document outDoc = domImp.createDocument(null, "TEI_S", dtd);
        outDoc.setXmlStandalone(true);
        Element outRacine = outDoc.getDocumentElement();
        Element noeud = outDoc.createElement(nom + ".xml");
        outRacine.appendChild(noeud);

        createSortie(racine, noeud, outDoc);

        TransformerFactory transformerFact = TransformerFactory.newInstance();
        Transformer transformer = transformerFact.newTransformer();

        DOMSource source = new DOMSource(outDoc);
        StreamResult resultat = new StreamResult(new File("Sorties\\" + sortie));

        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
        transformer.transform(source, resultat);
    }


	public static void main(String[] args) {
		try {
			out(new File("C:\\Users\\user\\eclipse-workspace\\Projet_XML\\projet\\M457.xml"), "M457");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}




