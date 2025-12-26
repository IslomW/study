package com.sharom.controller;

import com.sharom.dto.*;
import com.sharom.entity.ExamplePair;
import com.sharom.entity.VocabularyWord;
import com.sharom.entity.VocabularyWordTranslation;
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
        VocabularyWord word = vocabularyService.createWord(req.word(), req.posType(), req.levelId());
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
    @Path("/{wordId}/translations")
    public Response addTranslation(@PathParam("wordId") Long wordId, CreateTranslationRequest req) {
        VocabularyWordTranslation translation = vocabularyService.addTranslation(wordId, req.lang(), req.translation());
        TranslationResponse resp = new TranslationResponse(translation.getId(), translation.getLang(), translation.getTranslation());
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }

    @PUT
    @Path("/translations/{translationId}")
    public Response updateTranslation(@PathParam("translationId") Long translationId, UpdateTranslationRequest req) {
        VocabularyWordTranslation translation = vocabularyService.updateTranslation(translationId, req.translation());
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
    @Path("/translations/{translationId}/examples")
    public Response addExample(@PathParam("translationId") Long translationId, CreateExampleRequest req) {
        ExamplePair example = vocabularyService.addExample(translationId, req.text());
        return Response.status(Response.Status.CREATED).entity(example).build();
    }

    @PUT
    @Path("/examples/{exampleId}")
    public Response updateExample(@PathParam("exampleId") Long exampleId, UpdateExampleRequest req) {
        ExamplePair example = vocabularyService.updateExample(exampleId, req.text());
        return Response.ok(example).build();
    }

    @DELETE
    @Path("/examples/{exampleId}")
    public Response deleteExample(@PathParam("exampleId") Long exampleId) {
        vocabularyService.deleteExample(exampleId);
        return Response.noContent().build();
    }
}
