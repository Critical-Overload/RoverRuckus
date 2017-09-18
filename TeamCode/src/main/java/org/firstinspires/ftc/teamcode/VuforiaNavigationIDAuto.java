package org.firstinspires.ftc.teamcode;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.Locale;

/*
 * This OpMode was written for the VuforiaDemo Basics video. This demonstrates basic principles of
 * using VuforiaDemo in FTC.
 */
@Autonomous(name = "VuforiaNavIDAuto")
public class VuforiaNavigationIDAuto extends LinearOpMode {
    TextToSpeech ttsobject;
    int results;

    // Variables to be used for later
    private VuforiaLocalizer vuforiaLocalizer;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaTrackables visionTargets;
    private VuforiaTrackable target;
    private VuforiaTrackableDefaultListener listener;

    private OpenGLMatrix lastKnownLocation;
    private OpenGLMatrix phoneLocation;

    private static final String VUFORIA_KEY = "AYo1nTj/////AAAAGVlGOH64pEX6klJ4dgiWacN/HarLpmVYmAwvJJWPDT+ZaIX3WsLgMqh8SMMvxWEvshYEOS94r93yHH4tFN5G8Evbk5GEvlIg4+hsZpP/QcuFNkWBIMNWer1zoQvcIkUexm3YDAMAQEp4B/2wFBWh8uBYwNZhDAAgw3YWi67+r8czfzan6Ict/Ooq/8Qfuic2kwwrVoMGq9fdRf+oevi3oLk4/A19fT+talVbmIMz7Zo1pr3cbJulXVEWuBTJ/xkofJQrODDjQZIGqF2qJVNWxoTHjCQaJN2Ya15eEwB1Vriq0Z/yvSP6bJqj0TMeahvYv8jvNAPAmXGUBAOs2USz3P35l83ISk1Upm3U9VYkxLDf\n"; // Insert your own key here

    private float robotX = 0;
    private float robotY = 0;
    private float robotAngle = 0;

   // public class ID_DATA_TYPE extends

    public void runOpMode() throws InterruptedException {
        setupVuforia();
        // We don't know where the robot is, so set it to the origin
        // If we don't include this, it would be null, which would cause errors later on
        lastKnownLocation = createMatrix(0, 0, 0, 0, 0, 0);

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

        // Start tracking the targets
        visionTargets.activate();

        int getDataType;

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(target);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
                telemetry.addData("VuMark", "%s visible", vuMark);

                ttsobject.speak(vuMark.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                sleep(1000);


                // Ask the listener for the latest information on where the robot is
                OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    lastKnownLocation = latestLocation;

                float[] coordinates = lastKnownLocation.getTranslation().getData();

                robotX = coordinates[0];
                robotY = coordinates[1];
                robotAngle = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + target.getName(), listener.isVisible());
                telemetry.addData("Last Known Location", formatMatrix(lastKnownLocation));


                // Send telemetry and idle to let hardware catch up
                telemetry.update();
                idle();
            }
        }
    }

    private void setupVuforia()
    {
        // Setup parameters to create localizer
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId); // To remove the camera view from the screen, remove the R.id.cameraMonitorViewId
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        // These are the vision targets that we want to use
        // The string needs to be the name of the appropriate .xml file in the assets folder
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("RelicVuMark");
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        // Setup the target to be tracked
        target = visionTargets.get(0); // 0 corresponds to the wheels target
        target.setName("RelicRecovery");
        target.setLocation(createMatrix(0, 500, 0, 90, 0, 90));

        // Set phone location on robot
        phoneLocation = createMatrix(0, 225, 0, 90, 0, 0);

        // Setup listener and inform it of phone information
        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    // Creates a matrix for determining the locations and orientations of objects
    // Units are millimeters for x, y, and z, and degrees for u, v, and w
    private OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w)
    {
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }

    // Formats a matrix into a readable string
    private String formatMatrix(OpenGLMatrix matrix)
    {
        return matrix.formatAsTransform();
    }


    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
