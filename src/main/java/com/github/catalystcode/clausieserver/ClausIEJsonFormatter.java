package com.github.catalystcode.clausieserver;

import org.json.JSONObject;

import java.util.Collection;

class ClausIEJsonFormatter {
    public String format(ClausIERelation relation) {
        JSONObject json = new JSONObject()
            .put("line", relation.getLine())
            .put("subject", relation.getSubject())
            .put("relation", relation.getRelation())
            .put("argument", relation.getArgument());

        Collection<String> extraArguments = relation.getExtraArguments();
        if (extraArguments.size() > 0) {
            json.put("extraArguments", extraArguments);
        }

        return json.toString();
    }
}
