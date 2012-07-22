package net.rwx.maven.asciidoc.backends;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackendSingleton {

    private static AsciidocBackendSingleton instance;
    private Map<String, AsciidocBackend> map;

    private AsciidocBackendSingleton() throws IOException {

        map = new HashMap<String, AsciidocBackend>();

        map.put( "html5", new AsciidocBackend( "html5", ".html" ) );
        map.put( "xhtml11", new AsciidocBackend( "xhtml11", ".html" ) );
        map.put( "html4", new AsciidocBackend( "html4", ".html" ) );
        map.put( "slidy", new AsciidocBackend( "slidy", ".html" ) );
        map.put( "docbook", new AsciidocBackend( "docbook", ".xml" ) );
        
        map.put( "pdf", new AsciidocBackend( "docbook", ".xml", ".fo", "fo.xsl" ) );
    }

    public static AsciidocBackendSingleton getInstance() {

        if ( instance == null ) {
            try {
                instance = new AsciidocBackendSingleton();
            } catch ( IOException ex ) {
                
            }
        }

        return instance;
    }
    
    public AsciidocBackend getBackend( String name ) {
        
        return map.get( name );
    }
}
