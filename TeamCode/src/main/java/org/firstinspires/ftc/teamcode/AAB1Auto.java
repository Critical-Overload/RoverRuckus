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

@Autonomous(name = "AAB1Auto")
public class AAB1Auto extends LinearOpMode {
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

        colorArm.setPosition(0.6);


        robot.closeClaw();
        robot.wait(5);
        robot.liftMovement(1, 0.1);

        waitForStart();

        
        while(opModeIsActive()) {
            
            char color = jewel.getColor();

            if(color == 'b'){
                robot.wait(1);
                robot.drive(-1, 0.5);
                robot.completeStop();
                robot.wait(1);
                colorArm.setPosition(0.6);
                robot.wait(1);
                robot.drive(1, 0.5);
                break;

            }
            if(color == 'r'){
                
                robot.wait(1);
                robot.drive(1, 0.5);
                robot.completeStop();
                robot.wait(1);
                colorArm.setPosition(0.6);
                robot.wait(1);
                robot.drive(-1, 0.5);
                break;

            }
            if( !((color == 'r') || (color == 'b'))){
                robot.completeStop();


            }
            telemetry.update();


            idle();
        }

        robot.fourWheelTurn(0.5 ,30);
        robot.drive(1,36);

    }

    
