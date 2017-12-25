package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

@Autonomous(name = "B1AutonomousMovement")
public class AAB1Auto extends LinearOpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private Servo leftServo;
    private Servo rightServo;
    private DcMotor liftMotor;
    public Servo ColorArm;
    public LynxI2cColorRangeSensor colorSensor;


    @Override

    public void runOpMode() throws InterruptedException {

        ColorArm = hardwareMap.servo.get("ColorArm");
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        colorSensor = (LynxI2cColorRangeSensor) hardwareMap.get("ColorSensor0");
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        int x = 2;

        ColorArm.setPosition(0.6);


        closeClaw();
        sleep(500);
        liftMovement(1, 100);

        waitForStart();

        while(opModeIsActive()) {
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            int color = colors.toColor();
            telemetry.addLine()
                    .addData("r", "%.3f", colors.red)
                    .addData("g", "%.3f", colors.green)
                    .addData("b", "%.3f", colors.blue);
            float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
            colors.red   /= max;
            colors.green /= max;
            colors.blue  /= max;
            color = colors.toColor();

            telemetry.addLine("normalized color:  ")
                    .addData("r", "%02x", Color.red(color))
                    .addData("g", "%02x", Color.green(color))
                    .addData("b", "%02x", Color.blue(color));

            Color.colorToHSV(colors.toColor(), hsvValues);
            telemetry.addLine()
                    .addData("H", "%.3f", hsvValues[0])
                    .addData("S", "%.3f", hsvValues[1])
                    .addData("V", "%.3f", hsvValues[2]);
            x = Math.round(hsvValues[0]);

            if(180 < x && x < 255){
                telemetry.addLine("Color: Blue");
                sleep(1000);
                driveBackward(1, 0.5);
                completeStop();
                sleep(1000);
                ColorArm.setPosition(0.6);
                sleep(1000);
                driveForward(1, 0.5);
                break;

            }
            if(351 < x){
                telemetry.addLine("Color: Red");
                sleep(1000);
                driveForward(1, 0.5);
                completeStop();
                sleep(1000);
                ColorArm.setPosition(0.6);
                sleep(1000);
                driveBackward(1, 0.5);
                break;

            }
            if( !((180 < x && x < 255) || (351 < x))){
                telemetry.addLine("Color: Null");
                completeStop();


            }
            telemetry.update();


            idle();
        }

        fourWheelTurnClock(0.5 ,30);
        driveForward(1,36);

    }

    public void driveForward(double power, double inches) {
        power = power*0.5;
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        double w = (inches/23)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine(x);
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void fourWheelTurnCClock(double power, double degrees) {
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(power);
        double w = (degrees/180)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine(x);
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void fourWheelTurnClock(double power, double degrees) {
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(-power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(-power);
        double w = (degrees/180)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine(x);
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void driveForwardT(double power, long time){
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        sleep(time);
    }
    public void driveBackward(double power, double inches) {
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(-power);
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(-power);
        double w = (inches/23)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine(x);
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void completeStop(){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }

    public void closeClaw(){
        leftServo.setPosition(1);
        rightServo.setPosition(0);
    }

    public void openClaw(){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.2);
    }
    public void liftMovement(double power, long time){
        liftMotor.setPower(power);
        sleep(time);
        completeStop();
    }
}