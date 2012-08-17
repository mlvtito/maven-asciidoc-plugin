/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
