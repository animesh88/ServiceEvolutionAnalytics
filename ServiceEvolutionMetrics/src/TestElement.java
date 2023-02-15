
import com.predic8.wsdl.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.predic8.schema.ComplexType;
import com.predic8.schema.ModelGroup;
import com.predic8.schema.Schema;
import com.predic8.schema.SchemaComponent;
import com.predic8.schema.SimpleType;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Animesh2
 */
public class TestElement {

    public static void main(String arg[]) {
        File firstDir = new File("D:\\2016-08-16\\AOI\\AOI\\IEEE CloudCom 2015\\Eucalyptus\\awssum-amazon-ec2-master\\wsdl");

        File summaryOfWSDLEvolution = new File("D:\\2016-08-16\\AOI\\AOI\\IEEE CloudCom 2015\\Eucalyptus\\awssum-amazon-ec2-master\\ParameterSummaryOfWSDLEvolution");
        summaryOfWSDLEvolution.mkdirs();
        File EvolutionWSDL = null;

        try {
            System.out.println(" File created " + summaryOfWSDLEvolution.getParent());
            EvolutionWSDL = File.createTempFile("ParameterCount", ".txt", new File(summaryOfWSDLEvolution.getAbsolutePath()));
        } catch (IOException ex) {
        }

        File[] observationFile = firstDir.listFiles();
//        int numberOfLine = 0;
//        int numberOfOperation = 0;
        int numberOfParameter = 0;
        String name = "Name";
        String x;
        x = "name,numberOfParameter" + "\n";
        try {
            File fileName1 = EvolutionWSDL;
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
            numberOfParameter = numberOfMessageDetails(observationFile[i]);
            x = name + "," + numberOfParameter + "\n";// + "," + numberOfLine + "," + numberOfOperation + "\n";
            System.out.println(x);
            try {
                File fileName1 = EvolutionWSDL;
                BufferedWriter bufwriter = new BufferedWriter(new FileWriter(fileName1, true));
                System.out.println(x);
                bufwriter.write(x); //writes the edited string buffer to the new file
                bufwriter.close(); //closes the file
            } catch (Exception e) {
                //if an exception occurs
            }
        }
    }

    private static int numberOfMessagesName(File file) {
        FileReader reader = null;
        try {
            WSDLParser parser = new WSDLParser();
            Definitions defs = parser.parse(file.getPath());
            List<String> out = new ArrayList<String>();

            int numberOfOperations = 0;

            for (PortType pt : defs.getPortTypes()) {
                out("  PortType Name: " + pt.getName());
                out("  PortType Operations: ");
                for (Operation op : pt.getOperations()) {
                    out("    Operation Input Message: "
                            + op.getInput().getMessage().getQname());

                    out("    Operation Output Message: "
                            + op.getOutput().getMessage().getQname());
                }
                out("");
            }
            out("");
            return numberOfOperations;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void out(String str) {
        System.out.println(str);
    }

    private static int numberOfMessageDetails(File file) {
        int numberOfElements = 0;
        FileReader reader = null;
        try {
            WSDLParser parser = new WSDLParser();
            Definitions defs = parser.parse(file.getPath());
            List<String> out = new ArrayList<String>();

            int numberOfOperations = 0;

            for (Message msg : defs.getMessages()) {
                out("  Message Name: " + msg.getName());
                out("  Message Parts: ");
                for (Part part : msg.getParts()) {
                    out("    Part Name: " + part.getName());
                    out("    Part Element: " + ((part.getElement() != null) ? part.getElement() : "not available!"));
                    numberOfElements++;
                    out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!"));
                    out("");
                }
            }
            out("");
            //return numberOfOperations;
        } catch (Exception e) {
           
        }
        return numberOfElements;
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
                        out("      Particle Name: " + sc.getName() + "\n");
                        numberOfParmeter++;
                    }
                }
            }

            if (schema.getSimpleTypes().size() > 0) {
                for (SimpleType st : schema.getSimpleTypes()) {
                    out("    SimpleType Name: " + st.getName());
                    numberOfParmeter++;
                }
            }
        }
        return numberOfParmeter;
    }
}
