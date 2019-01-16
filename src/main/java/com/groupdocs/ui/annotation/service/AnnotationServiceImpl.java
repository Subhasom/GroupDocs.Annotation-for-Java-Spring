package com.groupdocs.ui.annotation.service;

import com.groupdocs.annotation.common.exception.AnnotatorException;
import com.groupdocs.annotation.common.license.License;
import com.groupdocs.annotation.domain.AnnotationInfo;
import com.groupdocs.annotation.domain.FileDescription;
import com.groupdocs.annotation.domain.PageData;
import com.groupdocs.annotation.domain.config.AnnotationConfig;
import com.groupdocs.annotation.domain.containers.DocumentInfoContainer;
import com.groupdocs.annotation.domain.containers.FileTreeContainer;
import com.groupdocs.annotation.domain.image.PageImage;
import com.groupdocs.annotation.domain.options.FileTreeOptions;
import com.groupdocs.annotation.domain.options.ImageOptions;
import com.groupdocs.annotation.handler.AnnotationImageHandler;
import com.groupdocs.ui.annotation.annotator.AnnotatorFactory;
import com.groupdocs.ui.annotation.config.AnnotationConfiguration;
import com.groupdocs.ui.annotation.entity.request.AnnotateDocumentRequest;
import com.groupdocs.ui.annotation.entity.web.AnnotatedDocumentEntity;
import com.groupdocs.ui.annotation.entity.web.AnnotationDataEntity;
import com.groupdocs.ui.annotation.entity.web.AnnotationPageDescriptionEntity;
import com.groupdocs.ui.annotation.importer.Importer;
import com.groupdocs.ui.annotation.util.AnnotationMapper;
import com.groupdocs.ui.annotation.util.SupportedAnnotations;
import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.request.LoadDocumentPageRequest;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.groupdocs.ui.annotation.util.DocumentTypesConverter.checkedDocumentType;
import static com.groupdocs.ui.annotation.util.DocumentTypesConverter.getDocumentType;
import static com.groupdocs.ui.annotation.util.PathConstants.OUTPUT_FOLDER;

@Service
public class AnnotationServiceImpl implements AnnotationService {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationServiceImpl.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @Autowired
    private AnnotationConfiguration annotationConfiguration;

    private AnnotationImageHandler annotationHandler;

    @PostConstruct
    public void init() {
        // init output directory
        initOutputDirectory();
        // create annotation application configuration
        AnnotationConfig config = new AnnotationConfig();
        // set storage path
        config.setStoragePath(annotationConfiguration.getFilesDirectory());
        config.getFontDirectories().add(annotationConfiguration.getFontsDirectory());

        annotationHandler = new AnnotationImageHandler(config);

        try {
            // set GroupDocs license
            License license = new License();
            license.setLicense(globalConfiguration.getApplication().getLicensePath());
        } catch (Throwable exc) {
            logger.error("Can not verify Annotation license!");
        }
    }

    private void initOutputDirectory() {
        if (StringUtils.isEmpty(annotationConfiguration.getOutputDirectory())) {
            String outputDirectory = String.format("%s%s", annotationConfiguration.getFilesDirectory(), OUTPUT_FOLDER);
            annotationConfiguration.setOutputDirectory(outputDirectory);
        }
        if (!new File(annotationConfiguration.getOutputDirectory()).exists()) {
            new File(annotationConfiguration.getOutputDirectory()).mkdirs();
        }
    }

    @Override
    public GlobalConfiguration getGlobalConfiguration() {
        return globalConfiguration;
    }

    @Override
    public AnnotationConfiguration getAnnotationConfiguration() {
        return annotationConfiguration;
    }

    @Override
    public List<FileDescriptionEntity> getFileList(FileTreeRequest fileTreeRequest) {
        String path = fileTreeRequest.getPath();
        // get file list from storage path
        FileTreeOptions fileListOptions = new FileTreeOptions(path);
        // get temp directory name
        String tempDirectoryName = new AnnotationConfig().getTempFolderName();
        try {
            FileTreeContainer fileListContainer = annotationHandler.loadFileTree(fileListOptions);

            List<FileDescriptionEntity> fileList = new ArrayList<>();
            // parse files/folders list
            for (FileDescription fd : fileListContainer.getFileTree()) {
                FileDescriptionEntity fileDescription = new FileDescriptionEntity();
                fileDescription.setGuid(fd.getGuid());
                // check if current file/folder is temp directory or is hidden
                if (tempDirectoryName.toLowerCase().equals(fd.getName()) || new File(fileDescription.getGuid()).isHidden()) {
                    // ignore current file and skip to next one
                    continue;
                } else {
                    // set file/folder name
                    fileDescription.setName(fd.getName());
                }
                // set file type
                fileDescription.setDocType(fd.getDocumentType());
                // set is directory true/false
                fileDescription.setDirectory(fd.isDirectory());
                // set file size
                fileDescription.setSize(fd.getSize());
                // add object to array list
                fileList.add(fileDescription);
            }
            return fileList;
        } catch (Exception ex) {
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    @Override
    public AnnotatedDocumentEntity getDocumentDescription(LoadDocumentRequest loadDocumentRequest) {
        try {
            // get/set parameters
            String documentGuid = loadDocumentRequest.getGuid();
            String password = loadDocumentRequest.getPassword();
            ImageOptions imageOptions = new ImageOptions();
            // set password for protected document
            if (!password.isEmpty()) {
                imageOptions.setPassword(password);
            }
            // get document info container
            String fileName = FilenameUtils.getName(documentGuid);
            DocumentInfoContainer documentDescription = annotationHandler.getDocumentInfo(fileName, password);

            String documentType = checkedDocumentType(documentGuid, documentDescription.getDocumentType());
            // check if document contains annotations
            AnnotationInfo[] annotations = getAnnotations(documentGuid, documentType);
            // get info about each document page
            List<PageImage> pageImages = null;
            if (annotationConfiguration.getPreloadPageCount() == 0) {
                pageImages = annotationHandler.getPages(fileName, imageOptions);
            }
            String[] supportedAnnotations = SupportedAnnotations.getSupportedAnnotations(documentType);
            // initiate custom Document description object
            AnnotatedDocumentEntity description = new AnnotatedDocumentEntity();
            description.setGuid(documentGuid);
            description.setSupportedAnnotations(supportedAnnotations);
            // initiate pages description list
            List<PageData> pages = documentDescription.getPages();
            List<AnnotationPageDescriptionEntity> pagesDescriptions = new ArrayList<>(pages.size());
            for (int i = 0; i < pages.size(); i++) {
                PageData pageData = pages.get(i);
                // set current page info for result
                PageImage pageImage = pageImages != null ? pageImages.get(i) : null;
                AnnotationPageDescriptionEntity page = getAnnotationPageDescriptionEntity(pageData, pageImage);
                // set annotations data if document page contains annotations
                if (annotations != null && annotations.length > 0) {
                    page.setAnnotations(AnnotationMapper.instance.mapForPage(annotations, page.getNumber()));
                }
                pagesDescriptions.add(page);
            }
            description.setPages(pagesDescriptions);
            // return document description
            return description;
        } catch (Exception ex) {
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    private AnnotationPageDescriptionEntity getAnnotationPageDescriptionEntity(PageData pageData, PageImage pageImage) throws IOException {
        AnnotationPageDescriptionEntity page = new AnnotationPageDescriptionEntity();
        page.setHeight(pageData.getHeight());
        page.setWidth(pageData.getWidth());
        page.setNumber(pageData.getNumber());

        if (pageImage != null) {
            byte[] bytes = IOUtils.toByteArray(pageImage.getStream());
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            page.setData(encodedImage);
        }
        return page;
    }

    @Override
    public AnnotationPageDescriptionEntity getDocumentPage(LoadDocumentPageRequest loadDocumentPageRequest) {
        try {
            // get/set parameters
            String documentGuid = loadDocumentPageRequest.getGuid();
            int pageNumber = loadDocumentPageRequest.getPage();
            String password = loadDocumentPageRequest.getPassword();
            // set options
            ImageOptions imageOptions = new ImageOptions();
            imageOptions.setPageNumber(pageNumber);
            imageOptions.setCountPagesToConvert(1);
            // set password for protected document
            if (!password.isEmpty()) {
                imageOptions.setPassword(password);
            }
            String fileName = FilenameUtils.getName(documentGuid);
            List<PageImage> images = annotationHandler.getPages(fileName, imageOptions);

            DocumentInfoContainer documentDescription = annotationHandler.getDocumentInfo(fileName, password);
            PageData pageData = documentDescription.getPages().get(pageNumber - 1);

            AnnotationPageDescriptionEntity page = getAnnotationPageDescriptionEntity(pageData, images.get(pageNumber - 1));

            // return loaded page object
            return page;
        } catch (Exception ex) {
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    @Override
    public AnnotatedDocumentEntity annotate(AnnotateDocumentRequest annotateDocumentRequest) {
        AnnotatedDocumentEntity annotatedDocument = new AnnotatedDocumentEntity();
        try {
            // get/set parameters
            String documentGuid = annotateDocumentRequest.getGuid();
            String password = annotateDocumentRequest.getPassword();
            AnnotationDataEntity[] annotationsData = annotateDocumentRequest.getAnnotationsData();
            String documentType = checkedDocumentType(documentGuid, annotateDocumentRequest.getDocumentType());
            // initiate AnnotatedDocument object
            // get document info - required to get document page height and calculate annotation top position
            DocumentInfoContainer documentInfo = annotationHandler.getDocumentInfo(new File(documentGuid).getName(), password);
            // initiate list of annotations to add
            List<AnnotationInfo> annotations = new ArrayList<>();
            InputStream file = new FileInputStream(documentGuid);
            file = annotationHandler.removeAnnotationStream(file);
            for (AnnotationDataEntity annotationData : annotationsData) {
                // create annotator
                PageData pageData = documentInfo.getPages().get(annotationData.getPageNumber() - 1);
                // add annotation, if current annotation type isn't supported by the current document type it will be ignored
                try {
                    annotations.add(AnnotatorFactory.createAnnotator(annotationData, pageData).getAnnotationInfo(documentType));
                } catch (Exception ex) {
                    throw new TotalGroupDocsException(ex.getMessage(), ex);
                }
            }
            String forPrint = annotateDocumentRequest.getPrint() ? "Temp" : "";
            String fileName = FilenameUtils.getBaseName(documentGuid) + forPrint + "." + FilenameUtils.getExtension(documentGuid);
            String path = annotationConfiguration.getOutputDirectory() + File.separator + fileName;
            // check if annotations array contains at least one annotation to add
            if (annotations.size() > 0) {
                // Add annotation to the document
                int type = getDocumentType(documentType);
                // Save result stream to file.
                file = annotationHandler.exportAnnotationsToDocument(file, annotations, type);
            }
            (new File(path)).delete();
            if (annotateDocumentRequest.getPrint()) {
                List<AnnotationPageDescriptionEntity> annotatedPages = getAnnotatedPages(password, file);
                annotatedDocument.setPages(annotatedPages);
                (new File(path)).delete();
            } else {
                try (OutputStream fileStream = new FileOutputStream(path)) {
                    IOUtils.copyLarge(file, fileStream);
                    annotatedDocument.setGuid(path);
                }
            }
        } catch (Exception ex) {
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
        return annotatedDocument;
    }

    /**
     * Get pages images of annotated file
     *
     * @param password    password for the file
     * @param inputStream stream of annotated file
     * @return list of pages
     * @throws IOException
     */
    private List<AnnotationPageDescriptionEntity> getAnnotatedPages(String password, InputStream inputStream) throws IOException {
        ImageOptions imageOptions = new ImageOptions();
        // set password for protected document
        if (!password.isEmpty()) {
            imageOptions.setPassword(password);
        }
        List<PageImage> pages = annotationHandler.getPages(inputStream, imageOptions);
        List<AnnotationPageDescriptionEntity> pagesDescriptions = new ArrayList<>(pages.size());
        for (PageImage pageImage : pages) {
            byte[] bytes = IOUtils.toByteArray(pageImage.getStream());
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            AnnotationPageDescriptionEntity page = new AnnotationPageDescriptionEntity();
            page.setData(encodedImage);

            pagesDescriptions.add(page);
        }
        return pagesDescriptions;
    }

    /**
     * Get all annotations from the document
     *
     * @param documentGuid
     * @param documentType
     * @return array of the annotations
     */
    private AnnotationInfo[] getAnnotations(String documentGuid, String documentType) {
        try (InputStream documentStream = new FileInputStream(documentGuid)) {
            int docType = getDocumentType(documentType);
            return new Importer(documentStream, annotationHandler).importAnnotations(docType);
        } catch (AnnotatorException annotatorException) {
            logger.error("Exception while extract annotations from file {}: {}", FilenameUtils.getName(documentGuid), annotatorException.getCause().getLocalizedMessage());
            return new AnnotationInfo[0];
        } catch (Exception ex) {
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

}
