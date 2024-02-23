package com.example.miaplicacionmovil;

import android.os.AsyncTask;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;




import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;
import java.util.ArrayList;


public class FragmentDialogFlow extends AppCompatActivity {
    View layout;
    private static final String TAG = FragmentDialogFlow.class.getSimpleName();

    private static final int REQUEST_CODE_STT = 1;
    private final String uuid = UUID.randomUUID().toString();
    private static final int USER = 10001;
    private static final int BOT = 10002;
    private LinearLayout chatLayout;
    private EditText queryEditText;
    private String et_text_input;
    private  int speakResult = 0;


    // Java V2
    private SessionsClient sessionsClient;
    private SessionName session;

    private TextToSpeech textToSpeechEngine;

    //SPEECH
    private static final int PERMISSION_REQUEST_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogflow);

        // Configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final ScrollView scrollview = findViewById(R.id.chatScrollView);
        scrollview.post(() -> scrollview.fullScroll(ScrollView.FOCUS_DOWN));

        chatLayout = findViewById(R.id.chatLayout);

        layout = findViewById(R.id.dialogflow);

        queryEditText = findViewById(R.id.queryEditText);
        queryEditText.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        sendMessage();
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_AUDIO);
            }
        }
        View fabListening = findViewById(R.id.fb_listening);
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                if (speakResult == TextToSpeech.SUCCESS) {
                    // Para detener la reproducción, puedes llamar a stop() en cualquier momento
                    textToSpeechEngine.stop();
                } else {
                    Log.e("TextToSpeech", "Error during speak operation");
                }
                queryEditText.setText("");
                queryEditText.setHint(getString(R.string.escuchando));
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                queryEditText.setText(data.get(0));
                queryEditText.setHint("");
                sendMessage();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        fabListening.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN){

                    speechRecognizer.startListening(speechRecognizerIntent);
                }

                return false;
            }
        });

        // Inicializa las vistas
        Button btn_tts = findViewById(R.id.btn_tts);

        // Inicializa el motor TextToSpeech
        textToSpeechEngine = new TextToSpeech(this, status -> {
            // Establece el idioma si la inicialización es exitosa
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.setLanguage(new Locale("es", "ES"));
            }
        });


        // Configura el botón para Text-to-Speech
        btn_tts.setOnClickListener(view -> {
            if(et_text_input != null){
                String text = et_text_input.trim();
                if (!text.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        speakResult = textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    } else {
                        speakResult = textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }
                } else {
                    Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Inicializar Chatbot con DialogFlow
        initV2Chatbot();

        // Brillo
        LightBrightness brillo = new LightBrightness(this, getApplicationContext());
        if (GlobalVariables.cambio){
            brillo.setAuto(true);
        }else{
            brillo.setAuto(false);
        }

    }//Final del onCreate

    private void initV2Chatbot() {
        try {
            InputStream stream = getResources().openRawResource(R.raw.bot_dialogflow);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            session = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String msg = queryEditText.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(FragmentDialogFlow.this, "¡Inserta una frase!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");

            // Java V2
            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("es-ES")).build();
            String sessionNameString = session.toString();
            Log.d("Session: ", sessionNameString);
            sessionNameString = sessionsClient.toString();
            Log.d("sessionsClient: ", sessionNameString);
            sessionNameString = queryInput.toString();
            Log.d("Query input: ", sessionNameString);
            RequestJavaV2Task myAsyncTask = new RequestJavaV2Task(FragmentDialogFlow.this, session, sessionsClient, queryInput);

            if (myAsyncTask != null){
                myAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    public void callbackV2(DetectIntentResponse response) {
        if (response != null) {
            // process aiResponse here
            String botReply = response.getQueryResult().getFulfillmentText();
            et_text_input = botReply;
            Log.d(TAG, "V2 Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } else {
            Log.d(TAG, "Bot Reply: Null");
            showTextView("Ha habido algún error de comunicación. Por favor, ¡inténtelo de nuevo!", BOT);
        }
    }

    private void showTextView(String message, int type) {
        FrameLayout layout;
        switch (type) {
            case USER:
                layout = getUserLayout();
                break;
            case BOT:
                layout = getBotLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        layout.requestFocus();
        queryEditText.requestFocus(); // change focus back to edit text to continue typing
    }

    FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(FragmentDialogFlow.this);
        return (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);
    }

    FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(FragmentDialogFlow.this);
        return (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_AUDIO && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, getString(R.string.audio_permission_granted), Toast.LENGTH_SHORT).show();
                onRestart();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeechEngine != null) {
            textToSpeechEngine.shutdown();
        }
        // speechRecognizer.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STT) {
            if (resultCode == FragmentDialogFlow.RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    String recognizedText = result.get(0);
                    et_text_input = recognizedText;
                }
            }
        }
    }
}