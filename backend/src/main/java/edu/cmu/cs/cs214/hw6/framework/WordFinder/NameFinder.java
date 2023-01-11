package edu.cmu.cs.cs214.hw6.framework.WordFinder;

public class NameFinder extends WordFinder{
    @Override
    protected String getModelPath() {
        return "/en-ner-person.bin";
    }
    
}
