package part2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {
    private List<Email> train_labelledEmailList;

    private List<Email> test_unlabelledEmailList;

    /**
     * map classLabel to the probability
     * <p>
     * i.e P(Class=spam) = ? && P(Class=non-spam) = ?
     */
    private Map<String, Double> label_prob_map;

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

        // calculate p(class) first
        this.label_prob_map = new HashMap<String, Double>();
        double spamOccurance = 0.0;
        double nonSpamOccurance = 0.0;
        for (Email email : train_labelledEmailList) {
            int classLabel = email.getClassLabel_spam_or_notSpam();
            if (classLabel == 1) {
                spamOccurance++;
            } else {
                nonSpamOccurance++;
            }
        }

        double spamProb = spamOccurance / train_labelledEmailList.size();
        double noSpamProb = nonSpamOccurance / train_labelledEmailList.size();
        this.label_prob_map.put("spam", spamProb);
        this.label_prob_map.put("noSpam", noSpamProb);

    }

    public void train_constructClassifier() {
        /*
         * first,Computes P(class | instance data)for each class, and choose the class with the
         * highest probability.
         */

    }

    public void test_applyClassifier() {

    }

}
