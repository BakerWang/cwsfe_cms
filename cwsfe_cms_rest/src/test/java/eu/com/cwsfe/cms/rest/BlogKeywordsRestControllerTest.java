package eu.com.cwsfe.cms.rest;

import eu.com.cwsfe.cms.dao.BlogKeywordsDAO;
import eu.com.cwsfe.cms.dao.CmsTextI18nDAO;
import eu.com.cwsfe.cms.model.BlogKeyword;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cwsfe-cms-rest-test.xml"})
@WebAppConfiguration
public class BlogKeywordsRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BlogKeywordsDAO blogKeywordsDAO;
    @Mock
    private CmsTextI18nDAO cmsTextI18nDAO;

    @InjectMocks
    private BlogKeywordsRestController blogKeywordsRestController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(blogKeywordsRestController).build();
    }

    @Test
    public void testBlogKeywordsList() throws Exception {
        String blogKeywordI18n = "blogKeywordI18n";
        long id = 1l;
        String status = "N";
        List<BlogKeyword> blogKeywords = new ArrayList<>();
        BlogKeyword blogKeyword = new BlogKeyword();
        blogKeyword.setId(id);
        blogKeyword.setKeywordName("keyword");
        blogKeyword.setStatus(status);
        blogKeywords.add(blogKeyword);
        when(blogKeywordsDAO.list()).thenReturn(blogKeywords);
        when(cmsTextI18nDAO.findTranslation(anyString(), anyString(), anyString())).thenReturn(blogKeywordI18n);

        ResultActions resultActions = mockMvc.perform(get("/rest/blogKeywordsList")
                .param("languageCode", "en")
                .accept(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value((int) id))
                .andExpect(jsonPath("$[0].keywordName").value(blogKeywordI18n))
                .andExpect(jsonPath("$[0].status").value(status));
    }

    @Test
    public void testBlogKeywordsListWithMissingTranslation() throws Exception {
        String keyword = "keyword";
        long id = 1l;
        String status = "N";
        List<BlogKeyword> blogKeywords = new ArrayList<>();
        BlogKeyword blogKeyword = new BlogKeyword();
        blogKeyword.setId(id);
        blogKeyword.setKeywordName(keyword);
        blogKeyword.setStatus(status);
        blogKeywords.add(blogKeyword);
        when(blogKeywordsDAO.list()).thenReturn(blogKeywords);
        when(cmsTextI18nDAO.findTranslation(anyString(), anyString(), anyString())).thenThrow(new EmptyResultDataAccessException(1));

        ResultActions resultActions = mockMvc.perform(get("/rest/blogKeywordsList")
                .param("languageCode", "en")
                .accept(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value((int) id))
                .andExpect(jsonPath("$[0].keywordName").value(keyword))
                .andExpect(jsonPath("$[0].status").value(status));
    }
}