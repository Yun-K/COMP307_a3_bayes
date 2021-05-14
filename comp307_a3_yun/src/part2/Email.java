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
    private int spam_or_notSpam = -1;

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
            this.spam_or_notSpam = -1;
        }
        this.spam_or_notSpam = spam;

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
     * Get the spam_or_notSpam.
     *
     * @return the spam_or_notSpam
     */
    public int getSpam_or_notSpam() {
        return spam_or_notSpam;
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
     * Set the spam_or_notSpam.
     *
     * @param spam_or_notSpam
     *            the spam_or_notSpam to set
     */
    public void setSpam_or_notSpam(int spam_or_notSpam) {
        this.spam_or_notSpam = spam_or_notSpam;
    }

}
