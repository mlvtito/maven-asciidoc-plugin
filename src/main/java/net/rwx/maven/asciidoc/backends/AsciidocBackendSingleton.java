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

    private static AsciidocBackendSingleton instance;
    private Map<String, AsciidocBackend> map;

    private AsciidocBackendSingleton() throws IOException {

        map = new HashMap<String, AsciidocBackend>();

        map.put( "html5", new AsciidocBackend( "html5", ".html" ) );
        map.put( "xhtml11", new AsciidocBackend( "xhtml11", ".html" ) );
        map.put( "html4", new AsciidocBackend( "html4", ".html" ) );
        map.put( "slidy", new AsciidocBackend( "slidy", ".html" ) );
        map.put( "docbook", new AsciidocBackend( "docbook", ".xml" ) );
        
        map.put( "pdf", new AsciidocBackend( "docbook", ".xml", ".fo", "fo/docbook.xsl" ) );
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
