package com.github.catalystcode.clausieserver;

import de.mpii.clausie.ClausIE;
import de.mpii.clausie.Proposition;

import java.util.concurrent.atomic.AtomicInteger;

class ClausIEFormatter {
    private final ClausIE clausIE;
    private final AtomicInteger lineCounter;

    ClausIEFormatter(ClausIE clausIE) {
        this.clausIE = clausIE;
        this.lineCounter = new AtomicInteger();
    }

    public String format(String sentence) {
        int lineNumber = lineCounter.getAndIncrement();

        if (sentence == null || sentence.isEmpty()) {
            return "";
        }

        clausIE.parse(sentence);
        clausIE.detectClauses();
        clausIE.generatePropositions();

        StringBuilder output = new StringBuilder();
        for (Proposition proposition : clausIE.getPropositions()) {
            int numArguments = proposition.noArguments();
            if (numArguments == 0) {
                continue;
            }

            output.append('{');
            output.append("\"line\":");
            output.append(lineNumber);
            output.append(',');
            output.append("\"subject\":\"");
            output.append(toJsonString(proposition.subject()));
            output.append('"');
            output.append(',');
            output.append("\"relation\":\"");
            output.append(toJsonString(proposition.relation()));
            output.append('"');
            output.append(',');
            output.append("\"argument\":\"");
            output.append(toJsonString(proposition.argument(0)));
            output.append('"');
            if (numArguments > 1) {
                output.append(',');
                output.append("\"extraArguments\":[");
                for (int i = 1; i < numArguments; i++) {
                    output.append('"');
                    output.append(toJsonString(proposition.argument(i)));
                    output.append('"');
                    output.append(',');
                }
                removeLastCharacter(output);
                output.append(']');
            }
            output.append("}\n");
        }
        removeLastCharacter(output);

        return output.toString();
    }

    private static void removeLastCharacter(StringBuilder sb) {
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
    }

    private static String toJsonString(String input) {
        return input.replaceAll("\"", "\\\"");
    }
}
