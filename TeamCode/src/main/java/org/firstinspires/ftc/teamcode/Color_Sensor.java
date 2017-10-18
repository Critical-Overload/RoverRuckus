package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;


/**
 * Created by mingch on 9/9/17.
 */

@TeleOp(name = "Color_Sensor")
public class Color_Sensor extends LinearOpMode
{
    public LynxI2cColorRangeSensor colorSensor;

    @Override
    public void runOpMode () throws InterruptedException
    {
        colorSensor = (LynxI2cColorRangeSensor) hardwareMap.get("ColorSensor0");
        float[] hsvValues = new float[3];
        final float values[] = hsvValues;
        int x = 1;

        waitForStart();

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

            }
            if(x < 11 || 351 < x){
                telemetry.addLine("Color: Red");

            }
            if( !((180 < x && x < 255) || (x < 11 || 351 < x))){
                telemetry.addLine("Color: Null");

            }
            telemetry.update();

            idle();
        }
    }

}
