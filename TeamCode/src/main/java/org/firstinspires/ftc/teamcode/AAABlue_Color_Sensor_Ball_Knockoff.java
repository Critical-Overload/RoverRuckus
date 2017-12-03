package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by mingch on 9/9/17.
 */

@Autonomous(name = "AARBlueColorSensorKnockoff")
public class AAABlue_Color_Sensor_Ball_Knockoff extends LinearOpMode
{
    public LynxI2cColorRangeSensor colorSensor;
    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;
    public Servo ColorArm;

    @Override
    public void runOpMode () throws InterruptedException
    {
        ColorArm = hardwareMap.servo.get("ColorArm");

        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight= hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        colorSensor = (LynxI2cColorRangeSensor) hardwareMap.get("ColorSensor0");
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        int x = 2;

        ColorArm.setPosition(0.6);

        waitForStart();

        ColorArm.setPosition(0.1);


        while(opModeIsActive())
        {


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


    }
    public void driveForward(double power, double inches) {
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
    public void completeStop () {
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }


}
