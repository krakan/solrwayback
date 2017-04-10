package dk.kb.netarchivesuite.solrwayback;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import dk.kb.netarchivesuite.solrwayback.facade.Facade;
import dk.kb.netarchivesuite.solrwayback.image.ImageUtils;
import dk.kb.netarchivesuite.solrwayback.service.dto.ArcEntry;


public class WarcGzParserTest {
       
    @Test
    public void testWarcGzParser() throws Exception {
        
        File file = getFile("src/test/resources/example_warc/IAH-20080430204825-00000-blackbook.warc.gz");
        
        ArcEntry arcEntry = Facade.getArcEntry(file.getCanonicalPath(), 48777); //Image entry. offsets can be seen in the cdx file
        assertEquals("image/jpeg", arcEntry.getContentType());
        assertEquals("hewlett.jpg", arcEntry.getFileName());
        assertEquals(7812, arcEntry.getWarcEntryContentLength());
        assertEquals(7510, arcEntry.getContentLength());
                        
        BufferedImage image = ImageUtils.getImageFromBinary(arcEntry.getBinary());
        assertEquals(300,image.getWidth());
        assertEquals(116,image.getHeight());        
        assertEquals(" http://www.archive.org/images/hewlett.jpg",arcEntry.getUrl());
    }


    /**
     * Multi protocol resource loader. Primary attempt is direct file, secondary is classpath resolved to File.
     *
     * @param resource a generic resource.
     * @return a File pointing to the resource.
     */
    private static File getFile(String resource) throws IOException {
        File directFile = new File(resource);
        if (directFile.exists()) {
            return directFile;
        }
        URL classLoader = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (classLoader == null) {
            throw new FileNotFoundException("Unable to locate '" + resource + "' as direct File or on classpath");
        }
        String fromURL = classLoader.getFile();
        if (fromURL == null || fromURL.isEmpty()) {
            throw new FileNotFoundException("Unable to convert URL '" + fromURL + "' to File");
        }
        return new File(fromURL);
    }

    /* The warc file used for these tests below can not be shared.                
    @Test
    public void testWarcParserJSZipped() throws Exception {
    
      
        File file = getFile("src/test/resources/273422-246-20170326210303322-00000-sb-prod-har-004.statsbiblioteket.dk.warc.gz");
        
        ArcEntry arcEntry = Facade.getArcEntry(file.getCanonicalPath(), 71228603); //Image entry

        System.out.println(arcEntry.getContentLength());
        System.out.println(new String(arcEntry.getBinary()));
        
        assertEquals("text/css", arcEntry.getContentType());
        assertEquals("style.css", arcEntry.getFileName());
        assertEquals(10952, arcEntry.getWarcEntryContentLength());
        assertEquals(10443, arcEntry.getContentLength());                                          
    }
    
    @Test
    public void testWarcParserHtmlZipped() throws Exception {
        
        File file = getFile("src/test/resources/273422-246-20170326210303322-00000-sb-prod-har-004.statsbiblioteket.dk.warc.gz");
        
        ArcEntry arcEntry = Facade.getArcEntry(file.getCanonicalPath(),     57516462); //HTML

        System.out.println(arcEntry.getContentLength());
        System.out.println(new String(arcEntry.getBinary()));
        
        assertEquals("text/css", arcEntry.getContentType());
        assertEquals("style.css", arcEntry.getFileName());
        assertEquals(10952, arcEntry.getWarcEntryContentLength());
        assertEquals(10443, arcEntry.getContentLength());                          
    }
    
    @Test
    public void testWarcParserImageZipped() throws Exception {
        
        File file = getFile("src/test/resources/273422-246-20170326210303322-00000-sb-prod-har-004.statsbiblioteket.dk.warc.gz");
        
        ArcEntry arcEntry = Facade.getArcEntry(file.getCanonicalPath(), 57271819); //Image entry

        System.out.println(arcEntry.getContentLength());
        System.out.println(new String(arcEntry.getBinary()));
        
        assertEquals("text/css", arcEntry.getContentType());
        assertEquals("style.css", arcEntry.getFileName());
        assertEquals(10952, arcEntry.getWarcEntryContentLength());
        assertEquals(10443, arcEntry.getContentLength());                          
    }
 */
}