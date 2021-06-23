package ch.bbcag.NFController.Features;

import android.app.Activity;

public class TextToSpeechConverter {

    private final Activity activity;

    private android.speech.tts.TextToSpeech tts;

    public TextToSpeechConverter(Activity activity) {
        this.activity = activity;
    }

    public void TextToSpeech(String speech) {
        tts = new android.speech.tts.TextToSpeech(activity, status -> {
            if (status == android.speech.tts.TextToSpeech.SUCCESS) {

                tts.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
            }
        });
        tts.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
    }
}
