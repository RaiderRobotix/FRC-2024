package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
    private final JoystickButton roller = new JoystickButton(driver, XboxController.Button.kB.value);
    //private final JoystickButton limelightTrack = new JoystickButton(operator, 4);
    private final JoystickButton open = new JoystickButton(operator, 5);
    private final JoystickButton close = new JoystickButton(operator, 3);
    // private final JoystickButton openMan = new JoystickButton(operator, 11);
    // private final JoystickButton closeMan = new JoystickButton(operator, 12);
    private final JoystickButton runConveyor = new JoystickButton(operator, 10);
    private final JoystickButton reverseConveyor = new JoystickButton(operator, 9);
    private final JoystickButton intake = new JoystickButton(operator, 11);
    private final JoystickButton reverseintake = new JoystickButton(operator, 12);
    //private final JoystickButton trapButton = new JoystickButton(operator, 5);
    
    //private final JoystickButton stop = new JoystickButton(driver, 9);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    private final shooter s_roller = new shooter();
    private final limelight s_Limelight = new limelight();
    private final arm s_Arm = new arm();
    private final intake s_Intake = new intake();
    private final conveyor s_Gullet = new conveyor();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(

            new TeleopSwerve(
                s_Swerve,
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
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
        roller.whileTrue(new InstantCommand(() -> s_roller.setSpeed(1.0,0.65)));
        roller.whileFalse(new InstantCommand(() -> s_roller.setSpeed(0,0)));

        // trapButton.whileTrue(new InstantCommand(() -> s_roller.setSpeed(0.45,0.45)));
        // trapButton.whileFalse(new InstantCommand(() -> s_roller.setSpeed(0,0)));
        
        open.and(new Trigger(s_Arm::upperLimitHit).negate()).
        whileTrue(new StartEndCommand(() -> s_Arm.setArmSpeed(-0.4),
                    () -> s_Arm.setArmSpeed(0),s_Arm));
        
        close.and(new Trigger(s_Arm::lowerLimitHit).negate()).
        whileTrue(new StartEndCommand(() -> s_Arm.setArmSpeed(0.4),
                    () -> s_Arm.setArmSpeed(0),s_Arm));

        // closeMan.whileTrue(new InstantCommand(() -> s_Arm.setArmSpeed(-0.4)));
        // closeMan.whileFalse(new InstantCommand(() -> s_Arm.setArmSpeed(0)));

        // openMan.whileTrue(new InstantCommand(() -> s_Arm.setArmSpeed(0.4)));
        // openMan.whileFalse(new InstantCommand(() -> s_Arm.setArmSpeed(0)));
        
        
        runConveyor.whileTrue(new InstantCommand(() -> s_Gullet.runConveyor()));
        runConveyor.whileFalse(new InstantCommand(() -> s_Gullet.stopConveyor()));
        
        reverseConveyor.whileTrue(new InstantCommand(() -> s_Gullet.reverseConveyor()));
        reverseConveyor.whileFalse(new InstantCommand(() -> s_Gullet.stopConveyor()));


        //runConveyor.toggleOnTrue(new InstantCommand(() -> s_Gullet.runConveyor()));
        // ADD REVERSE FOR INTAKE
        intake.whileTrue(new InstantCommand(() -> s_Intake.setIntakeSpeed(0.35,0.35))); // Speed for Intake has to be set to 0.35 percent with net and mesh on top - Montagna
        intake.whileFalse(new InstantCommand(() -> s_Intake.setIntakeSpeed(0,0)));

        reverseintake.whileTrue(new InstantCommand(() -> s_Intake.setIntakeSpeed(-0.35,-0.35)));
        reverseintake.whileFalse(new InstantCommand(() -> s_Intake.setIntakeSpeed(0,0)));

        


        //roller.toggleOnTrue(Commands.startEnd(s_roller::setSpeed(1), s_roller::stop(), s_roller));





    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}
