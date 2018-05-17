package com.github.catalystcode.clausieserver;

import de.mpii.clausie.ClausIE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;

class ClausIEJsonlProcessor {
    private static final byte[] NEWLINE_BYTES = "\n".getBytes(UTF_8);

    private final ClausIE clausIE;
    private final ClausIEJsonFormatter formatter;

    ClausIEJsonlProcessor() {
        this.clausIE = new ClausIE();
        this.clausIE.initParser();
        this.formatter = new ClausIEJsonFormatter();
    }

    void process(InputStream instream, OutputStream outstream) throws IOException {
        ClausIEParser parser = new ClausIEParser(this.clausIE);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(instream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Collection<ClausIERelation> relations = parser.parse(line);
                if (relations != null) {
                    for (ClausIERelation relation : relations) {
                        String json = formatter.format(relation);
                        outstream.write(json.getBytes(UTF_8));
                        outstream.write(NEWLINE_BYTES);
                    }
                }
            }
        }
    }
}
