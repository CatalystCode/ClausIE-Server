package com.github.catalystcode.clausieserver;

import spark.Request;
import spark.Spark;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.System.getProperty;

public class Main {
    private static final String TMPDIR = getProperty("java.io.tmpdir");

    public static void main(String[] args) {
        ClausIEJsonlProcessor clausIEProcessor = new ClausIEJsonlProcessor();

        Spark.post("/openie/form", "multipart/form-data", (req, res) -> {
            enableMultipart(req);

            try {
                InputStream instream = req.raw().getPart("upload").getInputStream();
                OutputStream outstream = res.raw().getOutputStream();
                clausIEProcessor.process(instream, outstream);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }

            return "";
        });

        Spark.exception(Exception.class, (exception, request, response) -> exception.printStackTrace());
    }

    private static void enableMultipart(Request req) {
        if (req.raw().getAttribute("org.eclipse.jetty.multipartConfig") == null) {
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMPDIR);
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        }
    }
}
