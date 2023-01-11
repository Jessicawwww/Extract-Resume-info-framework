package edu.cmu.cs.cs214.hw6.framework.WordFinder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TokenFinder extends WordFinder{

    @Override
    protected String getModelPath() {
        return "/en-token.bin";
    }

    @Override 
    public List<String> findToken(String paragraph) throws IOException {
        return Arrays.asList(this.tokenize(paragraph));
    }
    
}
