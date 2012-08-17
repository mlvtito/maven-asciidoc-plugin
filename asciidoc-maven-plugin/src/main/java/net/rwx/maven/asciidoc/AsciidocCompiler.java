package net.rwx.maven.asciidoc;

import java.io.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.rwx.maven.asciidoc.backends.AsciidocBackend;
import net.rwx.maven.asciidoc.backends.AsciidocBackendSingleton;
import net.rwx.maven.asciidoc.backends.AsciidocBackendTransformation;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.python.util.PythonInterpreter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocCompiler {

    public static final String VERSION = "8.6.7";
    
    private String asciidocFile;
    private Document document;

    public AsciidocCompiler() throws IOException {
        
        ClassLoader loader = this.getClass().getClassLoader();
        
        InputStream is = loader.getResourceAsStream( "asciidoc.tar.gz" );
        asciidocFile = FileUtils.getTemporayAsciidoc();
        FileUtils.uncompress( is, asciidocFile );
        
        String xslDocbook = getBaseXsl( "" );
        is = loader.getResourceAsStream( "docbook-xsl.tar.gz" );
        FileUtils.uncompress( is, xslDocbook );
        
    }
    public void setDocument( Document document ) {

        this.document = document;
    }

    private String getAsciidoc() {
        
        StringBuilder builder = new StringBuilder();
        builder.append( asciidocFile );
        builder.append( File.separator );
        builder.append( "asciidoc-" );
        builder.append( AsciidocCompiler.VERSION );
        builder.append( File.separator );
        builder.append( "asciidoc.py" );
        
        return builder.toString();
    }
    
    private String getBaseXsl( String name ) {
        
        StringBuilder builder = new StringBuilder();
        builder.append( asciidocFile );
        builder.append( File.separator );
        builder.append( "asciidoc-" );
        builder.append( AsciidocCompiler.VERSION );
        builder.append( File.separator );
        builder.append( "docbook-xsl" );
        builder.append( File.separator );
        builder.append( name );
        
        return builder.toString();
    }
    
    private String getXsl( String name ) {
    
        StringBuilder builder = new StringBuilder();
        builder.append( getBaseXsl( "docbook-xsl" ) );
        builder.append( File.separator );
        builder.append( name );
        
        return builder.toString();
    }
    
    private void executeAsciidoc( String input, String backend, String output ) throws IOException {

        /*PySystemState state = new PySystemState();
        
        state.argv.clear();
        state.argv.append( new PyString( getAsciidoc() ) );
        state.argv.append( new PyString( "-b" ) );
        state.argv.append( new PyString( backend ) );
        state.argv.append( new PyString( "--out-file=" + output ) );
        state.argv.append( new PyString( input ) );
*/
        String[] argv = { getAsciidoc(), "-b", backend, "--out-file=" + output, input };
        PythonInterpreter.initialize( null, null, argv );
        PythonInterpreter interp = new PythonInterpreter();
        interp.execfile( getAsciidoc() );
    }

    private void executeTransformation( String input, String stylesheet, String output ) throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        try {
            File xmlFile = new File( input );
            File xsltFile = new File( getXsl(stylesheet) );
            File resultFile = new File( output );

            Source xmlSource = new StreamSource( xmlFile );
            Source xsltSource = new StreamSource( xsltFile );
            Result result = new StreamResult( new BufferedOutputStream( new FileOutputStream( resultFile ) ) );

            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer( xsltSource );
            trans.setParameter( "paper.type", "A4" );
            // trans.setParameter( "fop.extensions", "1" ); Not working yet
            /*trans.setParameter( "page.margin.inner", "0in" ); // left & right margin
            trans.setParameter( "page.margin.outer", "0in" );*/
            trans.transform( xmlSource, result );
        } catch ( IOException ex ) {
            Logger.getLogger( AsciidocCompiler.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    private void executeFop( String input, String output ) throws FileNotFoundException, FOPException, TransformerConfigurationException, TransformerException, IOException {
        
        OutputStream out = null;
        try {
            FopFactory fopFactory = FopFactory.newInstance();

            File outputFile = new File( output );
            out = new BufferedOutputStream( new FileOutputStream( outputFile) );

            Fop fop = fopFactory.newFop( MimeConstants.MIME_PDF, out );

            TransformerFactory factory = TransformerFactory.newInstance(); 
            Transformer transformer = factory.newTransformer(); 

            File inputFile = new File( input );
            Source src = new StreamSource( inputFile );
            Result res = new SAXResult( fop.getDefaultHandler() );

            transformer.transform( src, res );
        } finally { 
            out.close(); 
        }
    }
    
    public void execute() throws IOException, TransformerConfigurationException, TransformerException, FileNotFoundException, FOPException {

        AsciidocBackendSingleton backends = AsciidocBackendSingleton.getInstance();
        String backendName = document.getBackend();
        AsciidocBackend backend = backends.getBackend( backendName );

        String path = document.getPath();
        String output = backend.getOutputFile( path );
        //System.out.println( "executeAsciidoc - START - " + Calendar.getInstance().getTimeInMillis() );
        executeAsciidoc( path, backend.getName(), output );

        for ( AsciidocBackendTransformation transformation : backend.getTransformations() ) 
        {
            String input = output;
            String stylesheet = transformation.getXsl();
            output = transformation.getOutputFile( input );

            executeTransformation( input, stylesheet, output );
        }

        if( document.getBackend().equals( "pdf" ) )
        {
            String input = output;
            output = backend.getOutputFilePDF( input );
            
            executeFop( input, output );
        }
        
        FileUtils.moveFileToDirectory( output, document.getOutputPath() );
    }
}
