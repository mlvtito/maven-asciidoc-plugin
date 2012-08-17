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
