package com.example.dashboard.service;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class TextProcessingService {

    private SentenceDetectorME sentenceDetector;

    public TextProcessingService() throws Exception {
        InputStream modelIn = getClass().getResourceAsStream("/en-sent.bin");
        SentenceModel model = new SentenceModel(modelIn);
        sentenceDetector = new SentenceDetectorME(model);
    }

    public String[] detectSentences(String text) {
        return sentenceDetector.sentDetect(text);
    }
}
