package com.github.catalystcode.clausieserver;

import de.mpii.clausie.ClausIE;
import de.mpii.clausie.Proposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.emptyList;

class ClausIEParser {
    private final ClausIE clausIE;
    private final AtomicInteger lineCounter;

    ClausIEParser(ClausIE clausIE) {
        this.clausIE = clausIE;
        this.lineCounter = new AtomicInteger();
    }

    public Collection<ClausIERelation> parse(String sentence) {
        int lineNumber = lineCounter.getAndIncrement();

        if (sentence == null || sentence.isEmpty()) {
            return emptyList();
        }

        clausIE.parse(sentence);
        clausIE.detectClauses();
        clausIE.generatePropositions();

        List<Proposition> propositions = clausIE.getPropositions();
        List<ClausIERelation> relations = new ArrayList<>(propositions.size());

        for (Proposition proposition : propositions) {
            int numArguments = proposition.noArguments();
            if (numArguments == 0) {
                continue;
            }

            String subject = proposition.subject();
            String relation = proposition.relation();
            String argument = proposition.argument(0);

            List<String> extraArguments = numArguments > 1
                ? new ArrayList<>(numArguments - 1)
                : emptyList();

            for (int i = 1; i < numArguments; i++) {
                extraArguments.add(proposition.argument(i));
            }

            relations.add(new ClausIERelation(lineNumber, subject, relation, argument, extraArguments));
        }

        return relations;
    }
}
