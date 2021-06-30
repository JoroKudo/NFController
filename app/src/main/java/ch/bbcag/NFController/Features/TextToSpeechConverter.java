package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.content.Context;

public class TextToSpeechConverter {


    private final Context context;

    private android.speech.tts.TextToSpeech tts;

    public TextToSpeechConverter(Context context) {
        this.context = context;
    }

    public void TextToSpeech(String speech) {
        tts = new android.speech.tts.TextToSpeech(context, status -> {
            if (status == android.speech.tts.TextToSpeech.SUCCESS) {

                tts.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
            }
        });
        tts.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
    }
}
