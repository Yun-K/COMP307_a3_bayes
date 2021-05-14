package part2;

import java.util.List;

public class NaiveBayes {
    private List<Email> train_labelledEmailList;

    private List<Email> test_unlabelledEmailList;

    /**
     * A constructor. It construct a new instance of NaiveBayes.
     *
     * @param labelledFilePath
     * @param unLabelledFilePath
     * @throws Exception
     */
    public NaiveBayes(String labelledFilePath, String unLabelledFilePath) throws Exception {
        this.train_labelledEmailList = Util.readFile(labelledFilePath);
        this.test_unlabelledEmailList = Util.readFile(unLabelledFilePath);

    }

    public void train_constructClassifier() {

    }

    public void test_applyClassifier() {

    }

}
