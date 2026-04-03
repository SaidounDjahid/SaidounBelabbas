import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void isDirectory(File repertoire){
        if (repertoire.isDirectory()) {
            parcourirRepertoire(repertoire);
        } else {
            System.out.println("Entrez un répertoire !!!");
        }
    }
    public static void parcourirRepertoire(File repertoire) {
        File directory = null;
        if (repertoire.canRead()) {
            for (File fichier : repertoire.listFiles()) {
                if (fichier.isDirectory()) {
                    directory = fichier;
                }
                else {
                    try {
                        switch (fichier.getName()) {
                            case "M457.xml" :
                                Sortie1_sortie2.out(fichier, "M457");
                                break;
                            case  "M674.xml":
                                Sortie1_sortie2.out(fichier, "M674");
                                break;
                            case "poeme.txt" :
                                Neruda.out(fichier);
                                break;
                            case "fiches.txt" :
                                Fiche1_fiche2.fiche1(fichier, "fiche1.xml");
                                Fiche1_fiche2.fiche2(fichier, "fiche2.xml");
                                break;
                            case "renault.html":
                                Renault.out(fichier);
                                break;
                            case "boitedialog.fxml":
                                Javafx.out(fichier);
                                break;
                        }

                    }
                    catch (ParserConfigurationException e){
                        e.printStackTrace();
                    }
                    catch (SAXException e){
                        e.printStackTrace();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    catch (TransformerException e){
                        e.printStackTrace();
                    }

                }
            }
            try {
                parcourirRepertoire(directory);
            }
            catch (java.lang.NullPointerException e){
                System.exit(0);
            }
        }
        else {
            System.out.println("Vous n'avez pas le droit de lire le répertoire");
        }


    }
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Attention vous avez oublié de spécifier le nom du répertoire a  traiter !");
        }
        else {
            File file = new File ("Sorties");
            file.mkdir();
            isDirectory(new File(args[0]));
        }
    }
}
