
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Renault {

    static void out(File chemin) throws ParserConfigurationException, SAXException, IOException, TransformerException {



        DocumentBuilderFactory parseur = DocumentBuilderFactory.newInstance();
        parseur.setNamespaceAware(false);
        parseur.setValidating(false);
        parseur.setFeature("http://xml.org/sax/features/namespaces", false);
        parseur.setFeature("http://xml.org/sax/features/validation", false);
        parseur.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        parseur.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder pars = parseur.newDocumentBuilder();
        Document document = pars.parse(chemin);
        Element racine = document.getDocumentElement();



        DOMImplementation domImp = pars.getDOMImplementation();
        Document outDoc = domImp.createDocument(null, "Concessionnaires", null);
        outDoc.setXmlStandalone(true);

        Element outRacine = outDoc.getDocumentElement();
        boolean first= true;
        NodeList listeP = racine.getElementsByTagName("p");

        for(int i=0; i<listeP.getLength(); i++){
            Element p = (Element)listeP.item(i);
            NodeList childNodes = p.getChildNodes();
            if(childNodes.getLength()>=4 && childNodes.item(1).getNodeName().compareTo("strong")==0){
                Element nom = outDoc.createElement("Nom");
                Element adresse = outDoc.createElement("Adresse");
                Element numTelephone = outDoc.createElement("Num_telephone");

                nom.appendChild(outDoc.createTextNode(childNodes.item(1).getFirstChild().getNodeValue().replaceAll("[\n\r]", " ").trim()));

                String valueAdr = childNodes.item(6).getNodeValue();
                if(first) valueAdr= valueAdr.replace(":", "");
                adresse.appendChild(outDoc.createTextNode(valueAdr.replaceAll("[\n\r]", " ").trim()));

                String valueTel = childNodes.item(first ? 10 : 8).getNodeValue();
                if(first) valueTel= valueTel.replace(":", "");
                numTelephone.appendChild(outDoc.createTextNode(valueTel.replaceAll("[\n\r]", " ").trim()));

                outRacine.appendChild(nom);
                outRacine.appendChild(adresse);
                outRacine.appendChild(numTelephone);

                if(first) first=false;
            }
        }

        DOMSource source=new DOMSource(outDoc);
        StreamResult resultat= new StreamResult(new File("Sorties\\renault.xml"));
        TransformerFactory transformerFact = TransformerFactory.newInstance();
        Transformer transformer = transformerFact.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
        transformer.transform(source, resultat);
    }
	/*public static void main(String[] args) {
		try {
			out(new File("projet\\projet_bis\\poeme\\fiches\\renault\\renault.html"));
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
