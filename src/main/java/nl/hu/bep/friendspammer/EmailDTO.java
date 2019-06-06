package nl.hu.bep.friendspammer;

public class EmailDTO {
    private String to;
    private String from;
    private String subject;
    private String text;
    private boolean asHtml;

    public EmailDTO(String to, String from, String subject, String text, boolean asHtml) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.asHtml = asHtml;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAsHtml() {
        return asHtml;
    }

    public void setAsHtml(boolean asHtml) {
        this.asHtml = asHtml;
    }
}
