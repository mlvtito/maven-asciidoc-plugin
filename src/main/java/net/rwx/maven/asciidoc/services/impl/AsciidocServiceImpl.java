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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import net.rwx.maven.asciidoc.backends.Backend;
import net.rwx.maven.asciidoc.configuration.Document;
import net.rwx.maven.asciidoc.services.AsciidocService;
import net.rwx.maven.asciidoc.utils.FileUtils;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class AsciidocServiceImpl extends RootServiceImpl implements AsciidocService {

    private String asciidocProgramPath;

    public AsciidocServiceImpl() throws IOException {
        ClassLoader loader = this.getClass().getClassLoader();
        InputStream is = loader.getResourceAsStream(ARCHIVE_NAME);
        String temporaryDirectory = FileUtils.getTemporaryDirectory();
        FileUtils.uncompress(is, temporaryDirectory);
        setAsciidocProgramPath(temporaryDirectory);
    }

    /**
     * Launch Asciidoc script.
     *
     * @param input Path to Asciidoc document to process
     * @param backend Determine output format for Asciidoc script
     * @param output Path to output file (file format should match backend)
     */
    @Override
    public void execute(String inputPath, Document document, Backend backend) throws Exception {
        setOuputPath(inputPath, backend);
        PySystemState state = getPySystemState(inputPath, backend.getName(), getOuputPath());

        PythonInterpreter interp = new PythonInterpreter(null, state);
        interp.set("__file__", asciidocProgramPath);
        interp.execfile(asciidocProgramPath);
    }

    /**
     * Initialize parameters and working dir for Jython interpreter.
     *
     * @param input Path to Asciidoc document to process
     * @param backend Determine output format for Asciidoc script
     * @param output Path to output file (file format should match backend)
     * @return a system state for Jython interpreter
     */
    private PySystemState getPySystemState(String input, String backend, String output) {
        PySystemState state = new PySystemState();

        state.argv.clear();
        state.argv.append(new PyString(asciidocProgramPath));
        state.argv.append(new PyString("-b"));
        state.argv.append(new PyString(backend));
        state.argv.append(new PyString("-a"));
        state.argv.append(new PyString("icons"));
        state.argv.append(new PyString("--out-file=" + output));
        state.argv.append(new PyString(input));

        File fInput = new File(input);
        state.setCurrentWorkingDir(fInput.getParent());

        return state;
    }

    /**
     * Build path to Asciidoc script based on temporary directory.
     */
    private void setAsciidocProgramPath(String temporaryDirectory) {
        String asciidocDir = "asciidoc-" + VERSION;
        asciidocProgramPath = FileUtils.getPath(temporaryDirectory, asciidocDir, "asciidoc.py");
    }

    @Override
    protected void setOuputPath(String inputPath, Backend backend) {
        setOutputPath(inputPath, backend.getAsciidocExtension());
    }
}
