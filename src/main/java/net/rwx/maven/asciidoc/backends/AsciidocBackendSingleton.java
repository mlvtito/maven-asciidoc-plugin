/*
 * This file is part of "Maven Asciidoc Plugin".
 * 
 * "Maven Asciidoc Plugin" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "Maven Asciidoc Plugin" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Maven Asciidoc Plugin.  If not, see <http://www.gnu.org/licenses/>
 */
package net.rwx.maven.asciidoc.backends;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackendSingleton {

    public static final String BACKEND_HTML    = "html5";
    public static final String BACKEND_XHTML   = "xhtml11";
    public static final String BACKEND_HTML4   = "html4";
    public static final String BACKEND_SLIDY   = "slidy";
    public static final String BACKEND_DOCBOOK = "docbook";
    public static final String BACKEND_PDF     = "pdf";
    
    public static final String EXTENSION_HTML = ".html";
    public static final String EXTENSION_XML  = ".xml";
    public static final String EXTENSION_PDF  = ".pdf";
    public static final String EXTENSION_FO   = ".fo";
    
    private static AsciidocBackendSingleton instance;
    private Map<String, AsciidocBackend> map;

    private AsciidocBackendSingleton() throws IOException {

        map = new HashMap<String, AsciidocBackend>();

        // only asciidoc compilation
        map.put( BACKEND_HTML,  new AsciidocBackend( BACKEND_HTML,    EXTENSION_HTML ) );
        map.put( BACKEND_XHTML, new AsciidocBackend( BACKEND_XHTML,   EXTENSION_HTML ) );
        map.put( BACKEND_HTML4, new AsciidocBackend( BACKEND_HTML4,   EXTENSION_HTML ) );
        map.put( BACKEND_SLIDY, new AsciidocBackend( BACKEND_SLIDY,   EXTENSION_HTML ) );
        map.put( BACKEND_PDF,   new AsciidocBackend( BACKEND_DOCBOOK, EXTENSION_XML ) );
        
        // asciidoc compilation with docbook transformations
        map.put( EXTENSION_PDF, new AsciidocBackend( BACKEND_DOCBOOK, EXTENSION_XML, EXTENSION_FO, "fo/docbook.xsl" ) );
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
