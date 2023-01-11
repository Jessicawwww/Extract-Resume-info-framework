package edu.cmu.cs.cs214.hw6.framework.WordFinder;

public class OrgFinder extends WordFinder{
    @Override
    protected String getModelPath() {
        return "/en-ner-organization.bin";
    }
    
}