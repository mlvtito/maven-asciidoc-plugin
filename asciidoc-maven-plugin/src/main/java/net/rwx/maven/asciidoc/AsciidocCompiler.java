package net.rwx.maven.asciidoc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocCompiler {

    private Document document;

    public void setDocument( Document document ) {
        
        this.document = document;
    }
    
    private String getAsciidocBackend() {
        
        String backend = document.getBackend();
        if( backend.equals( "pdf" ) ) 
        {
            return "docbook";
        }
        
        return backend;
    }
    
    private String getAsciidocOutput() throws IOException {
        
        File tmpDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
        tmpDir.mkdir();
        tmpDir.deleteOnExit();
        
        String output = document.getOutputPath();
        if( document.getBackend().equals( "pdf" ) )
        {
            return "/tmp/tmpasciidoc";
        }
        
        return output;
    }
    
    private void executeAsciidoc() throws IOException {
        
        String backend = getAsciidocBackend();
        String output = getAsciidocOutput();
        
        PySystemState state = new PySystemState();
        state.argv.append( new PyString( "-b" ) );
        state.argv.append( new PyString( backend ) );
        state.argv.append( new PyString( "--out-file="+output ) );
        

        String path = document.getPath();
        state.argv.append( new PyString( path ) );
        
        PythonInterpreter interp = new PythonInterpreter( null, state );
        
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream( "asciidoc/asciidoc.py" );
        interp.execfile( is );
        is.close();
    }
    
    public void execute() throws IOException {
        
        executeAsciidoc();
    }
}
