package ch.bbcag.NFController.Features;

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

                //noinspection deprecation
                tts.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
            }
        });
        //noinspection deprecation
        tts.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
    }
}
