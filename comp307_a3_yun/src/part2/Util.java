package part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
     * Read the file and parse the file into the Java project.
     * 
     * @author Yun Zhou
     * @param filePath
     *            the path to the file that need to be read
     * @throws Exception
     *             like IOException, Runtime exccetion, etc
     */
    public static void readFile(String filePath) throws Exception {

        FileReader fileReader = new FileReader(new File(filePath));
        BufferedReader bReader = new BufferedReader(fileReader);
        String line = bReader.readLine();
        while (!line.isEmpty() || line != null) {
            String regex = "\\s+"; // split by the space
            String[] attribute = line.split(regex);

            line = bReader.readLine();
        }

        bReader.close();

    }

}
