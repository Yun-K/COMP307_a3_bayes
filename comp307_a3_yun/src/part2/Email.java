package part2;

import java.util.List;

public class Email {

    /**
     * 12 binary attributes, only contains 0 or 1.
     * <p>
     * 0 for false, 1 for true
     */
    private List<Integer> attributeList;

    /** right-most column for labelledSpam.data is the class: 1 = spam, 0 = non-spam. */
    private int classLabel_spam_or_notSpam = -1;

    private int predicted_classLabel = -1;

    /**
     * A constructor. It construct a new instance of Email.
     *
     * @param attributeList
     *            12 binary attributes
     * @param spam
     *            right-most column for labelledSpam.data is the class: 1 = spam, 0 = non-spam.
     */
    public Email(List<Integer> attributeList, int spam) {
        this.attributeList = attributeList;
        if (spam != 0 || spam != 1) {
            // if it's unLabelled email, then set it to -1
            this.classLabel_spam_or_notSpam = -1;
        }
        this.classLabel_spam_or_notSpam = spam;

    }

    /**
     * Get the attributeList.
     *
     * @return the attributeList
     */
    public List<Integer> getAttributeList() {
        return attributeList;
    }

    /**
     * Set the attributeList.
     *
     * @param attributeList
     *            the attributeList to set
     */
    public void setAttributeList(List<Integer> attributeList) {
        this.attributeList = attributeList;
    }

    /**
     * Get the classLabel_spam_or_notSpam.
     * <p>
     * 1 = spam, 0 = non-spam.
     * <p>
     *
     * @return the classLabel_spam_or_notSpam
     */
    public int getClassLabel_spam_or_notSpam() {
        return classLabel_spam_or_notSpam;
    }

    /**
     * Set the classLabel_spam_or_notSpam.
     * <p>
     * 1 = spam, 0 = non-spam.
     * 
     * @param classLabel_spam_or_notSpam
     *            the classLabel_spam_or_notSpam to set
     */
    public void setClassLabel_spam_or_notSpam(int classLabel_spam_or_notSpam) {
        this.classLabel_spam_or_notSpam = classLabel_spam_or_notSpam;
    }

    /**
     * Get the predicted_classLabel.
     *
     * @return  the predicted_classLabel
     */
    public int getPredicted_classLabel() {
        return predicted_classLabel;
    }

    /**
     * Set the predicted_classLabel.
     *
     * @param   predicted_classLabel    the predicted_classLabel to set
     */
    public void setPredicted_classLabel(int predicted_classLabel) {
        this.predicted_classLabel = predicted_classLabel;
    }

}
