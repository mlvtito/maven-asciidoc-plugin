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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.rwx.maven.asciidoc.backends.Backend;
import net.rwx.maven.asciidoc.configuration.Document;
import net.rwx.maven.asciidoc.services.TransformationService;
import net.rwx.maven.asciidoc.utils.FileUtils;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class TransformationServiceImpl extends RootServiceImpl implements TransformationService {

    private String pathToXslFiles;

    public TransformationServiceImpl() throws IOException {
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream(ARCHIVE_NAME);
        pathToXslFiles = FileUtils.getTemporaryDirectory();
        FileUtils.uncompress(is, pathToXslFiles);
    }

    @Override
    public void execute(String inputPath, Document document, Backend backend) throws Exception {
        setOuputPath(inputPath, backend);
        if( backend.isIsTransformation()) {
            File xmlFile = new File(inputPath);
            File xsltFile = new File(getXsl(backend.getTransformationStylesheet()));
            File resultFile = new File(getOuputPath());

            Source xmlSource = new StreamSource(xmlFile);
            Source xsltSource = new StreamSource(xsltFile);
            Result result = new StreamResult(new BufferedOutputStream(new FileOutputStream(resultFile)));

            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer(xsltSource);
            trans.setParameter("paper.type", "A4");

            // TODO: will be used with images management - does not work yet
            /*String imgDir = new File(documentPath).getParentFile().getAbsolutePath();
            trans.setParameter("img.src.path", imgDir + "/");*/
            trans.transform(xmlSource, result);
        }
    }

    private String getXsl(String name) {

        StringBuilder builder = new StringBuilder();
        builder.append(pathToXslFiles);
        builder.append(File.separator);
        builder.append("docbook-xsl");
        builder.append(File.separator);
        builder.append(name);

        return builder.toString();
    }

    @Override
    protected void setOuputPath(String inputPath, Backend backend) {
        if( backend.isIsTransformation() ) {
            setOutputPath( inputPath, backend.getTransformationExtension() );
        }else {
            setOutputPath(inputPath);
        }
    }
}
