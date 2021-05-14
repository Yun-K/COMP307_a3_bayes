package part2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {
    private List<Email> train_labelledEmailList;

    private List<Email> test_unlabelledEmailList;

    /**
     * map classLabel to the prior probability
     * <p>
     * i.e P(Class=spam) = ? && P(Class=non-spam) = ?
     */
    private Map<String, Double> label_prior_prob_map;

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

        // calculate and assign the prior prob from train_dataset
        this.label_prior_prob_map = calculatePriorProbability(this.train_labelledEmailList);

    }

    public void train_constructClassifier() {
        /*
         * first,Computes P(class | instance data)for each class, and choose the class with the
         * highest probability.
         */

    }

    public void test_applyClassifier() {

    }

    /**
     * Description: <br/>
     * Method for calculating the prior probability.(i.e. p(class))
     * <p>
     * i.e P(Class=spam) = ? && P(Class=non-spam) = ?
     * 
     * @author Yun Zhou
     * @param labelledEmails
     *            the labelled data set for training
     * @return the map that map the classLabel to the corresponding probability
     */
    private Map<String, Double> calculatePriorProbability(List<Email> labelledEmails) {
        // calculate p(class) from train_dataSet first
        double spamOccurance = 0.0;
        double nonSpamOccurance = 0.0;
        for (Email email : labelledEmails) {
            int classLabel = email.getClassLabel_spam_or_notSpam();
            if (classLabel == 1) {
                spamOccurance++;
            } else {
                nonSpamOccurance++;
            }
        }
        Map<String, Double> tempMap = new HashMap<String, Double>();
    
        double spamProb = spamOccurance / train_labelledEmailList.size();
        double noSpamProb = nonSpamOccurance / train_labelledEmailList.size();
    
        assert spamProb + noSpamProb == 1;
    
        tempMap.put("spam", spamProb);
        tempMap.put("noSpam", noSpamProb);
        return tempMap;
    
        // this.label_prob_map.put("spam", spamProb);
        // this.label_prob_map.put("noSpam", noSpamProb);
    
    }

}
