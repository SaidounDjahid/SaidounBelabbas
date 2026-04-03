import java.io.File;
import java.io.FileInputStream;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Fiche1_fiche2 {

    public static void fiche1 (File chemin, String sortie) {
        try{
            FileInputStream in = new FileInputStream(chemin);
            Scanner input = new Scanner(in, "UTF-8");
            StreamResult out = new StreamResult("Sorties\\"+sortie);

            DOMImplementation domImp = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
            Document outDoc = domImp.createDocument(null,"FICHES",null);
            Element outRacine = outDoc.getDocumentElement();
            String str = input.nextLine();
            String[] splitted = null;
            String Do="",Sd="";

            for(int i=1; i<5; i++){
                Element f = outDoc.createElement("FICHE");
                f.setAttribute("id", ""+i+"");
                outRacine.appendChild(f);
                while(!str.startsWith("PNR")) str = input.nextLine();
                while (str.contains("\t")) {
                    splitted = str.split("\\t");
                    if (splitted[1].equals("BE")) {
                        Element e = outDoc.createElement(splitted[1]);
                        Node n = outDoc.createTextNode(splitted[0]+"\t");
                        e.appendChild(n);
                        f.appendChild(e);
                    }
                    //Traitement des cas "DO" et "SD"
                    else if(splitted[1].equals("DO"))
                    {	Do=str;}
                    else if(splitted[1].equals("SD"))
                    {	Sd=str;}
                    else
                    {Element e = outDoc.createElement(splitted[1]);
                        Node n = outDoc.createTextNode(splitted[1]+" : "+splitted[0]+"\t");
                        e.appendChild(n);
                        f.appendChild(e);
                    }
                    str = input.nextLine();
                }
                while(input.hasNextLine() && str.contains("AR")){
                    // Str pointe sur AR
                    Element langue = outDoc.createElement("Langue");
                    // id="AR" - On fait le substring pour éviter le car de AR+tabulations dans id=3.
                    langue.setAttribute("id", str.substring(0,2));
                    //<Langue id="AR">
                    f.appendChild(langue);
                    //Traitement de la balise "DO"
                    splitted=Do.split("\\t");
                    Element e = outDoc.createElement(splitted[1]);
                    Node n = outDoc.createTextNode(splitted[1]+" : "+splitted[0]+"\t");
                    e.appendChild(n);
                    langue.appendChild(e);
                    //Traitement de la balise "SD".
                    splitted=Sd.split("\\t");
                    e = outDoc.createElement(splitted[1]);
                    n = outDoc.createTextNode(splitted[1]+" : "+splitted[0]+"\t");
                    e.appendChild(n);
                    langue.appendChild(e);
                    str = input.nextLine();
                    while (str.contains("\t")) {
                        splitted=str.split("\\t");
                        if(splitted.length==3){
                            if(splitted[splitted.length-1].split(" ")[0].equals("RF")){
                                e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                String s = splitted[splitted.length-2];
                                //Construction de la chaine inversée à afficher.
                                String _s="";
                                for(int $=s.length()-1;$>0;$--){
                                    if(s.charAt($)==':') _s=_s+s.charAt($-3)+s.charAt($-2)+" : ";}
                                n = outDoc.createTextNode("RF | "+_s+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                            else{
                                e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                n = outDoc.createTextNode(splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                        }
                        else if(splitted.length==2)
                        { e = outDoc.createElement("RF");
                            n = outDoc.createTextNode("RF | "+splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                            e.appendChild(n);
                            langue.appendChild(e);
                            str = input.nextLine();
                        }
                    }
                    //REPEATED
                    langue = outDoc.createElement("Langue");
                    // id="AR" - On fait le substring pour éviter le car de AR+tabulations dans id=3.
                    langue.setAttribute("id", str.substring(0,2));
                    //<Langue id="AR">
                    f.appendChild(langue);
                    //Traitement de la balise "DO"
                    splitted=Do.split("\\t");
                    e = outDoc.createElement(splitted[1]);
                    n = outDoc.createTextNode(splitted[1]+" : "+splitted[0]+"\t");
                    e.appendChild(n);
                    langue.appendChild(e);
                    //Traitement de la balise "SD".
                    splitted=Sd.split("\\t");
                    e = outDoc.createElement(splitted[1]);
                    n = outDoc.createTextNode(splitted[1]+" : "+splitted[0]+"\t");
                    e.appendChild(n);
                    langue.appendChild(e);
                    str = input.nextLine();
                    while (str.contains("\t")) {
                        splitted=str.split("\\t");
                        if(splitted.length==3){
                            if(splitted[splitted.length-1].split(" ")[0].equals("RF")){
                                e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                String s = splitted[splitted.length-2];
                                //Construction de la chaine inversée à afficher.
                                String _s="";
                                for(int $=s.length()-1;$>0;$--){
                                    if(s.charAt($)==':') _s=_s+s.charAt($-3)+s.charAt($-2)+" : ";}
                                n = outDoc.createTextNode("RF | "+_s+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                            else{
                                e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                n = outDoc.createTextNode(splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                        }
                        else if(splitted.length==2)
                        { e = outDoc.createElement("RF");
                            n = outDoc.createTextNode("RF | "+splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                            e.appendChild(n);
                            langue.appendChild(e);
                            str = input.nextLine();
                        }
                    }
                }
            } // fin for-id
            input.close();
            DOMSource source = new DOMSource(outDoc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "fiches.dtd");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","4");
            outDoc.setXmlStandalone(true);
            transformer.transform(source, out);
            Path file = Paths.get("Sorties\\"+sortie);
            Charset charset = StandardCharsets.UTF_8;
            String content = new String(Files.readAllBytes(file), charset);
            content = content.replaceAll("    ", "\t");
            Files.write(file, content.getBytes(charset));
        }
        catch (Exception e) { e.printStackTrace(); }
    }



    public static void fiche2 (File entre, String sortie) {//fiche2.xml
        try{
            FileInputStream in = new FileInputStream(entre);
            Scanner input = new Scanner(in, "UTF-8");
            StreamResult out = new StreamResult("Sorties\\"+sortie);

            DOMImplementation domImp = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
            Document outDoc = domImp.createDocument(null,"FICHES",null);
            outDoc.setXmlStandalone(true);
            Element outRacine = outDoc.getDocumentElement();
            String str = input.nextLine();
            String[] splitted = null;
            for(int i=1; i<5; i++){
                Element f = outDoc.createElement("FICHE");
                f.setAttribute("id", ""+i+"");
                outRacine.appendChild(f);
                while(!str.startsWith("PNR")) str = input.nextLine();
                while (str.contains("\t")) {
                    splitted = str.split("\\t");
                    if (splitted[1].equals("BE")) {
                        Element e = outDoc.createElement(splitted[1]);
                        Node n = outDoc.createTextNode(splitted[0]+"\t");
                        e.appendChild(n);
                        f.appendChild(e);
                    }
                    else {
                        Element e = outDoc.createElement(splitted[1]);
                        Node n = outDoc.createTextNode(splitted[1]+" : "+splitted[0]+"\t");
                        e.appendChild(n);
                        f.appendChild(e);
                    }
                    str = input.nextLine();
                }
                while(input.hasNextLine() && str.contains("AR")){
                    // Str pointe sur AR
                    Element langue = outDoc.createElement("Langue");
                    // id="AR" - On fait le substring pour Ã©viter le car de AR+tabulations dans id=3.
                    langue.setAttribute("id", str.substring(0,2));
                    //<Langue id="AR">
                    f.appendChild(langue);
                    str = input.nextLine();
                    while (str.contains("\t")) {
                        splitted=str.split("\\t");
                        if(splitted.length==3){
                            if(splitted[splitted.length-1].split(" ")[0].equals("RF")){
                                Element e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                String s = splitted[splitted.length-2];
                                //Construction de la chaine inversÃ©e Ã  afficher.
                                String _s="";
                                for(int $=s.length()-1;$>0;$--){
                                    if(s.charAt($)==':') _s=_s+s.charAt($-3)+s.charAt($-2)+" : ";}
                                Node n = outDoc.createTextNode("RF | "+_s+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                            else{
                                Element e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                Node n = outDoc.createTextNode(splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                        }
                        else if(splitted.length==2)
                        {Element e = outDoc.createElement("RF");
                            Node n = outDoc.createTextNode("RF | "+splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                            e.appendChild(n);
                            langue.appendChild(e);
                            str = input.nextLine();
                        }
                    }
                    //REPEATED
                    langue = outDoc.createElement("Langue");
                    // id="AR"
                    langue.setAttribute("id", str);
                    //<Langue id="AR">
                    f.appendChild(langue);
                    str = input.nextLine();
                    while (str.contains("\t")) {
                        splitted=str.split("\\t");
                        if(splitted.length==3){
                            if(splitted[splitted.length-1].split(" ")[0].equals("RF")){
                                Element e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                String s = splitted[splitted.length-2];
                                //Construction de la chaine inversÃ©e Ã  afficher.
                                String _s="";
                                for(int $=s.length()-1;$>0;$--){
                                    if(s.charAt($)==':') _s=_s+s.charAt($-3)+s.charAt($-2)+" : ";}
                                Node n = outDoc.createTextNode("RF | "+_s+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                            else{
                                Element e = outDoc.createElement(splitted[splitted.length-1].split(" ")[0]);
                                Node n = outDoc.createTextNode(splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                                e.appendChild(n);
                                langue.appendChild(e);
                                str = input.nextLine();
                            }
                        }
                        else if(splitted.length==2)
                        {Element e = outDoc.createElement("RF");
                            Node n = outDoc.createTextNode("RF | "+splitted[splitted.length-1]+" "+splitted[0]+"\t\t");
                            e.appendChild(n);
                            langue.appendChild(e);
                            str = input.nextLine();
                        }
                    }
                }
            } // fin for-id
            input.close();
            DOMSource source = new DOMSource(outDoc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            outDoc.setXmlStandalone(true);
            //ligne pour DTD
            transformer.transform(source, out);
            Path file = Paths.get("Sorties\\"+sortie);
            Charset charset = StandardCharsets.UTF_8;
            String content = new String(Files.readAllBytes(file), charset);
            content = content.replaceAll("    ", "\t");
            Files.write(file, content.getBytes(charset));

        }
        catch (Exception e) { e.printStackTrace(); }
    }
	/*public static void main(String[] args) {
	  	fiche1(new File("projet\\projet_bis\\poeme\\fiches\\fiches.txt"), "fiche1.xml");

	}*/
}
