package net.rwx.maven.asciidoc;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackendSingleton {

    private static AsciidocBackendSingleton instance;
    private Map<String, AsciidocBackend> map;

    private AsciidocBackendSingleton() {

        map = new HashMap<String, AsciidocBackend>();

        map.put( "html5", new AsciidocBackend( "html5", ".html" ) );
        map.put( "xhtml11", new AsciidocBackend( "xhtml11", ".html" ) );
        map.put( "html4", new AsciidocBackend( "html4", ".html" ) );
        map.put( "slidy", new AsciidocBackend( "slidy", ".html" ) );
        map.put( "docbook", new AsciidocBackend( "docbook", ".xml" ) );
        
        map.put( "chunked", new AsciidocBackend( "docbook", ".xml", ".html" ) );
        map.put( "pdf", new AsciidocBackend( "docbook", ".xml", ".fo", ".pdf" ) );
    }

    public static AsciidocBackendSingleton getInstance() {

        if ( instance == null ) {
            instance = new AsciidocBackendSingleton();
        }

        return instance;
    }
}
