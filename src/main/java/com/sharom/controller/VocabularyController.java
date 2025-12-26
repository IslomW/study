package com.sharom.controller;

import com.sharom.dto.*;
import com.sharom.entity.Example;
import com.sharom.entity.ExampleTranslation;
import com.sharom.entity.Word;
import com.sharom.entity.WordTranslation;
import com.sharom.service.VocabularyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/vocabulary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VocabularyController {

    @Inject
    VocabularyService vocabularyService;

    // ------------------- Words -------------------

    @POST
    public Response createWord(CreateWordRequest req) {
        Word word = vocabularyService.createWord(req);
        return Response.status(Response.Status.CREATED).entity(word).build();
    }

    @DELETE
    @Path("/{wordId}")
    public Response deleteWord(@PathParam("wordId") Long wordId) {
        vocabularyService.deleteWord(wordId);
        return Response.noContent().build();
    }

    // ------------------- Translations -------------------

    @POST
    @Path("/translations")
    public Response addTranslation(CreateTranslationRequest req) {
        WordTranslation translation = vocabularyService.addTranslation(req);
        TranslationResponse resp = new TranslationResponse(translation.getId(), translation.getLanguage().getName(), translation.getText());
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }

    @PUT
    @Path("/translations/{translationId}")
    public Response updateTranslation(@PathParam("translationId") Long translationId, UpdateTranslationRequest req) {
        WordTranslation translation = vocabularyService.updateTranslation(translationId, req.translation());
        return Response.ok(translation).build();
    }

    @DELETE
    @Path("/translations/{translationId}")
    public Response deleteTranslation(@PathParam("translationId") Long translationId) {
        vocabularyService.deleteTranslation(translationId);
        return Response.noContent().build();
    }

    // ------------------- Examples -------------------

    @POST
    @Path("/example")
    public Response addExample(CreateExampleRequest req) {
        Example example = vocabularyService.addExample(req);
        return Response.status(Response.Status.CREATED).entity(example).build();
    }

    @POST
    @Path("/translation/example")
    public Response addExampleToTranslation(CreateExampleTranslationRequest req) {
        ExampleTranslation exampleTranslation = vocabularyService.addExampleTranslation(req);
        return Response.status(Response.Status.CREATED).entity(exampleTranslation).build();
    }

    @PUT
    @Path("/examples/{exampleId}")
    public Response updateExample(@PathParam("exampleId") Long exampleId, UpdateExampleRequest req) {
        Example example = vocabularyService.updateExample(exampleId, req.text());
        return Response.ok(example).build();
    }

    @DELETE
    @Path("/examples/{exampleId}")
    public Response deleteExample(@PathParam("exampleId") Long exampleId) {
        vocabularyService.deleteExample(exampleId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/examples/translation/{translationId}")
    public Response deleteExampleTranslation(@PathParam("translationId") Long translationId) {
        vocabularyService.deleteExampleTranslation(translationId);
        return Response.noContent().build();
    }
}
