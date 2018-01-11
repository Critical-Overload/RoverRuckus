package org.firstinspires.ftc.teamcode;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.Locale;

/*
Side Project to try to use the Android Text to Speech functionality
 */
@TeleOp(name = "SpeakingJavaOp")
public class SpeakingJavaOp extends LinearOpMode {//implements TextToSpeech.OnInitListener {
    TextToSpeech ttsobject;
    int results;
    double x = 1;

    public class Speaking {
        public void type() {
            while (x == x){
                if (gamepad1.a){
                    ttsobject.speak("is the best person in the world", TextToSpeech.QUEUE_FLUSH, null, null);
                    sleep(600);
                    break;
                }
                if (gamepad1.b){
                    ttsobject.speak("is evil", TextToSpeech.QUEUE_FLUSH, null, null);
                    sleep(600);
                    break;
                }
                if (gamepad1.x){
                    ttsobject.speak("is a human being", TextToSpeech.QUEUE_FLUSH, null, null);
                    sleep(600);
                    break;
                }
                if (gamepad1.y){
                    ttsobject.speak("is irrelevant", TextToSpeech.QUEUE_FLUSH, null, null);
                    sleep(600);
                    break;
                }
            }
        }
    }

    Speaking spkobj = new  Speaking ();
    @Override
        public void runOpMode() throws InterruptedException {

            ttsobject = new TextToSpeech(AppUtil.getInstance().getRootActivity(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        results = ttsobject.setLanguage(Locale.ENGLISH);
                        Log.i("info", "TextToSpeech.SUCCESS");

                    } else {
                        Log.e("error", "This Language is not supported");
                    }
                }
            }); //TextToSpeech

            waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.a) {
                sleep(600);
                while (x == x) {
                if (gamepad1.a) {
                    ttsobject.speak("Mireya", TextToSpeech.QUEUE_FLUSH, null, null);
                    sleep(600);
                    spkobj.type();
                    break;
                    }
                if (gamepad1.b) {
                    ttsobject.speak("Adrian", TextToSpeech.QUEUE_FLUSH, null, null);
                    sleep(600);
                    spkobj.type();

                    break;
                    }
                }

            }
            if (gamepad1.b){
                sleep(600);
                while (x == x) {
                    if (gamepad1.a) {
                        ttsobject.speak("Jonathan", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                    if (gamepad1.b) {
                        ttsobject.speak("Kevin", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                }

            }
            if (gamepad1.x){
                sleep(600);
                while (x == x) {
                    if (gamepad1.a) {
                        ttsobject.speak("Rushabh", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                    if (gamepad1.b) {
                        ttsobject.speak("Elaina", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                }

            }
            if (gamepad1.y){
                sleep(600);
                while (x == x) {
                    if (gamepad1.a) {
                        ttsobject.speak("Ineeka", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                    if (gamepad1.b) {
                        ttsobject.speak("Jolie", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                    if (gamepad1.x) {
                        ttsobject.speak("Grey", TextToSpeech.QUEUE_FLUSH, null, null);
                        sleep(600);
                        spkobj.type();

                        break;
                    }
                }

            }

        }

        }


    }
