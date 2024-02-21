package frc.robot;

import java.time.Instant;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final XboxController driver = new XboxController(0);
    private final Joystick operator = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton roller = new JoystickButton(operator, 1);
    //private final JoystickButton limelightTrack = new JoystickButton(operator, 4);
    private final JoystickButton open = new JoystickButton(operator, 5);
    private final JoystickButton close = new JoystickButton(operator, 3);
    // private final JoystickButton openMan = new JoystickButton(operator, 11);
    // private final JoystickButton closeMan = new JoystickButton(operator, 12);
    private final JoystickButton runConveyor = new JoystickButton(operator, 10);
    private final JoystickButton reverseConveyor = new JoystickButton(operator, 9);
    private final JoystickButton intake = new JoystickButton(operator, 11);
    private final JoystickButton reverseintake = new JoystickButton(operator, 12);


    private final JoystickButton pickupNote = new JoystickButton(operator, 7);
    



    private final JoystickButton trapButton = new JoystickButton(operator, 8);
    
    //private final JoystickButton stop = new JoystickButton(driver, 9);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final shooter s_Shooter = new shooter();
    private final limelight s_Limelight = new limelight();
    private final Arm s_Arm = new Arm();
    private final intake s_Intake = new intake();
    private final Conveyor s_Conveyor = new Conveyor();

    private SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(

            new TeleopSwerve(
                s_Swerve,
                () -> driver.getRawAxis(translationAxis), 
                () -> driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();

        autoChooser = AutoBuilder.buildAutoChooser();
    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));

        roller.whileTrue(new StartEndCommand(() -> s_Shooter.setSpeed(Constants.Shooter.LRollerSpeed,Constants.Shooter.RRollerSpeed),
                                            () -> s_Shooter.stop()));

        
        //TODO NEED TO TEST!
       pickupNote.whileTrue(new pickup(s_Intake, s_Arm, s_Conveyor)).whileFalse(new stopPickup(s_Intake, s_Arm, s_Conveyor));


        //roller.whileFalse(new InstantCommand(() -> s_roller.setSpeed(0,0)));

        // trapButton.whileTrue(new InstantCommand(() -> s_roller.setSpeed(0.45,0.45)));
        // trapButton.whileFalse(new InstantCommand(() -> s_roller.setSpeed(0,0)));
        
        trapButton.whileTrue(new StartEndCommand(() -> s_Shooter.setSpeed(Constants.Shooter.LTrapRollerSpeed,Constants.Shooter.RTrapRollerSpeed),
                                            () -> s_Shooter.stop()));

        //ORIGINAL FOR POT ARM LIMIT
        // open.and(new Trigger(s_Arm::upperLimitHit).negate()).
        // whileTrue(new StartEndCommand(() -> s_Arm.setArmSpeed(-0.4), //Negative is moving the arm up
        //             () -> s_Arm.setArmSpeed(0),s_Arm));
        
        // close.and(new Trigger(s_Arm::lowerLimitHit).negate()).
        // whileTrue(new StartEndCommand(() -> s_Arm.setArmSpeed(0.4), //Positive is moving the arm down
        //             () -> s_Arm.setArmSpeed(0),s_Arm));

        //NEED TO TEST
        open.and(new Trigger(s_Arm::upperLimitHit).negate()).
        whileTrue(new StartEndCommand(() -> s_Arm.moveArmUp(Constants.Arm.ManualMotorSpeed), //Negative is moving the arm up
                    () -> s_Arm.stop(),s_Arm));
        
        close.and(new Trigger(s_Arm::lowerLimitHit).negate()).
        whileTrue(new StartEndCommand(() -> s_Arm.moveArmDown(Constants.Arm.ManualMotorSpeed), //Positive is moving the arm down
                    () -> s_Arm.stop(),s_Arm));

        // closeMan.whileTrue(new InstantCommand(() -> s_Arm.setArmSpeed(-0.4)));
        // closeMan.whileFalse(new InstantCommand(() -> s_Arm.setArmSpeed(0)));

        // openMan.whileTrue(new InstantCommand(() -> s_Arm.setArmSpeed(0.4)));
        // openMan.whileFalse(new InstantCommand(() -> s_Arm.setArmSpeed(0)));
        
        
        runConveyor.whileTrue(new StartEndCommand(() -> s_Conveyor.runConveyor(),
                                                    () -> s_Conveyor.stopConveyor()));
        //runConveyor.whileFalse(new InstantCommand(() -> s_Gullet.stopConveyor()));
        
        reverseConveyor.whileTrue(new StartEndCommand(() -> s_Conveyor.reverseConveyor(),
                                                            () -> s_Conveyor.stopConveyor()));
        //reverseConveyor.whileFalse(new InstantCommand(() -> s_Gullet.stopConveyor()));


        //runConveyor.toggleOnTrue(new InstantCommand(() -> s_Gullet.runConveyor()));
        // ADD REVERSE FOR INTAKE
        intake.whileTrue(new StartEndCommand(() -> s_Intake.setIntakeSpeed(Constants.Intake.LIntakeMotorSpeed,Constants.Intake.RIntakeMotorSpeed),
                                            () -> s_Intake.stop())); // Speed for Intake has to be set to 0.35 percent with net and mesh on top - Montagna
        //intake.whileFalse(new InstantCommand(() -> s_Intake.setIntakeSpeed(0,0)));

        reverseintake.whileTrue(new StartEndCommand(() -> s_Intake.setIntakeSpeed(-Constants.Intake.LIntakeMotorSpeed,-Constants.Intake.RIntakeMotorSpeed),
                                                    () -> s_Intake.stop()));
        //reverseintake.whileFalse(new InstantCommand(() -> s_Intake.setIntakeSpeed(0,0)));

        


        //roller.toggleOnTrue(Commands.startEnd(s_roller::setSpeed(1), s_roller::stop(), s_roller));





    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return autoChooser.getSelected();
    }
}
