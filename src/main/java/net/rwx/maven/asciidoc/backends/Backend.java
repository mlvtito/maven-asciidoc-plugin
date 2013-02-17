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
package net.rwx.maven.asciidoc.backends;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class Backend {

    public static final String HTML    = "html5";
    public static final String XHTML   = "xhtml11";
    public static final String HTML4   = "html4";
    public static final String SLIDY   = "slidy";
    public static final String DOCBOOK = "docbook";
    public static final String PDF     = "pdf";
    
    private String name;
    private String asciidocExtension;
    private boolean isTransformation;
    private String transformationExtension;
    private String transformationStylesheet;
    private boolean isFopTransformation;
    private File temporaryDirectory;

    public Backend( String name, String asciidocExtension ) throws IOException {
        this( name, asciidocExtension, false, null, null );
    }

    public Backend( String name, String asciidocExtension, boolean isTransformation, String transformationExtension, String transformationStylesheet ) {
        this( name, asciidocExtension, isTransformation, transformationExtension, transformationStylesheet, false );
    }
    
    public Backend( String name, String asciidocExtension, boolean isTransformation, String transformationExtension, String transformationStylesheet, boolean isFop ) {
        this.name = name;
        this.asciidocExtension = asciidocExtension;
        this.isTransformation = isTransformation;
        this.transformationExtension = transformationExtension;
        this.transformationStylesheet = transformationStylesheet;
        this.isFopTransformation = isFop;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAsciidocExtension() {
        return asciidocExtension;
    }

    public void setAsciidocExtension(String asciidocExtension) {
        this.asciidocExtension = asciidocExtension;
    }

    public boolean isIsTransformation() {
        return isTransformation;
    }

    public void setIsTransformation(boolean isTransformation) {
        this.isTransformation = isTransformation;
    }

    public String getTransformationExtension() {
        return transformationExtension;
    }

    public void setTransformationExtension(String transformationExtension) {
        this.transformationExtension = transformationExtension;
    }

    public String getTransformationStylesheet() {
        return transformationStylesheet;
    }

    public void setTransformationStylesheet(String transformationStylesheet) {
        this.transformationStylesheet = transformationStylesheet;
    }

    public boolean isIsFopTransformation() {
        return isFopTransformation;
    }

    public void setIsFopTransformation(boolean isFopTransformation) {
        this.isFopTransformation = isFopTransformation;
    }

    public File getTemporaryDirectory() {
        return temporaryDirectory;
    }

    public void setTemporaryDirectory(File temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }
}
