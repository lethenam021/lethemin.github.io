/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mol;

/**
 *
 * @author ADMIN
 */
public class FeedBack {
    private int feedbackId;
    private int soldProductId;
    private String userName;
    private String feedbackContent;
    private int rating;
    private String feedbackDate;
    

    public FeedBack() {
    }

    public FeedBack(int feedbackId, int soldProductId, String userName, String feedbackContent, int rating, String feedbackDate) {
        this.feedbackId = feedbackId;
        this.soldProductId = soldProductId;
        this.userName = userName;
        this.feedbackContent = feedbackContent;
        this.rating = rating;
        this.feedbackDate = feedbackDate;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public int getSoldProductId() {
        return soldProductId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public int getRating() {
        return rating;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setSoldProductId(int soldProductId) {
        this.soldProductId = soldProductId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Override
    public String toString() {
        return "FeedBack{" + "feedbackId=" + feedbackId + ", soldProductId=" + soldProductId + ", userName=" + userName + ", feedbackContent=" + feedbackContent + ", rating=" + rating + ", feedbackDate=" + feedbackDate + '}';
    }
    
}
