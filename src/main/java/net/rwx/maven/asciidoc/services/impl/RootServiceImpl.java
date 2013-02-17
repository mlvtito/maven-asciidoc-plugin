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
package net.rwx.maven.asciidoc.services.impl;

import net.rwx.maven.asciidoc.backends.Backend;
import net.rwx.maven.asciidoc.configuration.Document;
import net.rwx.maven.asciidoc.services.RootService;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public abstract class RootServiceImpl implements RootService {

    private Log logger;
    private String outputPath;
    
    @Override
    public void setLogger(Log logger) {
        this.logger = logger;
    }
    
    public Log getLogger() {
        return logger;
    }

    protected abstract void setOuputPath( String inputPath, Backend backend );
    
    protected void setOutputPath( String inputPath, String extension ) {
        StringBuilder builder = new StringBuilder();
        builder.append( FilenameUtils.removeExtension( inputPath ) );
        builder.append( extension );
        
        setOutputPath( builder.toString() );
    }
    
    protected void setOutputPath( String outputPath ) {
        this.outputPath = outputPath;
    }

    @Override
    public String getOuputPath() {
        return outputPath;
    }
}
