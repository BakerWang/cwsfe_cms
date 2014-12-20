<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericPage>
    <jsp:body>

        <h3><spring:message code="BlogKeywordsManagement"/></h3>
        <table id="blogKeywordsList">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><spring:message code="Keyword"/></th>
                <th scope="col"><spring:message code="Actions"/></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

        <h3><spring:message code="BlogKeywordsAdding"/></h3>

        <form>
            <div class="row">
                <label for="keywordName"><spring:message code="Keyword"/></label>
                <input type="text" id="keywordName"/>
            </div>
            <div class="row">
                <input type="button" id="addBlogKeywordButton" class="button small radius"
                       value="<spring:message code="Add"/>">
                <input type="reset" value="Reset" class="button small radius alert">
            </div>
        </form>

    </jsp:body>
</t:genericPage>