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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>
 * 
 * Copyright 2012 Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
package net.rwx.maven.asciidoc.backends;

import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocBackendTransformation {

    private String extension;
    private String xsl;

    public AsciidocBackendTransformation( String extension, String xsl ) {
        
        this.extension = extension;
        this.xsl = xsl;
    }
    
    public String getOutputFile( String inputName ) throws IOException {

        String atomicName = FilenameUtils.removeExtension( inputName );
        return net.rwx.maven.asciidoc.utils.FileUtils.getAsciidocTemporaryPath( atomicName, extension );
    }

    
    public String getExtension() {
        return extension;
    }

    public void setExtension( String extension ) {
        this.extension = extension;
    }

    public String getXsl() {
        return xsl;
    }

    public void setXsl( String xsl ) {
        this.xsl = xsl;
    }
}
