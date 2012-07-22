package net.rwx.maven.asciidoc;

import java.io.*;
import javax.xml.transform.*;
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
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

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
        asciidocFile = FileUtils.uncompress( is );
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
    
    private void executeAsciidoc( String input, String backend, String output ) throws IOException {

        PySystemState state = new PySystemState();
        state.argv.append( new PyString( "-b" ) );
        state.argv.append( new PyString( backend ) );
        state.argv.append( new PyString( "--out-file=" + output ) );
        state.argv.append( new PyString( input ) );

        PythonInterpreter interp = new PythonInterpreter( null, state );

        interp.execfile( getAsciidoc() );
    }

    private void executeTransformation( String input, String stylesheet, String output ) throws TransformerConfigurationException, TransformerException, FileNotFoundException {

        File xmlFile = new File( input );
        File xsltFile = new File( "/etc/asciidoc/docbook-xsl/fo.xsl" );
        File resultFile = new File( output );

        Source xmlSource = new StreamSource( xmlFile );
        Source xsltSource = new StreamSource( xsltFile );
        Result result = new StreamResult( new FileOutputStream( resultFile ) );

        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer( xsltSource );
        trans.transform( xmlSource, result );
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
