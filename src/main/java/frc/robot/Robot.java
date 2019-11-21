
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;


public class Robot extends TimedRobot
{
    private WPI_VictorSPX leftFront;
    private WPI_VictorSPX leftBack;
    private WPI_VictorSPX rightFront;
    private WPI_VictorSPX rightBack;
    private Joystick joystick;
    private DifferentialDrive drive;
    private StringBuilder sb = new StringBuilder();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
    private int counter = 0;

    @Override
    public void robotInit(){
        leftFront = new WPI_VictorSPX(0);
        leftBack = new WPI_VictorSPX(1);
        rightFront = new WPI_VictorSPX(2);
        rightBack = new WPI_VictorSPX(3);
        joystick = new Joystick(0);
        drive = new DifferentialDrive(leftFront, rightFront);

        leftFront.configFactoryDefault();
        leftBack.configFactoryDefault();
        rightFront.configFactoryDefault();
        rightBack.configFactoryDefault();

        leftBack.follow(leftFront);
        rightBack.follow(rightFront);

        Shuffleboard.getTab("Drivetrain").add(drive);
        File file = new File(Robot.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        sb.append("Code Last Deployed: ").append(sdf.format(file.lastModified()));
        sb.append("\nLeftFront firmware: ").append(leftFront.getFirmwareVersion());
        sb.append("\nLeftBack firmware: ").append(leftBack.getFirmwareVersion());
        sb.append("\nRightFront firmware: ").append(rightFront.getFirmwareVersion());
        sb.append("\nRightBack firmware: ").append(rightBack.getFirmwareVersion());

        System.out.println(sb + "-----");
        sb.setLength(0);
    }
    @Override
    public void autonomousPeriodic(){
        teleopPeriodic();
    }

    @Override
    public void teleopPeriodic(){
        drive.arcadeDrive(-joystick.getY(), joystick.getX());
        counter++;
        if (counter >= 10){
            printStatistics();
            counter = 0;
        }
    }
    private void printStatistics(){
        sb.append("TimeStamp: ").append(sdf.format(Instant.now()));
        sb.append("\nLeftFront: ").append(leftFront.get());
        sb.append("\nLeftBack: ").append(leftBack.get());
        sb.append("\nRightFront: ").append(rightFront.get());
        sb.append("\nRightBack: ").append(rightBack.get());
        sb.append("\n-----");
        System.out.println(sb);
        sb.setLength(0);
    }
}