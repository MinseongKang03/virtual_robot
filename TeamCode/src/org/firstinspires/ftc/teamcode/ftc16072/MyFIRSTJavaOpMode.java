package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private Servo servoTest;


    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");
        frontRight = hardwareMap.get(DcMotor.class, "front_right_motor");
        servoTest = hardwareMap.get(Servo.class, "back_servo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        double leftY = 0;
        double leftX = 0;

        while (opModeIsActive()) {
            //for Dc motor
            leftY = -this.gamepad1.left_stick_y;
            leftX = this.gamepad1.left_stick_x;
            frontLeft.setPower(leftY + leftX);
            frontRight.setPower(leftY - leftX);

            //check to see if we have to move the servo motor
            if (gamepad1.y) {
                servoTest.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                servoTest.setPosition(0.5);
            } else if (gamepad1.a) {
                servoTest.setPosition(1);
            }
            telemetry.addData("Servo Position", servoTest.getPosition());
            telemetry.addData("Front Left Motor Power", frontLeft.getPower());
            telemetry.addData("Front Right Motor Power", frontRight.getPower());
            telemetry.addData("left_stick_y", gamepad1.left_stick_y);
            telemetry.addData("left_stick_x", gamepad1.left_stick_x);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
