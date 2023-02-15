
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Animesh2
 */
public class TestWSCode {

    public static void main(String arg[]) throws FileNotFoundException, IOException {
        File firstDir = new File("C:\\Users\\Animesh2\\Desktop\\Eucalyptus\\Eucalyptus_handler_CC_Code");

        File summaryOfWSCodeEvolution = new File("C:\\Users\\Animesh2\\Desktop\\Eucalyptus\\SummaryOfCodeEvolution");
        summaryOfWSCodeEvolution.mkdirs();
        File EvolutionWSCode = null;

        try {
            System.out.println(" File created " + summaryOfWSCodeEvolution.getParent());
            EvolutionWSCode = File.createTempFile("CodeLineCount", ".txt", new File(summaryOfWSCodeEvolution.getAbsolutePath()));
        } catch (IOException ex) {
        }

        File[] observationFile = firstDir.listFiles();
//        int numberOfLine = 0;
//        int numberOfOperation = 0;
        int numberOfCodeLines = 0;
        String name = "Name";
        String x;
        x = "name,numberOfCodeLines" + "\n";
        try {
            File fileName1 = EvolutionWSCode;
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(fileName1, true));
            bufwriter.write(x);//writes the edited string buffer to the new file
            bufwriter.close();//closes the file
        } catch (Exception e) {//if an exception occurs
        }

        for (int i = 0; i < firstDir.list().length; i++) {

            System.out.println(observationFile[i].getName() + "   " + firstDir.list().length);
            name = observationFile[i].getName();
//                numberOfLine = numberOfLines(observationFile[i]);
//                numberOfOperation = numberOfOperation(observationFile[i]);
            numberOfCodeLines = numberOfLines(observationFile[i]);
            x = name + "," + numberOfCodeLines + "\n";// + "," + numberOfLine + "," + numberOfOperation + "\n";
            System.out.println(x);
            try {
                File fileName1 = EvolutionWSCode;
                BufferedWriter bufwriter = new BufferedWriter(new FileWriter(fileName1, true));
                System.out.println(x);
                bufwriter.write(x); //writes the edited string buffer to the new file
                bufwriter.close(); //closes the file
            } catch (Exception e) {
                //if an exception occurs
            }
        }
    }

     private static int numberOfLines(File file) throws FileNotFoundException, IOException {
        int linenumber = 0;
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);
            while (lnr.readLine() != null) {
                linenumber++;
            }
            lnr.close();
        } else {
            System.out.println("File does not exists!");
        }
        return linenumber;
    }
}
