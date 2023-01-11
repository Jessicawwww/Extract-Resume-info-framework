package edu.cmu.cs.cs214.hw6.framework.WordFinder;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public abstract class WordFinder {

    public List<String> findToken(String paragraph) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(this.getModelPath());
        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
        NameFinderME nameFinder = new NameFinderME(model);
        String p = paragraph.replace("]", "").replace("[", "");
        String[] tokens = tokenize(p);

        Span[] nameSpans = nameFinder.find(tokens);
        List<String> res = new ArrayList<>();
        for(Span s: nameSpans) {
            res.add(tokens[s.getStart()]);
        }
        return res;
    }

    public String[] tokenize(String sentence) throws IOException{
        InputStream inputStreamTokenizer = getClass().getResourceAsStream("/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
        TokenizerME tokenizer = new TokenizerME(tokenModel);
        return tokenizer.tokenize(sentence);
    }

    abstract String getModelPath();
}
