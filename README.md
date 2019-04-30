![Groupdocs document & pdf annotator](https://raw.githubusercontent.com/groupdocs-annotation/groupdocs-annotation.github.io/master/resources/image/banner.png "GroupDocs.Annotation")
# GroupDocs.Annotation for Java Spring Example
###### version 1.12.20

[![Build Status](https://travis-ci.org/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring.svg?branch=master)](https://travis-ci.org/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring)
[![Maintainability](https://api.codeclimate.com/v1/badges/f6de7b4597a02ddb09df/maintainability)](https://codeclimate.com/github/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/maintainability)
[![GitHub license](https://img.shields.io/github/license/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring.svg)](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/blob/master/LICENSE)

## System Requirements
- Java 8 (JDK 1.8)
- Maven 3


## Annotate & write on document with Java Spring

**GroupDocs.Annotations for Java** is a powerful library that provides flexible API which allows you to **annotate PDF**, DOCX, PPT, and over 90 document formats with no external dependencies or need to convert files to PDF. Using API you can write on documents using multiple annotation tools such as arrow annotation, text annotation or even draw on a document using freehand drawing tool.

With this app, you can annotate and **write on document** using modern and responsive web UI interface. It is possible to use it as a standalone application or integrate it into your project.

**Note:** without a license application will run in trial mode, purchase [GroupDocs.Annotation for Java license](https://purchase.groupdocs.com/order-online-step-1-of-8.aspx) or request [GroupDocs.Annotation for Java temporary license](https://purchase.groupdocs.com/temporary-license).


## Demo Video

<p align="center">
  <a title="Document annotation for JAVA " href="https://www.youtube.com/watch?v=TVb7GwSNDqw"> 
    <img src="https://raw.githubusercontent.com/groupdocs-annotation/groupdocs-annotation.github.io/master/resources/image/document-annotation-demo.gif" width="100%" style="width:100%;">
  </a>
</p>


## Features 
<p>
<img src="https://raw.githubusercontent.com/groupdocs-annotation/groupdocs-annotation.github.io/master/resources/image/Text_annotation.png?v=1" align="left" width="430"/>
<br/><br/><br/>
  <b>Text annotation</b>
<div>Add text annotation anywhere on a document. Specify font size, color, move around, comment and discuss.</div>
<br/><br/><br/><br/>
</p>
<br/>
<p>
<img src="https://raw.githubusercontent.com/groupdocs-annotation/groupdocs-annotation.github.io/master/resources/image/Free-hand-annotation.png?v=1" align="left" width="430"/>
<br/><br/><br/>
  <b>Freehand Drawing</b>
<div>Draw on a document using freehand drawing tool. It is easy to point attention to specific areas on your document page.</div>
<br/><br/><br/>
</p>
<br/>
<p>
<img src="https://raw.githubusercontent.com/groupdocs-annotation/groupdocs-annotation.github.io/master/resources/image/Blackout-annotation.png?v=1" align="left" width="430"/>
<br/><br/><br/>
  <b>Blackout & Redaction</b>
<div>Useful for blackout or redact sensitive or personally identifiable information on your document.</div>
<br/><br/><br/><br/>
</p>
<br/>
<p>
<img src="https://raw.githubusercontent.com/groupdocs-annotation/groupdocs-annotation.github.io/master/resources/image/Collaborate-annotation.png?v=1" align="left" width="430"/>
<br/><br/><br/>
  <b>Comments</b>
<div>Collaborate and comment on any annotation. Start a discussion right in a document without need for a database.</div>
<br/><br/><br/><br/><br/><br/>
</p>
<hr/>

### More features

- Clean, modern and intuitive design
- Easily switchable colour theme (create your own colour theme in 5 minutes)
- Responsive design
- Mobile support (open application on any mobile device)
- Support over 50 documents and image formats
- Image mode
- Fully customizable navigation panel
- Annotate password protected documents
- Download original documents
- Download annotated documents
- Upload documents
- Annotate document with such annotation types: 
   * **Text** – highlights and comments selected text
   * **Area** – marks an area with a rectangle and adds notes to it
   * **Point** – sticks comments to any point in a document
   * **TextStrikeout** – marks text with a strikethrough styling
   * **Polyline** – draws shapes and freehand lines
   * **TextField** – adds rectangle with a text inside
   * **Watermark** - Horizontal textual watermark 
   * **TextReplacement** – replaces original text with user’s text
   * **Arrow** – draws an arrow on a document
   * **TextRedaction** – fills black rectangle with fixed position (used if you want to hide some text)
   * **ResourcesRedaction**  – fills black rectangle with fixed position
   * **TextUnderline** – marks text with a underline styling
   * **Distance** – measures a distance between objects in a document
- Draw annotation over the document page
- Add comment or reply
- Print document
- Smooth page navigation
- Smooth document scrolling
- Preload pages for faster document rendering
- Multi-language support for displaying errors
- Cross-browser support (Safari, Chrome, Opera, Firefox)
- Cross-platform support (Windows, Linux, MacOS)


## How to run

You can run this sample by one of following methods

#### Build from source

Download [source code](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/archive/master.zip) from github or clone this repository.

```bash
git clone https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring
cd GroupDocs.Annotation-for-Java-Spring
mvn clean spring-boot:run
## Open http://localhost:8080/annotation/ in your favorite browser.
```

#### Build war from source

Download [source code](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/archive/master.zip) from github or clone this repository.

```bash
git clone https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring
cd GroupDocs.Annotation-for-Java-Spring
mvn package -P war
## Deploy this war on any server
```

#### Binary release (with all dependencies)

Download [latest release](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/releases/latest) from [releases page](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/releases). 

**Note**: This method is **recommended** for running this sample behind firewall.

```bash
curl -J -L -o release.tar.gz https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Spring/releases/download/1.12.20/release.tar.gz
tar -xvzf release.tar.gz
cd release
java -jar annotation-spring-1.12.20.jar configuration.yml
## Open http://localhost:8080/annotation/ in your favorite browser.
```

#### Docker image
Use [docker](https://hub.docker.com/u/groupdocs) image.

```bash
mkdir DocumentSamples
mkdir Licenses
docker run -p 8080:8080 --env application.hostAddress=localhost -v `pwd`/DocumentSamples:/home/groupdocs/app/DocumentSamples -v `pwd`/Licenses:/home/groupdocs/app/Licenses groupdocs/annotation
## Open http://localhost:8080/annotation/ in your favorite browser.
```

#### Configuration
For all methods above you can adjust settings in `configuration.yml`. By default in this sample will lookup for license file in `./Licenses` folder, so you can simply put your license file in that folder or specify relative/absolute path by setting `licensePath` value in `configuration.yml`. 

### Annotation configuration options

| Option                             | Type    |   Default value   | Description                                                                                                                                  |
| ---------------------------------- | ------- |:-----------------:|:-------------------------------------------------------------------------------------------------------------------------------------------- |
| **`filesDirectory`**               | String  | `DocumentSamples` | Files directory path. Indicates where uploaded and predefined files are stored. It can be absolute or relative path                          |
| **`fontsDirectory`**               | String  |                   | Path to custom fonts directory.                                                                                                              |
| **`defaultDocument`**              | String  |                   | Absolute path to default document that will be loaded automaticaly.                                                                          |
| **`preloadPageCount`**             | Integer |        `0`        | Indicate how many pages from a document should be loaded, remaining pages will be loaded on page scrolling.Set `0` to load all pages at once |
| **`textAnnotation`**               | Boolean |      `true`       | Enable/disable Text annotation                                                                                                               |
| **`areaAnnotation`**               | Boolean |      `true`       | Enable/disable Area annotation                                                                                                               |
| **`areaAnnotation`**               | Boolean |      `true`       | Enable/disable Point annotation                                                                                                              |
| **`pointAnnotation`**              | Boolean |      `true`       | Enable thumbnails preview                                                                                                                    |
| **`textStrikeoutAnnotation`**      | Boolean |      `true`       | Enable/disable TextStrikeout annotation                                                                                                      |
| **`polylineAnnotation`**           | Boolean |      `true`       | Enable/disable Polyline annotation                                                                                                           |
| **`textFieldAnnotation`**          | Boolean |      `true`       | Enable/disable TextField annotation                                                                                                          |
| **`watermarkAnnotation`**          | Boolean |      `true`       | Enable/disable Watermark annotation                                                                                                          |
| **`textReplacementAnnotation`**    | Boolean |      `true`       | Enable/disable TextReplacement annotation                                                                                                    |
| **`arrowAnnotation`**              | Boolean |      `true`       | Enable/disable Arrow annotation                                                                                                              |
| **`textRedactionAnnotation`**      | Boolean |      `true`       | Enable/disable TextRedaction annotation                                                                                                      |
| **`resourcesRedactionAnnotation`** | Boolean |      `true`       | Enable/disable ResourcesRedaction annotation                                                                                                 |
| **`textUnderlineAnnotation`**      | Boolean |      `true`       | Enable/disable TextUnderline annotation                                                                                                      |
| **`distanceAnnotation`**           | Boolean |      `true`       | Enable/disable Distance annotation                                                                                                           |
| **`downloadOriginal`**             | Boolean |      `true`       | Enable/disable original document downloading                                                                                                 |
| **`downloadAnnotated`**            | Boolen  |      `true`       | Enable/disable signed document downloading                                                                                                   |
| **`zoom`**                         | Boolean |      `true`       | Enable/disable zoom                                                                                                                          |
| **`fitWidth`**                     | Boolean |      `true`       | Enable/disable fit width. Set true to zoom document pages fit width                                                                          | 

## License
The MIT License (MIT). 

Please have a look at the LICENSE.md for more details

## GroupDocs Annotation on other platforms & frameworks

- JAVA Spring [Document & PDF annotator](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-Java-Dropwizard) 
- .NET MVC [Document & PDF annotator](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-.NET-MVC)
- .NET WebForms [Document & PDF annotator](https://github.com/groupdocs-annotation/GroupDocs.Annotation-for-.NET-WebForms)

## Resources
- **Website:** [www.groupdocs.com](http://www.groupdocs.com)
- **Product Home:** [GroupDocs.Annotation for Java](https://products.groupdocs.com/annotation/java)
- **Product API References:** [GroupDocs.Annotation for Java API](https://apireference.groupdocs.com)
- **Download:** [Download GroupDocs.Annotation for Java](http://downloads.groupdocs.com/annotation/java)
- **Documentation:** [GroupDocs.Annotation for Java Documentation](https://docs.groupdocs.com/dashboard.action)
- **Free Support Forum:** [GroupDocs.Annotation for Java Free Support Forum](https://forum.groupdocs.com/c/annotation)
- **Paid Support Helpdesk:** [GroupDocs.Annotation for Java Paid Support Helpdesk](https://helpdesk.groupdocs.com)
- **Blog:** [GroupDocs.Annotation for Java Blog](https://blog.groupdocs.com/category/groupdocs-annotation-product-family)
