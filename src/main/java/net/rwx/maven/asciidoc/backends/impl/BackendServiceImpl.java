/*
 * Copyright (C) 2013 Room Work eXperience
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.rwx.maven.asciidoc.backends.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.rwx.maven.asciidoc.backends.Backend;
import net.rwx.maven.asciidoc.backends.BackendService;
import net.rwx.maven.asciidoc.constants.Extension;

/**
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class BackendServiceImpl implements BackendService {

    private Map<String, Backend> map;
    
    public BackendServiceImpl() throws IOException {
        map = new HashMap<String, Backend>();

        // only asciidoc compilation
        map.put( Backend.HTML,    new Backend( Backend.HTML,    Extension.HTML ) );
        map.put( Backend.XHTML,   new Backend( Backend.XHTML,   Extension.HTML ) );
        map.put( Backend.HTML4,   new Backend( Backend.HTML4,   Extension.HTML ) );
        map.put( Backend.SLIDY,   new Backend( Backend.SLIDY,   Extension.HTML ) );
        map.put( Backend.DOCBOOK, new Backend( Backend.DOCBOOK, Extension.XML ) );
        
        // asciidoc compilation with docbook transformations
        map.put( Backend.PDF, new Backend( Backend.DOCBOOK, Extension.XML, true, Extension.FO, "fo/docbook.xsl", true ) );
    }

    @Override
    public Backend getBackend( String name ) {
        return map.get( name );
    }
}
