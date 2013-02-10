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
package net.rwx.maven.asciidoc.services;

import net.rwx.maven.asciidoc.configuration.Document;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public interface ServiceOrchestrator {
    
    void setLogger( Log logger );
    
    void execute( Document document );
    
}
