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

/**
 * This service handle Asciidoc transformation.
 * 
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public interface AsciidocService extends RootService {
    
    /** Asciidoc version in use by the plugin. */
    static final String VERSION = "8.6.7";
    
    /** Asciidoc archive name. */
    static final String ARCHIVE_NAME = "asciidoc-" + VERSION + ".tar.gz";
    
    /**
     * Launch Asciidoc script.
     * 
     * @param input Path to Asciidoc document to process
     * @param backend Determine output format for Asciidoc script
     * @param output Path to output file (file format should match backend)
     */
    void execute( String input, String backend, String output );
}
