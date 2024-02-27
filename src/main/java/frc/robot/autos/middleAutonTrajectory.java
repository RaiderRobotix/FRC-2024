// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.DriveAtSpeed;
import frc.robot.commands.pickup;
import frc.robot.commands.runShooterConveyor;
import frc.robot.commands.setArmPosition;
import frc.robot.commands.stopPickup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class middleAutonTrajectory extends SequentialCommandGroup {
  /** Creates a new middleAutonTrajectory. */
  public middleAutonTrajectory(Swerve s_Swerve, Arm m_Arm, Conveyor m_Conveyor, shooter m_Shooter, intake m_Intake) {

    TrajectoryConfig config =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);

    var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0,0.5, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

    Trajectory toNoteTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(/*new Translation2d(1, 1), new Translation2d(2, -1)*/),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(1.0414, 0, new Rotation2d(0)),
                config);

    SwerveControllerCommand toNoteTrajectoryCmd =
        new SwerveControllerCommand(
            toNoteTrajectory,
            s_Swerve::getPose,
            Constants.Swerve.swerveKinematics,
            new PIDController(Constants.AutoConstants.kPXController, 0, 0),
            new PIDController(Constants.AutoConstants.kPYController, 0, 0),
            thetaController,
            s_Swerve::setModuleStates,
            s_Swerve);

    Trajectory toSpeakerTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(1.0414, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(0, 0, new Rotation2d(0)),
                config);

    SwerveControllerCommand toSpeakerCmd =
        new SwerveControllerCommand(
            toSpeakerTrajectory,
            s_Swerve::getPose,
            Constants.Swerve.swerveKinematics,
            new PIDController(Constants.AutoConstants.kPXController, 0, 0),
            new PIDController(Constants.AutoConstants.kPYController, 0, 0),
            thetaController,
            s_Swerve::setModuleStates,
            s_Swerve);


    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      //new InstantCommand(() -> s_Swerve.setPose(toNoteTrajectory.getInitialPose())),
      //new setArmPosition(m_Arm, 0.108),
      //new runShooterConveyor(m_Conveyor, m_Shooter),
      //new WaitCommand(2),
      //new setArmPosition(m_Arm, 0.05),
      toNoteTrajectoryCmd,
      //new pickup(m_Intake, m_Arm, m_Conveyor),
     // new DriveAtSpeed(m_Swerve, -0.4, 0,0,0.5),
      //new pickup(m_Intake, m_Arm, m_Conveyor),
      //new WaitCommand(4.0),
      toSpeakerCmd
      //new setArmPosition(m_Arm, 0.108),
      //new runShooterConveyor(m_Conveyor, m_Shooter),
      //new stopPickup(m_Intake, m_Arm, m_Conveyor)
    );
  }
}
