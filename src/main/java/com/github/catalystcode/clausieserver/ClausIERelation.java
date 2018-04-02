package com.github.catalystcode.clausieserver;

import java.util.Collection;

class ClausIERelation {
    private final int line;
    private final String subject;
    private final String relation;
    private final String argument;
    private final Collection<String> extraArguments;

    ClausIERelation(int line, String subject, String relation, String argument, Collection<String> extraArguments) {
        this.line = line;
        this.subject = subject;
        this.relation = relation;
        this.argument = argument;
        this.extraArguments = extraArguments;
    }

    public int getLine() {
        return line;
    }

    public String getSubject() {
        return subject;
    }

    public String getRelation() {
        return relation;
    }

    public String getArgument() {
        return argument;
    }

    public Collection<String> getExtraArguments() {
        return extraArguments;
    }
}
