package part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {

    /** string for before adding emails to address zero occurance case */
    String preFix = "before_";

    /**
     * spamEmail, it's for solving zero occurance, this email got everything with true value
     */
    Email fakeEmail_allTrue = new Email(new ArrayList<Integer>() {
        { // we've 12 attributes in total, so assign it first
            for (int i = 0; i < 12; i++)
                add(1);
        }
    }, 1);// 1 means it is spam email

    /**
     * non-spam Email, it's for solving zero occurance, this email got everything with false
     * value.
     */
    Email fakeEmail1_allFalse = new Email(new ArrayList<Integer>() {
        { // we've 12 attributes in total, so assign it first
            for (int i = 0; i < 12; i++)
                add(0);
        }
    }, 0);// 0 means it is non-spam email

    private List<Email> train_labelledEmailList;

    private List<Email> test_unlabelledEmailList;

    /**
     * map classLabel to the prior probability.
     * <p>
     * 
     * i.e P(Class=spam=1=true) = ? && P(Class=non-spam=0=false) = ?
     * <p>
     * p(attribute0 = true|1),...,p(att 11 = true|1 ) = ?
     */
    private Map<String, Double> prior_classAttLabel_prob_map;

    private Map<String, Double> likelihood_att_prob_map;

    private Map<String, Double> posterior_attLabel_prob_map;

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
        this.prior_classAttLabel_prob_map = calculatePriorProbability(this.train_labelledEmailList,
                false);

    }

    public void train_constructClassifier() {
        /*
         * first,Computes P(class | instance data)for each class, and choose the class with the
         * highest probability.
         */

    }

    /**
     * Description: <br/>
     * To classify unlabelled Email objects, and print the classify result on the screen.
     * <p>
     * Need to calculate P(Spam | att0,...,att11) & P(noSpam | att0,...,att11), then see
     * classify this instance with the higher one.
     * 
     * @author Yun Zhou
     */
    public void test_applyClassifier() {
        // for (Email email : test_unlabelledEmailList) {
        // email.getAttributeList();
        //
        //
        // }

    }

    public double getLikeliHood(String attribute, String classLabel_spamOrNotSpam) {
        double toReturn = 0;
        double classLabel_prob = this.prior_classAttLabel_prob_map.get(classLabel_spamOrNotSpam);
        double attr_prob = this.prior_classAttLabel_prob_map.get(attribute);

        return toReturn;
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
     * @param isAddZero
     * @return the map that map the classLabel to the corresponding probability
     */
    private Map<String, Double> calculatePriorProbability(List<Email> labelledEmails,
            boolean isAddZero) {
        // calculate p(class) from train_dataSet
        // as well as p(attribute 0....attribute 11)from the train_dataset
        double spamOccurance = 0.0;
        double nonSpamOccurance = 0.0;

        // list for assigning attribute occurance among all the labelled email data set,
        // only increase the corresponding index value iff the int att_label is equal to 1
        List<Double> attributeOccuranceList = new ArrayList<Double>() {
            { // we've 12 attributes in total, so assign it first
                for (int i = 0; i < 12; i++)
                    add(0.0);
            }
        };

        for (Email email : labelledEmails) {
            // do the classLabel: spam or noSpam first
            int classLabel = email.getClassLabel_spam_or_notSpam();
            if (classLabel == 1) {
                spamOccurance++;
            } else if (classLabel == 0) {
                nonSpamOccurance++;
            }

            // then calculate the corresponding attribute occurances
            for (int attIndex = 0; attIndex < email.getAttributeList().size(); attIndex++) {
                int att_label = email.getAttributeList().get(attIndex);
                if (att_label == 1) {
                    // check if it is null
                    double oldVal = attributeOccuranceList.get(attIndex) == null ? 0
                            : attributeOccuranceList.get(attIndex);
                    attributeOccuranceList.set(attIndex, (oldVal + 1));

                } else if (att_label == 0) {
                    // do nothing
                }

            }
        }
        assert attributeOccuranceList.size() == 12;

        // tempMap for storing the prior prob of classLabel/attribute
        // like P(Spam) = ?, P(att0)= ?
        Map<String, Double> tempMap = new HashMap<String, Double>();
        // calculte & assign the p(att)
        for (int attIndex = 0; attIndex < attributeOccuranceList.size(); attIndex++) {
            double prob = attributeOccuranceList.get(attIndex) / labelledEmails.size();
            StringBuffer key = new StringBuffer();
            if (!isAddZero) {
                key.append(this.preFix);
            }
            key.append(attIndex);

            // String key = "before_att" + attIndex;
            tempMap.put(key.toString(), prob);
            System.out.println(key.toString() + "\t" + prob);
        }

        // assigning the spam class label: p(class)
        double spamProb = spamOccurance / labelledEmails.size();
        double noSpamProb = nonSpamOccurance / labelledEmails.size();
        assert spamProb + noSpamProb == 1;// assertion check
        System.out.println(
                "According to the spamlabelled.dat, we can get:\n"
                           + "Before applying Zero-Occurance:\n"
                           + "\tSpam prob, P(Spam)= " + spamProb
                           + "\tNo-spam prob,P(noSpam)= " + noSpamProb);

        StringBuffer tempPreFix = new StringBuffer();
        if (!isAddZero) {
            tempPreFix.append(this.preFix);
        }

        tempMap.put(tempPreFix.toString() + "spam", spamProb);
        tempMap.put(tempPreFix.toString() + "noSpam", noSpamProb);
        return tempMap;
        // this.label_prob_map.put("spam", spamProb);
        // this.label_prob_map.put("noSpam", noSpamProb);

    }

}
