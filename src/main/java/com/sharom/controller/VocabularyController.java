package com.sharom.controller;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.VocabularyWord;
import com.sharom.service.VocabularyService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/vocabulary")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VocabularyController {


    @Inject
    VocabularyService vocabularyService;

    // =========================
    // CREATE WORD
    // =========================
    @POST
    @Transactional
    public Response addWord(VocabularyWord word) {
        VocabularyWord saved = vocabularyService.addWord(word);
        return Response.ok(saved).build();
    }

    // =========================
    // ADD EXAMPLES TO WORD
    // =========================
    @POST
    @Path("/{id}/examples")
    @Transactional
    public Response addSentences(@PathParam("id") Long wordId, List<ExamplePair> sentences) {
        VocabularyWord updated = vocabularyService.addSentenceById(wordId, sentences);
        return Response.ok(updated).build();
    }

    // =========================
    // UPDATE WORD
    // =========================
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateWord(@PathParam("id") Long id, VocabularyWord word) {
        VocabularyWord updated = vocabularyService.updateWord(id, word);
        return Response.ok(updated).build();
    }

    // =========================
    // GET WORD BY ID
    // =========================
    @GET
    @Path("/{id}")
    public Response getWordById(@PathParam("id") Long id) {
        VocabularyWord word = vocabularyService.getWordById(id);
        return Response.ok(word).build();
    }

    // =========================
    // GET ALL WORDS WITH FILTER & PAGINATION
    // =========================
    @GET
    public Response getAllWords(
            @QueryParam("level") int level,
            @QueryParam("posType") String  posType,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("sortBy") @DefaultValue("id") String sortBy
    ) {

        List<VocabularyWord> words = vocabularyService.getAllWordsFiltered(level, posType, page, size, sortBy);
        return Response.ok(words).build();
    }

    // =========================
    // GET RANDOM WORD BY LEVEL
    // =========================
    @GET
    @Path("/random")
    public Response getRandomWord(@QueryParam("level") int level) {
        VocabularyWord word = vocabularyService.getRandomWordByLevel(level);
        return word != null ? Response.ok(word).build() : Response.noContent().build();
    }

    // =========================
    // DELETE EXAMPLE
    // =========================
    @DELETE
    @Path("/examples/{exampleId}")
    @Transactional
    public Response deleteExample(@PathParam("exampleId") Long exampleId) {
        vocabularyService.deleteExampleSentence(exampleId);
        return Response.noContent().build();
    }

    // =========================
    // DELETE WORD
    // =========================
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteWord(@PathParam("id") Long id) {
        vocabularyService.deleteWord(id);
        return Response.noContent().build();
    }
}
