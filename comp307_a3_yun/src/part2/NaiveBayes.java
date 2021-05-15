package part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {

    /** string for before adding emails to address zero occurance case */
    final String preFix_bef0 = "before_";

    /** prefix for attributes */
    final String preFix_true = "true_", preFix_false = "false_";

    final String spam = "spam", noSpam = "noSpam";

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
    Email fakeEmail_allFalse = new Email(new ArrayList<Integer>() {
        { // we've 12 attributes in total, so assign it first
            for (int i = 0; i < 12; i++)
                add(0);
        }
    }, 0);// 0 means it is non-spam email

    private List<Email> train_labelledEmailList;

    private List<Email> test_unlabelledEmailList;

    /**
     * map the corresponing att/classLabel to it's Email list
     * <p>
     * for posterior & likelihood use
     */
    private Map<String, List<Email>> aMap = new HashMap<String, List<Email>>();

    /**
     * map classLabel to the prior probability.
     * <p>
     * 
     * i.e P(Class=spam=1=true) = ? && P(Class=non-spam=0=false) = ?
     * <p>
     * p(attribute0 = true|1),...,p(att 11 = true|1 ) = ?
     */
    private Map<String, Double> prior_classAttLabel_prob_map;

    /**
     * p(att=? | ClassLabel=?)
     * <p>
     * hint: classLabel=spam OR noSpam
     */
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

        // add emails to avoid the zero occurance
        this.train_labelledEmailList.add(fakeEmail_allTrue);
        this.train_labelledEmailList.add(fakeEmail_allFalse);
        //
        this.prior_classAttLabel_prob_map
                .putAll(calculatePriorProbability(this.train_labelledEmailList, true));

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

    /**
     * Description: <br/>
     * Calculate the likelihood prob of the given 2 input args.
     * <P>
     * i.e. P(att=? | ClassLabel=? )=?
     * 
     * @author Yun Zhou
     * @param attribute
     * @param classLabel_spamOrNotSpam
     * @return
     */
    public double getSingleLikeliHood(String attribute, String classLabel_spamOrNotSpam) {
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

        // set up the tempPrefix
        StringBuffer tempPreFix = isAddZero ? new StringBuffer("")
                : new StringBuffer(this.preFix_bef0);

        /*
         * this list is for prior use which is P(att=?)=?
         * 
         * list for assigning attribute occurance among all the labelled email data set, only
         * increase the corresponding index value iff the int att_label is equal to 1
         */
        List<Double> attributeOccuranceList = new ArrayList<Double>() {
            { // we've 12 attributes in total, so assign it first
                for (int i = 0; i < 12; i++)
                    add(0.0);
            }
        };

        for (Email email : labelledEmails) {
            // do the classLabel: spam or noSpam first
            int classLabel = email.getClassLabel_spam_or_notSpam();
            String k = "";
            if (classLabel == 1) {
                spamOccurance++;
                // map classLabel to it's occurance
                k = tempPreFix + this.spam;
            } else if (classLabel == 0) {
                nonSpamOccurance++;
                k = tempPreFix + this.noSpam;
            }
            if (!aMap.containsKey(k)) {// map corresponding email to this ClassLabel
                aMap.put(k, new ArrayList<Email>());
            }
            aMap.get(k).add(email);

            // then calculate the corresponding attribute occurances
            for (int attIndex = 0; attIndex < email.getAttributeList().size(); attIndex++) {
                int att_label = email.getAttributeList().get(attIndex);
                String att_key = "";
                if (att_label == 1) {
                    double oldVal = attributeOccuranceList.get(attIndex);
                    attributeOccuranceList.set(attIndex, (oldVal + 1));
                    // map this email into attribute=1=true
                    att_key = tempPreFix.toString() + this.preFix_true + "att" + attIndex;

                } else if (att_label == 0) {
                    // map this email into attribute=0=false
                    att_key = tempPreFix.toString() + this.preFix_false + "att" + attIndex;
                }

                if (!aMap.containsKey(att_key)) {
                    this.aMap.put(att_key, new ArrayList<Email>());
                }
                aMap.get(att_key).add(email);
                assert aMap.get(att_key).size() > 0;
                assert aMap.get(att_key).size() <= labelledEmails.size();

            }
        }
        assert attributeOccuranceList.size() == 12;

        // tempMap for storing the prior prob of classLabel/attribute
        // like P(Spam) = ?, P(att0)= ?
        Map<String, Double> tempMap = new HashMap<String, Double>();
        // calculte & assign the p(att)
        for (int attIndex = 0; attIndex < attributeOccuranceList.size(); attIndex++) {
            double prob = attributeOccuranceList.get(attIndex) / labelledEmails.size();
            String key = tempPreFix + String.valueOf(attIndex);
            tempMap.put(key, prob);
            System.out.println(key + "\t" + prob);
        }

        // assigning the spam class label: p(class)
        double spamProb = spamOccurance / labelledEmails.size();
        double noSpamProb = nonSpamOccurance / labelledEmails.size();
        assert spamProb + noSpamProb == 1;// assertion check
        System.out.println(
                "According to the spamlabelled.dat, we can get:");
        String tempString = isAddZero ? "After " : "Before ";// check if it's before or after
        System.out.println(tempString + "applying Zero-Occurance:\n"
                           + "\tSpam prob, P(Spam)= " + spamProb
                           + "\n\tNo-spam prob,P(noSpam)= " + noSpamProb);

        tempMap.put(tempPreFix.toString() + this.spam, spamProb);
        tempMap.put(tempPreFix.toString() + this.noSpam, noSpamProb);
        // System.out.println(tempPreFix.toString());
        return tempMap;
    }

}
