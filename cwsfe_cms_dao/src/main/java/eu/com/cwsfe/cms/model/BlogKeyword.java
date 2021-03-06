package eu.com.cwsfe.cms.model;

import eu.com.cwsfe.cms.domains.BlogKeywordStatus;

import java.io.Serializable;

public class BlogKeyword implements Serializable {

    private static final long serialVersionUID = 5920513316101148082L;

    private Long id;
    private String keywordName;
    private BlogKeywordStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public BlogKeywordStatus getStatus() {
        return status;
    }

    public void setStatus(BlogKeywordStatus status) {
        this.status = status;
    }
}
