package eu.com.cwsfe.cms.model;

import eu.com.cwsfe.cms.domains.NewsletterTemplateStatus;

import java.io.Serializable;

/**
 * @author radek
 */
public class NewsletterTemplate implements Serializable {

    private static final long serialVersionUID = -3032324578784912033L;

    private Long id;
    private Long languageId;
    private String name;
    private NewsletterTemplateStatus status;
    private String subject;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewsletterTemplateStatus getStatus() {
        return status;
    }

    public void setStatus(NewsletterTemplateStatus status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
