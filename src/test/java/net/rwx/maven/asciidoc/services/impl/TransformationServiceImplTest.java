/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rwx.maven.asciidoc.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import static org.junit.Assert.*;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class TransformationServiceImplTest {
    
    private static String HTML_OUTPUT = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"><title>Simple Document</title><meta content=\"DocBook XSL Stylesheets V1.77.1\" name=\"generator\"></head><body bgcolor=\"white\" text=\"black\" link=\"#0000FF\" vlink=\"#840084\" alink=\"#0000FF\"><div lang=\"en\" class=\"article\"><div class=\"titlepage\"><div><div><h2 class=\"title\"><a name=\"N10003\"></a>Simple Document</h2></div><div><div class=\"author\"><h3 class=\"author\"><span class=\"firstname\">Arnaud</span> <span class=\"surname\">Fonce</span></h3><code class=\"email\">&lt;<a class=\"email\" href=\"mailto:arnaud.fonce@r-w-x.net\">arnaud.fonce@r-w-x.net</a>&gt;</code></div></div></div><hr></div><div class=\"toc\"><p><b>Table of Contents</b></p><dl><dt><span class=\"section\"><a href=\"#_introduction\">Introduction</a></span></dt></dl></div><p>This is a simple document.</p><div class=\"section\"><div class=\"titlepage\"><div><div><h2 class=\"title\" style=\"clear: both\"><a name=\"_introduction\"></a>Introduction</h2></div></div></div><p>This the simple introduction into simple document.</p></div></div></body></html>";

    String input, output, documentPath;

    @Before
    public void setUp() throws Exception {
        String temporaryDirectory = FileUtils.getTemporaryDirectory();
        output = temporaryDirectory + File.separator + "output.txt";
        input = temporaryDirectory + File.separator + "test.xml";
        documentPath = temporaryDirectory + File.separator + "static";
        writeFileFromResource( "test.xml", input );
    }
    
    @Test
    public void testExecuteOK() throws Exception {
        TransformationServiceImpl instance = new TransformationServiceImpl();
        instance.execute( input, "html/docbook.xsl", output, documentPath );

        assertEquals( HTML_OUTPUT, FileUtils.readFileToString( new File( output ) ) );
    }
    
    @Test(expected = TransformerException.class)
    public void testExecuteNoInputFile() throws IOException, TransformerException {
        TransformationServiceImpl instance = new TransformationServiceImpl();
        instance.execute( documentPath + File.separator + "fake.xml", "html/docbook.xsl", output, documentPath );
    }
    
    @Test(expected = TransformerConfigurationException.class)
    public void textExecuteNoXsl() throws IOException, TransformerException {
        TransformationServiceImpl instance = new TransformationServiceImpl();
        instance.execute( input, "fake/docbook.xsl", output, documentPath );
    }
    
    @Test(expected = FileNotFoundException.class)
    public void testExecuteNoOutput() throws Exception {
        TransformationServiceImpl instance = new TransformationServiceImpl();
        instance.execute( input, "html/docbook.xsl", "/fake/output", documentPath );
    }
    
    private void writeFileFromResource( String resourceName, String fileName ) throws IOException {
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream( resourceName );
        
        File outputFile = new File( fileName );
        outputFile.createNewFile();
        OutputStream os = new FileOutputStream( outputFile );
        
        byte[] buffer = new byte[1024];
        int nbBytes = is.read( buffer );
        do {
            os.write( buffer, 0, nbBytes );
            nbBytes = is.read( buffer );
        }while( nbBytes > 0 );
        
        os.close();
        is.close();
    }
}
