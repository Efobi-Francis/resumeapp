package com.fobi.myHngi8InternshipProject2.model;

//model to class to hold contact form data
public class ContactForm {

    private String name;
    private String emailId;
    private String message;

    public ContactForm() {
        super();
    }

    public ContactForm(String name, String emailId, String message){
        super();
        this.name = name;
        this.emailId = emailId;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
