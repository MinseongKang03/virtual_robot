package org.firstinspires.ftc.teamcode.ftc16072;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MecanumDriveOnly extends LinearOpMode {

    private boolean slowMode = false, accelerating = false;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;
    private Servo servoTest;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");
        frontRight = hardwareMap.get(DcMotor.class, "front_right_motor");
        backRight = hardwareMap.get(DcMotor.class, "back_right_motor");
        backLeft = hardwareMap.get(DcMotor.class, "back_left_motor");
        digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        servoTest = hardwareMap.get(Servo.class, "back_servo");

        boolean lastAState = false;
        double acceleratePower = 0.0;

        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double r = Math.hypot(-gamepad1.left_stick_y, gamepad1.left_stick_x);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            double v1 = (r * Math.cos(robotAngle) + rightX) * Math.sqrt(2);
            double v2 = (r * Math.sin(robotAngle) - rightX) * Math.sqrt(2);
            double v3 = (r * Math.sin(robotAngle) + rightX) * Math.sqrt(2);
            double v4 = (r * Math.cos(robotAngle) - rightX) * Math.sqrt(2);


            if (gamepad1.y) {
                accelerating = false;
                acceleratePower = 0.0;
            }

            if (slowMode && !accelerating) {
                frontLeft.setPower(v1 / 2);
                frontRight.setPower(v2 / 2);
                backLeft.setPower(v3 / 2);
                backRight.setPower(v4 / 2);
            } else if (!accelerating) {
                frontLeft.setPower(v1);
                frontLeft.setPower(v2);
                backLeft.setPower(v3);
                backRight.setPower(v4);
            }

            if (gamepad1.x) {
                accelerating = true;
                if (acceleratePower < 0.5) {
                    acceleratePower += 0.001;
                }
                frontRight.setPower(acceleratePower);
                frontRight.setPower(acceleratePower);
                backLeft.setPower(acceleratePower);
                backRight.setPower(acceleratePower);
            }

            if (acceleratePower > 0 && !gamepad1.x) {
                acceleratePower -= 0.001;
                if (acceleratePower <= 0) {
                    acceleratePower = 0;
                    accelerating = false;
                }
                frontRight.setPower(-acceleratePower);
                backRight.setPower(-acceleratePower);
                backLeft.setPower(-acceleratePower);
                frontLeft.setPower(-acceleratePower);
            }

            if (gamepad1.a && !lastAState) {
                slowMode = !slowMode;
            }
            lastAState = gamepad1.a;

            telemetry.update();
        }
    }

    private void composeTelemetry() {
        telemetry.addData("leftFront", round(frontLeft.getPower()));
        telemetry.addData("leftRear", round(backLeft.getPower()));
        telemetry.addData("rightFront", round(frontRight.getPower()));
        telemetry.addData("rightRear", round(backRight.getPower()));
        telemetry.addData("Accelerating", accelerating);
        telemetry.addData("Slow Mode", slowMode);
    }

    private static double round(double value) {
        return round(value, 4);
    }

    private static double round(double value, @SuppressWarnings("SameParameterValue") int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}