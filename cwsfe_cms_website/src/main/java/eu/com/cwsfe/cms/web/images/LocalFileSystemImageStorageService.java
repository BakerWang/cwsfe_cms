package eu.com.cwsfe.cms.web.images;

import eu.com.cwsfe.cms.dao.CmsGlobalParamsDAO;
import eu.com.cwsfe.cms.model.BlogPostImage;
import eu.com.cwsfe.cms.model.CmsGlobalParam;
import eu.com.cwsfe.cms.model.CmsNewsImage;
import eu.com.cwsfe.cms.web.news.CmsNewsImagesController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Radoslaw Osinski
 */
@Profile({"wildfly", "tomcat"})
@Service
public class LocalFileSystemImageStorageService implements ImageStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsNewsImagesController.class);

    @Autowired
    private CmsGlobalParamsDAO cmsGlobalParamsDAO;

    public void storeNewsImage(CmsNewsImage cmsNewsImage) {
        CmsGlobalParam newsImagesPath = cmsGlobalParamsDAO.getByCode("CWSFE_CMS_NEWS_IMAGES_PATH");
        storeImage(cmsNewsImage.getFile(), newsImagesPath.getValue());
    }

    public void storeBlogImage(BlogPostImage blogPostImage) {
        CmsGlobalParam blogImagesPath = cmsGlobalParamsDAO.getByCode("CWSFE_CMS_BLOG_IMAGES_PATH");
        storeImage(blogPostImage.getFile(), blogImagesPath.getValue());
    }

    private void storeImage(MultipartFile image, String imagePath) {
        File copiedFile = new File(imagePath, image.getOriginalFilename());
        try {
            copiedFile.setExecutable(false);
            Files.copy(image.getInputStream(), copiedFile.toPath());
        } catch (IOException e) {
            LOGGER.error("Cannot save image to " + copiedFile.getAbsolutePath(), e);
        }
    }

}