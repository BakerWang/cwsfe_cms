package eu.com.cwsfe.cms.rest;

import eu.com.cwsfe.cms.dao.CmsNewsImagesDAO;
import eu.com.cwsfe.cms.model.CmsNewsImage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cwsfe-cms-rest-test.xml"})
@WebAppConfiguration
public class NewsImagesRestControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CmsNewsImagesDAO cmsNewsImagesDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cmsNewsImagesDAO).build();
    }

    @Test
    public void testGetImagesForNews() throws Exception {
        long thumbnailId = 1l;
        String thumbnailFileName = "thumbnailFileName";
        Integer thumbnailHeight = 2;
        Integer thumbnailWidth = 3;
        String thumbnailTitle =  "thumbnailTitle";
        long imageId = 11l;
        String imageFileName = "imageFileName";
        Integer imageHeight = 21;
        Integer imageWidth = 31;
        String imageTitle =  "imageTitle";
        CmsNewsImage thumbnail = new CmsNewsImage();
        thumbnail.setId(thumbnailId);
        thumbnail.setFileName(thumbnailFileName);
        thumbnail.setHeight(thumbnailHeight);
        thumbnail.setWidth(thumbnailWidth);
        thumbnail.setTitle(thumbnailTitle);
        CmsNewsImage image = new CmsNewsImage();
        image.setId(imageId);
        image.setFileName(imageFileName);
        image.setHeight(imageHeight);
        image.setWidth(imageWidth);
        image.setTitle(imageTitle);
        ArrayList<CmsNewsImage> images = new ArrayList<>();
        images.add(image);
        when(cmsNewsImagesDAO.getThumbnailForNews(anyLong())).thenReturn(thumbnail);
        when(cmsNewsImagesDAO.listImagesForNewsWithoutThumbnails(anyLong())).thenReturn(images);

        ResultActions resultActions = mockMvc.perform(get("/rest/newsI18nPairsTotal")
                .param("folderName", "folderName")
                .param("languageCode", "languageCode")
                .param("newsType", "newsType")
                .accept(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.thumbnailImage.id").value((int) thumbnailId))
                .andExpect(jsonPath("$.thumbnailImage.fileName").value(thumbnailFileName))
                .andExpect(jsonPath("$.thumbnailImage.height").value((int) thumbnailHeight))
                .andExpect(jsonPath("$.thumbnailImage.width").value((int) thumbnailWidth))
                .andExpect(jsonPath("$.thumbnailImage.title").value(thumbnailTitle))
                .andExpect(jsonPath("$[0].newsImages.id").value((int) imageId))
                .andExpect(jsonPath("$[0].newsImages.fileName").value(imageFileName))
                .andExpect(jsonPath("$[0].newsImages.height").value((int) imageHeight))
                .andExpect(jsonPath("$[0].newsImages.width").value((int) imageWidth))
                .andExpect(jsonPath("$[0].newsImages.title").value(imageTitle))
        ;
    }
}