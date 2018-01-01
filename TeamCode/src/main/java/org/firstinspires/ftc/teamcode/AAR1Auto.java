package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.JewelColor;
import org.firstinspires.ftc.teamcode.DriveControl;

/**
 * Created by mingch on 9/9/17.
 */

@Autonomous(name = "AAR1Auto")
public class AAR1Auto extends LinearOpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private Servo leftServo;
    private Servo rightServo;
    private DcMotor liftMotor;
    public Servo colorArm;
    public LynxI2cColorRangeSensor colorSensor;


    @Override

    public void runOpMode() throws InterruptedException {

        colorArm = hardwareMap.servo.get("ColorArm");
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        liftMotor = hardwareMap.dcMotor.get("liftMotor");

        DriveControl robot = new DriveControl(motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft,
                leftServo, rightServo, liftMotor);

        colorSensor = (LynxI2cColorRangeSensor) hardwareMap.get("ColorSensor0");

        JewelColor jewel = new JewelColor(colorSensor);

        colorArm.setPosition(0.5);

        robot.closeClaw();
        robot.waitFor(3);
        robot.moveLift(1, 0.1);

        waitForStart();

        colorArm.setPosition(0.1);




        while(opModeIsActive()) {

            colorArm.setPosition(0.1);
            char color = jewel.getColor();
            telemetry.addLine(Character.toString(color));
            telemetry.update();

            if(color == 'r'){
                robot.waitFor(1);
                robot.drive(1, 2);
                robot.completeStop();
                robot.waitFor(1);
                colorArm.setPosition(0.6);
                robot.waitFor(1);
                robot.drive(-1, 2);
                break;

            }
            if(color == 'b'){

                robot.waitFor(1);
                robot.drive(-1, 2);
                robot.completeStop();
                robot.waitFor(1);
                colorArm.setPosition(0.6);
                robot.waitFor(1);
                robot.drive(1, 2);
                break;
            }
            if( !((color == 'r') || (color == 'b'))){
                robot.completeStop();


            }
            telemetry.update();


            idle();
        }

        robot.fourWheelTurn(0.5 ,32);
        robot.drive(1,17);
        robot.fourWheelTurn(0.5, 180);
        robot.drive(1, 16);
        robot.openClaw();
        robot.drive(1, 4);

    }
}


