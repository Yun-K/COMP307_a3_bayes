package part2;

import java.util.List;

public class NaiveBayes {
    List<Email> train_labelledEmailList;

    List<Email> test_unlabelledEmailList;

    public NaiveBayes(String labelledFilePath, String unLabelledFilePath) throws Exception {
        this.train_labelledEmailList = Util.readFile(labelledFilePath);
        this.test_unlabelledEmailList = Util.readFile(unLabelledFilePath);
    }

}
