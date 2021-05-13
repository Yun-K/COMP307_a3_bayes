package part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: <br/>
 * Class for reading and parsing the file into the java format.
 * 
 * @author Yun Zhou 300442776
 * @version
 */
public class Util {

    /**
     * Description: <br/>
     * Read the file and parse the file into the Java <code>Email</code> objects and return the
     * list of <code> Email</code>.
     * 
     * @author Yun Zhou
     * @param filePath
     *            the path to the file that need to be read
     * @return the list of <code>Email</code> objects that is parsed from the datafile
     * @throws Exception
     *             like IOException, Runtime exccetion, etc
     */
    public static List<Email> readFile(String filePath) throws Exception {
        List<Email> emailsToReturn = new ArrayList<Email>();
        /*
         * do the preCondition Check first
         */
        if (filePath == null || filePath.isEmpty()) {
            throw new NullPointerException("File path can not be null!");
        }
        boolean isLabelled = false;// for checking if it is labelled file or unlabelled file
        if (filePath.contains("spamLabelled")) {
            isLabelled = true;
        } else if (filePath.contains("spamUnlabelled")) {
            isLabelled = false;
        } else {
            System.err.println(
                    "please check file, only consider spamUnlabelled.dat && spamLabelled.dat,"
                               + " should not contains other file");
            assert false;//
        }

        FileReader fileReader = new FileReader(new File(filePath));
        BufferedReader bReader = new BufferedReader(fileReader);
        String line = bReader.readLine();
        while (!line.isEmpty() || line != null) {
            String regex = "\\s+"; // split by the space
            List<String> parseAttributes_string = new ArrayList<String>(
                    Arrays.asList(line.split(regex)));
            parseAttributes_string.remove(0);// remove the 1st element since it is the empty
                                             // string

            int classLabel = -1;
            if (isLabelled) {
                // System.out.println(parseAttributes_string.size());
                assert parseAttributes_string.size() == 13;
                // remove the last attribute and add it into the classLabel list
                classLabel = (Integer
                        .valueOf(parseAttributes_string.remove(parseAttributes_string.size() - 1)));
            }
            assert parseAttributes_string.size() == 12;// check if it is removed

            List<Integer> attributeList_integer = new ArrayList<Integer>();
            for (String attribute : parseAttributes_string) {
                attributeList_integer.add(Integer.parseInt(attribute));
            }

            emailsToReturn.add(new Email(attributeList_integer, classLabel));

            line = bReader.readLine();// read next line
            if (line == null || line.isEmpty()) {
                break;
            }
        }

        bReader.close();
        return emailsToReturn;
    }

}
