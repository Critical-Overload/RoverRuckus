
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import java.lang.annotation.Target;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous
public class AutonomousCombinedTest extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    //For Precious Metal Start

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AeXjUy3/////AAABmbHLSgwTzU5tnItQeMYIKBuJtzLC3Yjgadn90RIg4wpjZJxXPoAwCZmsm+bAMXon60mNlk3UZvSNQaabvijg0UZ+9vB/U+d8CeHrLU4FziM5JseM/zIMAdJoePSg1sli9hlC1LYIPMd6uCYwuS8QZvkhHkBisttfhafsExbOSeIP/a3sBqDAxQ7rm1SIvWxdGAgu2iUrLVMx6affe6GAtcFMRhIVWLj+m6XyRnswbtS/Sh4hNZXoBTJn4py4rhZ4iouKw6SvZvYCokuqxObpBxG8Ni0pLcbtctO5+8xtb47Y4uwkkI9t7L4IUfntsQzaMcd6eMtoPcYIhLKhiydVa8iG8u9aqJCExcdS7BSKZpTf";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    //For Precious Metal End

    /* Declare OpMode members. */
    //declares motors and sensors
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackLeft;
    private DcMotor motorBackRight;
    private DcMotor liftMotor;
    private DcMotor hingeMotor;
    private Servo bucketServo;
    private Servo intakeServo;
    ModernRoboticsI2cGyro   gyro    = null;                    // Additional Gyro device

    static final double     COUNTS_PER_MOTOR_REV    = 288 ;     // REV Motor Encoder
    static final double        COUNTS_PER_MOTOR_TETRIX = 1440;        //Tetrix encoder (for lift)
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);

    // These constants define the desired driving/control characteristics
    // The can/should be tweaked to suite the specific robot drive train.
    static final double     DRIVE_SPEED             = 0.7;     // Nominal speed for better accuracy.
    static final double     TURN_SPEED              = 0.5;     // Nominal half speed for better accuracy.

    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
    static final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable


    @Override
    public void runOpMode() throws InterruptedException {

        //For Precious Metal Start
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        //For Precious Metal End

        /*
         * Initialize the standard drive system variables.
         * The init() method of the hardware class does most of the work here
         */
        //initializes motors and sensors
        motorFrontRight = hardwareMap.dcMotor.get("FR");
        motorFrontLeft = hardwareMap.dcMotor.get("FL");
        motorBackRight = hardwareMap.dcMotor.get("BR");
        motorBackLeft = hardwareMap.dcMotor.get("BL");
        liftMotor = hardwareMap.dcMotor.get("lift");
        hingeMotor = hardwareMap.dcMotor.get("hingeMotor");
        bucketServo = hardwareMap.servo.get("bucketServo");

        //REVERSES these motors for proper driving
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        //initialize gyro
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        // Ensure the robot it stationary, then reset the encoders and calibrate the gyro.
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Send telemetry message to alert driver that we are calibrating;
        telemetry.addData(">", "Calibrating Gyro");    //
        telemetry.update();

        gyro.calibrate();

        // make sure the gyro is calibrated before continuing
        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();
        }

        telemetry.addData(">", "Robot Ready.");    //
        telemetry.update();

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(0);

        // Wait for the game to start (Display Gyro value), and reset gyro before we move..
        while (!isStarted()) {
            telemetry.addData(">", "Robot Heading = %d", gyro.getIntegratedZValue());
            telemetry.update();
        }
        gyro.resetZAxisIntegrator();

        /////Actual autonomous directions (need revising)
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // Put a hold after each turn
        //telemetry.addData("land");
        //telemetry.update();
        //land();                               // land
        telemetry.addData("BWD","10");
        telemetry.update();
        //gyroDrive(DRIVE_SPEED, -30.0, 0.0);    // Drive BWD 10 inches
        /*telemetry.addData("Side","10");
        telemetry.update();
        gyroSide(DRIVE_SPEED, 10.0, 0.0);       // Drive Sideways 10 inches
        telemetry.addData("Turn CCW","45 degrees");
        telemetry.update();
        gyroTurn( TURN_SPEED, -45.0);         // Turn  CCW to -45 Degrees
        telemetry.addData("hold heading","0.5 seconds");
        telemetry.update();
        gyroHold( TURN_SPEED, -45.0, 0.5);    // Hold -45 Deg heading for a 1/2 second
        telemetry.addData("FWD","10");
        telemetry.update();
        gyroDrive(DRIVE_SPEED, 12.0, -45.0);  // Drive FWD 12 inches at 45 degrees
        telemetry.addData("Side","10");
        telemetry.update();
        gyroSide(DRIVE_SPEED,-10.0, 0.0);    // Drive Sideways 10 inches
        */
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    //FPMS
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
    //FPMS

    /**
     *  Method to drive on a fixed compass bearing (angle), based on encoder counts.
     *  Move will stop if either of these conditions occur:
     *  1) Move gets to the desired position
     *  2) Driver stops the opmode running.
     *
     * @param speed      Target speed for forward motion.  Should allow for _/- variance for adjusting heading
     * @param distance   Distance (in inches) to move from current position.  Negative distance means move backwards.
     * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     */
    public void gyroDrive ( double speed,
                            double distance,
                            double angle) {

        int     newLeftTarget;
        int     newRightTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            //For Precious Metal Start

            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }
            //For Precious Metal End

            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH); //could revise to measured figures if necessary
            newLeftTarget = motorFrontLeft.getCurrentPosition() + moveCounts;
            newRightTarget = motorFrontRight.getCurrentPosition() + moveCounts;

            // Set Target and Turn On RUN_TO_POSITION
            motorFrontLeft.setTargetPosition(newLeftTarget);
            motorBackLeft.setTargetPosition(newLeftTarget);
            motorFrontRight.setTargetPosition(newRightTarget);
            motorBackRight.setTargetPosition(newRightTarget);

            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            motorFrontLeft.setPower(speed);
            motorFrontRight.setPower(speed);
            motorBackLeft.setPower(speed);
            motorBackRight.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive()) {


                //For Precious Metal Actual Detection
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X /*&& goldMineralX < silverMineral2X*/) {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    gyroDrive(DRIVE_SPEED, -30.0, 0.0);    // Drive BWD 10 inches

                                } else if (goldMineralX > silverMineral1X /*&& goldMineralX > silverMineral2X*/) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    gyroTurn( TURN_SPEED, 45.0);         // Turn  CCW to -45 Degrees
                                    gyroDrive(DRIVE_SPEED, -30.0, 0.0);    // Drive BWD 10 inches


                                } else {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    gyroTurn( TURN_SPEED, -45.0);         // Turn  CCW to -45 Degrees
                                    gyroDrive(DRIVE_SPEED, -30.0, 0.0);    // Drive BWD 10 inches
                                }
                            }
                        }
                        telemetry.update();
                    }
                }
                //For Precious Metal End

                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                motorFrontLeft.setPower(leftSpeed);
                motorBackLeft.setPower(leftSpeed);
                motorFrontRight.setPower(rightSpeed);
                motorBackRight.setPower(rightSpeed);

                // Display drive status for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("Target",  "%7d:%7d",      newLeftTarget,  newRightTarget);
                telemetry.addData("Actual",  "%7d:%7d",      motorFrontLeft.getCurrentPosition(),
                        motorFrontRight.getCurrentPosition());
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            //FPMS
            if (tfod != null) {
                tfod.shutdown();
            }
            //FPME

            // Stop all motion;
            motorFrontRight.setPower(0);
            motorFrontLeft.setPower(0);
            motorBackRight.setPower(0);
            motorBackLeft.setPower(0);

            // Turn off RUN_TO_POSITION
            motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     *  Method to drive SIDEWAYS on a fixed compass bearing (angle), based on encoder counts.
     *  Move will stop if either of these conditions occur:
     *  1) Move gets to the desired position
     *  2) Driver stops the opmode running.
     *
     * @param speed      Target speed for SIDEWAYS motion.  Should allow for _/- variance for adjusting heading
     * @param distance   Distance (in inches) to move from current position.  Negative distance means move backwards.
     * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     */
    public void gyroSide ( double speed,
                           double distance,
                           double angle) {

        int     newFTarget;
        int     newBTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  FSpeed;
        double  BSpeed;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH); //could revise to measured figures if necessary
            newFTarget = motorFrontRight.getCurrentPosition() + moveCounts;
            newBTarget = motorBackRight.getCurrentPosition() - moveCounts; //sign change should let us go sidewats

            // Set Target and Turn On RUN_TO_POSITION
            motorFrontLeft.setTargetPosition(newFTarget);
            motorBackLeft.setTargetPosition(newBTarget);
            motorFrontRight.setTargetPosition(newFTarget);
            motorBackRight.setTargetPosition(newBTarget);

            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            motorFrontLeft.setPower(speed);
            motorFrontRight.setPower(speed);
            motorBackLeft.setPower(speed);
            motorBackRight.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    (motorFrontLeft.isBusy() && motorFrontRight.isBusy())) {

                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                FSpeed = speed - steer;
                BSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(FSpeed), Math.abs(BSpeed));
                if (max > 1.0)
                {
                    FSpeed /= max;
                    BSpeed /= max;
                }

                motorFrontLeft.setPower(FSpeed);
                motorBackLeft.setPower(BSpeed);
                motorFrontRight.setPower(FSpeed);
                motorBackRight.setPower(BSpeed);

                // Display drive status for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("Target",  "%7d:%7d",      newFTarget,  newBTarget);
                telemetry.addData("Actual",  "%7d:%7d",      motorFrontLeft.getCurrentPosition(),
                        motorFrontRight.getCurrentPosition());
                telemetry.addData("Speed",   "%5.2f:%5.2f",  FSpeed, BSpeed);
                telemetry.update();
            }

            // Stop all motion;
            motorFrontRight.setPower(0);
            motorFrontLeft.setPower(0);
            motorBackRight.setPower(0);
            motorBackLeft.setPower(0);

            // Turn off RUN_TO_POSITION
            motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     *  Method to spin on central axis to point in a new direction.
     *  Move will stop if either of these conditions occur:
     *  1) Move gets to the heading (angle)
     *  2) Driver stops the opmode running.
     *
     * @param speed Desired speed of turn.
     * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     */
    public void gyroTurn (  double speed, double angle) {

        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
    }

    /**
     *  Method to obtain & hold a heading for a finite amount of time
     *  Move will stop once the requested time has elapsed
     *
     * @param speed      Desired speed of turn.
     * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
     *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                   If a relative angle is required, add/subtract from current heading.
     * @param holdTime   Length of time (in seconds) to hold the specified heading.
     */
    public void gyroHold( double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading(speed, angle, P_TURN_COEFF);
            telemetry.update();
        }

        // Stop all motion;
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);

    }

    /**
     * Perform one cycle of closed loop heading control.
     *
     * @param speed     Desired speed of turn.
     * @param angle     Absolute Angle (in Degrees) relative to last gyro reset.
     *                  0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                  If a relative angle is required, add/subtract from current heading.
     * @param PCoeff    Proportional Gain coefficient
     * @return
     */
    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        motorFrontLeft.setPower(leftSpeed);
        motorBackLeft.setPower(leftSpeed);
        motorFrontRight.setPower(rightSpeed);
        motorBackRight.setPower(rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - gyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    /**
     * lands
     */
    public void land() throws InterruptedException{
        liftMotor.setPower(1);
        liftMotor.setTargetPosition((int)(COUNTS_PER_MOTOR_TETRIX*5.3));
        while (opModeIsActive() && liftMotor.isBusy()){
            Thread.sleep(10);
        }
    }


}
