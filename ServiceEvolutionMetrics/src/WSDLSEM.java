
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
import static sun.misc.MessageUtils.out;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AnimeshChaturvedi
 */
public class WSDLSEM {

    static String path = "G:\\BackUp\\16-11-16\\WebServices\\Eucalyptus-CC\\";

    public static void main(String arg[]) throws ParserConfigurationException, SAXException, IOException {
        File firstDir = new File(path + "wsdl");

        File summaryOfWSDLEvolution = new File(path + "SEM");
        summaryOfWSDLEvolution.mkdirs();
        File SEMWSDLResult = new File(path + "\\SEM\\WSDLSEM" + summaryOfWSDLEvolution.getParentFile().getName() + ".csv");

        FileWriter fileWritter1 = new FileWriter(SEMWSDLResult, false);
        BufferedWriter bufwriter1 = new BufferedWriter(fileWritter1);
        bufwriter1.write("");
        bufwriter1.close();

        File[] observationFile = firstDir.listFiles();
//        int numberOfLine = 0;
//        int numberOfOperation = 0;
        double numberOfLines = 0, numberOfParameter = 0, numberOfMessage = 0, numberOfOperation = 0;
        double LOWSDL = 0, POWSDL = 0, MOWSDL = 0;
        String name = "Name";
        String x;
        x = "name,numberOfLines,numberOfParameter,numberOfMessages,numberOfOperation,LOWSDL,POWSDL,EOWSDL" + "\n";
        try {
            File fileName1 = SEMWSDLResult;
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(fileName1, true));
            bufwriter.write(x);//writes the edited string buffer to the new file
            bufwriter.close();//closes the file
        } catch (Exception e) {//if an exception occurs
        }

        for (int i = 0; i < firstDir.list().length; i++) {
            System.out.println("\n" + observationFile[i].getName());

            name = observationFile[i].getName();
//                numberOfLine = numberOfLines(observationFile[i]);
//                numberOfOperation = numberOfOperation(observationFile[i]);
            numberOfLines = numberOfLines(observationFile[i]);
            numberOfMessage = numberOfMessageDetails(observationFile[i]);
            numberOfParameter = numberOfParameter(observationFile[i]);
            numberOfOperation = numberOfOperation(observationFile[i]);
            LOWSDL = numberOfLines / numberOfOperation;
            POWSDL = numberOfParameter / numberOfOperation;
            MOWSDL = numberOfMessage / numberOfOperation;
            x = name + "," + numberOfLines + "," + numberOfParameter + "," + numberOfMessage + "," + numberOfOperation + "," + LOWSDL + "," + POWSDL + "," + MOWSDL + "\n";// + "," + numberOfLine + "," + numberOfOperation + "\n";
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
                if (line.contains("<!--")) {
//                    System.out.println("Inside if " + line);
                    numberOfCommentLines++;
                    while (!line.contains("-->")) {
//                        System.out.println("Inside while " + line);
                        numberOfCommentLines++;
                        line = br.readLine();
                    }
                }
            }

            br.close();

            System.out.println("number of comment lines " + numberOfCommentLines);
//                System.out.println("Total number of lines : " + linenumber);

        } catch (IOException e) {
            e.printStackTrace();
        }

        numberOfLineWithoutComments = numberOfLineWithComent - numberOfCommentLines;
        return numberOfLineWithoutComments;
    }

    private static int numberOfParameter(File file) {
        int numberOfParmeter = 0;
        WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(file.getPath());
        for (Schema schema : defs.getSchemas()) {
            for (ComplexType ct : schema.getComplexTypes()) {
//                out("    ComplexType Name: " + ct.getName());
//                if (ct.getAttributes().size() > 0) {
//                    for (Attribute attr : ct.getAttributes()) {
//                        out("      Attribute Name: " + attr.getName());
//                    }
//                }
                /*
                 * ct.getModel() delivers the child element used in complexType. In case
                 * of 'sequence' you can also use the getSequence() method.
                 */
                if (ct.getModel() instanceof ModelGroup) {
                    for (SchemaComponent sc : ((ModelGroup) ct.getModel()).getParticles()) {
//                        out("      Particle Name: " + sc.getName() + "\n");
                        numberOfParmeter++;
                    }
                }
            }

            if (schema.getSimpleTypes().size() > 0) {
                for (SimpleType st : schema.getSimpleTypes()) {
//                    out("    SimpleType Name: " + st.getName());
                    numberOfParmeter++;
                }
            }
        }
        return numberOfParmeter;
    }

    private static int numberOfMessageDetails(File file) {
        int numberOfMessages = 0;
        try {
            WSDLParser parser = new WSDLParser();
            Definitions defs = parser.parse(file.getPath());
            List<String> out = new ArrayList<String>();

            for (Message msg : defs.getMessages()) {
                out("  Message Name: " + msg.getName());
                out("  Message Parts: ");
                numberOfMessages++;
                for (Part part : msg.getParts()) {
                    out("    Part Name: " + part.getName());
                    out("    Part Element: " + ((part.getElement() != null) ? part.getElement() : "not available!"));
                    out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!"));
                    out("");
                }
            }
            out("");
            //return numberOfOperations;
        } catch (Exception e) {

        }
        return numberOfMessages;
    }

    private static int numberOfOperation(File file) {
        FileReader reader = null;
        int numberOfOperations = 0;
        try {
            WSDLParser parser = new WSDLParser();
            Definitions defs = parser.parse(file.getPath());
//            List<String> out = new ArrayList<String>();

            for (PortType pt : defs.getPortTypes()) {
//                out("  PortType Name: " + pt.getName());
//                out("  PortType Operations: ");
                for (Operation op : pt.getOperations()) {
//                    out("    Operation Input Message: "
//                            + op.getInput().getMessage().getQname());
//
//                    out("    Operation Output Message: "
//                            + op.getOutput().getMessage().getQname());
                    numberOfOperations++;
                }
                out("");
            }
            out("");
        } catch (Exception e) {
            return 0;
        }
        return numberOfOperations;
    }

}
