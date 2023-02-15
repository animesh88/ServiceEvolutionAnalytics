
import com.predic8.schema.ComplexType;
import com.predic8.schema.ModelGroup;
import com.predic8.schema.Schema;
import com.predic8.schema.SchemaComponent;
import com.predic8.schema.SimpleType;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.WSDLParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import static sun.misc.MessageUtils.out;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AnimeshChaturvedi
 */
public class WSCL {
    static String path = "G:\\BackUp\\16-11-16\\WebServices\\Eucalyptus-CC\\";

    public static void main(String arg[]) throws ParserConfigurationException, SAXException, IOException {
        File firstDir = new File(path + "Eucalyptus_handler_CC_Code");

        File summaryOfWSDLEvolution = new File(path + "SEM");
        summaryOfWSDLEvolution.mkdirs();
        File SEMWSDLResult = new File(path + "\\SEM\\WSCLOSEM4"+ summaryOfWSDLEvolution.getParentFile().getName() +".csv");

        FileWriter fileWritter1 = new FileWriter(SEMWSDLResult, false);
        BufferedWriter bufwriter1 = new BufferedWriter(fileWritter1);
        bufwriter1.write("");
        bufwriter1.close();

        File[] observationFile = firstDir.listFiles();
//        int numberOfLine = 0;
//        int numberOfOperation = 0;
        double numberOfLines = 0;
        String name = "Name";
        String x;
        x = "name,numberOfLines" + "\n";
        try {
            File fileName1 = SEMWSDLResult;
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(fileName1, true));
            bufwriter.write(x);//writes the edited string buffer to the new file
            bufwriter.close();//closes the file
        } catch (Exception e) {//if an exception occurs
        }

        for (int i = 0; i < firstDir.list().length; i++) {
            System.out.println(observationFile[i].getName());

            name = observationFile[i].getName();
//                numberOfLine = numberOfLines(observationFile[i]);
//                numberOfOperation = numberOfOperation(observationFile[i]);
            numberOfLines = numberOfLines(observationFile[i]);
            
            x = name + "," + numberOfLines + "\n";// + "," + numberOfLine + "," + numberOfOperation + "\n";
//            System.out.println(x);
            try {
                File fileName1 = SEMWSDLResult;
                BufferedWriter bufwriter = new BufferedWriter(new FileWriter(fileName1, true));
//                System.out.println(x);
                bufwriter.write(x); //writes the edited string buffer to the new file
                bufwriter.close(); //closes the file
            } catch (Exception e) {
                //if an exception occurs
            }
        }
    }

    private static int numberOfLines(File file) throws ParserConfigurationException, SAXException, IOException {
        int numberOfCommentLines = 0, numberOfLineWithComent = 0, numberOfLineWithoutComments = 0;

        try {
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);

            while (lnr.readLine() != null) {
                numberOfLineWithComent++;
            }

//                System.out.println("Total number of lines : " + linenumber);
            lnr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("number of lines " + numberOfLineWithComent);

        try {

            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                if (line.contains("/*")) {
                    System.out.println("Inside if " + line);
                    numberOfCommentLines++;
                    while (!line.contains("*/")){
                    System.out.println("Inside while " + line);
                        numberOfCommentLines++;
                        line = br.readLine();
                    } 
                }
            }
            
            br.close();
            
            String line2 = "";
            BufferedReader br2 = new BufferedReader(new FileReader(file));

            while ((line2 = br2.readLine()) != null) {
                if (line2.contains("//")) {
                    numberOfCommentLines++;
                }
            }
            
            br2.close();

            System.out.println("number of comment lines " + numberOfCommentLines);
//                System.out.println("Total number of lines : " + linenumber);

        } catch (IOException e) {
            e.printStackTrace();
        }

        numberOfLineWithoutComments = numberOfLineWithComent - numberOfCommentLines;
        return numberOfLineWithoutComments;
    }
}
