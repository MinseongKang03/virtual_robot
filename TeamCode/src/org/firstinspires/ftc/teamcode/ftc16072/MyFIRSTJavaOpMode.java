package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor motorTest;
    private DistanceSensor sensorColorRange;
    private Servo servoTest;


    @Override
    public void runOpMode() {
        motorTest = hardwareMap.get(DcMotor.class, "front_left_motor");
        sensorColorRange = hardwareMap.get(DistanceSensor.class, "front_distance");
        servoTest = hardwareMap.get(Servo.class, "back_servo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        double tgtPower = 0;

        while (opModeIsActive()) {
            //for DC motor
            tgtPower = -this.gamepad1.left_stick_y;
            motorTest.setPower(tgtPower);
            //check to see if we have to move the servo motor
            if (gamepad1.y) {
                servoTest.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                servoTest.setPosition(0.5);
            } else if (gamepad1.a) {
                servoTest.setPosition(1);
            }

            telemetry.addData("Servo Position", servoTest.getPosition());
            telemetry.addData("Target Power", tgtPower);
            telemetry.addData("Motor Power", motorTest.getPower());

            //checking if button is pressed

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
