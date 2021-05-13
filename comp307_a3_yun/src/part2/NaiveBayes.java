package part2;

import java.util.List;

public class NaiveBayes {
    List<Email> train_labelledEmailList;

    List<Email> test_unlabelledEmailList;

    public NaiveBayes(String labelledFilePath, String unLabelledFilePath) throws Exception {
        this.train_labelledEmailList = Util.readFile(labelledFilePath);

        train();

        this.test_unlabelledEmailList = Util.readFile(unLabelledFilePath);

        test();
    }

    private void test() {
        // TODO Auto-generated method stub

    }

    private void train() {
        // TODO Auto-generated method stub

    }

}
