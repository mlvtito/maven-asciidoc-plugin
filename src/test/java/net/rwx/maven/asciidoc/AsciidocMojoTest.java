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
package net.rwx.maven.asciidoc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.rwx.maven.asciidoc.configuration.Document;
import net.rwx.maven.asciidoc.services.impl.ParentTest;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arnaud Fonce <arnaud.fonce@r-w-x.net>
 */
public class AsciidocMojoTest extends ParentTest {

    String asciidocFilePath;
    String outputPath;

    @Before
    public void setUp() throws Exception {
        String temporaryDirectory = FileUtils.getTemporaryDirectory();
        asciidocFilePath = temporaryDirectory + File.separator + "test.asciidoc";
        outputPath = temporaryDirectory + File.separator + "output";
        writeFileFromResource("test.asciidoc", asciidocFilePath);
    }

    @After
    public void tearDown() {
    }

    private Document getDocument(String path, String title) {
        Document doc = new Document();
        doc.setPath(path);
        doc.setTitle(title);

        return doc;
    }

    private List<Document> getDocuments() {
        List<Document> docs = new ArrayList<Document>();
        docs.add(getDocument("test.asciidoc", "Simple Asciidoc Article"));
        return docs;
    }

    /**
     * Test of execute method, of class AsciidocMojo.
     */
    @Test
    public void testExecute() throws Exception {
        AsciidocMojo mojo = new AsciidocMojo();
        mojo.setDefaultBackend("html5");
        mojo.setDefaultDocumentType("article");
        mojo.setDefaultOutputPath(outputPath);
        mojo.setProjectFile(new File(asciidocFilePath));
        mojo.setDocuments(getDocuments());

        mojo.execute();

        File outputFile = new File(outputPath + File.separator + "test.html");
        assertTrue(outputFile.exists());
        // TODO: add assertion on output content
    }
}